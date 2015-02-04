package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockAir extends Block{
	public BlockAir(Location location) {
		super(location, Material.AIR, 100);
		this.isSolid = false;
	}
	
	@Override
	public void updateState() {
		
	}
}
