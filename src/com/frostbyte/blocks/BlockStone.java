package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockStone extends Block{
	public BlockStone(Location location) {
		super(location, Material.STONE, 100);
	}

	public BlockStone() {
		super(null, Material.STONE, 100);
	}
}