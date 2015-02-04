package com.frostbyte.entities;

import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.display.Animation;
import com.frostbyte.entities.types.HostileEntity;
import com.frostbyte.util.MathUtil;
import com.frostbyte.world.World;

public class Gman extends HostileEntity {
	public Gman(World world, int x, int y) {
		super(world, x, y);

		this.animations = new ArrayList<Animation>(Arrays.asList(new Animation(new String[] { "/Sprites/Gman/GMAN_IDLE_1.png", "/Sprites/Gman/GMAN_IDLE_2.png" }, 2), new Animation(new String[] { "/Sprites/Gman/GMAN_WALK_1.png", "/Sprites/Gman/GMAN_WALK_2.png", "/Sprites/Gman/GMAN_WALK_3.png",
				"/Sprites/Gman/GMAN_WALK_4.png" }, 2), new Animation(new String[] { "/Sprites/Gman/GMAN_IDLE_1.png", "/Sprites/Gman/GMAN_IDLE_2.png" }, 2), new Animation(new String[] { "/Sprites/Gman/GMAN_IDLE_1.png", "/Sprites/Gman/GMAN_IDLE_2.png" }, 2), new Animation(new String[] {
				"/Sprites/Gman/GMAN_DEATH_1.png", "/Sprites/Gman/GMAN_DEATH_2.png", "/Sprites/Gman/GMAN_DEATH_3.png", "/Sprites/Gman/GMAN_DEATH_4.png" }, 2), new Animation(new String[] { "/Sprites/Gman/GMAN_DAMAGE.png" }, 0)));
		
		this.width = 40;
		this.height = 62;

		this.moveSpeed = 1;
		this.fallSpeed = 1;
		this.jumpSpeed = 1;

		this.maxSpeed = 1;
		this.maxFall = 1;
		this.maxJump = 1;

		this.maxJumpDistance = 20;

		this.health = this.maxHealth = this.hunger = 20;

		this.hostileRange = 400;

		this.armor = 3;
		this.damage = 2;
		this.maxDamageTick = 20;

		if (MathUtil.getRandomBoolean(50)) {
			this.isRight = true;
		} else {
			this.isLeft = true;
		}
	}
}
