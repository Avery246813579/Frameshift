package com.frostbyte.blocks;

import java.awt.image.BufferedImage;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.util.MathUtil;

public abstract class Block {
	private Location location;
	private Material material;
	private int duration, maxDuration, dropChance;
	private ItemStack drop;
	protected boolean isSolid = true;

	public Block(Location location, Material material, int duration) {
		this.location = location;
		this.material = material;
		this.dropChance = 100;
		this.duration = this.maxDuration = duration;
		drop = new ItemStack(material, 1);
	}
	
	public Block(Location location, Material material, int duration, int dropChance) {
		this.location = location;
		this.material = material;
		this.duration = this.maxDuration = duration;
		this.dropChance = dropChance;
		drop = new ItemStack(material, 1);
	}

	protected abstract void updateState();

	public void checkUpdateState() {
		updateState();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
		this.maxDuration = BlockHelper.getBlockType(material).getMaxDuration();
		this.duration = maxDuration;
		drop = new ItemStack(BlockHelper.getBlockType(material, getLocation()).getDrop().getMaterial(), 1);
		dropChance = BlockHelper.getBlockType(material, getLocation()).getDropChance();
	}

	public BufferedImage getImage() {
		return material.getImage();
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public boolean isSolid() {
		return isSolid;
	}
	
	public ItemStack getDrop(){
		return drop;
	}
	
	public ItemStack getItemDrop(){
		if(MathUtil.getRandomBoolean(dropChance)){
			return drop;
		}
		
		return null;
	}


	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}

	public int getDropChance() {
		return dropChance;
	}

	public void setDropChance(int dropChance) {
		this.dropChance = dropChance;
	}
}
