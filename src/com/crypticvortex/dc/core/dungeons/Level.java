package com.crypticvortex.dc.core.dungeons;

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
	}
	
	public Level(Dungeon dungeon, String name) {
		this.name = name;
		this.levelID = dungeon.levels.size() + 1;
	}
	
}