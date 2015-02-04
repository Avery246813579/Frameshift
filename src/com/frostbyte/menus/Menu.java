package com.frostbyte.menus;

import java.awt.Graphics;

import com.frostbyte.main.GameManager;

public abstract class Menu {
	GameManager gameManager;
	
	public Menu(GameManager gameManager){
		this.gameManager = gameManager;
	}
	
	public abstract void draw(Graphics g);
	public abstract void update();
	public abstract void onMove(int button, int x, int y);
	public abstract void onKeyPressed(int x);
	public abstract void onMousePressed(int button, int x, int y);
}
