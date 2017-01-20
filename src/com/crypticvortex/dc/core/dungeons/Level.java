package com.crypticvortex.dc.core.dungeons;

import java.util.ArrayList;
import java.util.List;

public class Level {
	public int levelID;
	public String name;
	public List<Room> rooms;
	protected Dungeon dungeon;
	public List<Secret> secrets;
	
	public Level(Dungeon dungeon, int levelID) {
		this.levelID = levelID;
		this.name = "Level-" + levelID;
		this.rooms = new ArrayList<Room>();
		this.secrets = new ArrayList<Secret>();
	}
	
	public Level(Dungeon dungeon, String name) {
		this.name = name;
		this.levelID = dungeon.levels.size() + 1;
	}
	
	public void reset() {
		for(Room r : rooms)
			r.reset();
		for(Secret s : secrets)
			if(dungeon.brokenSecrets.contains(s.getLocation()) || dungeon.brokenVases.contains(s.getLocation()))
				s.restore();
	}
	
}