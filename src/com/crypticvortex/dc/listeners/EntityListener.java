package com.crypticvortex.dc.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.loot.LootGenerator;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getCause() == DamageCause.SUFFOCATION || event.getCause() == DamageCause.STARVATION) event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(DungeonCrawler.currentLevel.fromSpawner((LivingEntity) event.getEntity()))
			DungeonCrawler.currentLevel.getSpawner((LivingEntity) event.getEntity()).remove((LivingEntity) event.getEntity());
		event.setDroppedExp(0);
		event.getDrops().clear();
		event.getDrops().addAll(LootGenerator.generateDrops(event));
	}
	
}