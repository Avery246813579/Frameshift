package com.frostbyte.audio;

public enum Sound {
	HURT_1("HURT_1"),
	HURT_2("HURT_2"),
	PUNCH("PUNCH"),
	CLASS_BREAK("GLASS_BREAk"),
	HAMMER("HAMMER");
	
	private String name;
	private Sound(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
