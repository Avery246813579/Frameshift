package com.frostbyte.display;

public class Location {
	private World world;
	private int x, y;
	
	public Location(World world, int x, int y){
		this.world = world;
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return world.getName() + "," + x + "," + y;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}