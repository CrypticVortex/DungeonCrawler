package com.crypticvortex.dc.core;

public class MButton {
	public int button = -1; // -1 none, 0 = left, 1 = right
	
	public MButton(int button) {
		this.button = button;
	}
	
	public static final MButton LEFT_CLICK = new MButton(0);
	public static final MButton RIGHT_CLICK = new MButton(1);
}