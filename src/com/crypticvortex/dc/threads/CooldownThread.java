package com.crypticvortex.dc.threads;

import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.spells.Spell;
import com.crypticvortex.dc.listeners.PlayerListener;

public class CooldownThread implements Runnable {
	
	public void run() {
		if(PlayerListener.mageAttack.size() > 0) {
			for(Entry<Player, AtomicInteger> entry : PlayerListener.mageAttack.entrySet()) {
				PlayerListener.mageAttack.get(entry.getKey()).getAndDecrement();
				if(entry.getValue().get() == 0) 
					Bukkit.getScheduler().runTaskLater(DungeonCrawler.INSTANCE, new Runnable() {
					public void run() {
						PlayerListener.mageAttack.remove(entry.getKey());
					}
				}, 5l);
			}
		}
		for(DPlayer p : DungeonCrawler.players.values()) {
			if(p.stamina < 20) p.stamina += 2;
			if(p.stamina > 20) p.stamina = 20;
			p.player.setFoodLevel(p.stamina);
			if(p.cooldowns.size() > 0) {
				for(Entry<Spell, AtomicInteger> entry : p.cooldowns.entrySet()) {
					int cooldown = p.cooldowns.get(entry.getKey()).getAndDecrement();
					if(cooldown == 0) {
						Bukkit.getScheduler().runTaskLater(DungeonCrawler.INSTANCE, new Runnable() {
							public void run() {
								p.cooldowns.remove(entry.getKey());
							}
						}, 5l);
					}
				}
			}
		}
	}
	
}