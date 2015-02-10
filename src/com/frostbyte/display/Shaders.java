package com.frostbyte.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.frostbyte.main.GameFrame;
import com.frostbyte.world.World;

public class Shaders {
	private World world;
	private BufferedImage diamond;

	public Shaders(World world) {
		try {
			diamond = ImageIO.read(getClass().getResourceAsStream("/GUI/DIAMOND.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.world = world;
	}

	public void draw(Graphics g2) {
		BufferedImage composed = compose(diamond, GameFrame.WIDTH, GameFrame.HEIGHT);
		g2.drawImage(composed, 0, 0, null);
	}

	private BufferedImage compose(final BufferedImage source, int w, int h) {
		BufferedImage destination = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = destination.createGraphics();
		try {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);

			if (source != null) {
				graphics.setComposite(AlphaComposite.SrcIn);
				graphics.drawImage(source, world.getPlayer().getX() - world.getPlayerCamera().getX() - (diamond.getWidth()/2), world.getPlayer().getY() - world.getPlayerCamera().getY() - (diamond.getHeight()/2), null);
			}
		} finally {
			graphics.dispose();
		}

		return destination;
	}
}
