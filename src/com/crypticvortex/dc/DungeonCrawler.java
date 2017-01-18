package com.crypticvortex.dc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.LootTable;
import com.crypticvortex.dc.core.MobTable;
import com.crypticvortex.dc.core.commands.CommandClass;
import com.crypticvortex.dc.core.commands.CommandItem;
import com.crypticvortex.dc.core.commands.CommandPopulate;
import com.crypticvortex.dc.core.commands.CommandReset;
import com.crypticvortex.dc.core.commands.CommandSpawn;
import com.crypticvortex.dc.core.commands.CommandTutorial;
import com.crypticvortex.dc.core.dungeons.CustomSpawner;
import com.crypticvortex.dc.core.dungeons.Dungeon;
import com.crypticvortex.dc.listeners.EntityListener;
import com.crypticvortex.dc.listeners.PlayerListener;
import com.crypticvortex.dc.threads.CooldownThread;
import com.crypticvortex.dc.threads.SpawnerThread;

public class DungeonCrawler extends JavaPlugin {
	public static Dungeon currentLevel;
	public static DungeonCrawler INSTANCE;
	public static List<Dungeon> loadedLevels = new ArrayList<Dungeon>();
	public static HashMap<UUID, DPlayer> players = new HashMap<UUID, DPlayer>();
	public static Map<Location, MaterialData> brokenBlocks = new HashMap<Location, MaterialData>();
	
	public void onLoad() {
		
	}
	
	public void onEnable() {
		INSTANCE = this;
		// ------------ Initalize player list ------------
		for(Player p : Bukkit.getOnlinePlayers())
			players.put(p.getUniqueId(), new DPlayer(p));
		// ------------ Initalize commands ------------
		getCommand("item").setExecutor(new CommandItem());
		getCommand("spawn").setExecutor(new CommandSpawn());
		getCommand("class").setExecutor(new CommandClass());
		getCommand("reset").setExecutor(new CommandReset());
		getCommand("tutorial").setExecutor(new CommandTutorial());
		getCommand("populate").setExecutor(new CommandPopulate());
		// ------------ Initalize event listeners ------------
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		// ------------ Initalize threads ------------
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new CooldownThread(), 20L, 20L);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SpawnerThread(), 20L, 20L);
		// ------------ Populate LootTable ------------
		LootTable.populate();
		// ------------ Initalize Level ------------
		currentLevel = new Dungeon("Test");
		//currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1277, 56, 803).getBlock(), 7, MobTable.SHAMBLER, 5, 1, 5));
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1219, 56, 887).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1211, 56, 887).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1198, 56, 887).getBlock(), 7, 3, 1, 3, MobTable.SHAMBLER, MobTable.UNDEAD));
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1189, 56, 887).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1178, 56, 887).getBlock(), 7, 3, 1, 3, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1165, 56, 887).getBlock(), 7, 3, 1, 3, MobTable.SHAMBLER, MobTable.UNDEAD));		
		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1218, 56, 873).getBlock(), 7, 3, 1, 3, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1209, 56, 869).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1198, 56, 875).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1188, 56, 874).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1177, 56, 874).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));
		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1218, 56, 864).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1186, 56, 866).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1175, 56, 866).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1163, 56, 871).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
	
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1213, 56, 856).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1164, 56, 854).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1175, 56, 853).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1184, 56, 851).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1184, 56, 841).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1212, 56, 848).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1212, 56, 840).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1214, 56, 829).getBlock(), 7, 1, 1, 1, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1214, 56, 821).getBlock(), 7, 1, 1, 1, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1214, 56, 814).getBlock(), 7, 1, 1, 1, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1206, 56, 818).getBlock(), 7, 3, 1, 3, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1204, 56, 829).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1193, 56, 818).getBlock(), 12, 5, 1, 5, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1180, 56, 818).getBlock(), 12, 4, 1, 4, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1167, 56, 816).getBlock(), 12, 3, 1, 3, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1167, 56, 824).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1164, 56, 835).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1167, 56, 846).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1189, 56, 829).getBlock(), 7, 2, 1, 2, MobTable.SHAMBLER, MobTable.UNDEAD));		
		currentLevel.addSpawner(new CustomSpawner(new Location(getMainWorld(), -1178, 56, 829).getBlock(), 7, 1, 1, 1, MobTable.SHAMBLER, MobTable.UNDEAD));		
	}
	
	@SuppressWarnings("deprecation")
	public void onDisable() {
		// ------------ Closes active threads ------------
		getServer().getScheduler().cancelTasks(this);
		getServer().getScheduler().cancelTasks(INSTANCE); // Might be redundant.
		// ------------ Reset Broken Blocks ------------
		for(Entry<Location, MaterialData> entry : DungeonCrawler.brokenBlocks.entrySet()) {
			entry.getKey().getBlock().setType(entry.getValue().getItemType());
			entry.getKey().getBlock().setData(entry.getValue().getData());
		}
		DungeonCrawler.brokenBlocks.clear();
	}
	
	public static World getMainWorld() {
		return Bukkit.getWorlds().get(0);
	}
	
}