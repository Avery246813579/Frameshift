package com.frostbyte.commands;

import com.frostbyte.main.GameManager;

public class CommandClearInventory extends Command{
	public CommandClearInventory(GameManager gameManager) {
		super("ClearInventory", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		gameManager.world.getPlayer().getInventory().clear();
		System.out.println("You'r inventory has been cleared!");
	}
}
