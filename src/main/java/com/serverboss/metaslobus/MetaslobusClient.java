package com.serverboss.metaslobus;

import com.serverboss.metaslobus.perspectiveModel.PerspectiveModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MetaslobusClient {

	@SubscribeEvent
	public static void onModelLoaderRegistry(ModelRegistryEvent event) {
		ModelLoaderRegistry.registerLoader(Metaslobus.of("perspective_model"), new PerspectiveModelLoader());
		MetaslobusKeybinds.register();
		MetaslobusPackets.register();
		System.out.println("HOW MUCH LONGER NOW...?");
		System.out.println("MY METASLOBUS.");
	}

}
