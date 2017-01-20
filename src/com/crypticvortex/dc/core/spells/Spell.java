package com.crypticvortex.dc.core.spells;

import java.util.LinkedList;

import com.crypticvortex.dc.core.DPlayer;
import com.crypticvortex.dc.core.MButton;

public class Spell {
	private String name;
	private LinkedList<MButton> buttons;
	
	public Spell(String name, LinkedList<MButton> buttons) {
		this.name = name;
		this.buttons = buttons;
	}
	
	public Spell(String name, MButton[] buttons) {
		this.name = name;
		this.buttons = new LinkedList<MButton>();
		for(MButton b : buttons) 
			this.buttons.add(b);
	}
	
	public boolean cast(DPlayer player) { return true; }
	
	public Spell addButton(MButton button) {
		this.buttons.add(button);
		return this;
	}
	
	public int getCost() {
		return 0;
	}
	
	public double getDamage() {
		return 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCooldown() {
		return 0;
	}
	
	public boolean doesFriendlyFire() {
		return false;
	}
	
	public LinkedList<MButton> getButtons() {
		return buttons;
	}
	
}