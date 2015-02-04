package com.frostbyte.items.types;

import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.display.Material;

public abstract class ItemPickaxe extends ItemTool{
	public ItemPickaxe(Material material, int amount) {
		super(material, amount);
		
		 this.breakList = new ArrayList<Material>(Arrays.asList(Material.STONE, Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GOLD_ORE, Material.IRON_ORE, Material.PYRITE_ORE, Material.QUARTZ_ORE, Material.RUBY_ORE, Material.SAPPHIRE_ORE, Material.TOPAZ_ORE));
	}

}
