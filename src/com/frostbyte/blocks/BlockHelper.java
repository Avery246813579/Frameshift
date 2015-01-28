package com.frostbyte.blocks;

import java.util.ArrayList;
import java.util.List;

import com.frostbyte.display.Material;

public class BlockHelper {
	static List<Block> blocks = new ArrayList<Block>();
	
	public static Block getBlockType(Material material){
		if(blocks.isEmpty()){
			loadBlocks();
		}
		
		for(Block block : blocks){
			if(block.getMaterial() == material){
				return block;
			}
		}
		
		return null;
	}
	
	public static void loadBlocks(){
		blocks.add(new BlockAir());
		blocks.add(new BlockBedRock());
		blocks.add(new BlockDirt());
		blocks.add(new BlockGrass());
		blocks.add(new BlockStone());
	}
}
