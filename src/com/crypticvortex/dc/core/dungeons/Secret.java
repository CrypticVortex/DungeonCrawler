package com.crypticvortex.dc.core.dungeons;

public class Secret {
	
	
	
	public enum TriggerType {
		MESSAGE,
		OPEN_ROOM,
		SPAWN_ITEM
	}
	
	public enum SecretType {
		DESTRUCTIBLE, // Breakable wall
		TRIGGER; // Standing near/hitting this block will activate something.
	}
}