package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockPyriteOre extends Block{
	public BlockPyriteOre(Location location) {
		super(location, Material.PYRITE_ORE, 100);
	}
	
	@Override
	public void updateState() {
		
	}
}
