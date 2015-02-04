package com.frostbyte.inventories;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;

public class CraftingRecipe {
	Material[] requiredMaterials;
	ItemStack result;

	public CraftingRecipe(Material[] requiredMaterials, ItemStack result) {
		this.requiredMaterials = requiredMaterials;
		this.result = result;
	}

	public boolean contains(CraftingInventory craftingInventory) {
		ItemStack[] hasAlready = new ItemStack[requiredMaterials.length];

		for (int i = 0; i < (craftingInventory.getContent().length - 1); i++) {
			ItemStack itemStack = craftingInventory.getContent()[i];

			if (itemStack != null) {
				if (contains(itemStack.getMaterial()) && !contains(hasAlready, itemStack.getMaterial())) {
					addItem(hasAlready, itemStack);
				} else {
					return false;
				}
			}
		}
		
		if(hasAlready.length == requiredMaterials.length){
			return true;
		}else{
			return false;
		}
	}

	public boolean contains(Material material) {
		for (int i = 0; i < requiredMaterials.length; i++) {
			Material m = requiredMaterials[i];

			if (m == material) {
				return true;
			}
		}

		return false;
	}

	public boolean contains(ItemStack[] items, Material material) {
		for (int i = 0; i < items.length; i++) {
			ItemStack itemStack = items[i];

			if (itemStack != null) {
				if (itemStack.getMaterial() == material) {
					return true;
				}
			}
		}

		return false;
	}

	public void addItem(ItemStack[] items, ItemStack is){
		for(int i = 0; i < items.length; i++){
			ItemStack itemStack = items[i];
			
			if(itemStack == null){
				items[i] = is;
				break;
			}
		}
	}
	
	public Material[] getRequiredMaterials() {
		return requiredMaterials;
	}

	public void setRequiredMaterials(Material[] requiredMaterials) {
		this.requiredMaterials = requiredMaterials;
	}

	public ItemStack getResult() {
		return result;
	}

	public void setResult(ItemStack result) {
		this.result = result;
	}

}
