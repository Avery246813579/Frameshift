package com.frostbyte.blocks;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.util.MathUtil;

public class BlockOakLeave extends Block {
	public BlockOakLeave(Location location) {
		super(location, Material.OAK_LEAVE, 100, 20);
		this.isSolid = false;
		
		setDrop(new ItemStack(Material.OAK_ACORN, 1));
	}

	@Override
	public void updateState() {
		boolean hasWood = false;

		for (int x = getLocation().getX() - 3; x < getLocation().getX() + 3; x++) {
			for (int y = getLocation().getY() - 5; y < getLocation().getY() + 5; y++) {
				if (getLocation().getWorld().getBlockAtLocation(new Location(getLocation().getWorld(), x * 20, y * 20)).getMaterial() == Material.OAK_WOOD) {
					hasWood = true;
				}
			}
		}

		if (!hasWood) {
			if (MathUtil.getRandomBoolean(0.2)) {
				getLocation().getWorld().getBlocks()[getLocation().getX()][getLocation().getY()].setMaterial(Material.AIR);
			
				if(MathUtil.getRandomBoolean(10)){
					getLocation().getWorld().dropItem(new ItemStack(Material.OAK_ACORN, 1), new Location(getLocation().getWorld(), getLocation().getX() * 20, getLocation().getY() * 20));
				}
			}
		}
	}
}
