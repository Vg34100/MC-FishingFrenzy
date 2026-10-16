# Publishing

## Goal

One source release, a publication plan discovered from the matrix, safe platform uploads, and one GitHub Release containing only installable matrix JARs.

## Source of Truth

Use:

```text
gradle/publishing.properties
gradle.properties
gradle/matrix/*.properties
loader metadata
docs/wiki/release-notes.md
```

Credentials are never publication configuration.

## Credentials

Root ignored `.env`:

```dotenv
MODRINTH_TOKEN=...
CURSEFORGE_TOKEN=...
```

Rules:

- never commit `.env`
- never print token values
- never pass tokens as command-line arguments
- plan/dry-run operations should not require real credentials

## Publishing Properties

Project-specific values typically include:

```properties
mod_id=
mod_name=
environment=
modrinth_project_id=
modrinth_project_slug=
curseforge_project_id=
curseforge_project_slug=
version_type=release
version_name=[{loader_name}] {mod_name} {mod_version} ({minecraft_version})
version_number={mod_version}-{minecraft_version}-{loader}
github_tag=v{mod_version}
github_title={mod_name} {mod_version}
notes_file=docs/wiki/release-notes.md
notes_section={mod_version}
```

Add dependency mappings only for dependencies actually declared by loader metadata.

## Semantic Duplicate Rule

A semantic publication node is:

```text
mod version + exact Minecraft version + loader
```

Historical display names/internal version strings do not justify reuploading the same semantic node.

## Plan and Dry Runs

Expected commands:

```bash
python build-smart.py publish:plan
python build-smart.py publish:modrinth-dry-run
python build-smart.py publish:curseforge-dry-run
python build-smart.py publish:all-dry-run
```

Dry-runs must never upload.

## One Preflight

After all publication facts are final:

```bash
python build-smart.py publish:preflight
```

Expected:

```text
frozen artifact hash verification (public retrieval for historical releases)
→ verifier
→ representative release smoke
→ both platform dry-runs
```

If green, do not rerun solely for reassurance.

## Real Uploads

Real publication requires explicit confirmation.

Uploads should be:

- sequential
- receipt-backed
- resume-safe
- exact-artifact-hash checked

On partial failure:

- preserve successful receipts
- do not rollback public files
- do not reupload successful nodes
- stop if the required fix changes source/artifact bytes/version/publication semantics

## GitHub Release

Recommended order:

1. commit/push exact release revision
2. complete external platform publication
3. create annotated `v<mod_version>` tag on that exact revision
4. push tag
5. GitHub Actions retrieves/hash-verifies the pinned public Modrinth artifacts and creates one release
6. release contains exactly the installable matrix JARs

The GitHub workflow must not republish Modrinth/CurseForge.

## Changelog Style

Keep user-facing release notes brief.

Do not mention:

- AI
- Stonecutter
- internal migration architecture

Describe user-relevant version/loader support and actual feature changes only.

## Fishing Frenzy 1.2.2 canonical artifacts

`gradle/canonical/1.2.2.json` records all 12 official Modrinth version IDs, exact
Minecraft/loader targets, filenames, download URLs, sizes and SHA-256/SHA-512/SHA-1
hashes. The audit checked both hashes advertised by Modrinth before computing
SHA-256. When that historical release is selected, retrieve with `python scripts/canonical-artifacts.py --public`. Publication,
production staging and GitHub collection verify those same bytes; none rebuilds
them. Modrinth 1.2.2 is read-only in this pipeline.

Classification B is accepted: each of the four 1.21/1.21.1 JARs already shipped
with the same 68 JSON resources ending in the two literal bytes `\\n`. The
exception is bound to the entire canonical JAR hash and the exact resource list
in the pin file. Only that suffix is stripped in the verifier; the remaining
JSON and every other resource/metadata/class check remain strict. No release
bytes are edited. A changed JAR hash, unexpected malformed resource, altered
suffix or invalid JSON body fails verification.

Minecraft 1.21.1's `GsonHelper` reads one JSON value through `TypeAdapter.read`
without checking end-of-input, so those trailing bytes are tolerated on the
verified resource paths. Fabric and NeoForge `runClient` were manually verified;
the packaged release smoke independently stages the canonical installable JAR.
Fresh builds use a real newline; no historical public bytes are changed.

Production smoke stages only the canonical mod and its public required mods;
it never adds a standalone MixinExtras library. Current public 26.2 Fabric
Architectury 21.1.11 requires Fabric Loader >=0.19.5 and Fabric API >=0.160.0.
Official Loader 0.19.5 bundles MixinExtras 0.5.5 automatically, whereas 0.19.4
bundles 0.5.4 and fails the canonical Fabric JAR's factory redirect.
Production-only runtime pins in `gradle/publishing.properties` select those
normal public dependencies without changing compiler pins or release bytes.
The earlier smoke with a manually staged 0.5.5 library is not acceptance
evidence for an ordinary installation.

The corrected 26.2 Fabric smoke passed with the canonical JAR, public
Architectury 21.1.11 and Fabric API 0.161.0+26.2, and official Loader
0.19.5. Both required mod JARs matched their public Modrinth SHA-256 hashes.
NeoForge 26.2.0.88 with public Architectury 21.1.11 also passed, using
NeoForge's automatically bundled MixinExtras 0.5.4. Neither mods directory
contained a standalone MixinExtras JAR. The prior 1.21.1 passes remain
valid because they already used only ordinary required dependencies.
Loader 0.19.4 with older Fabric dependencies remains a known failing
combination; release notes direct 26.2 Fabric users to Loader >=0.19.5.

## Fresh releases after 1.2.2

Select an unused version in `gradle.properties`, add its release notes, and set
`canonical_artifacts_file` to a new versioned JSON path. Keep every older pin
file unchanged. Compile, package and strictly verify the complete discovered
matrix, then run `python scripts/canonical-artifacts.py --freeze` once.
This exclusively creates the new SHA-256/SHA-512/SHA-1 inventory; existing
inventories cannot be overwritten. Fresh releases allow no legacy JSON exception.

Local preflight verifies these frozen local bytes without requiring unpublished
Modrinth files. Real uploads use those same bytes, with semantic duplicate
guards, sequential tasks and durable receipts. Historical inventories without
`origin: build` remain read-only on Modrinth.

GitHub uses `python scripts/canonical-artifacts.py --public`: it matches each
exact version/Minecraft/loader on official Modrinth, checks filename, size and
advertised hashes, then verifies downloaded SHA-256 against the committed pins.
It fails if any target is missing, ambiguous or changed. It never builds a
replacement JAR. The GitHub Release contains only the installable JARs.

The 1.2.3 release has 16 targets. Its production sentinel versions are 1.20.1,
1.21.1, 26.2 and 26.3, with every registered loader for those versions.
The 26.3 Fabric production selector uses PortableMC and Mojang-managed Java,
like the other production selectors, after a native Windows fault under the
host development JDK. No extra mod dependency or changed release JAR is used.
Final user approval is required before the first real upload for this release.
