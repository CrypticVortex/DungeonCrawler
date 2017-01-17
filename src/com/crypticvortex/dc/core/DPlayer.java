package com.crypticvortex.dc.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.entity.Player;

import com.falconetwork.fca.api.ChatManager;

public class DPlayer {
	public Player player;
	private PlayerClass pClass;
	public LinkedList<MButton> currentCombo;
	public Map<Spell, AtomicInteger> cooldowns;
	
	public DPlayer(Player player) {
		this.player = player;
		currentCombo = new LinkedList<MButton>();
		cooldowns = new HashMap<Spell, AtomicInteger>();
		this.pClass = PlayerClass.MAGE;
	}
	
	public void combo(MButton button) {
		currentCombo.add(button);
		boolean casted = false;
		if(pClass != null) {
			Spell spell = pClass.getSpell(currentCombo);
			if(spell != null) {
				if(!cooldowns.containsKey(spell)) {
					if(spell.cast(this)) {
						player.sendMessage("§6Casted Spell \"§c" + spell.getName() + "§6\"");
						cooldowns.put(spell, new AtomicInteger(spell.getCooldown()));
					}
				} else
					player.sendMessage("§c" + spell.getName() + "§6 is currently on cooldown! (" + cooldowns.get(spell).get() + "s)");
				casted = true;
			}
		}
		if(currentCombo.size() > 0) {
			StringBuilder sb = new StringBuilder("§6");
			for(int i = 0; i < currentCombo.size(); i++)
				sb.append(currentCombo.get(i).button == 0 ? "L" : "R").append((i == currentCombo.size() - 1) ? "" : "-");
			ChatManager.sendActionbar(player, sb.toString());
			sb = null;
		}
		if(casted) currentCombo.clear();
		if(currentCombo.size() >= 5) currentCombo.clear();
	}
	
	public PlayerClass getCurrentClass() {
		return pClass;
	}
	
}