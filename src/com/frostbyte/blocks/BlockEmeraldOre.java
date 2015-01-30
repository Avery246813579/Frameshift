package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockEmeraldOre extends Block{
	public BlockEmeraldOre(Location location) {
		super(location, Material.EMERALD_ORE);
		this.duration = 100;
	}
	
	@Override
	public void updateState() {
		
	}
}
