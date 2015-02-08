package com.frostbyte.entities.types;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.blocks.Block;
import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Vector;
import com.frostbyte.world.World;

public abstract class LivingEntity extends Entity {
	protected boolean facingRight = true, isDead = false;
	protected List<ItemStack> drops = new ArrayList<ItemStack>();
	protected boolean isJumping, isFalling, isRight, isLeft, isRunning, inJump, isTired;
	protected int moveSpeed, jumpSpeed, fallSpeed, maxSpeed, maxJump, maxFall, maxRun;
	protected int health, hunger, maxHealth, maxHunger, jumpDistance, maxJumpDistance, damage, deathWait, armor;
	protected double stamina, maxStamina;

	public static final int IDLE = 0;
	public static final int MOVING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;
	public static final int DEATH = 4;
	public static final int DAMAGE = 5;

	public LivingEntity(World world, int x, int y) {
		super(world, x, y);
	}

	public void update() {
		super.update();
		checkMovement();
		updateLocation();
		checkEntityCollisions();
	}

	public void checkMovement() {
		if (isDead) {
			isLeft = false;
			isRight = false;
			isJumping = false;
			isFalling = false;

			setVelocity(new Vector(0, 0));

			deathWait++;

			if (deathWait >= 50) {
				if (!drops.isEmpty()) {
					for (ItemStack itemStack : drops) {
						getWorld().dropItem(itemStack, getLocation());
					}
				}

				getWorld().getEntities().remove(this);
			}
		}

		if (isRight) {
			isLeft = false;

			if (getVelocity().getX() + moveSpeed >= maxSpeed) {
				if (!isRunning || isTired) {
					getVelocity().setX(maxSpeed);
				} else {
					stamina = stamina - 1;

					if (getVelocity().getX() + moveSpeed >= maxSpeed) {
						getVelocity().setX(maxRun);
					} else {
						getVelocity().setX(getVelocity().getX() + moveSpeed);
					}
				}
			} else {
				getVelocity().setX(getVelocity().getX() + moveSpeed);
			}

			setCurrentAnimation(MOVING);
			this.facingRight = true;
		}

		if (isLeft) {
			isRight = false;

			if (getVelocity().getX() - moveSpeed <= -maxSpeed) {
				if (!isRunning || isTired) {
					getVelocity().setX(-maxSpeed);
				} else {
					stamina = stamina - 1;

					if (getVelocity().getX() - moveSpeed <= -maxRun) {
						getVelocity().setX(-maxRun);
					} else {
						getVelocity().setX(getVelocity().getX() - moveSpeed);
					}
				}
			} else {
				getVelocity().setX(getVelocity().getX() - moveSpeed);
			}

			setCurrentAnimation(MOVING);
			this.facingRight = false;
		}

		if (isJumping && getVelocity().getY() <= 0 && !inJump) {
			isFalling = false;

			if (getVelocity().getY() - jumpSpeed <= maxJump) {
				getVelocity().setY(-maxJump);
			} else {
				getVelocity().setY(getVelocity().getY() - jumpSpeed);
			}

			if (jumpDistance > maxJumpDistance) {
				isJumping = false;
				jumpDistance = 0;
				inJump = true;
			} else {
				jumpDistance += -getVelocity().getY();
			}

			setCurrentAnimation(JUMPING);
		}

		if (isFalling) {
			if (!inJump) {
				inJump = true;
			}

			isJumping = false;

			if (jumpDistance != 0) {
				jumpDistance = 0;
			}

			if (getVelocity().getY() + fallSpeed >= maxFall) {
				getVelocity().setY(maxFall);
			} else {
				getVelocity().setY(getVelocity().getY() + fallSpeed);
			}
		}

		if (stamina <= 0) {
			isTired = true;
			isRunning = false;
		}

		if (!isLeft && !isRight && !isRunning && stamina < maxStamina && !isTired) {
			isTired = true;
		}

		if (isTired) {
			if (stamina >= maxStamina) {
				isTired = false;
			} else {
				stamina = stamina + 0.4;
			}
		}

		if (getX() - (width / 2) <= 0) {
			isLeft = false;
		}

		if (!isJumping && !BlockHelper.getBlockType(getWorld().getBlockAtLocation(new Location(getWorld(), getX() + (width / 2), getY() + height + getVelocity().getY())).getMaterial(), new Location(getWorld(), getX() + (width / 2), getY() + height + getVelocity().getY())).isSolid()) {
			isFalling = true;
		}

		int renderDistance = 1;

		int startX = (int) (getX() / 20) - renderDistance;
		int finishX = (int) ((getX() + width) / 20) + renderDistance;
		int startY = (int) (getY() / 20) - renderDistance;
		int finishY = (int) ((getY() + height) / 20) + renderDistance;

		if (startX < 0) {
			startX = 0;
		}

		if (startY < 0) {
			startY = 0;
		}

		if (finishX > (getWorld().getBlocks().length - 1)) {
			finishX = (getWorld().getBlocks().length - 1);
		}

		if (finishY >= (getWorld().getBlocks()[0].length - 1)) {
			finishY = (getWorld().getBlocks()[0].length - 1);
		}

		if (isJumping) {
			for (int x = (startX + 1); x <= (finishX - 1); x++) {
				Block block = getWorld().getBlocks()[x][(getY() - 5) / 20];

				if (BlockHelper.getBlockType(block.getMaterial(), block.getLocation()).isSolid()) {
					isJumping = false;
				}
			}
		}

		if (isFalling) {
			for (int x = (startX + 1); x <= (finishX - 1); x++) {
				Block block = getWorld().getBlocks()[x][(getY() + height + 5) / 20];

				if (BlockHelper.getBlockType(block.getMaterial(), block.getLocation()).isSolid()) {
					isFalling = false;
					inJump = false;
				}
			}
		}

		if (isRight) {
			for (int y = (startY + 2); y <= (finishY - 1); y++) {
				Block block = getWorld().getBlocks()[(getX() + width + 5) / 20][y];

				if (BlockHelper.getBlockType(block.getMaterial(), block.getLocation()).isSolid()) {
					isRight = false;
				}
			}
		}

		if (isLeft) {
			for (int y = (startY + 2); y <= (finishY - 1); y++) {
				Block block = getWorld().getBlocks()[(getX() - 5) / 20][y];

				if (BlockHelper.getBlockType(block.getMaterial(), block.getLocation()).isSolid()) {
					isLeft = false;
				}
			}
		}

		if (!isFalling && !isJumping) {
			getVelocity().setY(0);
		}

		if (!isRight && !isLeft && !isDead) {
			getVelocity().setX(0);
			setCurrentAnimation(IDLE);
		}

		super.checkMovement();
	}

	public void updateLocation() {
		super.updateLocation();
	}

	public List<Entity> checkEntityCollisions() {
		return super.checkEntityCollisions();
	}

	@Override
	public void draw(Graphics g) {
		if (facingRight) {
			g.drawImage(animations.get(getCurrentAnimation()).getAnimation(), getX() - getWorld().getPlayerCamera().getX(), getY() - getWorld().getPlayerCamera().getY(), null);
		} else {
			g.drawImage(animations.get(getCurrentAnimation()).getAnimation(), getX() + width - getWorld().getPlayerCamera().getX(), getY() - getWorld().getPlayerCamera().getY(), -getCurrentFrame().getAnimation().getWidth(), getCurrentFrame().getAnimation().getHeight(), null);
		}
	}

	public void damage(int amount) {
		int damage = amount - armor;

		if (damage < 0) {
			damage = 0;
		}

		health = health - damage;

		setCurrentAnimation(LivingEntity.DAMAGE);
		checkAlive();
	}

	public void checkAlive() {
		if (health > 0) {
			return;
		}

		this.isDead = true;
		this.setCurrentAnimation(DEATH);
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public void setMaxHunger(int maxHunger) {
		this.maxHunger = maxHunger;
	}

	public double getStamina() {
		return stamina;
	}

	public void setStamina(double stamina) {
		this.stamina = stamina;
	}

	public double getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(double maxStamina) {
		this.maxStamina = maxStamina;
	}
}
