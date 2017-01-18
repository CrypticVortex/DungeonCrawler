package com.crypticvortex.dc.core.dungeons;

import java.util.Optional;

import org.bukkit.Location;

public class Room {
	public Optional<String> name; // To name certain rooms for easy lookup if need arises
	public CustomSpawner spawner;
	public Optional<Room[]> opened; // A list of rooms that should be opened (if any) 
	public Location pointA, pointB; // Two locations to iterate through to determine proper location for spawning mobs.
	public RoomType type = RoomType.STANDARD;
	
	public Room(Location pointA, Location pointB) {
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	public Room(Location pointA, Location pointB, Optional<String> name) {
		this(pointA, pointB);
		this.name = name;
	}
	
	public Room(Location pointA, Location pointB, RoomType type, Optional<String> name) {
		this(pointA, pointB);
		this.type = type;
		this.name = name;
	}
	
	public Room setOpened(Room[] opened) {
		this.opened = Optional.of(opened);
		return this;
	}
	
	public enum RoomType {
		LOOT,
		STANDARD,
		MOB_HEAVY,
		BOSS;
	}
	
}