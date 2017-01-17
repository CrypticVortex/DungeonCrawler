package com.crypticvortex.dc;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.LootTable;
import com.crypticvortex.dc.core.commands.CommandPopulate;
import com.crypticvortex.dc.core.commands.CommandTutorial;
import com.crypticvortex.dc.listeners.EntityListener;
import com.crypticvortex.dc.listeners.PlayerListener;

public class DungeonCrawler extends JavaPlugin {
	public static DungeonCrawler INSTANCE;
	public static HashMap<UUID, DPlayer> players = new HashMap<UUID, DPlayer>();
	
	public void onLoad() {
		
	}
	
	public void onEnable() {
		INSTANCE = this;
		
		for(Player p : Bukkit.getOnlinePlayers())
			players.put(p.getUniqueId(), new DPlayer(p));
		
		getCommand("tutorial").setExecutor(new CommandTutorial());
		getCommand("populate").setExecutor(new CommandPopulate());
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		LootTable.populate();
	}
	
	public void onDisable() {
		
	}
	
}