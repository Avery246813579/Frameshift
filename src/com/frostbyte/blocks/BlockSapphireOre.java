package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockSapphireOre extends Block{
	public BlockSapphireOre(Location location) {
		super(location, Material.SAPPHIRE_ORE);
		this.duration = 100;
	}
	
	@Override
	public void updateState() {
		
	}
}
