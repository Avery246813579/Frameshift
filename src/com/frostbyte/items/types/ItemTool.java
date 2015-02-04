package com.frostbyte.items.types;

import java.util.ArrayList;
import java.util.List;

import com.frostbyte.blocks.Block;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public abstract class ItemTool extends Item{
	protected List<Material> breakList = new ArrayList<Material>();
	protected int damageValue;
	
	public ItemTool(Material material, int amount) {
		super(material, amount);
	}

	@Override
	protected void itemInteraction(Location location, Location targetLocation) {
		Block breakBlock = location.getWorld().getBlockAtLocation(new Location(targetLocation.getWorld(), targetLocation.getX() + targetLocation.getWorld().getPlayerCamera().getX(), targetLocation.getY() + targetLocation.getWorld().getPlayerCamera().getY()));
		
		if(breakBlock.getMaterial() == Material.BEDROCK){
			return;
		}
		
		if(breakList.contains(breakBlock.getMaterial())){
			breakBlock.setDuration(breakBlock.getDuration() - damageValue);
		}else{
			breakBlock.setDuration(breakBlock.getDuration() - 2);
		}
		
		if(breakBlock.getDuration() <= 0){
			location.getWorld().dropItem(breakBlock.getItemDrop(), new Location(breakBlock.getLocation().getWorld(), (int) (breakBlock.getLocation().getX() * 20 + 5), (int) (breakBlock.getLocation().getY() * 20 + 5)));
			breakBlock.setMaterial(Material.AIR);
		}
	}
}
