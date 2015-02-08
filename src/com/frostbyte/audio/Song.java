package com.frostbyte.audio;

public enum Song {
	INTRO("MENU");

	private String name;
	private Song(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
