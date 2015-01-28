package com.frostbyte.listeners;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.frostbyte.display.ItemStack;
import com.frostbyte.main.GameFrame;
import com.frostbyte.main.GameManager;

public class InventoryListener {
	GameManager gameManager;
	
	public InventoryListener(GameManager gameManager){
		this.gameManager = gameManager;
	}
	
	public void onInventoryInteract(int x, int y){
		ItemStack itemStack = gameManager.world.getPlayer().getInventory().itemClick(x, y);

		if (itemStack != null) {
			gameManager.world.getPlayer().setItemInHand(itemStack);
			return;
		}
	}
	
	public void onGuiInteract(int x, int y){
		int startX = GameFrame.WIDTH/2 + gameManager.world.getPlayerCamera().getX() - 114;
		
		Rectangle2D playerRect = new Rectangle(x, y, 1, 1);
		for(int i = 0; i < 10; i++){
			int tempX = startX + (i * 23);
			ItemStack itemStack = gameManager.world.getPlayer().getInventory().getContent()[i];

			if(itemStack != null){
				Rectangle2D itemRect = new Rectangle(tempX, 28 + gameManager.world.getPlayerCamera().getY(), 20, 20);
				
				if(playerRect.intersects(itemRect)){
					gameManager.world.getPlayer().setItemInHand(itemStack);
				}
			}
		}
	}
}
