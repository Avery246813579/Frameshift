package com.frostbyte.commands;

import com.frostbyte.main.GameManager;

public class CommandDamage extends Command{
	public CommandDamage(GameManager gameManager) {
		super("damage", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length <= 0){
			System.out.println("Usage: /Damage (Amount)");
			return;
		}
		
		try{
			gameManager.world.getPlayer().damage(Integer.parseInt(args[1]));
			System.out.println("You have damaged your player by " + args[1] + ". Now has " + gameManager.world.getPlayer().getHealth() + " health!");
		}catch(Exception ex){
			System.out.println("Could not parse integer!");
		}
	}
}
