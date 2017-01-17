package com.crypticvortex.dc.core.commands;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.material.MaterialData;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.CustomSpawner;

public class CommandPopulate implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("test")) {
				//LootGenerator.populateTest(Bukkit.getWorlds().get(0));
				for(CustomSpawner s : DungeonCrawler.currentLevel.spawners)
					s.reset();
				for(Entry<Location, MaterialData> entry : DungeonCrawler.brokenBlocks.entrySet()) {
					entry.getKey().getBlock().setType(entry.getValue().getItemType());
					entry.getKey().getBlock().setData(entry.getValue().getData());
				}
				DungeonCrawler.brokenBlocks.clear();
			}
		}
		return true;
	}
	
}