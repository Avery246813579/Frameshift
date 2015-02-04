package com.frostbyte.items;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.items.types.Item;
import com.frostbyte.projectiles.ProjectileBullet;
import com.frostbyte.util.MathUtil;

public class ItemGun extends Item {
	public ItemGun(int amount) {
		super(Material.GUN, amount);
	}

	@Override
	protected void itemInteraction(Location location, Location targetLocation) {
		location.getWorld().getEntities().add(new ProjectileBullet(location, MathUtil.getSlope(location, targetLocation)));
	}
}
