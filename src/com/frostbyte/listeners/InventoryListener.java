package com.frostbyte.listeners;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.frostbyte.display.ItemStack;
import com.frostbyte.inputs.InputType;
import com.frostbyte.inputs.MouseInput;
import com.frostbyte.main.GameFrame;
import com.frostbyte.main.GameManager;

public class InventoryListener {
	GameManager gameManager;

	public InventoryListener(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public void onInventoryInteract(int x, int y) {
		InputType inputType = InputType.DRAG;
		int i = gameManager.world.getPlayer().getInventory().getInventorySlot(x, y);

		if(i < 0){
			i = gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(x, y);
			
			if(i < 0){
				return;
			}
			
			inputType = InputType.CRAFTING;
		}
		
		ItemStack itemStack = null;
		if(inputType == InputType.DRAG){
			itemStack = gameManager.world.getPlayer().getInventory().getContent()[i];
		}else{
			itemStack = gameManager.world.getPlayer().getCraftingInventory().getContent()[i];
		}

		if (itemStack != null) {
			if(inputType == InputType.DRAG){
				gameManager.world.getPlayer().getInventory().remove(itemStack);
			}else{
				if(i == 13){
					gameManager.world.getPlayer().getCraftingInventory().clear();
				}
				
				gameManager.world.getPlayer().getCraftingInventory().remove(itemStack);
			}
			
			gameManager.inputHandler.inputs.add(new MouseInput(gameManager.world.getPlayer(), itemStack, i, inputType, x, y));
			return;
		}
	}

	public void onGuiInteract(int x, int y) {
		int startX = GameFrame.WIDTH / 2 - 114;

		Rectangle2D playerRect = new Rectangle(x - gameManager.world.getPlayerCamera().getX(), y - gameManager.world.getPlayerCamera().getY(), 1, 1);
		for (int i = 0; i < 10; i++) {
			int tempX = startX + (i * 23);
			ItemStack itemStack = gameManager.world.getPlayer().getInventory().getContent()[i];

			if (itemStack != null) {
				Rectangle2D itemRect = new Rectangle(tempX, 28, 20, 20);

				if (playerRect.intersects(itemRect)) {
					gameManager.world.getPlayer().setItemInHand(itemStack);
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (MouseInput input : gameManager.inputHandler.inputs) {
			if (input.getInputType() == InputType.DRAG || input.getInputType() == InputType.CRAFTING) {
				g.drawImage(input.getItemStack().getMaterial().getImage(), input.getX(), input.getY(), null);
			}
		}
	}
}
