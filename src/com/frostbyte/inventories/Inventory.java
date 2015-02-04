package com.frostbyte.inventories;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;
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
		this.setPlayer(player);
	}

	public void draw(Graphics g) {
		try {
			g.drawImage(ImageIO.read(getClass().getResourceAsStream("/GUI/Inventory.png")), GameFrame.WIDTH / 3, GameFrame.HEIGHT / 3, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int x = GameFrame.WIDTH / 3 + 21;
		int y = GameFrame.HEIGHT / 3 + 95;
		for (int i = 0; i < content.length; i++) {
			ItemStack itemStack = content[i];
			if (itemStack != null) {
				g.drawImage(itemStack.getMaterial().getImage(), x, y, null);
			}

			x += 23;
			if (i == 9 || i == 19 || i == 29) {
				if (i == 9) {
					y = y - 5;
				}

				y = y - 23;
				x = GameFrame.WIDTH / 3 + 21;
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
		for (int i = 0; i < content.length; i++) {
			ItemStack is = content[i];
			
			if (is != null) {
				if (is.getMaterial() == material) {
					return true;
				}
			}
		}

		return false;
	}

	public void remove(ItemStack itemStack) {
		for (int i = 0; i < content.length; i++) {
			ItemStack is = content[i];

			if (itemStack == is) {
				content[i] = null;
				return;
			}
		}
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
		int x = GameFrame.WIDTH / 3 + 21;
		int y = GameFrame.HEIGHT / 3 + 95;
		int items = 0;

		Rectangle2D playerRect = new Rectangle(rawX, rawY, 1, 1);
		for (int i = 0; i < content.length; i++) {
			ItemStack itemStack = content[i];
			
			if (itemStack != null) {
				items++;

				Rectangle2D itemRect = new Rectangle(x, y, 20, 20);
				if (playerRect.intersects(itemRect)) {
					return itemStack;
				}

				x += 23;
			}

			if (items == 9 || items == 18 || items == 27) {
				if (items == 9) {
					y = y - 5;
				}

				y = y - 23;
				x = GameFrame.WIDTH / 3 + 21;
			}
		}

		return null;
	}

	public int getInventorySlot(int rawX, int rawY) {
		int x = GameFrame.WIDTH / 3 + 21;
		int y = GameFrame.HEIGHT / 3 + 95;

		for (int i = 0; i < content.length; i++) {
			if (rawX > x && rawX < (x + 23)) {
				if (rawY > y && rawY < (y + 23)) {
					return i;
				}
			}

			x += 23;

			if (i == 9 || i == 19 || i == 29) {
				if (i == 9) {
					y = y - 5;
				}

				y = y - 23;
				x = GameFrame.WIDTH / 3 + 21;
			}
		}

		return -1;
	}

	public void setItem(int x, ItemStack itemStack) {
		content[x] = itemStack;
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
