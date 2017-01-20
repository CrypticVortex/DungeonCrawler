package com.crypticvortex.dc.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.crypticvortex.dc.core.loot.LootGenerator;

public class CommandPopulate implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("test")) {
				LootGenerator.populateTest(Bukkit.getWorlds().get(0));
			}
		}
		return true;
	}
	
}