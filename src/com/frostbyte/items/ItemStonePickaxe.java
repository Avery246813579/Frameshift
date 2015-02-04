package com.frostbyte.items;

import com.frostbyte.display.Material;
import com.frostbyte.items.types.ItemPickaxe;

public class ItemStonePickaxe extends ItemPickaxe{
	public ItemStonePickaxe() {
		super(Material.STONE_PICKAXE, 1);
		
		this.damageValue = 50;
	}
}
