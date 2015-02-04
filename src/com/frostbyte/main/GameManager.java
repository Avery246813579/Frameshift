package com.frostbyte.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.commands.CommandManager;
import com.frostbyte.display.Location;
import com.frostbyte.inputs.InputHandler;
import com.frostbyte.inputs.InputType;
import com.frostbyte.inputs.MouseInput;
import com.frostbyte.items.types.Item;
import com.frostbyte.listeners.BlockListener;
import com.frostbyte.listeners.EntityListener;
import com.frostbyte.listeners.InventoryListener;
import com.frostbyte.menus.GameMenu;
import com.frostbyte.menus.Menu;
import com.frostbyte.world.World;

public class GameManager {
	/** Classes **/
	GameLoop gameLoop = new GameLoop(this);
	public GameFrame gameFrame = new GameFrame();
	public InputHandler inputHandler = new InputHandler(this);

	/** Listeners **/
	public BlockListener blockListener = new BlockListener(this);
	public EntityListener entityListener = new EntityListener(this);
	public InventoryListener inventoryListener = new InventoryListener(this);

	/** Variables **/
	public List<Menu> menus = new ArrayList<Menu>(Arrays.asList(new GameMenu(this)));
	public World world = new World("Test World");
	private boolean running;
	public boolean inGame = false;
	public int currentMenu;

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
		if (inGame) {
			world.update();

			for (MouseInput input : inputHandler.inputs) {
				if (input.getInputType() == InputType.BREAK) {
					if (input.getInputType() == InputType.BREAK || input.getInputType() == InputType.ITEM) {
						if (input.getBlock().getLocation() != world.getBlockAtLocation(new Location(world, input.getX() + world.getPlayerCamera().getX(), input.getY() + world.getPlayerCamera().getY())).getLocation()) {
							inputHandler.inputs.remove(input);
							break;
						}
					}

					Location dropLocation = new Location(world, input.getX() + world.getPlayerCamera().getX(), input.getY() + world.getPlayerCamera().getY());
					blockListener.onBlockBreak(world.getPlayer(), world.getBlockAtLocation(dropLocation), dropLocation);

					if (world.getBlockAtLocation(dropLocation).getDuration() <= 0) {
						break;
					}

					if (!inputHandler.inputs.contains(input)) {
						break;
					}
				}

				if (input.getInputType() == InputType.ITEM) {
					((Item) world.getPlayer().getItemInHand()).interact(world.getPlayer().getLocation(), new Location(world, input.getX(), input.getY()));
				}
			}
		} else {
			if (!menus.isEmpty()) {
				menus.get(currentMenu).update();
			}
		}
	}

	public void draw(Graphics g) {
		if (inGame) {
			world.draw(g);
			inventoryListener.draw(g);
		} else {
			if (!menus.isEmpty()) {
				menus.get(currentMenu).draw(g);
			}
		}
	}

	public void drawOffScreen() {
		Graphics offgc;
		Image offscreen = null;
		Dimension d = gameFrame.getSize();
		offscreen = gameFrame.createImage(GameFrame.WIDTH, GameFrame.HEIGHT);
		offgc = offscreen.getGraphics();
		offgc.setColor(gameFrame.getBackground());
		offgc.fillRect(0, 0, d.width, d.height);
		offgc.setColor(gameFrame.getForeground());
		draw(offgc);
		gameFrame.getGraphics().drawImage(offscreen, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, 0, 0, offscreen.getWidth(null), offscreen.getHeight(null), null);
		offgc.dispose();
	}

	public boolean isRunning() {
		return running;
	}
}