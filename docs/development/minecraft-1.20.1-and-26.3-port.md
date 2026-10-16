# Minecraft 1.20.1 and 26.3 port

Branch: `feature/minecraft-1.20.1-and-26.3`, based on `b661f37`.
The starting working tree was clean. The original port evidence below predates
the 1.2.3 stability pass. See [session-summary.md](session-summary.md) for the
current release scope, checks and approval boundary.

## Scope

Add 1.20.1 Fabric/Forge and 26.3 Fabric/NeoForge to the existing twelve-target
Stonecutter 0.9.7 matrix. Keep the shared Groovy build, common gameplay, assets,
and existing loader source trees. Forge gets its own small loader directory.
Target registration and aggregate tasks discover `gradle/matrix/*.properties`.

Published 1.2.2 artifact pins remain frozen. Newly built port artifacts are
development outputs, not replacements for the twelve published JARs.

## Dependencies

| Target | Java | Architectury | Loader | Fabric API | Mod Menu |
| --- | --- | --- | --- | --- | --- |
| 1.20.1 Fabric | 17 | 9.2.14 | 0.19.5 | 0.92.12+1.20.1 | 7.2.2 |
| 1.20.1 Forge | 17 | 9.2.14 | 47.4.28 | — | — |
| 1.21 Fabric | 21 | 13.0.8 | 0.16.2 | 0.102.0+1.21 | 11.0.1 |
| 1.21 NeoForge | 21 | 13.0.8 | 21.0.167 | — | — |
| 1.21.1 Fabric | 21 | 13.0.8 | 0.16.5 | 0.104.0+1.21.1 | 11.0.3 |
| 1.21.1 NeoForge | 21 | 13.0.8 | 21.1.220 | — | — |
| 26.1 Fabric | 25 | 20.0.4 | 0.19.3 | 0.155.2+26.1.2 | 18.0.0 |
| 26.1 NeoForge | 25 | 20.0.4 | 26.1.0.19-beta | — | — |
| 26.1.1 Fabric | 25 | 20.0.4 | 0.19.3 | 0.155.2+26.1.2 | 18.0.0 |
| 26.1.1 NeoForge | 25 | 20.0.4 | 26.1.1.15-beta | — | — |
| 26.1.2 Fabric | 25 | 20.0.7 | 0.19.3 | 0.153.0+26.1.2 | 18.0.0-beta.1 |
| 26.1.2 NeoForge | 25 | 20.0.7 | 26.1.2.76 | — | — |
| 26.2 Fabric | 25 | 21.0.7 | 0.19.4 | 0.156.0+26.2 | 20.0.1 |
| 26.2 NeoForge | 25 | 21.0.7 | 26.2.0.62 | — | — |
| 26.3 Fabric | 25 | 22.0.5 | 0.19.5 | 0.162.0+26.3 | 21.0.0 |
| 26.3 NeoForge | 25 | 22.0.5 | 26.3.0.76 | — | — |

Pins were checked against official Maven metadata and Architectury's packaged
dependency declarations. See [Architectury setup](https://docs.architectury.dev/api/getting-started/setup/)
and [26.3 porting changes](https://docs.architectury.dev/api/porting/port-26.2-26.3/).

## Acceptance

| Target | Compile/package/strict JAR checks | Development client | Dedicated server | Packaged client |
| --- | --- | --- | --- | --- |
| 26.3 Fabric | Pass | Pass | Pass | Pass |
| 26.3 NeoForge | Pass | Pass | Pass | Pass |
| 1.20.1 Fabric | Pass | Pass | Pass | Pass |
| 1.20.1 Forge | Pass | Pass | Pass | Pass |

Clients completed initial resource reload, model baking and first-screen setup.
Servers reached `Done`, saved their worlds and exited cleanly after `stop`.
Packaged clients proved the staged SHA-256 and loaded-class JAR origin, then
terminated only their owned JVM. Their mod directories contained Fishing Frenzy
and Architectury; Fabric additionally had Fabric API. No optional mod or extra
MixinExtras JAR was staged.

Final aggregate compilation, installable packaging and strict artifact
verification passed for all 16 targets. All twelve existing targets passed
those gates, including 26.2 and 1.21.1 Fabric/NeoForge. Their previous runtime
gates were not repeated in this port. These initial startup checks preceded the
spawn-egg fix and missing bait recipes; startup did not prove gameplay.

The follow-up native world suite passed 61/61 cases on both 1.20.1 Fabric and
Forge: all fifteen land-spawn eggs, bucket capture/release with name/health,
baby-state save/load and breeding, all fifteen matching bait recipes, and rod
bait storage/save/load/casting. Before the null-sound correction, the full
Fabric suite reproduced the user's exact `Holder.value()` / `SoundEvent`
server crash. Forge tests use its supported fake player; contained land
fixtures prevent unrelated falls off the test platform. These tests exercise
development classes rather than claiming packaged-JAR origin.

After the spawn-egg/recipe fix, both 1.20.1 installable JARs passed isolated production
client startup, resource reload and first-screen initialization. Their staged
hashes and loaded-class JAR origins matched the rebuilt files, with only the
publicly required Architectury/Fabric API dependencies. The 26.3 startup
evidence above predates the recipe-only follow-up; its gameplay Java did not
change. Those startup results predate the subsequent bait-timer correction.

The updated installable artifacts were packaged and strictly verified for
16/16 targets. Every JAR contains all fifteen fish-to-bait recipes and recipe
unlocks, and none contains the gameplay test classes or test datapack. Copied
build outputs matched their SHA-256 hashes, including the two 1.20.1 hashes
used in the fresh production checks. Temporary Linux build directories avoided
Windows-owned cache permissions; the successful targets were not rebuilt when
recovering the last 26.3 NeoForge resource-copy failure. Public release pins,
the mod version and public files remain unchanged.

The later 1.20.1 bait-timer correction passed a focused fishing check on both
loaders: all 21 registered baits and an unbaited rod, each with Lure 0 and III
(44 casts per loader). Each actual hook advanced naturally to a bite, retrieval
produced loot and the bait was consumed. Only these two installable JARs were
rebuilt and verified afterward, including their remapped timer-accessor fields;
the successful matrix and 61-case suites were not repeated.

Publication safety tests: 24 passed. Published 1.2.2 remains a twelve-artifact
release; new matrix targets cannot use its frozen hashes for publication.
Raw evidence stays outside tracked documentation in
`/tmp/fishingfrenzy-port-research/`.

## Proven boundaries

- The parent plugin classpath supplied ASM 9.5, which could not parse Java 25
  game classes. The root controller now supplies ASM 9.11. Stonecutter and the
  shared Loom layout remain unchanged.
- 26.3 uses `BundleContents.asMutable()` / `itemCopies()`, `PoseStack.rotate()`,
  an `IntProvider` for spawn group counts, and recipe/advancement bootstrap
  contexts. Narrow substitutions preserve the original behavior.
- 1.20.1 has a small Forge bootstrap/client implementation and a separate NBT
  rod implementation. Other source starts from the proven 1.21 compatibility
  view, with targeted API changes. Parsed resource conversion handles plural
  data directories, recipe results, item predicates, and biome-tag expansion.
  Loot-table references use `name`; OR conditions use the registered `any_of`
  type. Vanilla's DFU schema-check messages are recognized only
  for the exact fish names in the canonical model registry on 1.20.1; loot,
  registry, mixin, and other runtime errors remain failures.
- Local development rebuilds write a real JSON newline. The original public
  1.2.2 inventory and its exact-hash-only legacy JSON exception remain frozen.
  The verifier separates local structure checks from canonical release checks;
  a new target cannot be published using the frozen 1.2.2 inventory.
- Production smoke accepts an explicit `--built-artifacts` flag for development
  ports. Normal publishing still requires the frozen canonical hashes. Forge
  selectors use Forge installers. PortableMC 5.0.5 is hash-pinned for Windows
  and Linux; 26.3 Fabric uses Loom's production task. This Linux test host
  supplies a temporary verified newer libc through
  `BUILD_SMART_PORTABLEMC_LIBRARY_DIR`, without changing system libraries or
  mod dependencies. Optional `BUILD_SMART_RELEASE_MAIN_DIR` reuses downloaded
  game assets/libraries; each test's installed mod directory stays isolated.
- Forge 1.20.1 registers items before entity types. Spawn eggs use
  `ForgeSpawnEggItem` and buckets use Forge's supplier constructor, avoiding
  eager registry lookups. These changes apply only to the Forge source view.
- 26.3 recipe-unlock advancements use `conditions.recipes` holder sets rather
  than `conditions.recipe`. A parsed conversion preserves the same recipe IDs.
- 26.3 standalone nodes use Architectury's supported `compileOnly()` mode.
  They contain native loader implementations and no `@ExpectPlatform` stubs.
  Loom's native launch avoids unnecessary transformer worker pools keeping
  stopped dedicated servers alive.
- 1.20.1's `AbstractFish` directly passes the shared fish's null flop sound to
  `playSound`, causing the reported server crash. The generated 1.20.1 view
  skips null sounds and keeps real sounds unchanged. Other targets retain the
  existing implementation and the fish remain silent when flopping.
- 1.20.1's fishing-hook constructor accepts Lure levels and multiplies them by
  100; newer hooks accept ticks. The 1.20.1 rod converts enchantment levels to
  ticks and retains bait bonuses in ticks. Its generated hook applies the
  combined reduction once to each newly initialized vanilla wait, through two
  target-only accessors. This preserves vanilla bite logic and enchantment
  strength without stalling deluxe/wild/magic bait or capping their bonuses.
  Other Minecraft targets, loot tables and multi-catch settings are unchanged.
- Bonefish, carp, cobblestone fish, dorado, eel, tuna, void salmon and walleye
  now have matching bait recipes and recipe-book unlocks. Like the original
  seven recipes, one fish produces two bait items. Existing resource
  conversions handle each target's schema.

The existing plugin declarations are unchanged: no-remap Loom `1.17-SNAPSHOT`,
legacy Loom `1.7.414`, Architectury plugin `3.5-SNAPSHOT`. This shared Gradle
classpath resolved Loom implementation `1.17.493` and Architectury plugin
`3.5.170`; Gradle is `9.5.1`. Legacy targets still use remapping and their pinned
Java 17/21 toolchains, rather than the modern no-remap pipeline.

## Changed areas

- Registry/controller and four matrix pin files; shared target-aware build;
  isolated `gradle/compat/1.20.1/` implementation and native `forge/` loader.
- Artifact verification, local packaged-smoke support, Forge publication
  discovery/labels, and safety tests. Canonical published pins stay unchanged.
- Opt-in native world tests under `tests/gameplay/`, with Gradle wiring under
  `gradle/gameplay-tests.gradle`; test classes/metadata do not ship in mod JARs.
- Eight bait recipes and their unlock advancements; shared gameplay Java,
  textures and models remain unchanged.
- Editor tasks, repository instructions, compatibility/validation playbooks,
  acceptance checklist and this report.

## Remaining manual checks

The 1.20.1 world suite covers spawn eggs, buckets, baby state, breeding,
fish-to-bait crafting and basic rod storage/casting. Broader player checks
still include:

- Rod cast/retrieve, bait insertion/removal, capacity, save/reload, consumption,
  lure/luck effects and multi-catch behavior.
- Natural fish spawning and rendering/animation; the same gameplay checks on
  26.3, which is outside the 1.20.1 world-test suite.
- Raw/cooked fish items, crafting/cooking recipes and fishing/magnet loot.
- Fishing Real integration with the optional mod present. All automated runs
  covered its absence. Listen for sounds and check the normal player UI.

Automated launches used Linux/WSLg and OpenGL. Missing Vulkan and offline
authentication/Realms messages were host/test-environment noise. The known
1.20.1 fish DFU messages are not loot or registry-loading failures. Windows
interop was unavailable during this session; Windows gameplay is still manual.

## Reproduce

Use Java 25 for Gradle and register local Java 17, 21 and 25 toolchains. Set
`BUILD_SMART_JAVA_HOME` locally; provide installation paths through local Gradle
configuration or `-Porg.gradle.java.installations.paths=...`, not tracked files.
Accept the EULA in each target's isolated server run directory before smoke.

```bash
python build-smart.py :26.3-fabric:compileJava :26.3-neoforge:compileJava
python build-smart.py :1.20.1-fabric:compileJava :1.20.1-forge:compileJava
python build-smart.py :26.3-fabric:runClient
python build-smart.py smoke-server:26.3-fabric
python build-smart.py smoke-release-client:26.3-fabric --built-artifacts
# Repeat the three launch commands with each other new target's name.
python build-smart.py matrix:compile
python build-smart.py matrix:package
python scripts/verify-matrix-artifacts.py
python scripts/test-publishing.py
python tests/gameplay/run.py
```

The existing sixteen-target build architecture is suitable for future 2.0 work.
No 2.0 feature has been added. Gameplay sign-off and fresh release pins are
separate requirements before a future distribution.
