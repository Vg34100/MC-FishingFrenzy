package net.vg.fishingfrenzy.validation;

import java.util.Collection;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraft.stats.Stat;

@Mod("fishingfrenzy_gameplay_tests")
@GameTestHolder("fishingfrenzy_gameplay_tests")
public class ForgeGameplayTests {
    @GameTestGenerator
    public static Collection<TestFunction> tests() {
        // Forge's supported fake player supplies a safe server connection;
        // vanilla GameTestHelper's login uses a connection without a channel.
        return FishGameplayTests.tests(h -> new FakePlayer(h.getLevel(),
                new GameProfile(UUID.randomUUID(), "FishTestPlayer")) {
            @Override public void awardStat(Stat stat, int amount) {
                getStats().increment(this, stat, amount);
            }
        });
    }
}
