package com.frostbyte.items;

import com.frostbyte.display.Material;
import com.frostbyte.items.types.ItemAxe;

public class ItemRockAxe extends ItemAxe{
	public ItemRockAxe() {
		super(Material.ROCK_AXE, 1);
		
		this.damageValue = 30;
	}
}
