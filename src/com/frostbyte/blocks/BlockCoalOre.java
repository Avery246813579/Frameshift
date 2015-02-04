package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockCoalOre extends Block{
	public BlockCoalOre(Location location) {
		super(location, Material.COAL_ORE, 100);
	}
	
	@Override
	public void updateState() {
		
	}
}
