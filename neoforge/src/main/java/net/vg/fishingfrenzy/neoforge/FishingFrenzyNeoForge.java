package net.vg.fishingfrenzy.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.FishingFrenzy;
import net.vg.fishingfrenzy.client.ModClientEvents;

@Mod(Constants.MOD_ID)
public final class FishingFrenzyNeoForge {
    public FishingFrenzyNeoForge(IEventBus modEventBus) {
        FishingFrenzy.init();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(FishingFrenzy::initWorldSpawns);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModClientEvents::registerClient);
    }
}
