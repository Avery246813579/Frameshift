package com.frostbyte.inventories;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Material;
import com.frostbyte.items.ItemRockAxe;
import com.frostbyte.items.ItemRockHammer;
import com.frostbyte.items.ItemRockPickaxe;
import com.frostbyte.items.ItemRockSpade;
import com.frostbyte.items.ItemStick;
import com.frostbyte.items.ItemStoneAxe;
import com.frostbyte.items.ItemStoneHammer;
import com.frostbyte.items.ItemStonePickaxe;
import com.frostbyte.items.ItemStoneSpade;
import com.frostbyte.main.GameFrame;
import com.frostbyte.player.Player;

public class CraftingInventory {
	private Player player;
	private String name;
	private ItemStack[] content;
	private boolean addingItem;
	private List<CraftingRecipe> recipes = new ArrayList<CraftingRecipe>();
	private List<ShapedCraftingRecipe> shapedRecipes = new ArrayList<ShapedCraftingRecipe>();

	/** Result Slot 617 | 269 **/
	public CraftingInventory(Player player, int size) {
		content = new ItemStack[size];
		this.player = player;

		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] { null, Material.STICK, null, null, Material.STICK, null, null, Material.STICK, null, Material.ROCK, Material.ROCK, Material.ROCK }, new ItemRockHammer()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] { null, Material.STICK, null, null, Material.STICK, null, Material.ROCK, Material.STICK, Material.ROCK, Material.ROCK, Material.ROCK, Material.ROCK}, new ItemRockPickaxe()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] {null, Material.STICK, null, null, Material.STICK, null, null, Material.STICK, null, null, Material.ROCK, null}, new ItemRockSpade()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] { null, Material.STICK, null, null, Material.STICK, null, null, Material.STICK, null, Material.STONE, Material.STONE, Material.STONE }, new ItemStoneHammer()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] { null, Material.STICK, null, null, Material.STICK, null, Material.STONE, Material.STICK, Material.STONE, Material.STONE, Material.STONE, Material.STONE}, new ItemStonePickaxe()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] {null, Material.STICK, null, null, Material.STICK, null, null, Material.STICK, null, null, Material.STONE, null}, new ItemStoneSpade()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] { null, Material.STICK, null, null, Material.STICK, null, Material.STONE, Material.STICK, null, Material.STONE, Material.STONE, Material.STONE}, new ItemStoneAxe()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] { null, Material.STICK, null, null, Material.STICK, null, Material.ROCK, Material.STICK, null, Material.ROCK, Material.ROCK, Material.ROCK}, new ItemRockAxe()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] {null, Material.OAK_WOOD, null, null, Material.OAK_WOOD, null, null, Material.OAK_WOOD, null, null, null, null}, new ItemStick()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] {null, null, null, null, Material.OAK_WOOD, null, null, Material.OAK_WOOD, null, null, Material.OAK_WOOD, null}, new ItemStick()));
		shapedRecipes.add(new ShapedCraftingRecipe(new Material[] {Material.STONE, Material.STONE, Material.STONE, Material.STONE, null, Material.STONE, Material.STONE, null, Material.STONE, Material.STONE, Material.STONE, Material.STONE}, new ItemStack(Material.STONE_FURNACE, 1)));
	}

	public void draw(Graphics g) {
		int startX = GameFrame.WIDTH / 2 + 134;
		int startY = GameFrame.HEIGHT / 3 + 92;

		int x = startX;
		int y = startY;

		for (int i = 0; i < content.length; i++) {
			ItemStack itemStack = content[i];
			if (itemStack != null) {
				g.drawImage(itemStack.getMaterial().getImage(), x, y, null);
			}

			if (i == 12) {
				x = 617;
				y = 269;
			} else if (i == 13) {
				x = 617;
				y = 269 - 23;
			} else {

				if (i <= 11) {
					x += 23;
				}

				if (i == 2 || i == 5 || i == 8) {
					y = y - 23;
					x = startX;
				}
			}
		}
	}

	public void update() {
		if (!checkCrafting()) {
			setItem(13, null);
		}

		if (!player.inventoryOpen() && hasItems()) {
			List<ItemStack> items = new ArrayList<ItemStack>();

			for (int i = 0; i < content.length; i++) {
				ItemStack itemStack = content[i];

				if (itemStack != null) {
					items.add(itemStack);
					remove(itemStack);
				}
			}

			for (ItemStack itemStack : items) {
				if (!player.getInventory().isFull()) {
					player.getInventory().addItem(itemStack);
				} else {
					player.getWorld().dropItem(itemStack, player.getLocation());
				}
			}
		}
	}

	public boolean checkCrafting() {
		if (hasItems() && player.inventoryOpen()) {
			for (CraftingRecipe craftingRecipe : recipes) {
				if (craftingRecipe.contains(this)) {
					setItem(13, craftingRecipe.result);
					return true;
				}
			}

			for (ShapedCraftingRecipe craftingRecipe : shapedRecipes) {
				if (craftingRecipe.contains(this)) {
					setItem(13, craftingRecipe.result);
					return true;
				}
			}
		}

		return false;
	}

	public void addItem(ItemStack itemStack) {
		if (addingItem == true) {
			addItem(itemStack);
			return;
		}

		addingItem = true;

		for (int i = 0; i < content.length; i++) {
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

	public int getInventorySlot(int rawX, int rawY) {
		int startX = GameFrame.WIDTH / 2 + 134;
		int startY = GameFrame.HEIGHT / 3 + 92;

		int x = startX;
		int y = startY;
		for (int i = 0; i < content.length; i++) {
			if (rawX > x && rawX < (x + 23)) {
				if (rawY > y && rawY < (y + 23)) {
					return i;
				}
			}

			if (i == 12) {
				x = 617;
				y = 269;
			} else if (i == 13) {
				x = 617;
				y = 269 - 23;
			} else {

				if (i <= 11) {
					x += 23;
				}

				if (i == 2 || i == 5 || i == 8) {
					y = y - 23;
					x = startX;
				}
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

	public boolean hasItems() {
		for (int i = 0; i < content.length; i++) {
			ItemStack itemStack = content[i];

			if (itemStack != null) {
				return true;
			}
		}

		return false;
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

	public List<CraftingRecipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<CraftingRecipe> recipes) {
		this.recipes = recipes;
	}

	public List<ShapedCraftingRecipe> getShapedRecipes() {
		return shapedRecipes;
	}

	public void setShapedRecipes(List<ShapedCraftingRecipe> shapedRecipes) {
		this.shapedRecipes = shapedRecipes;
	}
}
