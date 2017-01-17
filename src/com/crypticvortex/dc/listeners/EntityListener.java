package com.crypticvortex.dc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.crypticvortex.dc.core.LootGenerator;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		event.setDroppedExp(0);
		event.getDrops().clear();
		event.getDrops().addAll(LootGenerator.generateDrops(event));
	}
	
}