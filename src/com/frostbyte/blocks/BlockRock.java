package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockRock extends Block{
	public BlockRock(Location location) {
		super(location, Material.ROCK, 50);
		this.isSolid = false;
	}

	@Override
	protected void updateState() {
		
	}
}
