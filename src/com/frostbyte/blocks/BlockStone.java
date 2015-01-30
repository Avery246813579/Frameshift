package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockStone extends Block{
	public BlockStone(Location location) {
		super(location, Material.STONE);
		this.duration = 100;
	}

	@Override
	public void updateState() {
		
	}
}
