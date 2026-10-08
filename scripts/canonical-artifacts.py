#!/usr/bin/env python3
"""Retrieve the pinned, already-public Modrinth release bytes; never rebuild."""
import hashlib
import json
from pathlib import Path
import re
import runpy
import urllib.request

ROOT = Path(__file__).resolve().parents[1]
WRAPPER = runpy.run_path(str(ROOT / 'build-smart.py'))


def inventory(root=ROOT):
    config = WRAPPER['read_properties'](root / 'gradle/publishing.properties')
    props = WRAPPER['read_properties'](root / 'gradle.properties')
    data = json.loads((root / config['canonical_artifacts_file']).read_text(encoding='utf-8'))
    if data['mod_version'] != props['mod_version'] or data['modrinth_project_id'] != config['modrinth_project_id']:
        raise ValueError('Canonical artifact pins belong to a different release/project')
    matrices = {p.stem: WRAPPER['matrix_properties'](p) for p in (root / 'gradle/matrix').glob('*.properties')}
    rows = data['artifacts']
    if len(rows) != len(matrices) or {r['target'] for r in rows} != set(matrices):
        raise ValueError('Canonical artifact pins must cover exactly the discovered matrix')
    for row in rows:
        pins = matrices[row['target']]
        filename = f"{props['archives_name']}-{pins['loader']}-{pins['minecraft_version']}-{props['mod_version']}.jar"
        url = f"https://cdn.modrinth.com/data/{data['modrinth_project_id']}/versions/{row['modrinth_version_id']}/{filename}"
        if (row['filename'] != filename or row['url'] != url or row['minecraft_version'] != pins['minecraft_version']
                or row['loader'] != pins['loader'] or row['size'] <= 0):
            raise ValueError('Invalid canonical artifact identity: ' + row['target'])
        for algorithm, length in (('sha256', 64), ('sha512', 128), ('sha1', 40)):
            if not re.fullmatch('[0-9a-f]{' + str(length) + '}', row[algorithm]):
                raise ValueError('Invalid canonical digest: ' + row['target'])
        expected_exception = 68 if data['mod_version'] == '1.2.2' and pins['minecraft_version'] in ('1.21', '1.21.1') else 0
        if row['legacy_json_count'] != expected_exception:
            raise ValueError('Unsupported canonical JSON exception: ' + row['target'])
    resources = data['legacy_json_resources']
    if len(resources) != len(set(resources)) or len(resources) != 68:
        raise ValueError('The documented 1.2.2 JSON exception must list exactly 68 resources')
    return data


def record(target, root=ROOT):
    return next(r for r in inventory(root)['artifacts'] if r['target'] == target)


def verify_bytes(payload, row):
    if len(payload) != row['size'] or any(hashlib.new(a, payload).hexdigest() != row[a] for a in ('sha256', 'sha512', 'sha1')):
        raise ValueError('Canonical artifact hash/size mismatch: ' + row['filename'])


def verify(artifact, target, root=ROOT):
    row = record(target, root)
    if artifact.name != row['filename']:
        raise ValueError('Wrong canonical artifact filename: ' + artifact.name)
    verify_bytes(artifact.read_bytes(), row)
    return row


def request(url):
    return urllib.request.Request(url, headers={'User-Agent': 'Vg34100/FishingFrenzy-release-pipeline'})


def fetch(root=ROOT):
    data = inventory(root)
    url = f"https://api.modrinth.com/v2/project/{data['modrinth_project_id']}/version"
    with urllib.request.urlopen(request(url), timeout=60) as response:
        versions = {v['id']: v for v in json.load(response)}
    # Validate the complete public inventory before writing any local release JAR.
    for row in data['artifacts']:
        version = versions.get(row['modrinth_version_id'], {})
        files = [f for f in version.get('files', []) if f.get('primary')]
        if (version.get('project_id') != data['modrinth_project_id'] or version.get('version_number') != data['mod_version']
                or version.get('game_versions') != [row['minecraft_version']] or version.get('loaders') != [row['loader']]
                or len(files) != 1):
            raise ValueError('Public canonical target changed/missing: ' + row['target'])
        file = files[0]
        if any(file.get(key) != row[key] for key in ('filename', 'url', 'size')) or any(
                file.get('hashes', {}).get(a) != row[a] for a in ('sha1', 'sha512')):
            raise ValueError('Public canonical file changed: ' + row['target'])
    for row in data['artifacts']:
        artifact = root / 'build/libs' / row['target'] / row['filename']
        payload = artifact.read_bytes() if artifact.is_file() else b''
        if hashlib.sha256(payload).hexdigest() != row['sha256'] or len(payload) != row['size']:
            with urllib.request.urlopen(request(row['url']), timeout=60) as response:
                payload = response.read()
            verify_bytes(payload, row)
            artifact.parent.mkdir(parents=True, exist_ok=True)
            temporary = artifact.with_suffix('.jar.download')
            temporary.write_bytes(payload)
            temporary.replace(artifact)
        verify_bytes(artifact.read_bytes(), row)
        print(f"CANONICAL {row['target']}: {row['filename']} | SHA-256 {row['sha256']}")
    print(f"CANONICAL MODRINTH AUDIT PASS ({len(data['artifacts'])}/{len(data['artifacts'])}; zero uploads)")


if __name__ == '__main__':
    fetch()
