# Validation and Release

## Principle

Use the smallest evidence set that can disprove the current change.

Do not use “more validation” as a substitute for choosing representative compatibility shapes.

## Validation Levels

### 1. Compile

Use during source/API work on the affected sentinel.

### 2. Package

Use when remapping, metadata, resources, or artifact construction matters.

### 3. Artifact Verification

Use `scripts/verify-matrix-artifacts.py` to prove release-JAR structure.

### 4. Development Runtime

Use `runClient` / targeted server checks for actual gameplay/UI/mixin behavior.

For 1.20.1 fish/rod changes, run `python tests/gameplay/run.py`. This opt-in
Minecraft GameTest suite uses fresh worlds on Fabric and Forge: every fish's
spawn egg on land, water-bucket capture/release with saved data, baby-state
save/load and breeding, fish-to-bait crafting, and rod bait storage/casting.
It discovers fish names from the shared registry and rejects crashes, zero
tests, incomplete coverage and failed cases. Test classes and metadata stay
outside installable JARs. Use `--targets 1.20.1-fabric` or
`--targets 1.20.1-forge` for an affected sentinel; Gradle options follow `--`.

For fishing-speed/bait changes, use `python tests/gameplay/run.py --bait-timers`.
This focused mode advances actual hooks to bites, reels in loot and checks bait
consumption for every registered bait and an unbaited rod, with Lure 0 and III.
It leaves the existing fish/egg/bucket suite separate. Use `--cases <name>` to rerun only a failed case.
The stability cases also check actual aging and cooldown completion, saved feeding
growth, synchronized baby flags, configured food/species checks, clock/weather/
water gates, two-result catches, bait metadata and caught-fish statistics.

Startup alone does not establish gameplay correctness. These world tests use
development classes; keep packaged-JAR origin checks as a separate gate.

### 5. Packaged-JAR Production Smoke

Use the exact installable release JAR in an isolated runtime.

This should prove:

- staged artifact hash
- loaded mod class origin
- deterministic startup/resource milestone
- no accidental alternate mod JAR
- bounded owned-process shutdown

## Default Representative Matrix

```text
26.2 Fabric
26.2 NeoForge
1.21.1 Fabric
1.21.1 NeoForge
```

Add another target only for a genuinely unique boundary.

The 1.20.1/26.3 extension additionally validates 26.3 Fabric/NeoForge and 1.20.1
Fabric/Forge. See `minecraft-1.20.1-and-26.3-port.md` for the port's evidence and
manual gameplay checklist. Use `--built-artifacts` for packaged smoke of local
compatibility builds; ordinary publishing continues to require canonical pins.

## Full Matrix Acceptance

After sentinel stability:

```bash
python build-smart.py matrix:compile
python build-smart.py matrix:package
python scripts/verify-matrix-artifacts.py
```

Run once for acceptance.

Do not repeat after no-op/documentation-only work.

## Manual Regression

Derive a short checklist from the mod's actual features.

Use representative clients unless a distinct runtime mechanism requires another target.

## Release Preflight

After release version, publishing metadata, notes, and artifacts are final:

```bash
python build-smart.py publish:preflight
```

Expected composition:

```text
canonical release artifact retrieval (1.2.2)
→ artifact verification
→ representative packaged release smoke
→ Modrinth dry-run
→ CurseForge dry-run
```

Run the full preflight once.

## Failure Handling

Classify before changing code:

```text
environment/JDK
target selection
Minecraft API
dependency ABI
compile
resource/metadata
mixin/runtime
release artifact
publishing metadata
real upload
```

Do not respond to every failure by rebuilding the whole matrix.

## Stop Rule

Once the requested acceptance evidence is green, stop unless a later edit invalidates it.
