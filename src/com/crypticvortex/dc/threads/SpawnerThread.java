package com.crypticvortex.dc.threads;

import java.util.List;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.CustomSpawner;

public class SpawnerThread implements Runnable {

	public void run() {
		List<CustomSpawner> near = DungeonCrawler.currentLevel.getSpawnersNear();
		for(CustomSpawner s : DungeonCrawler.currentLevel.spawners) {
			if(!near.contains(s)) {
				s.kill();
			} else {
				if(!s.hasMobs)
					s.spawn();
			}
		}
	}
	
}