package com.crypticvortex.dc.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.crypticvortex.dc.core.LootTable;

public class CommandItem implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		if(sender instanceof Player) {
			Player pl = (Player) sender;
			if(args.length == 2) { // item [percent] [index]
				float percent = Float.parseFloat(args[0]);
				int index = Integer.parseInt(args[1]);
				ItemStack item = LootTable.itemTable.get(percent)[index];
				ItemMeta meta = item.getItemMeta();
				pl.getInventory().addItem(item);
				pl.sendMessage("§6Added " + (meta.getDisplayName() == null ? "Item " : "§c" + meta.getDisplayName() + "§6 ") + " to your inventory.");
			}
		}
		return true;
	}
	
}