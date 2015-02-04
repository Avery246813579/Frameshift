package com.frostbyte.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frostbyte.display.Animation;
import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;
import com.frostbyte.entities.types.Entity;
import com.frostbyte.entities.types.LivingEntity;
import com.frostbyte.inventories.CraftingInventory;
import com.frostbyte.inventories.Inventory;
import com.frostbyte.inventories.InventoryHotbar;
import com.frostbyte.items.types.ItemDrop;
import com.frostbyte.world.World;

public class Player extends LivingEntity {
	private ItemStack itemInHand;
	private Inventory inventory;
	CraftingInventory craftingInventory;
	private Gamemode gamemode = Gamemode.SURVIVAL;
	boolean inventoryOpen;
	InventoryHotbar inventoryHotbar;

	public Player(World world, int x, int y) {
		super(world, x, y);
		this.animations = new ArrayList<Animation>(Arrays.asList(new Animation(new String[] { "/Sprites/Player/PLAYER_1.png", "/Sprites/Player/PLAYER_2.png", "/Sprites/Player/PLAYER_3.png" }, 5), new Animation(new String[] { "/Sprites/Player/PLAYER_4.png", "/Sprites/Player/PLAYER_5.png",
				"/Sprites/Player/PLAYER_6.png" }, 10), new Animation(new String[] { "/Sprites/Player/PLAYER_7.png" }, 0), new Animation(new String[] { "/Sprites/Player/PLAYER_7.png" }, 0), new Animation(new String[] { "/Sprites/Player/PLAYER_DEATH_1.png", "/Sprites/Player/PLAYER_DEATH_2.png",
				"/Sprites/Player/PLAYER_DEATH_3.png" }, 3), new Animation(new String[] { "/Sprites/Player/PLAYER_DAMAGE.png" }, 1)));

		this.height = 59;
		this.width = 35;

		this.moveSpeed = 1;
		this.fallSpeed = 1;
		this.jumpSpeed = 1;

		this.maxRun = 3;
		this.maxSpeed = 2;
		this.maxJump = 3;
		this.maxFall = 2;

		this.maxJumpDistance = 20;

		this.inventory = new Inventory(this, "Test", 40);
		this.inventoryHotbar = new InventoryHotbar(this);
		this.itemInHand = new ItemStack(Material.STONE, 1);

		this.craftingInventory = new CraftingInventory(this, 14);

		this.health = this.hunger = this.maxHealth = 20;
		this.stamina = this.maxStamina = 100;
		this.damage = 5;
	}

	public void update() {
		super.update();
		checkMovement();
		updateLocation();
		checkEntityCollisions();
		craftingInventory.update();
	}

	public void checkMovement() {
		Rectangle2D playerRect = new Rectangle(getX(), getY(), width, height);

		if (!getWorld().getDrops().isEmpty()) {
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
		super.draw(g);
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

	public CraftingInventory getCraftingInventory() {
		return craftingInventory;
	}

	public boolean isInventoryOpen() {
		return inventoryOpen;
	}

	public void setInventoryOpen(boolean inventoryOpen) {
		this.inventoryOpen = inventoryOpen;
	}

	public InventoryHotbar getInventoryHotbar() {
		return inventoryHotbar;
	}

	public void setInventoryHotbar(InventoryHotbar inventoryHotbar) {
		this.inventoryHotbar = inventoryHotbar;
	}

	public void setCraftingInventory(CraftingInventory craftingInventory) {
		this.craftingInventory = craftingInventory;
	}
}
