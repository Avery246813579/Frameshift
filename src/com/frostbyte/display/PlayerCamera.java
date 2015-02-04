package com.frostbyte.display;

import com.frostbyte.entities.types.Entity;
import com.frostbyte.main.GameFrame;
import com.frostbyte.world.World;

public class PlayerCamera {
	private World world;
	private Entity entity;
	private int x, y;

	public PlayerCamera(World world, Entity entity) {
		this.entity = entity;
		this.world = world;

		x = 0;
		y = 0;
	}

	public void updateMovement() {
		if (GameFrame.WIDTH / 2 <= entity.getX() && (world.getBlocks().length * 20) > entity.getX() + (GameFrame.WIDTH / 2)) {
			x = entity.getX() - GameFrame.WIDTH / 2;
		}

		if (GameFrame.HEIGHT / 2 < entity.getY() && (world.getBlocks()[0].length * 20) >= entity.getY() + (GameFrame.HEIGHT / 2)) {
			y = entity.getY() - (GameFrame.HEIGHT / 2);
		} 
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
