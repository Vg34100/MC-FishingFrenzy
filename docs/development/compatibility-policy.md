# Compatibility Policy

## Decision Order

Use the first mechanism that fits.

### 1. Shared source

If the same code/resource works, leave it shared.

### 2. Local Stonecutter condition

Use for small API drift:

- import/package move
- method rename
- one changed argument
- short alternate call
- annotation/interface difference

### 3. Narrow deterministic replacement

Use only for truly mechanical drift.

Requirements:

- narrow scope
- deterministic
- behavior-preserving
- expected matches can be asserted

Avoid broad regex/package rewrites.

### 4. Parsed resource/data transform

Use when behavior is shared but serialized schema changed.

Examples:

- recipe representation
- loader metadata
- model/item-definition schema
- advancement/data-component formats

Parse structured data where practical and verify processed output.

### 5. Separate compatibility implementation

Use a real alternate file when the implementation or lifecycle materially differs.

Examples:

- renderer architecture
- menu/screen lifecycle
- entity render-state model
- fundamentally different mixin injection strategy

Keep the compatibility area small.

## Loader Differences

Minecraft-version differences and loader differences are separate axes.

Prefer Fabric-specific source for Fabric-only behavior and NeoForge-specific source for NeoForge-only behavior.

## Mixins

Compilation does not prove mixin correctness.

Verify:

- target class
- target method/descriptor
- injection point
- actual dispatch path
- whether the mixin should exist on the target

## Optional Integrations

Optional means optional.

- no accidental required metadata
- no accidental bundling
- dev runtimes may attach compatible optional mods
- test present/absent states when integration behavior changes

## Runtime ABI

When runtime libraries/loaders differ:

1. identify actual runtime versions
2. compare good/bad combinations
3. pin the narrow dependency when proven
4. do not contort application source around an unproven loader/library issue

## Durable Boundary Log

Record only proven reusable boundaries:

```text
Boundary:
Affected targets:
Affected files/system:
Mechanism:
Reason:
Validation:
```

Raw migration diaries do not belong here.

## 1.20.1 / 26.3 boundaries

See `minecraft-1.20.1-and-26.3-port.md` for acceptance evidence.

- 26.3 keeps the modern no-remap pipeline. Bundle mutation, spawn count
  providers, pose rotation, and recipe bootstrap contexts use narrow API substitutions.
- 1.20.1 uses Java 17 and remapped distribution JARs. It extends the established
  1.21 source view with small API deltas and parsed resource conversion.
- NBT rod storage is a separate small item implementation under
  `gradle/compat/1.20.1/java/`; the shared fish definitions and assets remain canonical.
- Forge has native bootstrap/client code under `forge/`; its event bus,
  dependency declarations and mixin manifest are independent of NeoForge.
- Local structure checks do not require historical release hashes. Malformed
  JSON exceptions still require exact pinned public 1.2.2 bytes; publication
  always requires canonical artifact hashes.
