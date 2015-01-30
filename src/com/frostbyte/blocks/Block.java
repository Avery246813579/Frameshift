package com.frostbyte.blocks;

import java.awt.image.BufferedImage;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public abstract class Block {
	private Location location;
	private Material material;
	protected int duration;
	protected boolean isSolid = true;

	public Block(Location location, Material material){
		this.location = location;
		this.material = material;
	}
	
	protected abstract void updateState();
	public void checkUpdateState(){
		if(material == Material.GRASS || material == Material.DIRT){
			updateState();
		}
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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
	
	public boolean isSolid(){
		return isSolid;
	}
}
