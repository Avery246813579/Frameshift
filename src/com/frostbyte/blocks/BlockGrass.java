package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockGrass extends Block {
	public BlockGrass(Location location) {
		super(location, Material.GRASS, 100);
	}

	public BlockGrass() {
		super(null, Material.GRASS, 100);
	}
}
