package com.crypticvortex.dc.threads;

import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.Spell;

public class CooldownThread implements Runnable {
	
	public void run() {
		for(DPlayer p : DungeonCrawler.players.values()) {
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