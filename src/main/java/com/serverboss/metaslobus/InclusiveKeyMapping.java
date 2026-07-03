package com.serverboss.metaslobus;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class InclusiveKeyMapping extends KeyMapping {

	public InclusiveKeyMapping(String name, int key) {
		super(name, GenericKeyConflictContext.INSTANCE, InputConstants.Type.KEYSYM, key, "key.categories.metaslobus");
	}

	public InclusiveKeyMapping(String name, InputConstants.Type type, int key) {
		super(name, GenericKeyConflictContext.INSTANCE, type, key, "key.categories.metaslobus");
	}

}
