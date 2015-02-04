package com.frostbyte.commands;

import com.frostbyte.main.GameManager;

public class CommandLocation extends Command{
	public CommandLocation(GameManager gameManager) {
		super("location", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		System.out.println("Location: " + gameManager.world.getPlayer().getX() + ", " + gameManager.world.getPlayer().getY());
	}
}
