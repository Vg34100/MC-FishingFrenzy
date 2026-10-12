# Fishing Frenzy — Bait Reference

Checked against the current `main` implementation in `BaitManager.java`, `FishRegistry.java`, `BaitPropertiesBuilder.java`, and `DeluxeFishingHook.java` on 2026-10-12.

There are **21 registered baits**: 6 general baits and 15 species-targeted baits. All baits are meant for the Deluxe Fishing Rod's internal bait storage.

## General baits

| Bait | ID | Lure | Luck | Extra-roll capacity | Chance per extra roll | Special |
|---|---|---:|---:|---:|---:|---|
| Worm Bait | `worm_bait` | 2 | 1 | 0 | 0% | Starter bait |
| Deluxe Bait | `deluxe_bait` | 10 | 3 | 2 | 20% | General purpose |
| Wild Bait | `wild_bait` | 8 | 0 | 1 | 90% | Consistent extra rolls |
| Magic Bait | `magic_bait` | 8 | 0 | 9 | 20% | Several possible extra rolls |
| Magnet | `magnet` | 0 | 0 | 1 | 5% | Uses `fishingfrenzy:gameplay/magnet_fishing` loot table |
| Challenge Bait | `challenge_bait` | 2 | 4 | 3 | 10% | Luck-heavy bait |

**Interpretation:** `multiCatchAmount` counts *possible extra loot rolls*, not guaranteed extra fish. The hook performs one base roll and then checks each extra roll separately using `multiCatchChance`. These are Java parameters; exact results should be tested in the game. Default bait use chance is `1.0`.

## Species-targeted baits

Every fish in `FishManager.java` registers a target bait with ID `<fish_id>_bait`:

`anchovy_bait`, `albacore_bait`, `blue_discus_bait`, `bream_bait`, `bullhead_bait`, `woodskip_bait`, `carp_bait`, `cobblestone_fish_bait`, `bonefish_bait`, `dorado_bait`, `eel_bait`, `tuna_bait`, `walleye_bait`, `catfish_bait`, `void_salmon_bait`.

Each targets its matching fish. `DeluxeFishingHook` checks an 80% replacement chance for targeted bait on eligible catch rolls. The `FishRegistry` constructor currently sets no additional lure, luck, or multi-catch bonuses for target bait; the builder defaults are 0 and the use chance is `1.0`. Do **not** use the outdated values (Lure 4, Luck 2, three extra rolls, chance 0.1).

## Crafting (main-branch recipe files)

All six general baits have shapeless recipes. Worm Bait returns 4; Deluxe/Wild/Magic/Magnet/Challenge return 2 each.

- Worm: dirt + string + leaves.
- Deluxe: Worm Bait + 2 gold nuggets + flowers.
- Wild: Worm Bait + string + sweet berries + leaves.
- Magic: Worm Bait + 2 glowstone dust + glow berries.
- Magnet: 2 iron nuggets + string + redstone.
- Challenge: Worm Bait + 2 bones + spider eye.

Named target-bait crafting recipes exist for Anchovy, Albacore, Blue Discus, Bream, Bullhead, Catfish and Woodskip: 1 fish converts to 2 target baits. The other eight target bait items are registered but do not have named recipes in the current `main` recipe directory. Check other branch/version data before claiming they are craftable.

## Source

- [BaitManager.java](../common/src/main/java/net/vg/fishingfrenzy/item/bait/BaitManager.java)
- [FishRegistry.java](../common/src/main/java/net/vg/fishingfrenzy/item/fish/FishRegistry.java)
- [BaitPropertiesBuilder.java](../common/src/main/java/net/vg/fishingfrenzy/item/bait/BaitPropertiesBuilder.java)
- [DeluxeFishingHook.java](../common/src/main/java/net/vg/fishingfrenzy/entity/DeluxeFishingHook.java)
- [Recipe files](../common/src/main/resources/data/fishingfrenzy/recipe)

The Google Drive [Baits, Rod & Crafting Guide](https://docs.google.com/document/d/1q5-ZNzoh--UAth_eUAKH0ZBAbrKI2aUsrd6Y05HpdQQ/edit) includes readable summaries and item art.
