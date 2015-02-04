package com.frostbyte.commands;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;
import com.frostbyte.main.GameManager;

public class CommandGive extends Command {
	public CommandGive(GameManager gameManager) {
		super("give", gameManager);
	}

	@Override
	public void onCommand(String[] args) {
		if (args.length <= 0) {
			System.out.println("Usage: /Give (Item) {Amount}");
		}

		Material material = null;
		int amount = 1;
		if (args.length >= 2) {
			try {
				amount = Integer.parseInt(args[2]);
			} catch (Exception e) {
				System.out.println("Could not parse amount of items!");
			}
		}
		
		for(Material m : Material.values()){
			if(m.toString().equalsIgnoreCase(args[1])){
				material = m;
				break;
			}
		}
		
		if(material != null){
			for(int i = 0; i < amount; i++){
				gameManager.world.getPlayer().getInventory().addItem(new ItemStack(material, 1));
			}
			
			System.out.println("You have been given " + amount + " items.");
		}else{
			System.out.println("Material could not be parsed!");
		}
	}
}
