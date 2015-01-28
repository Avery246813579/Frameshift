package com.frostbyte.listeners;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.frostbyte.blocks.Block;
import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.main.GameManager;
import com.frostbyte.player.Player;

public class BlockListener {
	GameManager gameManager;
	
	public BlockListener(GameManager gameManager){
		this.gameManager = gameManager;
	}
	
	public void onBlockPlace(Player player, Block block, Location placeLocation){
		Block blockAtLocation = gameManager.world.getBlockAtLocation(placeLocation);
		
		if(blockAtLocation.getMaterial() != Material.AIR){
			return;
		}
		
		if(!player.getInventory().contains(player.getItemInHand().getMaterial())){
			return;
		}
		
		Rectangle2D playerRect = new Rectangle(player.getX() + gameManager.world.getPlayerCamera().getX(), player.getY() + gameManager.world.getPlayerCamera().getY(), player.getWidth(), player.getHeight());
		Rectangle2D blockRect = new Rectangle(blockAtLocation.getLocation().getX() * 20 + gameManager.world.getPlayerCamera().getX(), blockAtLocation.getLocation().getY() * 20+ gameManager.world.getPlayerCamera().getY(), 20, 20);
		if(blockRect.intersects(playerRect)){
			return;
		}
		
		blockAtLocation.setMaterial(block.getMaterial());
		player.getInventory().remove(block.getMaterial());
	}

	public void onBlockBreak(Player player, Block block, Location dropLocation){
		gameManager.world.dropItem(new ItemStack(gameManager.world.getBlockAtLocation(dropLocation).getMaterial(), 1), new Location(dropLocation.getWorld(), (int) (dropLocation.getX() / 20) * 20 + 5, (int) (dropLocation.getY() / 20) * 20 + 5));
		gameManager.world.getBlockAtLocation(dropLocation).setMaterial(Material.AIR);
	}
}