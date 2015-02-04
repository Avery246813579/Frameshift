package com.frostbyte.items;

import com.frostbyte.display.Material;
import com.frostbyte.items.types.ItemSpade;

public class ItemStoneSpade extends ItemSpade{
	public ItemStoneSpade() {
		super(Material.STONE_SPADE, 1);
		
		this.damageValue = 50;
	}

}
