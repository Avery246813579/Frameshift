package com.frostbyte.menus;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.frostbyte.main.GameFrame;
import com.frostbyte.main.GameManager;

public class GameMenu extends Menu {
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	private boolean playHover, optionHover, quitHover;

	public GameMenu(GameManager gameManager) {
		super(gameManager);

		try {
			images.add(ImageIO.read(getClass().getResourceAsStream("/GUI/MENU_PLAY.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/GUI/MENU_OPTIONS.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/GUI/MENU_QUIT.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/GUI/MENU_PLAY_CLICK.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/GUI/MENU_OPTIONS_CLICK.png")));
			images.add(ImageIO.read(getClass().getResourceAsStream("/GUI/MENU_QUIT_CLICK.png")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		if (!playHover) {
			g.drawImage(images.get(0), (GameFrame.WIDTH / 2) - (images.get(0).getWidth() / 2), (int) (GameFrame.HEIGHT * 0.25), null);
		} else {
			g.drawImage(images.get(3), (GameFrame.WIDTH / 2) - (images.get(0).getWidth() / 2), (int) (GameFrame.HEIGHT * 0.25), null);
		}

		if (!optionHover) {
			g.drawImage(images.get(1), (GameFrame.WIDTH / 2) - (images.get(1).getWidth() / 2), (int) (GameFrame.HEIGHT * 0.50), null);
		} else {
			g.drawImage(images.get(4), (GameFrame.WIDTH / 2) - (images.get(4).getWidth() / 2), (int) (GameFrame.HEIGHT * 0.50), null);
		}

		if (!quitHover) {
			g.drawImage(images.get(2), (GameFrame.WIDTH / 2) - (images.get(2).getWidth() / 2), (int) (GameFrame.HEIGHT * 0.75), null);
		} else {
			g.drawImage(images.get(5), (GameFrame.WIDTH / 2) - (images.get(5).getWidth() / 2), (int) (GameFrame.HEIGHT * 0.75), null);
		}
	}

	public void update() {

	}

	@Override
	public void onMove(int button, int x, int y) {
		Rectangle2D playerRect = new Rectangle(x, y, 1, 1);

		int i = 0;
		for (BufferedImage bufferedImage : images) {
			int rectY = 0;

			if (i == 0) {
				rectY = (int) (GameFrame.HEIGHT * 0.25);
			}
			if (i == 1) {
				rectY = (int) (GameFrame.HEIGHT * 0.50);
			}
			if (i == 2) {
				rectY = (int) (GameFrame.HEIGHT * 0.75);
			}

			Rectangle2D imageRect = new Rectangle((GameFrame.WIDTH / 2) - (bufferedImage.getWidth() / 2), rectY, bufferedImage.getWidth(), bufferedImage.getHeight());

			if (playerRect.intersects(imageRect)) {
				if (i == 0) {
					playHover = true;
				} else if (i == 1) {
					optionHover = true;
				} else if (i == 2) {
					quitHover = true;
				}
			} else {
				if (i == 0) {
					playHover = false;
				} else if (i == 1) {
					optionHover = false;
				} else if (i == 2) {
					quitHover = false;
				}
			}

			i++;
		}
	}

	@Override
	public void onKeyPressed(int x) {
		
	}

	@Override
	public void onMousePressed(int button, int x, int y) {
		Rectangle2D playerRect = new Rectangle(x, y, 1, 1);

		int i = 0;
		for (BufferedImage bufferedImage : images) {
			int rectY = 0;

			if (i == 0) {
				rectY = (int) (GameFrame.HEIGHT * 0.25);
			}
			if (i == 1) {
				rectY = (int) (GameFrame.HEIGHT * 0.50);
			}
			if (i == 2) {
				rectY = (int) (GameFrame.HEIGHT * 0.75);
			}

			Rectangle2D imageRect = new Rectangle((GameFrame.WIDTH / 2) - (bufferedImage.getWidth() / 2), rectY, bufferedImage.getWidth(), bufferedImage.getHeight());

			if (playerRect.intersects(imageRect)) {
				if(i == 0){
					gameManager.inGame = true;
				}
				
				if(i == 2){
					System.exit(0);
					gameManager.gameFrame.dispose();
					gameManager.gameFrame.setVisible(false);
				}
			} 
			
			i++;
		}
	}
}
