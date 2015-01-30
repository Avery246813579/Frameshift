package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockOakWood extends Block{
	public BlockOakWood(Location location) {
		super(location, Material.OAK_WOOD);
		this.duration = 100;
		this.isSolid = false;
	}

	@Override
	public void updateState() {
		
	}
}
