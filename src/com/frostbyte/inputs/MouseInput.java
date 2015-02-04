package com.frostbyte.inputs;

import com.frostbyte.blocks.Block;
import com.frostbyte.display.ItemStack;
import com.frostbyte.player.Player;

public class MouseInput {
	private int x, y, inventoryLocation;
	private InputType inputType;
	private ItemStack itemStack;
	private Player player;
	private Block block;
	
	public MouseInput(Player player, Block block, InputType inputType, int x, int y){
		this.player = player;
		this.inputType = inputType;
		this.block = block;
		this.x = x;
		this.y = y;
	}
	
	public MouseInput(Player player, ItemStack itemStack, int inventoryLocation, InputType inputType, int x, int y){
		this.player = player;
		this.inventoryLocation = inventoryLocation;
		this.inputType = inputType;
		this.itemStack = itemStack;
		this.x = x;
		this.y = y;
	}
	
	public MouseInput(Player player, ItemStack itemStack, InputType inputType, int x, int y){
		this.player = player;
		this.inputType = inputType;
		this.itemStack = itemStack;
		this.x = x;
		this.y = y;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public int getInventoryLocation() {
		return inventoryLocation;
	}

	public void setInventoryLocation(int inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
}
