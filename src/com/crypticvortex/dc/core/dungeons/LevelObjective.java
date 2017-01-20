package com.crypticvortex.dc.core.dungeons;

import org.bukkit.Location;

// TODO : Delete and incorporate into other files
public class LevelObjective {
	public ObjectiveType type;
	public Location[][] locations;
	
	
	
	public enum ObjectiveType {
		CLOSE_ON_ENTER, // Close the room when players enter
		OPEN_ON_CLEAR, // Open a room when the room is cleared of mobs
		PLAYERS_IN_POSITION, // Requires players to be in either positions
		OPEN_WITH_ITEM, // Opens an objective if a player has a certain item
	}
}