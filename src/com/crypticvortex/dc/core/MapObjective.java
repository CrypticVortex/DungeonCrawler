package com.crypticvortex.dc.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class MapObjective {
	public List<Location> locations = new ArrayList<Location>();
	
	
	
	public enum ObjectiveType {
		CLOSE_ON_ENTER, // Close the room when players enter
		OPEN_ON_CLEAR, // Open a room when the room is cleared of mobs
		PLAYERS_IN_POSITION, // Requires players to be in either positions
		OPEN_WITH_ITEM, // Opens an objective if a player has a certain item
		BOSS, // Signifies a boss room, closes 
	}
}