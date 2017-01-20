package com.crypticvortex.dc.threads;

import com.crypticvortex.dc.DungeonCrawler;
import com.crypticvortex.dc.core.DPlayer;

public class ComboThread implements Runnable {

	public void run() {
		for(DPlayer p : DungeonCrawler.players.values()) {
			if(!p.doneCombo)
				p.comboTimer.getAndDecrement();
			if(p.doneCombo)
				p.doneCombo = false;
			if(p.comboTimer.get() == 0)
				p.currentCombo.clear();
		}
	}
	
}