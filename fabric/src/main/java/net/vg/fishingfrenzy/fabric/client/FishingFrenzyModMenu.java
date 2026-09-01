package net.vg.fishingfrenzy.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
//import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.vg.fishingfrenzy.FishingFrenzy;
import net.vg.fishingfrenzy.config.ModConfigs;

public final class FishingFrenzyModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }

    private static final class ConfigScreen extends Screen {
        private static final Component TITLE = Component.literal("Fishing Frenzy Config");
        private static final Component EASY_MODE_LABEL = Component.literal("Easy Mode");
        private static final Component FISHING_REAL_LABEL = Component.literal("FishingReal Compat");
        private final Screen parent;
        private Button easyModeButton;
        private Button fishingRealCompatButton;

        private ConfigScreen(Screen parent) {
            super(TITLE);
            this.parent = parent;
        }

        @Override
        protected void init() {
            int centerX = this.width / 2;
            int y = this.height / 4;

            this.easyModeButton = this.addRenderableWidget(
                    Button.builder(getToggleText(EASY_MODE_LABEL, ModConfigs.EASY_MODE), button -> {
                                ModConfigs.EASY_MODE = !ModConfigs.EASY_MODE;
                                button.setMessage(getToggleText(EASY_MODE_LABEL, ModConfigs.EASY_MODE));
                            })
                            .bounds(centerX - 110, y, 220, 20)
                            .build()
            );

            this.fishingRealCompatButton = this.addRenderableWidget(
                    Button.builder(getToggleText(FISHING_REAL_LABEL, ModConfigs.FISHINGREAL_COMPAT_ENABLED), button -> {
                                ModConfigs.FISHINGREAL_COMPAT_ENABLED = !ModConfigs.FISHINGREAL_COMPAT_ENABLED;
                                button.setMessage(getToggleText(FISHING_REAL_LABEL, ModConfigs.FISHINGREAL_COMPAT_ENABLED));
                            })
                            .bounds(centerX - 110, y + 24, 220, 20)
                            .build()
            );

            this.addRenderableWidget(
                    Button.builder(Component.literal("Save & Back"), button -> {
                                ModConfigs.saveConfigs();
                                onClose();
                            })
                            .bounds(centerX - 110, y + 56, 220, 20)
                            .build()
            );

            this.addRenderableWidget(
                    Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
                            .bounds(centerX - 110, y + 80, 220, 20)
                            .build()
            );
        }

        @Override
        public void onClose() {
            if (this.minecraft != null) {
                this.minecraft.setScreen(this.parent);
            }
        }

//        @Override
//        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
//            this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
//            super.render(guiGraphics, mouseX, mouseY, partialTick);
//            guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
//            guiGraphics.drawCenteredString(this.font, Component.literal("FishingReal installed: " + FishingFrenzy.isFishingRealLoaded()), this.width / 2, 40, 0xA0A0A0);
//        }

        private static Component getToggleText(Component label, boolean value) {
            return Component.literal(label.getString() + ": " + (value ? "ON" : "OFF"));
        }
    }
}
