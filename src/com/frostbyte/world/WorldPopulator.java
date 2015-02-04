package com.frostbyte.world;

import java.util.Random;

import com.frostbyte.blocks.Block;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.util.MathUtil;

public class WorldPopulator {
	World world;
	int worldType;

	public static final int OVERWORLD = 0;

	public WorldPopulator(World world, int worldType) {
		this.world = world;
		this.worldType = worldType;

		initialGroundTerrian();
		generateOres();
		generateHills();
		createTrees();
		generateBedrock();
		spawnRocks();
	}

	private void initialGroundTerrian() {
		for (int x = 0; x < world.getBlocks().length; x++) {
			for (int y = (world.getBlocks()[0].length - 1); y > 0; y--) {
				int oppSide = world.getBlocks()[0].length - y;

				Block currentBlock = world.getBlocks()[x][y];
				if (oppSide == 1) {
					currentBlock.setMaterial(Material.BEDROCK);
				}

				if (oppSide > 1 && oppSide < 40) {
					currentBlock.setMaterial(Material.STONE);
				}

				if (oppSide == 40) {
					currentBlock.setMaterial(Material.DIRT);
				}

				if (oppSide == 41) {
					currentBlock.setMaterial(Material.DIRT);
				}
			}
		}
	}

	private void generateBedrock() {
		for (int x = 0; x < world.getBlocks().length; x++) {
			world.getBlocks()[x][world.getBlocks()[0].length - 1].setMaterial(Material.BEDROCK);
		}
	}

	private void generateOres() {
		for (int x = 0; x < world.getBlocks().length; x++) {
			for (int y = (world.getBlocks()[0].length - 1); y > 0; y--) {
				int oppSide = world.getBlocks()[0].length - y;

				if (worldType == OVERWORLD) {
					if (oppSide > 1 && oppSide < 13 && MathUtil.getRandomBoolean(.1)) {
						generateOreChunk(Material.PYRITE_ORE, x, y);
					}

					if (oppSide > 14 && oppSide < 27 && MathUtil.getRandomBoolean(.2)) {
						generateOreChunk(Material.IRON_ORE, x, y);
					}

					if (oppSide > 28 && oppSide < 39 && MathUtil.getRandomBoolean(.3)) {
						generateOreChunk(Material.QUARTZ_ORE, x, y);
					}

					if (oppSide > 1 && oppSide < 39 && MathUtil.getRandomBoolean(.1)) {
						generateOreChunk(Material.COAL_ORE, x, y);
					}
				}
			}
		}
	}

	public void generateOreChunk(Material material, int rawX, int rawY) {
		int amountY = new Random().nextInt(3) + 2;

		for (int y = rawY; y < rawY + amountY; y++) {
			int amountX = new Random().nextInt(3) + 2;

			for (int x = rawX; x < rawX + amountX; x++) {
				if (x >= world.getBlocks().length) {
					break;
				}

				if (y >= world.getBlocks()[0].length) {
					break;
				}

				world.getBlocks()[x][y].setMaterial(material);
			}
		}
	}

	private void generateHills() {
		for (int x = 0; x < world.getBlocks().length; x++) {
			for (int y = (world.getBlocks()[0].length - 1); y > 0; y--) {
				int oppSide = world.getBlocks()[0].length - y;

				if (oppSide == 42 && MathUtil.getRandomBoolean(3)) {
					randomHill(x, y);
				}
			}
		}
	}

	public void randomHill(int rawX, int y) {
		int hillLength = new Random().nextInt(25) + 10;

		if (rawX + hillLength > world.getBlocks().length) {
			hillLength = 0;
		}

		for (int x = rawX; x < rawX + hillLength; x++) {
			Block block = world.getBlocks()[x][y];

			block.setMaterial(Material.GRASS);
		}

		if (hillLength != 0) {
			addLayer(hillLength, rawX, y);
		}
	}

	public void addLayer(int lastLayer, int rawX, int rawY) {
		int layerSize = new Random().nextInt(lastLayer);

		if (rawX + layerSize > world.getBlocks().length) {
			layerSize = 0;
		}

		for (int x = (rawX + (lastLayer / 2) - (layerSize / 2)); x < rawX + (lastLayer / 2) - (layerSize / 2) + layerSize; x++) {
			Block block = world.getBlocks()[x][rawY - 1];

			block.setMaterial(Material.GRASS);
		}
	}

	private void createTrees() {
		int distanceBetween = 0;

		for (int x = 0; x < world.getBlocks().length; x++) {
			for (int y = (world.getBlocks()[0].length - 1); y > 0; y--) {
				int oppSide = world.getBlocks()[0].length - y;

				if (oppSide == 42) {
					distanceBetween++;
				}
				
				if (oppSide == 42 && MathUtil.getRandomBoolean(10) && world.getBlockAtLocation(new Location(world, x * 20, y * 20)).getMaterial() == Material.AIR) {
					if (distanceBetween >= 10) {
						distanceBetween = 0;
						createTree(x, y);
					}
				}
			}
		}
	}

	private void createTree(int rawX, int rawY) {
		int layerSize = new Random().nextInt(10) + 5;

		for (int y = rawY; y > rawY - layerSize; y--) {
			Block block = world.getBlocks()[rawX][y];
			block.setMaterial(Material.OAK_WOOD);
		}

		int yAmount = new Random().nextInt(5) + 3;
		int xAmount = new Random().nextInt(5) + 3;

		if (yAmount == 4) {
			yAmount = 5;
		}

		if (xAmount == 4) {
			xAmount = 5;
		}

		if (xAmount > 5) {
			xAmount = 5;
		}

		if (yAmount > 5) {
			yAmount = 5;
		}

		for (int y = rawY - layerSize; y < rawY + yAmount - layerSize; y++) {
			for (int x = rawX - (xAmount / 2); x < rawX + xAmount - (xAmount / 2); x++) {
				if (x >= world.getBlocks().length) {
					break;
				}

				if (y >= world.getBlocks()[0].length) {
					break;
				}

				Block block = world.getBlocks()[x][y];
				if (block.getMaterial() == Material.AIR) {
					block.setMaterial(Material.OAK_LEAVE);
				}
			}
		}
	}
	
	public void spawnRocks(){
		for(int x = 0; x < world.getBlocks().length; x++){
			for (int y = (world.getBlocks()[0].length - 1); y > 0; y--) {
				int oppSide = world.getBlocks()[0].length - y;
				
				if(oppSide == 42 && MathUtil.getRandomBoolean(5) && world.getBlockAtLocation(new Location(world, x * 20, y * 20)).getMaterial() == Material.AIR){
					world.getBlocks()[x][y].setMaterial(Material.ROCK);
				}
			}	
		}
	}

	public void updateBlock(Block block) {
		block.setMaterial(Material.GRASS);
	}
}
