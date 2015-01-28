package com.frostbyte.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.blocks.BlockHelper;
import com.frostbyte.display.Location;
import com.frostbyte.display.Material;
import com.frostbyte.items.Item;
import com.frostbyte.main.GameFrame;
import com.frostbyte.main.GameManager;

public class InputHandler implements MouseListener, KeyListener, MouseMotionListener {
	public List<MouseInput> inputs = new ArrayList<MouseInput>();
	GameManager gameManager;

	public InputHandler(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_D) {
			gameManager.world.getPlayer().setRight(true);
		}

		if (event.getKeyCode() == KeyEvent.VK_A) {
			gameManager.world.getPlayer().setLeft(true);
		}

		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			gameManager.world.getPlayer().setJumping(true);
		}

		if (event.getKeyCode() == KeyEvent.VK_E) {
			gameManager.world.getPlayer().openInventory();
		}
	}

	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_D) {
			gameManager.world.getPlayer().setRight(false);
		}

		if (event.getKeyCode() == KeyEvent.VK_A) {
			gameManager.world.getPlayer().setLeft(false);
		}

		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			gameManager.world.getPlayer().setJumping(false);
		}

		if (event.getKeyCode() == KeyEvent.VK_E) {
			gameManager.world.getPlayer().closeInventory();
		}
	}

	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			if (gameManager.world.getPlayer().inventoryOpen()) {
				gameManager.inventoryListener.onInventoryInteract(event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());
			}
		}

		if (event.getButton() == MouseEvent.BUTTON1) {
			if (event.getX() + gameManager.world.getPlayerCamera().getX() > GameFrame.WIDTH / 2 + gameManager.world.getPlayerCamera().getX() - 124 && event.getX() + gameManager.world.getPlayerCamera().getX() < GameFrame.WIDTH / 2 + gameManager.world.getPlayerCamera().getX() + 123) {
				if(event.getY() + gameManager.world.getPlayerCamera().getY() > 26 + gameManager.world.getPlayerCamera().getY() && event.getY() + gameManager.world.getPlayerCamera().getY() < 51 + gameManager.world.getPlayerCamera().getY()){
					gameManager.inventoryListener.onGuiInteract(event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());
					return;
				}
			}

			if (gameManager.world.getPlayer().getItemInHand() instanceof Item) {
				((Item) gameManager.world.getPlayer().getItemInHand()).interact(gameManager.world.getPlayer().getLocation(), new Location(gameManager.world, event.getX(), event.getY()));
				return;
			}
		}
	}

	public void mousePressed(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			Location dropLocation = new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());

			if (gameManager.world.getBlockAtLocation(dropLocation).getMaterial() != Material.AIR) {
				gameManager.blockListener.onBlockBreak(gameManager.world.getPlayer(), gameManager.world.getBlockAtLocation(dropLocation), dropLocation);
			}
		}

		if (event.getButton() == MouseEvent.BUTTON3) {
			Location placeLocation = new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());

			if (gameManager.world.getPlayer().getItemInHand() != null) {
				gameManager.blockListener.onBlockPlace(gameManager.world.getPlayer(), BlockHelper.getBlockType(gameManager.world.getPlayer().getItemInHand().getMaterial()), placeLocation);
			}
		}
	}

	public void mouseDragged(MouseEvent event) {
		if(inputs.isEmpty()){
			return;
		}
		
		MouseInput input = null;
		for(MouseInput mouseInput : inputs){
			if(mouseInput.getPlayer() == gameManager.world.getPlayer()){
				input = mouseInput;
				break;
			}
		}
		
		if(input == null){
			return;
		}
		
		/** TODO for breaking blocks **/
	}
	
	public void mouseReleased(MouseEvent event) {}

	public void mouseMoved(MouseEvent event) {}
	
	public void mouseEntered(MouseEvent event) {}

	public void mouseExited(MouseEvent event) {}
	
	public void keyTyped(KeyEvent event) {}
}
