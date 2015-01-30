package com.frostbyte.blocks;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class BlockOakLeave extends Block{
	public BlockOakLeave(Location location){
		super(location, Material.OAK_LEAVE);
		this.duration = 100;
		this.isSolid = false;
	}

	@Override
	public void updateState() {
		
	}
}
