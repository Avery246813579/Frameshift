package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockDirt extends Block{
	public BlockDirt(Location location) {
		super(location, Material.DIRT, 100);
	}
	
	public BlockDirt() {
		super(null, Material.DIRT, 100);
	}
}
