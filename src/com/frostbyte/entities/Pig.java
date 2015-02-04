package com.frostbyte.entities;

import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;
import com.frostbyte.entities.types.NeutralEntity;
import com.frostbyte.world.World;
import com.frostbyte.display.Animation;

public class Pig extends NeutralEntity {
	public Pig(World world, int x, int y) {
		super(world, x, y);

		this.animations = new ArrayList<Animation>(Arrays.asList(new Animation(new String[] { "/Sprites/Pig/PIG_IDLE.png" }, 2), new Animation(new String[] { "/Sprites/Pig/PIG_WALK_1.png", "/Sprites/Pig/PIG_WALK_2.png", "/Sprites/Pig/PIG_WALK_3.png" }, 2), new Animation(
				new String[] { "/Sprites/Pig/PIG_IDLE.png" }, 2), new Animation(new String[] { "/Sprites/Pig/PIG_IDLE.png" }, 2), new Animation(new String[] { "/Sprites/Pig/PIG_DEATH_1.png", "/Sprites/Pig/PIG_DEATH_2.png", "/Sprites/Pig/PIG_DEATH_3.png", "/Sprites/Pig/PIG_DEATH_4.png" }, 3),
				new Animation(new String[] { "/Sprites/Pig/PIG_DAMAGE.png" }, 2)));
		this.drops = new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Material.BEDROCK, 1)));

		this.width = 48;
		this.height = 54;

		this.moveSpeed = 1;
		this.fallSpeed = 1;
		this.jumpSpeed = 1;

		this.maxSpeed = 2;
		this.maxFall = 2;
		this.maxJump = 2;

		this.maxJumpDistance = 20;
		this.deathWait = 30;

		this.health = this.maxHealth = this.hunger = 20;
	}
}
