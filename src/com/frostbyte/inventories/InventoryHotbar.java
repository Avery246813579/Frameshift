package com.frostbyte.inventories;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.frostbyte.display.ItemStack;
import com.frostbyte.main.GameFrame;
import com.frostbyte.player.Player;

public class InventoryHotbar {
	Player player;
	BufferedImage image, itemGui;

	public InventoryHotbar(Player player) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/GUI/HOTBAR.png"));
			itemGui = ImageIO.read(getClass().getResourceAsStream("/GUI/ITEM_GUI.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.player = player;
	}

	public void draw(Graphics g) {
		g.drawImage(image, GameFrame.WIDTH / 2 - 124, 26, null);
		g.drawImage(itemGui, 0, 25, null);
		if (player.getItemInHand() != null) {
			g.drawImage(player.getItemInHand().getMaterial().getImage(), 1, 20 + (player.getItemInHand().getMaterial().getImage().getHeight() / 3), null);
		}
		
		int startX = GameFrame.WIDTH / 2 - 114;
		for (int i = 0; i < 10; i++) {
			ItemStack itemStack = player.getInventory().getContent()[i];

			if (itemStack != null) {
				int tempX = startX + (i * 23);
				g.drawImage(itemStack.getMaterial().getImage(), tempX, 28, null);
			}
		}
	}
}
