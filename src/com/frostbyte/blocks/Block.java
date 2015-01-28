package com.frostbyte.blocks;

import java.awt.image.BufferedImage;

import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public abstract class Block {
	private Location location;
	private Material material;
	private int duration;

	public Block(Location location, Material material, int duration){
		this.location = location;
		this.material = material;
		this.duration = duration;
	}
	
	public Location getLocation() {
		return location;
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
}
