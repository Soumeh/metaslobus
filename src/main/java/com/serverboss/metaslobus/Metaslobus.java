package com.serverboss.metaslobus;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(Metaslobus.MOD_ID)
public final class Metaslobus {

	public static final String MOD_ID = "metaslobus";

	public static ResourceLocation of(String name) {
		return new ResourceLocation(MOD_ID, name);
	}

}
