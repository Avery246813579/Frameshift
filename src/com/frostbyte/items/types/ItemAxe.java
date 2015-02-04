package com.frostbyte.items.types;

import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.display.Material;

public class ItemAxe extends ItemTool{
	public ItemAxe(Material material, int amount) {
		super(material, amount);

		this.breakList = new ArrayList<Material>(Arrays.asList(Material.OAK_WOOD));
	}
}
