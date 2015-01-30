package com.frostbyte.entities;

import com.frostbyte.display.World;

public class LivingEntity extends Entity {
	protected boolean facingRight = true, isDead = false;
	protected int health, hunger, maxHealth;

	public static final int IDLE = 0;
	public static final int MOVING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;
	public static final int DEATH = 4;

	public LivingEntity(World world, int x, int y) {
		super(world, x, y);
	}

	public void damage(int amount) {
		health = health - amount;

		checkAlive();
	}

	public void checkAlive() {
		if (health > 0) {
			return;
		}

		this.isDead = true;
		this.setCurrentAnimation(DEATH);
	}
	
	public int getHealth(){
		return health;
	}
}
