package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

import net.minecraft.world.entity.HumanoidArm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SprintIndicator implements ModInitializer {
	public static final String MOD_ID = "sprint-indicator";

	private static final Identifier SPRINT_LAYER = Identifier.fromNamespaceAndPath(MOD_ID, "indicator");
	private static final Identifier SPEED_ICON = Identifier.fromNamespaceAndPath("minecraft", "textures/mob_effect/speed.png");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				SPRINT_LAYER,
				SprintIndicator::render
		);
	}

	public static void render(GuiGraphicsExtractor guiGraphicsExtractor, DeltaTracker deltaTracker){
		Minecraft client = Minecraft.getInstance();
		if (client.player == null)
			return;

		if (client.options.keySprint.isDown() && !client.player.isSpectator()){
			int xPos = (int) (client.getWindow().getGuiScaledWidth() * 0.5f) + (client.options.mainHand().get() == HumanoidArm.RIGHT ? 93 : -111);

			guiGraphicsExtractor.blit(
					RenderPipelines.GUI_TEXTURED,
					SPEED_ICON,
                    xPos,client.getWindow().getGuiScaledHeight() - 20,
					0, 0,
					18, 18,
					18, 18);
		}
	}
}
