package com.frostbyte.blocks;

import java.util.Random;

import com.frostbyte.display.ItemStack;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.util.MathUtil;

public class BlockOakAcorn extends Block {
	public BlockOakAcorn(Location location) {
		super(location, Material.OAK_ACORN, 50);
		this.isSolid = false;
	}

	@Override
	protected void updateState() {
		if (MathUtil.getRandomBoolean(.01)) {
			createTree(getLocation().getX(), getLocation().getY());
		}
		
		if(getLocation().getWorld().getBlocks()[getLocation().getX()][getLocation().getY() + 1].getMaterial() == Material.AIR){
			getLocation().getWorld().dropItem(new ItemStack(Material.OAK_ACORN, 1), new Location(getLocation().getWorld(), getLocation().getX() * 20, getLocation().getY() * 20));
			getLocation().getWorld().getBlocks()[getLocation().getX()][getLocation().getY()].setMaterial(Material.AIR);
		}
	}

	private void createTree(int rawX, int rawY) {
		int layerSize = new Random().nextInt(10) + 5;

		for (int y = rawY; y > rawY - layerSize; y--) {
			Block block = getLocation().getWorld().getBlocks()[rawX][y];
			block.setMaterial(Material.OAK_WOOD);
		}

		int yAmount = new Random().nextInt(5) + 3;
		int xAmount = new Random().nextInt(5) + 3;

		if (yAmount == 4) {
			yAmount = 5;
		}

		if (xAmount == 4) {
			xAmount = 5;
		}

		if (xAmount > 5) {
			xAmount = 5;
		}

		if (yAmount > 5) {
			yAmount = 5;
		}

		for (int y = rawY - layerSize; y < rawY + yAmount - layerSize; y++) {
			for (int x = rawX - (xAmount / 2); x < rawX + xAmount - (xAmount / 2); x++) {
				if (x >= getLocation().getWorld().getBlocks().length) {
					break;
				}

				if (y >= getLocation().getWorld().getBlocks()[0].length) {
					break;
				}

				Block block = getLocation().getWorld().getBlocks()[x][y];
				if (block.getMaterial() == Material.AIR) {
					block.setMaterial(Material.OAK_LEAVE);
				}
			}
		}
	}

}
