package com.frostbyte.inventories;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;

public class ShapedCraftingRecipe {
	Material[] requiredMaterials;
	ItemStack result;

	public ShapedCraftingRecipe(Material[] requiredMaterials, ItemStack result) {
		this.requiredMaterials = requiredMaterials;
		this.result = result;
	}

	public boolean contains(CraftingInventory craftingInventory) {
		ItemStack[] hasAlready = new ItemStack[requiredMaterials.length];

		for (int i = 0; i < requiredMaterials.length; i++) {
			ItemStack itemStack = craftingInventory.getContent()[i];
			Material shapedMaterial = requiredMaterials[i];

			if (itemStack != null) {
				if (itemStack.getMaterial() == shapedMaterial) {
					addItem(hasAlready, itemStack);
				} else {
					return false;
				}
			}
		}

		if (getArraySize(hasAlready) == getArraySize(requiredMaterials)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getArraySize(ItemStack[] array){
		int items = 0;
		
		for(int i = 0; i < array.length; i++){
			ItemStack itemStack = array[i];
			
			if(itemStack != null){
				items++;
			}
		}
		
		return items;
	}
	
	public int getArraySize(Material[] array){
		int items = 0;
		
		for(int i = 0; i < array.length; i++){
			Material material = array[i];
			
			if(material != null){
				items++;
			}
		}
		
		return items;
	}

	public void addItem(ItemStack[] items, ItemStack is) {
		for (int i = 0; i < items.length; i++) {
			ItemStack itemStack = items[i];

			if (itemStack == null) {
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
