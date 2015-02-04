package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockBedRock extends Block{
	public BlockBedRock(Location location) {
		super(location, Material.BEDROCK, 100);
	}

	@Override
	public void updateState() {
		
	}
}
