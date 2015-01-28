package com.frostbyte.items;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public abstract class Item extends ItemStack{
	public Item(Material material, int amount){
		super(material, amount);
	}
	
	public void interact(Location location, Location targetLocation){
		itemInteraction(location, targetLocation);
	}
	
	protected abstract void itemInteraction(Location location, Location targetLocation);
}
