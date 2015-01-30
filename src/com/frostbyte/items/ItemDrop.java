package com.frostbyte.items;

import java.awt.Graphics;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;

public class ItemDrop {
	private ItemStack itemStack;
	private Location location;
	private int moveSpeed;
	int timeDropped;
	boolean removed;

	public ItemDrop(ItemStack itemStack, Location location) {
		this.itemStack = itemStack;
		this.location = location;
	}

	public void draw(Graphics g) {
		if (removed) {
			return;
		}

		g.drawImage(itemStack.getMaterial().getImage(), location.getX() - location.getWorld().getPlayerCamera().getX(), location.getY() - location.getWorld().getPlayerCamera().getY(), 10, 10, null);
	}

	public void update() {
		if (removed) {
			return;
		}

		if (location.getWorld().getBlockAtLocation(new Location(location.getWorld(), location.getX(), location.getY() + 11)).getMaterial() == Material.AIR) {
			if (location.getWorld().getBlockAtLocation(new Location(location.getWorld(), location.getX(), location.getY() + moveSpeed)).getMaterial() != Material.AIR) {
				moveSpeed = 0;
				return;
			}

			location.setY(location.getY() + moveSpeed);

			if (moveSpeed < 10) {
				moveSpeed++;
			}
		} else {
			if (moveSpeed != 0) {
				moveSpeed = 0;
			}
		}

		if (timeDropped < 6000) {
			timeDropped++;
			return;
		}

		remove();
	}

	public void remove() {
		removed = true;
		location.getWorld().getDrops().remove(this);
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
