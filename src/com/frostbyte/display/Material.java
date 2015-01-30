package com.frostbyte.display;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public enum Material {
	GRASS("GRASS"), 
	DIRT("DIRT"), 
	STONE("STONE"), 
	AIR("AIR"), 
	BEDROCK("BEDROCK"), 
	GUN("GUN"), 
	COAL_ORE("COAL_ORE"), 
	DIAMOND_ORE("DIAMOND_ORE"), 
	EMERALD_ORE("EMERALD_ORE"), 
	GOLD_ORE("GOLD_ORE"), 
	IRON_ORE("IRON_ORE"), 
	PYRITE_ORE("PYRITE_ORE"), 
	QUARTZ_ORE("QUARTZ_ORE"), 
	RUBY_ORE("RUBY_ORE"), 
	SAPPHIRE_ORE("SAPPHIRE_ORE"),
	TOPAZ_ORE("TOPAZ_ORE"),
	OAK_WOOD("OAK_WOOD"),
	OAK_LEAVE("OAK_LEAVE");

	private BufferedImage image;
	private boolean block;

	Material(String image) {
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/Blocks/" + image + ".png"));
		} catch (Exception ex) {
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
