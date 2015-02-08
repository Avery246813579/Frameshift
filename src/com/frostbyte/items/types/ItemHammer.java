package com.frostbyte.items.types;

import com.frostbyte.audio.Sound;
import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.util.SoundUtil;

public abstract class ItemHammer extends Item{
	public ItemHammer(Material material, int amount) {
		super(material, amount);
	}

	@Override
	protected void itemInteraction(Location location, Location targetLocation) {
		Block breakBlock = location.getWorld().getBlockAtLocation(new Location(targetLocation.getWorld(), targetLocation.getX() + targetLocation.getWorld().getPlayerCamera().getX(), targetLocation.getY() + targetLocation.getWorld().getPlayerCamera().getY()));
		breakBlock.setDuration(BlockHelper.getBlockType(breakBlock.getMaterial(), breakBlock.getLocation()).getMaxDuration());
		SoundUtil.playSound(Sound.HAMMER);
	}
}
