package com.frostbyte.items;

import com.frostbyte.display.Material;
import com.frostbyte.items.types.ItemPickaxe;

public class ItemRockPickaxe extends ItemPickaxe{
	public ItemRockPickaxe() {
		super(Material.ROCK_PICKAXE, 1);
		
		this.damageValue = 30;
	}
}
