package com.frostbyte.display;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.frostbyte.main.GameFrame;
import com.frostbyte.player.Player;

public class Inventory {
	private Player player;
	private String name;
	private ItemStack[] content;
	private boolean addingItem;

	public Inventory(Player player, String name, int size) {
		this.name = name;
		content = new ItemStack[size];
		this.player = player;
	}

	public void draw(Graphics g) {
		try {
			g.drawImage(ImageIO.read(getClass().getResourceAsStream("/GUI/Inventory.png")), player.getWorld().getPlayerCamera().getX() + GameFrame.WIDTH / 3, player.getWorld().getPlayerCamera().getY() + GameFrame.HEIGHT / 3, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int items = 0;
		int x = player.getWorld().getPlayerCamera().getX() + GameFrame.WIDTH / 3 + 21;
		int y = player.getWorld().getPlayerCamera().getY() + GameFrame.HEIGHT / 3 + 95;
		for (ItemStack itemStack : content) {
			if (itemStack != null) {
				items++;
				g.drawImage(itemStack.getMaterial().getImage(), x, y, null);
				x += 23;
			}

			if (items == 10 || items == 20 || items == 30) {
				if (items == 10) {
					y = y - 5;
				}

				y = y - 23;
				x = player.getWorld().getPlayerCamera().getX() + GameFrame.WIDTH / 3 + 21;
			}
		}
	}

	public void addItem(ItemStack itemStack) {
		if (addingItem == true) {
			addItem(itemStack);
			return;
		}

		addingItem = true;

		for (int i = 0; i < 40; i++) {
			ItemStack is = content[i];
			if (is == null) {
				this.content[i] = itemStack;
				addingItem = false;
				return;
			}
		}

		addingItem = false;
	}

	public boolean contains(Material material) {
		for (ItemStack is : content) {
			if (is != null) {
				if (is.getMaterial() == material) {
					return true;
				}
			}
		}

		return false;
	}

	public void remove(Material material) {
		for (int i = 0; i < 40; i++) {
			ItemStack itemStack = content[i];

			if (itemStack != null) {
				if (itemStack.getMaterial() == material) {
					content[i] = null;
					return;
				}
			}
		}
	}

	public ItemStack itemClick(int rawX, int rawY) {
		int x = player.getWorld().getPlayerCamera().getX() + GameFrame.WIDTH / 3 + 21;
		int y = player.getWorld().getPlayerCamera().getY() + GameFrame.HEIGHT / 3 + 95;
		int items = 0;

		Rectangle2D playerRect = new Rectangle(rawX, rawY, 1, 1);
		for (ItemStack itemStack : content) {
			if (itemStack != null) {
				items++;

				Rectangle2D itemRect = new Rectangle(x, y, 20, 20);
				if (playerRect.intersects(itemRect)) {
					return itemStack;
				}

				x += 23;
			}

			if (items == 10 || items == 20 || items == 30) {
				if (items == 10) {
					y = y - 5;
				}

				y = y - 23;
				x = player.getWorld().getPlayerCamera().getX() + GameFrame.WIDTH / 3 + 21;
			}
		}

		return null;
	}

	public boolean isFull() {
		int i = 0;

		for (ItemStack itemStack : content) {
			if (itemStack != null) {
				i++;
			}
		}

		if (40 > i) {
			return false;
		} else {
			return true;
		}
	}

	public void clear() {
		int size = content.length;
		content = new ItemStack[size];
	}

	public ItemStack[] getContent() {
		return content;
	}

	public void setContent(ItemStack[] content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAddingItem() {
		return addingItem;
	}

	public void setAddingItem(boolean addingItem) {
		this.addingItem = addingItem;
	}
}
