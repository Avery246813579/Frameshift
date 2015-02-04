package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockIronOre extends Block{
	public BlockIronOre(Location location) {
		super(location, Material.IRON_ORE, 100);
	}
	
	@Override
	public void updateState() {
		
	}
}
