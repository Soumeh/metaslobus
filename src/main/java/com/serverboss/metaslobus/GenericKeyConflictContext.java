package com.serverboss.metaslobus;

import net.minecraftforge.client.settings.IKeyConflictContext;

public class GenericKeyConflictContext implements IKeyConflictContext {

	public static final GenericKeyConflictContext INSTANCE = new GenericKeyConflictContext();

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public boolean conflicts(IKeyConflictContext other) {
		return false;
	}

}
