package com.frostbyte.entities.types;

import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.util.MathUtil;
import com.frostbyte.world.World;

public class NeutralEntity extends LivingEntity {
	public NeutralEntity(World world, int x, int y) {
		super(world, x, y);
	}

	public void update() {
		super.update();
		updateMovement();
	}

	public void updateMovement() {
		if (!isRight && !isLeft) {
			if (MathUtil.getRandomBoolean(1)) {
				if (MathUtil.getRandomBoolean(50)) {
					isRight = true;
					isLeft = false;
				} else {
					isLeft = true;
					isRight = false;
				}
			}
		} else {
			if (isLeft) {
				Block targetBlock = getLocation().getWorld().getBlocks()[(getX() - 20) / 20][(getY() + height) / 20];

				if (BlockHelper.getBlockType(targetBlock.getMaterial(), targetBlock.getLocation()).isSolid()) {
					Block aboveTarget = getLocation().getWorld().getBlocks()[(getX() - 20) / 20][(getY() + height - 20) / 20];

					if (!BlockHelper.getBlockType(aboveTarget.getMaterial(), aboveTarget.getLocation()).isSolid()) {
						isLeft = true;
						isJumping = true;
					}
				}
			}

			if (isRight) {
				Block targetBlock = getLocation().getWorld().getBlocks()[(getX() + width + 20) / 20][(getY() + height) / 20];

				if (BlockHelper.getBlockType(targetBlock.getMaterial(), targetBlock.getLocation()).isSolid()) {
					Block aboveTarget = getLocation().getWorld().getBlocks()[(getX() + width + 20) / 20][(getY() + height - 20) / 20];

					if (!BlockHelper.getBlockType(aboveTarget.getMaterial(), aboveTarget.getLocation()).isSolid()) {
						isRight = true;
						isJumping = true;
					}
				}
			}

			if (MathUtil.getRandomBoolean(5)) {
				if (isRight) {
					isRight = false;
				}

				if (isLeft) {
					isLeft = false;
				}
			}
		}
	}
}
