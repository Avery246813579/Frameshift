package com.frostbyte.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.frostbyte.main.GameFrame;
import com.frostbyte.player.Player;

public class InventoryHotbar {
	Player player;
	BufferedImage image;
	
	public InventoryHotbar(Player player){
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/GUI/HOTBAR.png"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		this.player = player;
	}
	
	public void draw(Graphics g){
		g.drawImage(image, GameFrame.WIDTH/2 - 124, 26, null);
		
		int startX = GameFrame.WIDTH/2 - 114;
		for(int i = 0; i < 10; i++){
			ItemStack itemStack = player.getInventory().getContent()[i];
			
			if(itemStack != null){
				int tempX = startX + (i * 23);
				g.drawImage(itemStack.getMaterial().getImage(), tempX, 28, null);
			}
		}
	}
}
