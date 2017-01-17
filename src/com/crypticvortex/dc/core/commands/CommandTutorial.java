package com.crypticvortex.dc.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.PlayerClass;

public class CommandTutorial implements CommandExecutor {
	public String[][] tutorials = {
		{ "Welcome to _NAME_! This game mode is an action driven dungeon crawler that allows you to play one of _CLASSES_ total classes!" }, // About segment
		{ "To select a class type \"/class\", or right click the class selection sign in the lobby. You're allowed to change your class at any point outside of a dungeon if you want to try something new. Each group may only one of each class!" }, // Classes segment
		{ "Each of your characters will have their own experience values. Your experience will allow you to choose new talents, and equip better gear." }, // Experience segment
		{ "Loot is a key part of this game mode! Every mob will have its own rarity values for dropping items, and all dropped items will be randomly generated. You may sell items at the shop or buy new gear for your character(s)." }, // Loot segment
		{ "Talents are passive traits that will boost various stats. These will allow you to specialize in doing more damage, or doing more healing, or even taking less damage, allowing you to play how you prefer." }, // Talents segment
	};
	
	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		/*
		 * tutorial | Displays tutorial sub commands.
		 * tutorial about | Displays the basic information about the game mode.
		 * tutorial classes/class | Displays class related help information
		 * tutorial experience/exp | Displays experience related information
		 * tutorial loot | Displays loot related information
		 */
		Player player = (Player) sender;
//		DPlayer pl = DungeonCrawler.players.get(player.getUniqueId());
		if(args.length == 0) {
			player.sendMessage("§6There are (" + tutorials.length + ") different categories of tutorial messages; §aabout, classes/class, experience/exp, loot, and talents. §6To view one simply type §c/tutorial <category>");
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("about")) {
				String message = "§b" + tutorials[0][0];
				message = message.replaceAll("_NAME_", DungeonCrawler.INSTANCE.getDescription().getFullName());
				message = message.replaceAll("_CLASSES_", "" + PlayerClass.getClasses().size());
				player.sendMessage(message);
			}
			if(args[0].equalsIgnoreCase("classes") || args[0].equalsIgnoreCase("class")) player.sendMessage("§b" + tutorials[1][0]);
			if(args[0].equalsIgnoreCase("experience") || args[0].equalsIgnoreCase("exp")) player.sendMessage("§b" + tutorials[2][0]);
			if(args[0].equalsIgnoreCase("loot")) player.sendMessage("§b" + tutorials[3][0]);
			if(args[0].equalsIgnoreCase("talents")) player.sendMessage("§b" + tutorials[4][0]);			
		}
		return true;
	}
	
}