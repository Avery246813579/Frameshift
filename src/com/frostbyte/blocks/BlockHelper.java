package com.frostbyte.blocks;

import java.util.ArrayList;
import java.util.List;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockHelper {
	static List<Block> blocks = new ArrayList<Block>();
	
	public static Block getBlockType(Material material, Location location){
		if(blocks.isEmpty()){
			loadBlocks();
		}
		
		for(Block block : blocks){
			if(block.getMaterial() == material){
				block.setLocation(location);
				return block;
			}
		}
		
		return null;
	}
	
	public static Class<?> getBlockClass(Material material){
		if(blocks.isEmpty()){
			loadBlocks();
		}
		
		for(Block block : blocks){
			if(block.getMaterial() == material){
				return block.getClass();
			}
		}
		
		return null;
	}
	
	public static void loadBlocks(){
		blocks.add(new BlockAir(null));
		blocks.add(new BlockBedRock(null));
		blocks.add(new BlockDirt(null));
		blocks.add(new BlockGrass(null));
		blocks.add(new BlockStone(null));
		blocks.add(new BlockCoalOre(null));
		blocks.add(new BlockDiamondOre(null));
		blocks.add(new BlockEmeraldOre(null));
		blocks.add(new BlockGoldOre(null));
		blocks.add(new BlockIronOre(null));
		blocks.add(new BlockPyriteOre(null));
		blocks.add(new BlockQuartzOre(null));
		blocks.add(new BlockRubyOre(null));
		blocks.add(new BlockSapphireOre(null));
		blocks.add(new BlockTopazOre(null));
		blocks.add(new BlockOakLeave(null));
		blocks.add(new BlockOakWood(null));
	}
}
