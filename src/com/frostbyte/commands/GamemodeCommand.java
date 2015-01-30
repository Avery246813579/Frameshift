package com.frostbyte.commands;

import com.frostbyte.main.GameManager;
import com.frostbyte.player.Gamemode;

public class GamemodeCommand extends Command{
	public GamemodeCommand(GameManager gameManager) {
		super("gamemode", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length <= 0){
			System.out.println("Usage: /Gamemode (Creative | Survival)");
			return;
		}
		
		if(args[1].equalsIgnoreCase("creative") || args[1].equalsIgnoreCase("1")){
			gameManager.world.getPlayer().setGamemode(Gamemode.CREATIVE);
			System.out.println("Gamemode changed to Creative!");
		}else if(args[1].equalsIgnoreCase("survival") || args[1].equalsIgnoreCase("0")){
			gameManager.world.getPlayer().setGamemode(Gamemode.SURVIVAL);
			System.out.println("Gamemode changed to Survival!");
		}else{
			System.out.println("Gamemode not found!");
		}
	}
}
