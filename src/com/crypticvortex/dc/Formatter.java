package com.crypticvortex.dc;

import java.util.Optional;

import com.crypticvortex.dc.core.PlayerClass;

public class Formatter { // TODO : Fill out formatter

	public static String format(String input, MessageType type, Optional<Object> parameter) {
		String formatted = "";
		switch(type) {
			case CLASS_CHANGE:
				formatted = ("§6" + input);
				formatted = formatted.replaceAll("%c", "§c" + ((PlayerClass) parameter.get()).getName() + "§6");
				break;
			case SPELL_COOLDOWN:
			case SPELL_CASTED:
			case LOW_MANA:
				formatted = "§6" + input;
				formatted = formatted.replaceAll("%s", "§c" + parameter.get() + "§6");
				break;
			default: break;
		}
		return formatted;
	}
	
	public enum MessageType {
		CLASS_CHANGE,
		SPELL_CASTED,
		SPELL_COOLDOWN,
		LOW_MANA
	}
}