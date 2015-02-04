package com.frostbyte.items;

import com.frostbyte.display.Material;
import com.frostbyte.items.types.ItemAxe;

public class ItemStoneAxe extends ItemAxe{
	public ItemStoneAxe() {
		super(Material.STONE_AXE, 1);
		
		this.damageValue = 50;
	}
}
