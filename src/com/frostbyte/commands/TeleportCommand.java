package com.frostbyte.commands;

import com.frostbyte.main.GameManager;

public class TeleportCommand extends Command{
	public TeleportCommand(GameManager gameManager) {
		super("tp", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length < 3){
			System.out.println("Not enough information to teleport!");
			return;
		}
		
		gameManager.world.getPlayer().setX(Integer.parseInt(args[1]));
		gameManager.world.getPlayer().setY(Integer.parseInt(args[2]));
		System.out.println("Player has been teleported to " + args[1] + "," + args[2] + "!");
	}
}
