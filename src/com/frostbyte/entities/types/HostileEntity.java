package com.frostbyte.entities.types;

import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.world.World;

public abstract class HostileEntity extends LivingEntity {
	protected int hostileRange, maxDamageTick;
	private int damageTick;

	public HostileEntity(World world, int x, int y) {
		super(world, x, y);
	}

	public void update() {
		super.update();

		trackPlayer();
		damagePlayer();
	}

	public void damagePlayer() {
		if (damageDistance() && !isDead) {
			if(damageTick >= maxDamageTick){
				getWorld().getPlayer().damage(damage);
				damageTick = 0;
			}else{
				damageTick++;
			}
		}
	}

	public void trackPlayer() {
		if (playerIsNear()) {
			if (getX() - getWorld().getPlayer().getX() > 0) {
				if (getX() > getWorld().getPlayer().getX() && getX() < getWorld().getPlayer().getX() + width) {
					isLeft = false;
					isRight = false;
				} else {
					isLeft = true;
					isRight = false;
				}
			} else {
				if (getX() > (getWorld().getPlayer().getX() - width) && getX() < getWorld().getPlayer().getX()) {
					isRight = false;
					isLeft = false;
				} else {
					isRight = true;
					isLeft = false;
				}
			}
		} else {
			isLeft = false;
			isRight = false;
		}

		if (isLeft) {
			Block targetBlock = getLocation().getWorld().getBlocks()[(getX() - 20) / 20][(getY() + height) / 20];

			if (BlockHelper.getBlockType(targetBlock.getMaterial(), targetBlock.getLocation()).isSolid()) {
				Block aboveTarget = getLocation().getWorld().getBlocks()[(getX() - 20) / 20][(getY() + height - 20) / 20];

				if (!BlockHelper.getBlockType(aboveTarget.getMaterial(), aboveTarget.getLocation()).isSolid()) {
					isJumping = true;
				}
			}
		}

		if (isRight) {
			Block targetBlock = getLocation().getWorld().getBlocks()[(getX() + width + 20) / 20][(getY() + height) / 20];

			if (BlockHelper.getBlockType(targetBlock.getMaterial(), targetBlock.getLocation()).isSolid()) {
				Block aboveTarget = getLocation().getWorld().getBlocks()[(getX() + width + 20) / 20][(getY() + height - 20) / 20];

				if (!BlockHelper.getBlockType(aboveTarget.getMaterial(), aboveTarget.getLocation()).isSolid()) {
					isJumping = true;
				}
			}
		}
	}

	public boolean playerIsNear() {
		for (int x = getX() - (hostileRange / 2); x < getX() + (hostileRange / 2); x++) {
			for (int y = getY() - (hostileRange / 2); y < getY() + (hostileRange / 2); y++) {
				if (getWorld().getPlayer().getX() == x && getWorld().getPlayer().getY() == y) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean damageDistance() {
		for (int x = getX() - 50; x < getX() + 50; x++) {
			for (int y = getY() - (hostileRange / 2); y < getY() + (hostileRange / 2); y++) {
				if (getWorld().getPlayer().getX() == x && getWorld().getPlayer().getY() == y) {
					return true;
				}
			}
		}

		return false;
	}
}
