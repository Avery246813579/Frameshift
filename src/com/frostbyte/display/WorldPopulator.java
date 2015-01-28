package com.frostbyte.display;

import com.frostbyte.blocks.Block;
import com.frostbyte.util.MathUtil;

public class WorldPopulator {
	World world;
	int worldType;
	
	public static final int OVERWORLD = 0;
	

	public WorldPopulator(World world, int worldType) {
		this.world = world;
		this.worldType = worldType;
		
		initialLevels();
		initialGroundTerrian();
	}

	private void initialLevels() {
		for (int x = 0; x < world.getBlocks().length; x++) {
			world.getBlocks()[x][63].setMaterial(Material.BEDROCK);
		}
	}

	private void initialGroundTerrian() {
		for (int x = 0; x < world.getBlocks().length; x++) {

			if (MathUtil.getRandomBoolean(10)) {
				int tempX = x; 
				
				updateBlock(world.getBlocks()[tempX][25]);
				while(MathUtil.getRandomBoolean(75)){
					tempX++;
					if(tempX >= 128){
						return;
					}
					
					updateBlock(world.getBlocks()[tempX][25]);
				}
			}
		}
	}

	public void updateBlock(Block block) {
		block.setMaterial(Material.GRASS);
	}
}
