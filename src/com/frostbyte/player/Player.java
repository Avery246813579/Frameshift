package com.frostbyte.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frostbyte.display.Animation;
import com.frostbyte.display.Inventory;
import com.frostbyte.display.InventoryHotbar;
import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.display.World;
import com.frostbyte.entities.Entity;
import com.frostbyte.items.ItemDrop;

public class Player extends Entity {
	private boolean isLeft, isRight, isJumping, isFalling;
	private boolean facingRight = true, inJump;
	private int maxJumpDistance, jumpDistance;
	private static final int WAITING = 0;
	private static final int MOVING = 1;
	private static final int JUMPING = 2;
	private ItemStack itemInHand;
	private Inventory inventory;
	private Gamemode gamemode = Gamemode.Creative;
	boolean inventoryOpen;
	InventoryHotbar inventoryHotbar;

	public Player(World world, int x, int y) {
		super(world, x, y);
		this.animations = new ArrayList<Animation>(Arrays.asList(new Animation(new String[] { "/Sprites/Player/PLAYER_1.png", "/Sprites/Player/PLAYER_2.png", "/Sprites/Player/PLAYER_3.png" }, 5), new Animation(new String[] { "/Sprites/Player/PLAYER_4.png", "/Sprites/Player/PLAYER_5.png",
				"/Sprites/Player/PLAYER_6.png" }, 10), new Animation(new String[] { "/Sprites/Player/PLAYER_7.png" }, 0)));

		this.height = 59;
		this.width = 35;

		this.moveSpeed = 2;
		this.fallSpeed = 2;
		this.jumpSpeed = 2;

		this.maxSpeed = 6;
		this.maxJump = 6;
		this.maxFall = 6;

		this.maxJumpDistance = 20;

		this.inventory = new Inventory(this, "Test", 40);
		this.inventoryHotbar = new InventoryHotbar(this);
		/**
		 * this.inventory.addItem(new ItemStack(Material.STONE, 1));
		 * this.inventory.addItem(new ItemStack(Material.DIRT, 1));
		 * this.inventory.addItem(new ItemStack(Material.GRASS, 1));
		 * this.inventory.addItem(new ItemGun(1)); this.itemInHand = new
		 * ItemStack(Material.STONE, 1);
		 **/
	}

	public void update() {
		super.update();
		checkMovement();
		updateLocation();
		checkEntityCollisions();
	}

	public void checkMovement() {
		if (isRight) {
			isLeft = false;

			if (getVelocity().getX() + moveSpeed >= maxSpeed) {
				getVelocity().setX(maxSpeed);
			} else {
				getVelocity().setX(getVelocity().getX() + moveSpeed);
			}

			setCurrentAnimation(MOVING);
			this.facingRight = true;
		}

		if (isLeft) {
			isRight = false;

			if (getVelocity().getX() - moveSpeed <= -maxSpeed) {
				getVelocity().setX(-maxSpeed);
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

		if (!isJumping && getWorld().getBlockAtLocation(new Location(getWorld(), getX() + (width / 2), getY() + height + getVelocity().getY())).getMaterial() == Material.AIR) {
			isFalling = true;
		}

		if (isJumping) {
			if (checkCollisionPoint(getX(), getY() + getVelocity().getY()) || checkCollisionPoint(getX() + (width / 2), getY() + getVelocity().getY()) || checkCollisionPoint(getX() + width, getY() + getVelocity().getY())) {
				isJumping = false;
			}
		}

		if (isFalling) {
			if (checkCollisionPoint(getX(), getY() + height + getVelocity().getY()) || checkCollisionPoint(getX() + (width / 2), getY() + height + getVelocity().getY()) || checkCollisionPoint(getX() + width, getY() + height + getVelocity().getY())) {
				isFalling = false;
				inJump = false;
			}
		}

		if (isRight) {
			if (checkCollisionPoint(getX() + width + getVelocity().getX(), getY()) || checkCollisionPoint(getX() + width + getVelocity().getX(), getY() + (height / 2)) || checkCollisionPoint(getX() + width + getVelocity().getX(), getY() + (height / 3))
					|| checkCollisionPoint(getX() + width + getVelocity().getX(), getY() + height)) {
				isRight = false;
			}
		}

		if (isLeft) {
			if (checkCollisionPoint(getX() + getVelocity().getX(), getY()) || checkCollisionPoint(getX() + getVelocity().getX(), getY() + (height / 2)) || checkCollisionPoint(getX() + getVelocity().getX(), getY() + (height / 3)) || checkCollisionPoint(getX() + getVelocity().getX(), getY() + height)) {
				isLeft = false;
			}
		}

		if (!isFalling && !isJumping) {
			getVelocity().setY(0);
		}

		if (!isRight && !isLeft) {
			getVelocity().setX(0);
			setCurrentAnimation(WAITING);
		}

		if (!getWorld().getDrops().isEmpty()) {
			Rectangle2D playerRect = new Rectangle(getX(), getY(), width, height);
			for (ItemDrop itemDrop : getWorld().getDrops()) {
				Rectangle2D itemRect = new Rectangle(itemDrop.getLocation().getX(), itemDrop.getLocation().getY(), 10, 10);

				if (itemRect.intersects(playerRect) && !getInventory().isAddingItem()) {
					if (!inventory.isFull()) {
						getInventory().addItem(itemDrop.getItemStack());
						itemDrop.remove();
						break;
					}
				}
			}
		}

		super.checkMovement();
	}

	public boolean checkCollisionPoint(int x, int y) {
		if (getWorld().getBlockAtLocation(new Location(getWorld(), x, y)).getMaterial() != Material.AIR) {
			return true;
		}

		return false;
	}

	public void openInventory() {
		inventoryOpen = true;
	}

	public void closeInventory() {
		inventoryOpen = false;
	}

	public boolean inventoryOpen() {
		return inventoryOpen;
	}

	@Override
	public void draw(Graphics g) {
		if (facingRight) {
			g.drawImage(animations.get(getCurrentAnimation()).getAnimation(), getX(), getY(), null);
		} else {
			g.drawImage(animations.get(getCurrentAnimation()).getAnimation(), getX() + width, getY(), -getCurrentFrame().getAnimation().getWidth(), getCurrentFrame().getAnimation().getHeight(), null);
		}
		
		if (inventoryOpen) {
			inventory.draw(g);
		}

		if (inventoryHotbar != null) {
			inventoryHotbar.draw(g);
		}
	}

	public void updateLocation() {
		super.updateLocation();
	}

	public List<Entity> checkEntityCollisions() {
		return super.checkEntityCollisions();
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}

	public ItemStack getItemInHand() {
		return itemInHand;
	}

	public void setItemInHand(ItemStack itemInHand) {
		this.itemInHand = itemInHand;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Gamemode getGamemode() {
		return gamemode;
	}

	public void setGamemode(Gamemode gamemode) {
		this.gamemode = gamemode;
	}
}
