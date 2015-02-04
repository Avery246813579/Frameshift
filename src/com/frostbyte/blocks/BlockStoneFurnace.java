package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockStoneFurnace extends Block{

	public BlockStoneFurnace(Location location) {
		super(location, Material.STONE_FURNACE, 250);
	}

	@Override
	protected void updateState() {
		
	}
}
