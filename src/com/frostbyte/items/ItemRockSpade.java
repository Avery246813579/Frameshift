package com.frostbyte.items;

import com.frostbyte.display.Material;
import com.frostbyte.items.types.ItemSpade;

public class ItemRockSpade extends ItemSpade{
	public ItemRockSpade() {
		super(Material.ROCK_SPADE, 1);
		
		this.damageValue = 30;
	}
}
