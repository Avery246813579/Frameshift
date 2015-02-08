package com.frostbyte.menus;

import java.awt.Graphics;

import com.frostbyte.main.GameFrame;
import com.frostbyte.main.GameManager;

public class SaveMenu extends Menu{
	public SaveMenu(GameManager gameManager) {
		super(gameManager);
	}

	@Override
	public void draw(Graphics g) {
		g.drawString("Game is saving. Please wait!", GameFrame.WIDTH/2, GameFrame.HEIGHT/2);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void onMove(int button, int x, int y) {
		
	}

	@Override
	public void onKeyPressed(int x) {

	}

	@Override
	public void onMousePressed(int button, int x, int y) {
		
	}
}
