#!/usr/bin/env python3
"""Verify Fishing Frenzy's installable matrix JARs, not gameplay behavior."""
import json
from pathlib import Path
import re
import runpy
import struct
from zipfile import BadZipFile, ZipFile

try:
    import tomllib
except ImportError:  # The established wrapper also supports Python 3.10.
    tomllib = None

ROOT = Path(__file__).resolve().parents[1]
WRAPPER = runpy.run_path(str(ROOT / 'build-smart.py'))
MOD_ID = WRAPPER['read_properties'](ROOT / 'gradle/publishing.properties')['mod_id']
PACKAGE = WRAPPER['read_properties'](ROOT / 'gradle.properties')['maven_group'].replace('.', '/') + '/'
CANONICAL = runpy.run_path(str(ROOT / 'scripts/canonical-artifacts.py'))


def properties(path):
    return {key.strip(): value.strip() for line in path.read_text().splitlines()
            if '=' in line and not line.lstrip().startswith('#')
            for key, value in [line.split('=', 1)]}


def quoted(text, key):
    match = re.search(rf'^\s*{re.escape(key)}\s*=\s*"([^"\n]*)"', text, re.MULTILINE)
    assert match, f'Missing metadata field: {key}'
    return match.group(1)


def release_jar(matrix):
    """Select exactly one expected release; reject classifiers and stale versions."""
    jars = [p for p in (ROOT / 'build/libs' / matrix.stem).glob('*.jar')
            if not p.name.endswith(('-dev.jar', '-sources.jar', '-javadoc.jar',
                                    '-dev-shadow.jar', '-raw.jar'))]
    assert len(jars) == 1, f'Expected one release JAR: {jars}'
    root_pins = properties(ROOT / 'gradle.properties')
    minecraft, loader = matrix.stem.rsplit('-', 1)
    expected = f"{root_pins['archives_name']}-{loader}-{minecraft}-{root_pins['mod_version']}.jar"
    assert jars[0].name == expected, f'Wrong/stale release artifact: {jars[0]} (expected {expected})'
    return jars[0]


def parse_json(payload, name, exceptions):
    if name in exceptions:
        assert payload.endswith(b'\\n'), f'Documented suffix missing: {name}'
        return json.loads(payload[:-2])  # Only the two documented literal bytes.
    return json.loads(payload)


def inspect(matrix):
    pins = WRAPPER["matrix_properties"](matrix)
    assert matrix.stem == f"{pins['minecraft_version']}-{pins['loader']}", 'Target/property mismatch'
    artifact = release_jar(matrix)
    canonical = CANONICAL['verify'](artifact, matrix.stem, ROOT)
    exceptions = set(CANONICAL['inventory'](ROOT)['legacy_json_resources']) if canonical['legacy_json_count'] else set()
    legacy = pins.get('loom_generation') == 'legacy'
    root_pins = properties(ROOT / 'gradle.properties')
    with ZipFile(artifact) as jar:
        entries = jar.namelist()
        files = set(entries)
        assert len(entries) == len(files), 'Duplicate archive entries'
        assert exceptions <= files, 'Missing documented legacy JSON resources'
        read_json = lambda name: parse_json(jar.read(name), name, exceptions)
        required_classes = (
            'FishingFrenzy', 'Constants', 'config/ModConfigs', 'client/ModClientEvents',
            'item/fish/FishManager', 'item/fish/FishRegistry', 'registry/ModItems',
            'registry/DynamicFishEntityGenerator', 'world/ModEntitySpawns',
            'entity/mob/CustomBreedableSchoolingFishEntity', 'command/FishingFrenzyDebugCommands',
        )
        for name in required_classes:
            assert PACKAGE + name + '.class' in files, f'Missing mod class: {name}'
        classes = [name for name in files if name.endswith('.class')]
        assert classes and all(name.startswith(PACKAGE) for name in classes), 'Bundled dependency classes'
        vanilla = {p.relative_to(ROOT / 'common/src/main/resources').as_posix()
                   for p in (ROOT / 'common/src/main/resources/data/minecraft').rglob('*') if p.is_file()}
        assert all(name.split('/')[1] == MOD_ID or name in vanilla
                   for name in files if name.startswith(('assets/', 'data/')) and not name.endswith('/')), 'Bundled external resources/datapack'
        for name in classes:
            bytecode = jar.read(name)
            assert bytecode[:4] == b'\xca\xfe\xba\xbe', f'Invalid class: {name}'
            assert struct.unpack('>H', bytecode[6:8])[0] == int(pins['java_version']) + 44, f'Wrong class level: {name}'
        for name in files:
            if name.endswith('.json'):
                read_json(name)
        def verify_mixins(name, source):
            mixins = read_json(name)
            expected = json.loads(source.read_text().replace('${java_version}', pins['java_version']))
            assert mixins == expected, f'Wrong mixin configuration: {name}'
            assert mixins['required'] is True
            assert mixins['compatibilityLevel'] == 'JAVA_' + pins['java_version']
            for side in ('mixins', 'client', 'server'):
                for mixin in mixins.get(side, []):
                    classname = (mixins['package'] + '.' + mixin).replace('.', '/') + '.class'
                    assert classname in files, f'Missing mixin class: {classname}'
            if 'plugin' in mixins:
                assert mixins['plugin'].replace('.', '/') + '.class' in files, 'Missing mixin plugin'
        verify_mixins('fishingfrenzy.mixins.json', ROOT / 'common/src/main/resources/fishingfrenzy.mixins.json')
        if pins['loader'] == 'fabric':
            assert 'META-INF/neoforge.mods.toml' not in files, 'Wrong loader metadata'
            meta = read_json('fabric.mod.json')
            assert meta['id'] == MOD_ID and meta['version'] == root_pins['mod_version']
            assert meta['depends']['minecraft'] == pins['minecraft_version']
            assert set(meta['depends']) == {'java', 'minecraft', 'fabricloader', 'architectury', 'fabric-api'}
            for dep, pin in (('java', 'java_version'), ('fabricloader', 'fabric_loader_version'),
                             ('architectury', 'architectury_api_version')):
                assert meta['depends'][dep] == '>=' + pins[pin], f'Wrong dependency: {dep}'
            assert meta['suggests'] == {'modmenu': '*'}
            assert meta['mixins'] == ['fishingfrenzy.mixins.json', 'fishingfrenzy-fabric.mixins.json']
            verify_mixins('fishingfrenzy-fabric.mixins.json', ROOT / 'common/src/main/resources/fishingfrenzy-fabric.mixins.json')
            assert meta['environment'] == '*'
            assert meta['entrypoints'] == {
                'main': ['net.vg.fishingfrenzy.fabric.FishingFrenzyFabric'],
                'client': ['net.vg.fishingfrenzy.fabric.client.FishingFrenzyFabricClient'],
                'modmenu': ['net.vg.fishingfrenzy.fabric.client.FishingFrenzyModMenu'],
                'fabric-datagen': ['net.vg.fishingfrenzy.fabric.data.ModDataGenerator'],
            }
            for key, names in meta['entrypoints'].items():
                if legacy and key == 'fabric-datagen':
                    continue  # The established legacy build excludes development datagen source.
                for name in names:
                    assert name.replace('.', '/') + '.class' in files, f'Missing entrypoint: {name}'
            assert meta['icon'] in files
            assert 'accessWidener' not in meta
            if legacy:
                assert b'net/minecraft/class_' in jar.read(PACKAGE + 'mixin/FishingHookAccessor.class'), 'Unremapped Fabric release'

        else:
            assert 'fabric.mod.json' not in files, 'Wrong loader metadata'
            meta = jar.read('META-INF/neoforge.mods.toml').decode()
            assert quoted(meta, 'modId') == MOD_ID
            assert quoted(meta, 'version') == root_pins['mod_version']
            assert PACKAGE + 'neoforge/FishingFrenzyNeoForge.class' in files
            blocks = re.findall(r'\[\[dependencies\.fishingfrenzy\]\](.*?)(?=\n\[|\Z)', meta, re.DOTALL)
            deps = {quoted(block, 'modId'): block for block in blocks}
            assert set(deps) == {'minecraft', 'neoforge', 'architectury'}
            assert quoted(deps['minecraft'], 'versionRange') == '[' + pins['minecraft_version'] + ',)'
            for dep, pin in (('neoforge', 'neoforge_version'), ('architectury', 'architectury_api_version')):
                assert quoted(deps[dep], 'versionRange') == '[' + pins[pin] + ',)'
            assert all(quoted(deps[dep], 'type') == 'required' for dep in ('minecraft', 'neoforge', 'architectury'))
            assert all(quoted(block, 'side') == 'BOTH' for block in deps.values())
            assert quoted(meta, 'displayName') == 'Fishing Frenzy'
            if tomllib is not None:
                tomllib.loads(meta)
            icon_field = 'logoFile' if legacy else 'iconFile'
            assert quoted(meta, icon_field) in files
            configs = re.findall(r'^\s*config\s*=\s*"([^"\n]+)"', meta, re.MULTILINE)
            expected_configs = ['fishingfrenzy.mixins.json']
            if not legacy:
                expected_configs.append('fishingfrenzy-neoforge-compat.mixins.json')
                verify_mixins('fishingfrenzy-neoforge-compat.mixins.json', ROOT / 'neoforge/src/main/resources/fishingfrenzy-neoforge-compat.mixins.json')
            assert configs == expected_configs, 'Wrong NeoForge mixin registrations'
            if legacy:
                assert 'fishingfrenzy-neoforge-compat.mixins.json' not in files
        assert 'assets/fishingfrenzy/lang/en_us.json' in files
        for resource in (
            'assets/fishingfrenzy/textures/entity/albacore.png',
            'assets/fishingfrenzy/textures/item/albacore.png',
            'assets/fishingfrenzy/models/item/deluxe_fishing_rod.json',
            'data/fishingfrenzy/recipe/deluxe_fishing_rod.json',
            'data/minecraft/loot_table/gameplay/fishing/fish.json',
        ):
            assert resource in files, f'Missing core resource: {resource}'
        assert ('assets/fishingfrenzy/items/albacore.json' in files) == (not legacy), 'Wrong item-definition generation'
        assert (meta['license'] if pins['loader'] == 'fabric' else quoted(meta, 'license')) == 'CC0-1.0'
        assert not any(name.endswith(('.jar', '.java')) for name in files), 'Embedded JAR or source archive'
        for name in ('fabric.mod.json', 'META-INF/neoforge.mods.toml', *[n for n in files if n.endswith('.mixins.json')]):
            if name in files:
                assert b'${' not in jar.read(name), f'Unexpanded metadata: {name}'
    print(f'{matrix.stem}: canonical SHA-256, release metadata, classes, mixins and resources OK'
          + (f' ({len(exceptions)} documented 1.2.2 JSON suffixes)' if exceptions else ''))
    return artifact


if __name__ == '__main__':
    matrices = sorted((ROOT / 'gradle/matrix').glob('*.properties'))
    assert matrices, 'No matrix properties found'
    for matrix in matrices:
        try:
            inspect(matrix)
        except (AssertionError, KeyError, ValueError, OSError, BadZipFile) as error:
            raise SystemExit(f'{matrix.stem}: {error}') from error
    print(f'ARTIFACT VERIFICATION PASS ({len(matrices)}/{len(matrices)})')
