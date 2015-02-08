package com.frostbyte.display;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public enum Material {
	GRASS("GRASS", 1), 
	DIRT("DIRT", 2), 
	STONE("STONE", 3), 
	AIR("AIR", 4), 
	BEDROCK("BEDROCK", 5), 
	GUN("GUN", 6), 
	COAL_ORE("COAL_ORE", 7), 
	DIAMOND_ORE("DIAMOND_ORE", 8), 
	EMERALD_ORE("EMERALD_ORE", 9), 
	GOLD_ORE("GOLD_ORE", 10), 
	IRON_ORE("IRON_ORE", 11), 
	PYRITE_ORE("PYRITE_ORE", 12), 
	QUARTZ_ORE("QUARTZ_ORE", 13), 
	RUBY_ORE("RUBY_ORE", 14), 
	SAPPHIRE_ORE("SAPPHIRE_ORE", 15),
	TOPAZ_ORE("TOPAZ_ORE", 16),
	OAK_WOOD("OAK_WOOD", 17),
	OAK_LEAVE("OAK_LEAVE", 18),
	OAK_ACORN("OAK_ACORN", 19),
	STONE_HAMMER("STONE_HAMMER", 20),
	STONE_SPADE("STONE_SPADE", 21),
	STONE_PICKAXE("STONE_PICKAXE", 22),
	STONE_AXE("STONE_AXE", 23),
	STONE_FURNACE("STONE_FURNACE", 24),
	ROCK_HAMMER("ROCK_HAMMER", 25),
	ROCK_SPADE("ROCK_SPADE", 26),
	ROCK_PICKAXE("ROCK_PICKAXE", 27),
	ROCK_AXE("ROCK_AXE", 28),
	ROCK("ROCK", 29),
	STICK("STICK", 30);

	private BufferedImage image;
	private boolean block;
	private int drawInt;

	Material(String image, int drawInt) {
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/Blocks/" + image + ".png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.setDrawInt(drawInt);
	}
	
	public static Material fromString(String s){
		for(Material material : Material.values()){
			if(material.toString().equalsIgnoreCase(s)){
				return material;
			}
		}
		
		return null;
	}
	
	public static Material fromInt(int number){
		for(Material material : Material.values()){
			if(material.getDrawInt() == number){
				return material;
			}
		}
		
		return null;
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

	public int getDrawInt() {
		return drawInt;
	}

	public void setDrawInt(int drawInt) {
		this.drawInt = drawInt;
	}
}
