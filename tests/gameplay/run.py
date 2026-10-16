#!/usr/bin/env python3
"""Run Minecraft 1.20.1 world tests; reject crashes, zero tests and partial coverage."""
import argparse
import json
import os
from pathlib import Path
import re
import runpy
import tempfile
import time
import uuid
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[2]
W = runpy.run_path(str(ROOT / 'build-smart.py'))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--targets', nargs='+')
    parser.add_argument('--bait-timers', action='store_true', help='Only check actual baited bites and catches')
    parser.add_argument('--cases', nargs='+', help='Only rerun named failed cases')
    args, extra = parser.parse_known_args()
    extra = [arg for arg in extra if arg != '--']
    project = W['Project'](ROOT)
    targets = [t for t in project.targets if t.version == '1.20.1']
    if args.targets:
        if not set(args.targets) <= {t.name for t in targets}:
            parser.error('World tests currently support only registered 1.20.1 targets')
        targets = [t for t in targets if t.name in args.targets]
    models = ROOT / 'common/src/main/java/net/vg/fishingfrenzy/registry/DynamicFishSystem.java'
    fish = re.findall(r'Map\.entry\("([a-z_]+)"', models.read_text())
    assert fish and len(fish) == len(set(fish)), 'Invalid fish inventory'
    cases = {'fishingfrenzy_gameplay_tests:' + name + suffix for name in fish
             for suffix in ('_egg_on_land', '_bucket_round_trip', '_save_and_breed', '_bait_crafting')}
    cases.add('fishingfrenzy_gameplay_tests:rod_bait_storage_and_cast')
    cases.update('fishingfrenzy_gameplay_tests:' + name for name in
                 ('breeding_rules', 'spawn_rules', 'rod_multiple_loot'))
    if args.bait_timers:
        cases = {'fishingfrenzy_gameplay_tests:bait_timers_and_catches'}
        extra.append('-Pgameplay_bait_timers=true')
    if args.cases:
        selected = {'fishingfrenzy_gameplay_tests:' + name for name in args.cases}
        if not selected <= cases:
            parser.error('Unknown gameplay case')
        cases = selected
        extra.append('-Pgameplay_test_cases=' + ','.join(args.cases))
    java, message, fatal = W['select_gradle_java'](project, extra)
    if fatal:
        raise RuntimeError(message)
    for target in targets:
        world = Path(tempfile.gettempdir()) / 'fishingfrenzy-world-tests' / target.name / uuid.uuid4().hex
        world.mkdir(parents=True)
        command = W['gradle_command'](project, [f':{target.name}:runGameplayTest', '-Pgameplay_tests=true',
                    f'-Pgameplay_test_dir={world}', *extra], java)
        print(f'Testing {target.name}: {len(cases)} required world cases', flush=True)
        process = W['popen'](command, ROOT, None, isolate=True)
        lines = []
        started = time.monotonic()
        try:
            for line in W['stream_lines'](process):
                if line is not None:
                    lines.append(line.rstrip())
                    if re.search(r'GAME TESTS COMPLETE|All \d+ required tests passed|tests failed|Game test server crashed', line):
                        print(W['ANSI_ESCAPE'].sub('', line).strip(), flush=True)
                if time.monotonic() - started > float(os.environ.get('BUILD_SMART_GAMEPLAY_TIMEOUT', '900')):
                    raise RuntimeError('World test timeout')
            process.wait()
            (world / 'gradle.log').write_text('\n'.join(lines) + '\n')
            runtime = world / 'logs/latest.log'
            text = '\n'.join(lines) + '\n' + (runtime.read_text(errors='replace') if runtime.exists() else '')
            if process.returncode != 0 or not re.search(rf'All {len(cases)} required tests passed', text):
                raise RuntimeError(f'{target.name}: crash/failure/incomplete coverage; evidence: {world}')
            report = world / 'results.xml'
            if target.loader == 'fabric':
                xml = ET.parse(report)
                actual = {test.attrib['name'] for test in xml.iter('testcase')}
                if actual != cases or any(test.find('failure') is not None or test.find('error') is not None for test in xml.iter('testcase')):
                    raise RuntimeError(f'{target.name}: incorrect test-case set; evidence: {world}')
            (world / 'passed.json').write_text(json.dumps(dict(target=target.name, passed=len(cases),
                  cases=sorted(cases), input='development classes', world=str(world)), indent=2) + '\n')
            print(f'PASS {target.name}: {len(cases)}/{len(cases)} world tests; {world}', flush=True)
        finally:
            if process.poll() is None:
                W['kill_tree'](process)
    return 0


if __name__ == '__main__':
    try:
        raise SystemExit(main())
    except (AssertionError, ValueError, OSError, RuntimeError, ET.ParseError) as error:
        print('FAIL ' + str(error), flush=True)
        raise SystemExit(1)
