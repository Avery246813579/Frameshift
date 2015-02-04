package com.frostbyte.items.types;

import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.display.Material;

public abstract class ItemSpade extends ItemTool{
	public ItemSpade(Material material, int amount) {
		super(material, amount);
		
		breakList = new ArrayList<Material>(Arrays.asList(Material.DIRT, Material.GRASS));
	}
}
