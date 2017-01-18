package com.crypticvortex.dc.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.PlayerClass;

public class CommandClass implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		if(sender instanceof Player) {
			Player pl = (Player) sender;
			DPlayer player = DungeonCrawler.players.get(pl.getUniqueId());
			if(args.length == 1) {
				PlayerClass pClass = PlayerClass.getByName(args[0]);
				if(pClass != null) {
					pClass.setGear(pl);
					player.setClass(pClass);
				}
			}
		}
		return true;
	}
	
}