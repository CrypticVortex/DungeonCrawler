package com.crypticvortex.dc.core.commands;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.crypticvortex.dc.core.MobTable;

public class CommandSpawn implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command c, String cmd, String[] args) {
		if (sender instanceof Player) {
			Player pl = (Player) sender;
			int count = 1;
			String name = String.join(" ", args);
			if (name.matches(".*\\d+.*")) {
				count = Integer.parseInt(name.substring(name.lastIndexOf(" ") + 1));
				name = name.substring(0, name.lastIndexOf(" "));
			}
			MobTable entity = MobTable.getEntityByName(name);
			if (entity != null) {
				spawnEntities(pl, entity, count);
				pl.sendMessage("§cSpawning entity \"§6" + name + "§c\"");
			} else
				pl.sendMessage("§cNo entity found by that name!");
		}
		return true;
	}

	private void spawnEntities(Player pl, MobTable entity, int count) {
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			int xOffset = rand.nextInt(3), zOffset = rand.nextInt(3);
			if (rand.nextBoolean()) xOffset = -xOffset;
			if (rand.nextBoolean()) zOffset = -zOffset;
			LivingEntity ent = (LivingEntity) pl.getWorld().spawnEntity(pl.getLocation().add(xOffset, 0, zOffset), entity.getType());
			ent.setCustomName(entity.getName());
			ent.setCustomNameVisible(true);
			EntityEquipment equip = ent.getEquipment();
			equip.clear();
			ItemStack[] equipment = entity.getEquipment();
			equip.setHelmet(equipment[0]);
			equip.setChestplate(equipment[1]);
			equip.setLeggings(equipment[2]);
			equip.setBoots(equipment[3]);
			equip.setItemInMainHand(equipment[4]);
			if (equipment.length == 6) equip.setItemInOffHand(equipment[5]);
			switch (entity.getSpeed()) {
				case FAST:
					ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
					break;
				case SLOW:
					ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
					break;
				default:
					break;
			}
		}
	}

}