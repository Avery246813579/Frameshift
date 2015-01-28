package com.frostbyte.commands;

import com.frostbyte.main.GameManager;

public abstract class Command {
	private String command;
	public GameManager gameManager;
	
	public Command(String command, GameManager gameManager){
		this.command = command;
		this.gameManager = gameManager;
	}
	
	public abstract void onCommand(String[] args);

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
