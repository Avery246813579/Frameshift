package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockDiamondOre extends Block{
	public BlockDiamondOre(Location location) {
		super(location, Material.DIAMOND_ORE);
		this.duration = 100;
	}
	
	@Override
	public void updateState() {
		
	}
}
