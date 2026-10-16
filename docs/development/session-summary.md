# Fishing Frenzy 1.2.3 release

Branch: `feature/minecraft-1.20.1-and-26.3`; base commit `b661f37`.
Final publication approval was given on 2026-10-15. Release commit
`d292e26815aa8e7a8798a99c9084115f2960db36` is published and tagged `v1.2.3`.
This documentation-only follow-up records the final distribution checks.
The release JARs and tag remain unchanged.

## Release scope

The matrix discovers 16 targets from `gradle/matrix/*.properties`: 1.20.1 on
Fabric/Forge; 1.21, 1.21.1, 26.1, 26.1.1, 26.1.2, 26.2 and 26.3 on Fabric/NeoForge.
Java 17, 21 and 25 follow the matrix pins. Existing item/entity/save identifiers
are unchanged. No 2.0 content or architecture redesign is included.

## Focused audit

| Area | Finding and disposition |
| --- | --- |
| Targeted bait | Eight missing recipes/unlocks added in the port follow-up. All 15 recipes yield two matching baits; models and translations already exist. Packaged checks now verify them for every fresh release target. |
| Spawn eggs | Confirmed 1.20.1 null flop-sound crash fixed in the 1.20.1 source view. Real sounds remain unchanged; no new flop sound is introduced. |
| Bait speed | Confirmed 1.20.1 Lure-level versus tick mismatch fixed at that boundary; all existing bait bonuses retained. |
| Breeding age | Babies and parental cooldowns now advance server-side. Feeding growth records the actual age delta and preserves saved keys. |
| Species and food | Breeding checks use registered entity type and require ready parents. Mate search uses the shared fish base. Configured breeding food is used consistently. |
| Baby synchronization | Baby flag updates refresh dimensions. Native synchronized-data transfer is tested; full visual/network play remains manual. |
| Rod consumption | A successful retrieval consumes at most one selected bait, regardless of initial loot count or extra catches. Replacement bait with different stored data is not consumed. |
| Rod item data | Bundle reconstruction copies full components; the NBT implementation retains full tags. Cast bait is copied defensively. |
| Caught-fish statistics | Count actual successfully spawned fish stacks, including additional and targeted fish, after cobblestone conversion. |
| Repeated extra catches | Retained intentionally: existing wild/multi-catch behaviour duplicates a draw using independent stack copies. No demonstrated reason to change probabilities or reroll tables. |
| Natural spawning | Uses world day/clock time instead of elapsed game time, with wrapped time windows. Existing biome, weather, height and connected-water gates remain. |
| Water AI, drops and animations | No demonstrated defect found. Retained existing swimming/navigation and custom drops; no duplicate entity loot tables ship. Baby render scaling remains unchanged. |
| Optional integration | Fishing Real remains optional and unbundled. Absent-state runs are covered; present-state integration and visual/audio play are manual. |

## Artifact and publication rules

Public Modrinth, CurseForge and GitHub were checked: the latest release was
1.2.2; 1.2.3 had zero existing semantic nodes on both external mod platforms.
CurseForge recognizes every planned Minecraft/loader/Java/environment combination.

`gradle/canonical/1.2.2.json` remains byte-for-byte unchanged. Fresh 1.2.3
artifacts are pinned separately in `gradle/canonical/1.2.3.json`, with all three
hashes and no malformed JSON exception. New pin files use exclusive creation;
existing inventories cannot be overwritten.

Local preflight checks these frozen bytes. Both platform publishers keep their
exact-file checks, semantic duplicate guards, sequential uploads and durable
receipts. GitHub retrieves official public Modrinth files only after their
metadata and pinned hashes match; it never rebuilds replacement JARs. Intended
GitHub tag is `v1.2.3`, on the exact approved release revision, with 16 JAR assets.

## Acceptance

- Eight source/API sentinels compiled during the fixes.
- Final aggregate matrix compilation: 16/16 passed.
- Final aggregate packaging: 16/16 passed.
- Strict packaged metadata, bytecode, mixins, JSON and bait resources: 16/16 passed.
- Publication safety tests: 25 passed, including immutable fresh pins, changed
  public bytes, wrong-loader duplicates, receipt conflicts and exact GitHub assets.
- 1.20.1 Fabric: all 64 world cases passed (63 in the main run; corrected weather
  fixture reran only the remaining case). Forge: 64/64 world cases passed.
- All 44 baited/unbaited fishing cases passed on each 1.20.1 loader, including Lure 0/III.
- Dedicated servers: 1.20.1 Forge and both 26.3 loaders reached `Done`, saved and stopped cleanly.
- Packaged clients: all eight configured sentinels passed, with exact staged
  SHA-256, loaded-class JAR origin, completed resource reload/first screen and
  owned-process termination. Coverage is both loaders for 1.20.1, 1.21.1, 26.2 and 26.3.
  The one preflight was interrupted by a native Windows access violation in the
  host Microsoft JDK 25.0.3 during 26.3 Fabric resource loading. The unchanged
  JAR passed using Mojang-managed Java 25.0.1 and the ordinary required dependencies.
  The production selector now uses PortableMC for that target. Only unfinished
  preflight steps were resumed; the earlier successful target is retained. The
  exact cause of the development-launcher native fault is unconfirmed; publication
  uses the verified ordinary-player runtime, without changing release bytes.

- Modrinth dry-run: 16/16 passed. CurseForge dry-run: 16/16 passed.
- Final preflight completed by resuming the unfinished steps after the single
  host-launcher failure. Passed clients were not repeated, and both upload
  previews ran once. The actual original command returned failure; the resumed
  sequence finished successfully and collected the release bundle.
- GitHub bundle: exactly 16 installable JARs, each matching its frozen SHA-256.
- Historical 1.2.2 pin file unchanged; SHA-256
  `59fafb5a4849cbb522370fcf0ebf9d1e4c9e43cf00eaf09dd1a08bf276198dbc`.
- No required validation gate remains unresolved. Both platform uploads and
  GitHub Actions completed successfully; all public downloadable JAR hashes match.

## Remaining manual checks

A normal player should still check fish animation/size, sounds and optional
Fishing Real integration on 26.3. Automated startup is not a claim of full
interactive gameplay coverage on every Minecraft version. The user has already
confirmed the 1.20.1 bait crafting and fishing fixes in normal play.

## Publication results

- Modrinth: 16/16 new 1.2.3 releases listed; filenames, sizes, SHA-1 and SHA-512
  match the frozen inventory. GitHub's clean retrieval independently verified
  all downloaded SHA-256 hashes. The twelve original public 1.2.2 files remain unchanged.
- CurseForge: 16/16 uploads acknowledged with distinct file IDs. All sixteen
  actual public CDN downloads independently match the frozen SHA-256 hashes.
  The public project file list still showed zero new entries at the final
  check; listing/review propagation remains outside the publisher. Do not
  reupload acknowledged files to address that delay.
- GitHub: [public release v1.2.3](https://github.com/Vg34100/MC-FishingFrenzy/releases/tag/v1.2.3),
  exactly 16 installable JARs. Every server-reported asset size and SHA-256
  matches the frozen release; the release points to the exact commit above.
- [GitHub Actions run 38835801847](https://github.com/Vg34100/MC-FishingFrenzy/actions/runs/38835801847):
  both retrieval/verification and public-release jobs succeeded.
- All 32 platform receipts are preserved in ignored
  `build/publishing/1.2.3-uploads.jsonl`. Full target IDs, URLs and hashes are
  recorded locally in `build/publishing/1.2.3-publication-report.json` and its
  readable Markdown companion. No release JAR was rebuilt after freezing.

The pre-commit `git status --short` snapshot is local and ignored at
`build/publishing/final-git-status.txt`. Credentials, generated JARs, worlds and
publication receipts are excluded from the release commit.
