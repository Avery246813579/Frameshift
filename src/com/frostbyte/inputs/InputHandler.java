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
import com.frostbyte.items.types.Item;
import com.frostbyte.main.GameFrame;
import com.frostbyte.main.GameManager;
import com.frostbyte.player.Player;
import com.frostbyte.world.WorldChunk;

public class InputHandler implements MouseListener, KeyListener, MouseMotionListener {
	public List<MouseInput> inputs = new ArrayList<MouseInput>();
	public boolean hasHit, pressed;
	private int lastButton;
	GameManager gameManager;

	public InputHandler(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public void keyPressed(KeyEvent event) {
		if (gameManager.inGame) {
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

			if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
				gameManager.world.getPlayer().setRunning(true);
			}

			if (event.getKeyCode() == KeyEvent.VK_Q) {
				if (pressed) {
					return;
				}

				pressed = true;
				if (gameManager.world.getPlayer().getItemInHand() != null) {
					if (!gameManager.world.getPlayer().getInventory().contains(gameManager.world.getPlayer().getItemInHand().getMaterial())) {
						return;
					}

					if (gameManager.world.getPlayer().isFacingRight()) {
						gameManager.world.dropItem(gameManager.world.getPlayer().getItemInHand(), gameManager.world.getPlayer().getLocation().add(50, -25));
					} else {
						gameManager.world.dropItem(gameManager.world.getPlayer().getItemInHand(), gameManager.world.getPlayer().getLocation().add(-50, -25));
					}

					gameManager.world.getPlayer().getInventory().remove(gameManager.world.getPlayer().getItemInHand().getMaterial());
				}
			}
		} else {
			gameManager.menus.get(gameManager.currentMenu).onKeyPressed(event.getKeyCode());
		}
	}

	public void keyReleased(KeyEvent event) {
		if (gameManager.inGame) {

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
				for (MouseInput input : inputs) {
					if (input.getInputType() == InputType.DRAG) {
						gameManager.world.getPlayer().getInventory().addItem(input.getItemStack());
					}

					if (input.getInputType() == InputType.CRAFTING) {
						gameManager.world.getPlayer().getInventory().addItem(input.getItemStack());
					}
				}

				inputs.remove(findPlayerInput(gameManager.world.getPlayer()));
			}

			if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
				gameManager.world.getPlayer().setRunning(false);
			}

			if (event.getKeyCode() == KeyEvent.VK_Q) {
				pressed = false;
			}
		}
	}

	public void mouseClicked(MouseEvent event) {
		if (gameManager.inGame) {

			if (event.getButton() == MouseEvent.BUTTON1) {
				if (gameManager.world.getPlayer().inventoryOpen()) {
					return;
				}

				if (gameManager.entityListener.doesHitEntity(event.getX(), event.getY()) != null && !hasHit) {
					gameManager.entityListener.damageEntity(gameManager.entityListener.doesHitEntity(event.getX(), event.getY()));
					hasHit = true;
					return;
				}

				if (event.getX() + gameManager.world.getPlayerCamera().getX() > GameFrame.WIDTH / 2 + gameManager.world.getPlayerCamera().getX() - 124 && event.getX() + gameManager.world.getPlayerCamera().getX() < GameFrame.WIDTH / 2 + gameManager.world.getPlayerCamera().getX() + 123) {
					if (event.getY() + gameManager.world.getPlayerCamera().getY() > 26 + gameManager.world.getPlayerCamera().getY() && event.getY() + gameManager.world.getPlayerCamera().getY() < 51 + gameManager.world.getPlayerCamera().getY()) {
						gameManager.inventoryListener.onGuiInteract(event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());
						return;
					}
				}

				if (gameManager.world.getPlayer().getItemInHand() instanceof Item) {
					return;
				}
			}
		}
	}

	public void mousePressed(MouseEvent event) {
		if (gameManager.inGame) {
			if (event.getButton() == MouseEvent.BUTTON1) {
				lastButton = MouseEvent.BUTTON1;

				if (gameManager.world.getPlayer().inventoryOpen()) {
					gameManager.inventoryListener.onInventoryInteract(event.getX(), event.getY());
					return;
				}

				if (gameManager.entityListener.doesHitEntity(event.getX(), event.getY()) != null) {
					return;
				}

				if (gameManager.world.getPlayer().getItemInHand() instanceof Item) {
					((Item) gameManager.world.getPlayer().getItemInHand()).interact(gameManager.world.getPlayer().getLocation(), new Location(gameManager.world, event.getX(), event.getY()));
					inputs.add(new MouseInput(gameManager.world.getPlayer(), gameManager.world.getPlayer().getItemInHand(), InputType.ITEM, event.getX(), event.getY()));
					WorldChunk chunk = gameManager.world.getChunkHandler().findWorldChunk(event.getX(), event.getY());
					if (!new Location(gameManager.world, event.getX(), event.getY()).getWorld().getChunkHandler().activeChunks.contains(chunk)) {
						new Location(gameManager.world, event.getX(), event.getY()).getWorld().getChunkHandler().activeChunks.add(chunk);
					}
					return;
				}

				Location dropLocation = new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());

				if (gameManager.world.getBlockAtLocation(dropLocation).getMaterial() != Material.AIR) {
					gameManager.blockListener.onBlockBreak(gameManager.world.getPlayer(), gameManager.world.getBlockAtLocation(dropLocation), dropLocation);
					inputs.add(new MouseInput(gameManager.world.getPlayer(), gameManager.world.getBlockAtLocation(dropLocation), InputType.BREAK, event.getX(), event.getY()));
					WorldChunk chunk = gameManager.world.getChunkHandler().findWorldChunk(event.getX(), event.getY());
					if (!new Location(gameManager.world, event.getX(), event.getY()).getWorld().getChunkHandler().activeChunks.contains(chunk)) {
						new Location(gameManager.world, event.getX(), event.getY()).getWorld().getChunkHandler().activeChunks.add(chunk);
					}
				}
			}

			if (event.getButton() == MouseEvent.BUTTON3) {
				lastButton = MouseEvent.BUTTON3;

				Location placeLocation = new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());

				if (gameManager.world.getPlayer().getItemInHand() != null) {
					gameManager.blockListener.onBlockPlace(gameManager.world.getPlayer(), BlockHelper.getBlockType(gameManager.world.getPlayer().getItemInHand().getMaterial(), null), placeLocation);
				}

				WorldChunk chunk = gameManager.world.getChunkHandler().findWorldChunk(event.getX(), event.getY());
				if (!new Location(gameManager.world, event.getX(), event.getY()).getWorld().getChunkHandler().activeChunks.contains(chunk)) {
					new Location(gameManager.world, event.getX(), event.getY()).getWorld().getChunkHandler().activeChunks.add(chunk);
				}
			}
		} else {
			gameManager.menus.get(gameManager.currentMenu).onMousePressed(event.getButton(), event.getX(), event.getY());
		}
	}

	public void mouseReleased(MouseEvent event) {
		if (gameManager.inGame) {

			if (event.getButton() == MouseEvent.BUTTON1) {
				if (hasHit) {
					hasHit = false;
				}

				for (MouseInput input : inputs) {
					if (input.getPlayer() == gameManager.world.getPlayer() && (input.getInputType() == InputType.DRAG || input.getInputType() == InputType.CRAFTING)) {
						if (input.getInputType() == InputType.DRAG) {
							if (gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY()) != -1) {
								if (gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY()) == 13) {
									gameManager.world.getPlayer().getInventory().setItem(input.getInventoryLocation(), input.getItemStack());
								} else {
									if (gameManager.world.getPlayer().getCraftingInventory().getContent()[gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY())] == null) {
										gameManager.world.getPlayer().getCraftingInventory().setItem(gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY()), input.getItemStack());
									} else {
										gameManager.world.getPlayer().getInventory().setItem(input.getInventoryLocation(), input.getItemStack());
									}
								}
							} else if (gameManager.world.getPlayer().getInventory().getInventorySlot(event.getX(), event.getY()) == -1) {
								gameManager.world.getPlayer().getInventory().setItem(input.getInventoryLocation(), input.getItemStack());
							} else {
								if (gameManager.world.getPlayer().getInventory().getContent()[gameManager.world.getPlayer().getInventory().getInventorySlot(event.getX(), event.getY())] == null) {
									gameManager.world.getPlayer().getInventory().setItem(gameManager.world.getPlayer().getInventory().getInventorySlot(event.getX(), event.getY()), input.getItemStack());
								} else {
									gameManager.world.getPlayer().getInventory().setItem(input.getInventoryLocation(), input.getItemStack());
								}
							}
						} else {
							if (gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY()) != -1) {
								if (gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY()) == 13) {
									gameManager.world.getPlayer().getCraftingInventory().setItem(input.getInventoryLocation(), input.getItemStack());
								} else {
									if (gameManager.world.getPlayer().getCraftingInventory().getContent()[gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY())] == null) {
										gameManager.world.getPlayer().getCraftingInventory().setItem(gameManager.world.getPlayer().getCraftingInventory().getInventorySlot(event.getX(), event.getY()), input.getItemStack());
									} else {
										if (!gameManager.world.getPlayer().getInventory().isFull()) {
											gameManager.world.getPlayer().getInventory().addItem(input.getItemStack());
										} else {
											gameManager.world.dropItem(input.getItemStack(), gameManager.world.getPlayer().getLocation());
										}
									}
								}
							} else if (gameManager.world.getPlayer().getInventory().getInventorySlot(event.getX(), event.getY()) == -1) {
								if (input.getInventoryLocation() != 13) {
									gameManager.world.getPlayer().getCraftingInventory().setItem(input.getInventoryLocation(), input.getItemStack());
								} else {
									if (!gameManager.world.getPlayer().getInventory().isFull()) {
										gameManager.world.getPlayer().getInventory().addItem(input.getItemStack());
									} else {
										gameManager.world.dropItem(input.getItemStack(), gameManager.world.getPlayer().getLocation());
									}
								}
							} else {
								if (gameManager.world.getPlayer().getInventory().getContent()[gameManager.world.getPlayer().getInventory().getInventorySlot(event.getX(), event.getY())] == null) {
									gameManager.world.getPlayer().getInventory().setItem(gameManager.world.getPlayer().getInventory().getInventorySlot(event.getX(), event.getY()), input.getItemStack());
								} else {
									if (input.getInventoryLocation() != 13) {
										gameManager.world.getPlayer().getCraftingInventory().setItem(input.getInventoryLocation(), input.getItemStack());
									} else {
										if (!gameManager.world.getPlayer().getInventory().isFull()) {
											gameManager.world.getPlayer().getInventory().addItem(input.getItemStack());
										} else {
											gameManager.world.dropItem(input.getItemStack(), gameManager.world.getPlayer().getLocation());
										}
									}
								}
							}
						}
					}
				}

				if (containsPlayer(gameManager.world.getPlayer())) {
					inputs.remove(findPlayerInput(gameManager.world.getPlayer()));
				}
			}
		}
	}

	public void mouseDragged(MouseEvent event) {
		if (gameManager.inGame) {
			if (inputs.isEmpty()) {
				if (lastButton == MouseEvent.BUTTON1) {
					if (gameManager.world.getPlayer().inventoryOpen()) {
						gameManager.inventoryListener.onInventoryInteract(event.getX(), event.getY());
						return;
					}

					if (gameManager.entityListener.doesHitEntity(event.getX(), event.getY()) != null) {
						return;
					}

					if (gameManager.world.getPlayer().getItemInHand() instanceof Item) {
						return;
					}

					Location dropLocation = new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY());

					if (gameManager.world.getBlockAtLocation(dropLocation).getMaterial() != Material.AIR) {
						gameManager.blockListener.onBlockBreak(gameManager.world.getPlayer(), gameManager.world.getBlockAtLocation(dropLocation), dropLocation);
						inputs.add(new MouseInput(gameManager.world.getPlayer(), gameManager.world.getBlockAtLocation(dropLocation), InputType.BREAK, event.getX(), event.getY()));
					}
				}

				if (lastButton == MouseEvent.BUTTON3) {
					if (gameManager.world.getPlayer().inventoryOpen()) {
						gameManager.inventoryListener.onInventoryInteract(event.getX(), event.getY());
						return;
					}

					if (gameManager.entityListener.doesHitEntity(event.getX(), event.getY()) != null) {
						return;
					}

					if (gameManager.world.getPlayer().getItemInHand() instanceof Item) {
						return;
					}

					gameManager.blockListener.onBlockPlace(gameManager.world.getPlayer(), BlockHelper.getBlockType(gameManager.world.getPlayer().getItemInHand().getMaterial(), null), new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY()
							+ gameManager.world.getPlayerCamera().getY()));
				}
				return;
			}

			MouseInput input = null;
			for (MouseInput mouseInput : inputs) {
				if (mouseInput.getPlayer() == gameManager.world.getPlayer()) {
					input = mouseInput;
					break;
				}
			}

			if (input == null) {
				return;
			}

			if (input.getInputType() == InputType.BREAK || input.getInputType() == InputType.ITEM) {
				if (input.getBlock() != null) {
					return;
				} else {
					if (input.getBlock().getLocation() != gameManager.world.getBlockAtLocation(new Location(gameManager.world, event.getX() + gameManager.world.getPlayerCamera().getX(), event.getY() + gameManager.world.getPlayerCamera().getY())).getLocation()) {
						inputs.remove(input);
					}
				}
			}

			if (input.getInputType() == InputType.DRAG || input.getInputType() == InputType.CRAFTING) {
				input.setX(event.getX());
				input.setY(event.getY());
			}
		}
	}

	public boolean containsPlayer(Player player) {
		for (MouseInput mouseInput : inputs) {
			if (mouseInput.getPlayer() == player) {
				return true;
			}
		}

		return false;
	}

	public MouseInput findPlayerInput(Player player) {
		for (MouseInput mouseInput : inputs) {
			if (mouseInput.getPlayer() == player) {
				return mouseInput;
			}
		}

		return null;
	}

	public void mouseMoved(MouseEvent event) {
		if (!gameManager.inGame) {
			gameManager.menus.get(gameManager.currentMenu).onMove(event.getButton(), event.getX(), event.getY());
		}
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	public void keyTyped(KeyEvent event) {
	}
}