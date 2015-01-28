package com.frostbyte.display;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockAir;
import com.frostbyte.entities.Entity;
import com.frostbyte.items.ItemDrop;
import com.frostbyte.player.Player;

public class World {
	private String name;
	private Block[][] blocks = new Block[128][128];
	private List<Entity> entities = new ArrayList<Entity>();
	private Player player = new Player(this, 500, 50);
	private List<ItemDrop> drops = new ArrayList<ItemDrop>();
	PlayerCamera playerCamera;

	public World(String name) {
		entities.add(player);
		playerCamera = new PlayerCamera(this, player);

		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks[0].length; y++) {
				blocks[x][y] = new BlockAir(new Location(this, x, y));
				System.out.println(x + " " + y);
			}
		}

		this.name = name;

		new WorldPopulator(this, WorldPopulator.OVERWORLD);
	}

	public void draw(Graphics graphics) {
		int renderDistance = 32;

		int startX = (int) (player.getX() / 20) - renderDistance;
		int finishX = (int) (player.getX() / 20) + renderDistance;
		int startY = (int) (player.getY() / 20) - renderDistance ;
		int finishY = (int) (player.getY() / 20) + renderDistance;

		if (startX < 0) {
			startX = 0;
		}

		if (startY < 0) {
			startY = 0;
		}

		if (finishX > (blocks.length - 1)) {
			finishX = (blocks.length - 1);
		}

		if(finishY >= (blocks[0].length - 1)) {
			finishY = (blocks[0].length - 1);
		}

		for (int x = startX; x <= finishX; x++) {
			for (int y = startY; y <= finishY; y++) {
				System.out.println(startX + " " + finishX + " " + startY + " " + finishY);

				if (blocks[x][y] != null) {
					if (blocks[x][y].getMaterial() != Material.AIR) {
						graphics.drawImage(blocks[x][y].getImage(), (x * 20), (y * 20), null);
					}
				}
			}
		}

		if (!drops.isEmpty()) {
			for (ItemDrop itemDrop : drops) {
				itemDrop.draw(graphics);
			}
		}

		for (Entity entity : entities) {
			entity.draw(graphics);
		}
	}

	public Block getBlockAtLocation(Location location) {
		int x = (int) (location.getX() / 20);
		int y = (int) (location.getY() / 20);

		if (y < 0) {
			y = 0;
		}

		if (x < 0) {
			x = 0;
		}

		if (x >= 128) {
			x = 127;
		}

		if (y >= 64) {
			y = 63;
		}

		return blocks[x][y];
	}

	public void dropItem(ItemStack itemStack, Location location) {
		drops.add(new ItemDrop(itemStack, location));
	}
	
	public void update() {
		for (Entity entity : entities) {
			if (entity instanceof Player) {
				player.update();
			} else {
				entity.update();
				entity.checkMovement();
				entity.updateLocation();
				entity.checkEntityCollisions();
			}
		}

		if (!drops.isEmpty()) {
			for (ItemDrop itemDrop : drops) {
				itemDrop.update();
				
				if(itemDrop.isRemoved()){
					break;
				}
			}
		}

		playerCamera.updateMovement();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerCamera getPlayerCamera() {
		return playerCamera;
	}

	public void setPlayerCamera(PlayerCamera playerCamera) {
		this.playerCamera = playerCamera;
	}

	public List<ItemDrop> getDrops() {
		return drops;
	}

	public void setDrops(List<ItemDrop> drops) {
		this.drops = drops;
	}
}
