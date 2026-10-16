package net.vg.fishingfrenzy.validation;

import java.util.Collection;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestFunction;

public class FabricGameplayTests {
    @GameTestGenerator
    public static Collection<TestFunction> tests() {
        return FishGameplayTests.tests(GameTestHelper::makeMockServerPlayerInLevel);
    }
}
