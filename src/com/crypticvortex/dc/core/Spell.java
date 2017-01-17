package com.crypticvortex.dc.core;

import java.util.LinkedList;

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
	
	public String getName() {
		return name;
	}
	
	public LinkedList<MButton> getButtons() {
		return buttons;
	}
	
}