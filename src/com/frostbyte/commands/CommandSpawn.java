package com.frostbyte.commands;

import com.frostbyte.entities.Gman;
import com.frostbyte.main.GameManager;

public class CommandSpawn extends Command {
	public CommandSpawn(GameManager gameManager) {
		super("spawn", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		if (args.length <= 2) {
			System.out.println("Usage: /Spawn (Mob)");
			return;
		}

		try {
			if (args[1].equalsIgnoreCase("GMAN")) {
				gameManager.world.getEntities().add(new Gman(gameManager.world, Integer.parseInt(args[2]), Integer.parseInt(args[3])));
			} else {
				System.out.println("Mob type could not be found");
				return;
			}
		} catch (Exception ex) {
			System.out.println("Could not parse integers.");
			return;
		}

		System.out.println("You have spaned in a " + args[1] + "!");
	}
}
