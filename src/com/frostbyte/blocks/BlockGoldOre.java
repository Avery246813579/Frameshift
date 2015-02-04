package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockGoldOre extends Block{
	public BlockGoldOre(Location location) {
		super(location, Material.GOLD_ORE, 100);
	}
	
	@Override
	public void updateState() {
		
	}
}
