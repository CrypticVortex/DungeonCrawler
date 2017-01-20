package com.crypticvortex.dc.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.entity.Player;

import com.crypticvortex.dc.Formatter;
import com.crypticvortex.dc.Formatter.MessageType;
import com.crypticvortex.dc.core.spells.Spell;
import com.falconetwork.fca.api.ChatManager;

public class DPlayer {
	public Player player;
	public int stamina = 20;
	public boolean doneCombo;
	private PlayerClass pClass;
	public AtomicInteger comboTimer;
	public LinkedList<MButton> currentCombo;
	public Map<Spell, AtomicInteger> cooldowns;
	
	public DPlayer(Player player) {
		this.player = player;
		comboTimer = new AtomicInteger();
		currentCombo = new LinkedList<MButton>();
		cooldowns = new HashMap<Spell, AtomicInteger>();
	}
	
	public void combo(MButton button) {
		currentCombo.add(button);
		boolean casted = false;
		if(pClass != null) {
			Spell spell = pClass.getSpell(currentCombo);
			if(spell != null) {
				if(!cooldowns.containsKey(spell)) {
					if(stamina >= spell.getCost()) {
						if(spell.cast(this)) {
							stamina -= spell.getCost();
							player.sendMessage(Formatter.format("Casted spell \"%s\"", MessageType.SPELL_CASTED, Optional.of(spell.getName())));
							if(spell.getCooldown() > 0)
								cooldowns.put(spell, new AtomicInteger(spell.getCooldown()));
						}
					} else
						player.sendMessage(Formatter.format("You don't have enough mana to cast %s!", MessageType.LOW_MANA, Optional.of(spell.getName())));
				} else
					player.sendMessage(Formatter.format("%s is currently on cooldown! (" + cooldowns.get(spell).get() + "s)", MessageType.SPELL_COOLDOWN, Optional.of(spell.getName())));
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
		doneCombo = true;
		if(comboTimer == null) comboTimer = new AtomicInteger(3);
		else comboTimer.set(3);
		player.setFoodLevel(stamina);
	}
	
	public boolean hasClass() {
		return (pClass != null);
	}
	
	public void setClass(PlayerClass pClass) {
		this.pClass = pClass;
		if(pClass != null)
			player.sendMessage(Formatter.format("Your class is now \"%c\"", MessageType.CLASS_CHANGE, Optional.of(pClass)));
	}
	
	public PlayerClass getCurrentClass() {
		return pClass;
	}
	
}