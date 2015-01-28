package com.frostbyte.display;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public enum Material {
	GRASS("GRASS"), DIRT("DIRT"), STONE("STONE"), AIR("AIR"), BEDROCK("BEDROCK"), GUN("GUN");
	
	private BufferedImage image;
	private boolean block;
	
	Material(String image){
		try{
			this.image = ImageIO.read(getClass().getResourceAsStream("/Blocks/" + image + ".png"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}
}
