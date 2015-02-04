package com.frostbyte.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockAir;
import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.display.PlayerCamera;
import com.frostbyte.entities.Gman;
import com.frostbyte.entities.Pig;
import com.frostbyte.entities.types.Entity;
import com.frostbyte.items.types.ItemDrop;
import com.frostbyte.player.Player;

public class World {
	private List<BufferedImage> breakImages = new ArrayList<BufferedImage>();
	private String name;
	private Block[][] blocks = new Block[4096][128];
	private List<Entity> entities = new ArrayList<Entity>();
	private Player player = new Player(this, 500, 1600);
	private List<ItemDrop> drops = new ArrayList<ItemDrop>();
	private WorldSaver worldSaver = new WorldSaver(this);
	private int saveTime = 0;
	PlayerCamera playerCamera;

	public World(String name) {
		entities.add(player);
		entities.add(new Gman(this, 500, 1600));
		entities.add(new Pig(this, 600, 1600));

		playerCamera = new PlayerCamera(this, player);

		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks[0].length; y++) {
				blocks[x][y] = new BlockAir(new Location(this, x, y));
			}
		}

		this.name = name;

		new WorldPopulator(this, WorldPopulator.OVERWORLD);
		worldSaver.loadWorld();
	}

	public void draw(Graphics graphics) {
		int renderDistance = 50;

		int startX = (int) (player.getX() / 20) - renderDistance;
		int finishX = (int) (player.getX() / 20) + renderDistance;
		int startY = (int) (player.getY() / 20) - renderDistance;
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

		if (finishY >= (blocks[0].length - 1)) {
			finishY = (blocks[0].length - 1);
		}

		for (int x = startX; x <= finishX; x++) {
			for (int y = startY; y <= finishY; y++) {
				if (blocks[x][y].getMaterial() != Material.AIR) {
					Block block = BlockHelper.getBlockType(blocks[x][y].getMaterial(), blocks[x][y].getLocation());
					block.checkUpdateState();
					graphics.drawImage(blocks[x][y].getImage(), (x * 20) - playerCamera.getX(), (y * 20) - playerCamera.getY(), null);

					Block blockLocation = getBlockAtLocation(new Location(this, (x * 20), (y * 20)));
					if (blockLocation.getDuration() < blockLocation.getMaxDuration()) {
						double durationPercent = ((double) blockLocation.getDuration() / (double) blockLocation.getMaxDuration()) * 100;
						int durationImage = 0;

						if (durationPercent <= 20) {
							durationImage = 4;
						} else if (durationPercent <= 40) {
							durationImage = 3;
						} else if (durationPercent <= 60) {
							durationImage = 2;
						} else if (durationPercent <= 80) {
							durationImage = 1;
						} else {
							durationImage = 0;
						}

						graphics.drawImage(getBreakImage(durationImage), (x * 20) - playerCamera.getX(), (y * 20) - playerCamera.getY(), null);
					}
				}
			}
		}

		if (!drops.isEmpty()) {
			for (ItemDrop itemDrop : drops) {
				itemDrop.draw(graphics);

				if (itemDrop.isRemoved()) {
					break;
				}
			}
		}

		for (Entity entity : entities) {
			entity.draw(graphics);
		}

		if (player.inventoryOpen()) {
			player.getInventory().draw(graphics);
		}

		if (player.getInventoryHotbar() != null) {
			player.getInventoryHotbar().draw(graphics);
		}

		if (player.getCraftingInventory() != null && player.isInventoryOpen()) {
			player.getCraftingInventory().draw(graphics);
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

		if (x >= (blocks.length)) {
			x = (blocks.length - 1);
		}

		if (y >= (blocks[0].length)) {
			y = (blocks[0].length - 1);
		}

		return blocks[x][y];
	}

	public void dropItem(ItemStack itemStack, Location location) {
		if (itemStack != null) {
			drops.add(new ItemDrop(itemStack, location));
		}
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

			if (!entities.contains(entity)) {
				break;
			}
		}

		if (!drops.isEmpty()) {
			for (ItemDrop itemDrop : drops) {
				itemDrop.update();

				if (itemDrop.isRemoved()) {
					break;
				}
			}
		}
		
		if(saveTime < 1500){
			saveTime++;
		}else{
			worldSaver.saveWorld();
			saveTime = 0;
		}

		playerCamera.updateMovement();
	}

	public BufferedImage getBreakImage(int i) {
		if (breakImages.isEmpty()) {
			try {
				breakImages.add(ImageIO.read(getClass().getResourceAsStream("/GUI/BLOCK_BREAK_1.png")));
				breakImages.add(ImageIO.read(getClass().getResourceAsStream("/GUI/BLOCK_BREAK_2.png")));
				breakImages.add(ImageIO.read(getClass().getResourceAsStream("/GUI/BLOCK_BREAK_3.png")));
				breakImages.add(ImageIO.read(getClass().getResourceAsStream("/GUI/BLOCK_BREAK_4.png")));
				breakImages.add(ImageIO.read(getClass().getResourceAsStream("/GUI/BLOCK_BREAK_5.png")));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return breakImages.get(i);
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

	public WorldSaver getWorldSaver() {
		return worldSaver;
	}

	public void setWorldSaver(WorldSaver worldSaver) {
		this.worldSaver = worldSaver;
	}
}
