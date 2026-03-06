package com.notcharrow.monopearl;

import com.notcharrow.monopearl.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

public class Monopearl implements ModInitializer {

	@Override
	public void onInitialize() {
		ConfigManager.loadConfig();
	}
}
