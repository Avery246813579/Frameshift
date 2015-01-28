package com.frostbyte.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.commands.CommandManager;
import com.frostbyte.display.World;
import com.frostbyte.inputs.InputHandler;
import com.frostbyte.listeners.BlockListener;
import com.frostbyte.listeners.InventoryListener;

public class GameManager {
	/** Classes **/
	GameLoop gameLoop = new GameLoop(this);
	GameFrame gameFrame = new GameFrame();
	InputHandler inputHandler = new InputHandler(this);

	/** Listeners **/
	public BlockListener blockListener = new BlockListener(this);
	public InventoryListener inventoryListener = new InventoryListener(this);

	/** Variables **/
	private boolean running = false;
	public World world = new World("Test World");

	public GameManager() {
		loadGame();
		addListener();

		new CommandManager(this);
		new BlockHelper();
	}

	public void addListener() {
		gameFrame.setFocusable(true);
		gameFrame.requestFocusInWindow();
		gameFrame.addKeyListener(inputHandler);
		gameFrame.addMouseListener(inputHandler);
		gameFrame.addMouseMotionListener(inputHandler);
	}

	public void loadGame() {
		gameLoop.start();
		running = true;
	}

	public void update() {
		world.update();
	}

	public void draw(Graphics g) {
		world.draw(g);
	}

	public void drawOffScreen() {
		Graphics offgc;
		Image offscreen = null;
		Dimension d = gameFrame.getSize();
		offscreen = gameFrame.createImage(world.getBlocks().length * 20, world.getBlocks()[0].length * 20);
		offgc = offscreen.getGraphics();
		offgc.setColor(gameFrame.getBackground());
		offgc.fillRect(0, 0, d.width, d.height);
		offgc.setColor(gameFrame.getForeground());
		draw(offgc);
		gameFrame.getGraphics().drawImage(offscreen, -world.getPlayerCamera().getX(), -world.getPlayerCamera().getY(), null);
		offgc.dispose();
	}

	public boolean isRunning() {
		return running;
	}
}
