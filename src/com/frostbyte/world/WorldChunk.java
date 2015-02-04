package com.frostbyte.world;

import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockAir;
import com.frostbyte.blocks.BlockStone;
import com.frostbyte.display.Location;

public class WorldChunk {
	private Block[][] blocks = new Block[40][40];
	World world;
	
	public WorldChunk(World world)	{
		this.world = world;
		loadChunk();
	}

	public void loadChunk(){
		for(int x = 0; x < blocks.length; x++){
			for(int y = 0; y < blocks[0].length; y++){
				if(y > 20){
					blocks[x][y] = new BlockAir(new Location(world, x, y));
				}else{
					blocks[x][y] = new BlockStone(new Location(world, x, y));
				}
			}
		}
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
}
