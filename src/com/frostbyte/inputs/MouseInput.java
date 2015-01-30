package com.frostbyte.inputs;

import com.frostbyte.blocks.Block;
import com.frostbyte.player.Player;

public class MouseInput {
	private Player player;
	private Block block;
	private int x, y;
	
	public MouseInput(Player player, Block block, int x, int y){
		this.player = player;
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
}
