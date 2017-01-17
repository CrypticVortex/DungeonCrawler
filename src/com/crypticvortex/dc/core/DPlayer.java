package com.crypticvortex.dc.core;

import java.util.LinkedList;

import org.bukkit.entity.Player;

import com.falconetwork.fca.api.ChatManager;

public class DPlayer {
	public Player player;
	private PlayerClass pClass;
	public LinkedList<MButton> currentCombo;
	
	public DPlayer(Player player) {
		this.player = player;
		currentCombo = new LinkedList<MButton>();
		this.pClass = PlayerClass.MAGE;
	}
	
	public void combo(MButton button) {
		currentCombo.add(button);
		boolean casted = false;
		if(pClass != null) {
			Spell spell = pClass.getSpell(currentCombo);
			if(spell != null) {
				if(spell.cast(this))
					player.sendMessage("§6Casted Spell \"§c" + spell.getName() + "§6\"");
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
		if(casted) 
			currentCombo.clear();
		if(currentCombo.size() >= 5) currentCombo.clear();
	}
	
	public PlayerClass getCurrentClass() {
		return pClass;
	}
	
}