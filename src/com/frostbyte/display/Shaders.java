package com.frostbyte.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.frostbyte.main.GameFrame;
import com.frostbyte.world.World;

public class Shaders {
	private World world;
	private BufferedImage diamond;
	private BufferedImage background;

	public Shaders(World world) {
		try {
			diamond = ImageIO.read(getClass().getResourceAsStream("/GUI/DIAMOND.png"));
			background = ImageIO.read(getClass().getResourceAsStream("/GUI/BACKGROUND.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.world = world;
	}

	public void draw(Graphics g2) {
		Rectangle2D rectangle2d = new Rectangle(0, 0, GameFrame.WIDTH, GameFrame.WIDTH);
		Rectangle2D shadeRect = new Rectangle(0, 0, diamond.getWidth(), diamond.getHeight());
		Area circle = new Area(rectangle2d);
		circle.subtract(new Area(shadeRect));

		Image offscreen = null;
		offscreen = background;
		Graphics2D g = (Graphics2D) offscreen.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setClip(circle);
		g.drawImage(diamond, 0, 0, null);
		g.setClip(null);
		Stroke s = new BasicStroke(2);
		g.setStroke(s);
		g.setColor(Color.BLACK);
		g.draw(circle);
		g.dispose();

		g2.drawImage(offscreen, 0, 0, null);
		/**
		 * BufferedImage bufferedImage = new BufferedImage(diamond.getWidth(),
		 * diamond.getHeight(), BufferedImage.TYPE_INT_RGB);
		 * bufferedImage.getGraphics(); g.drawImage(diamond,
		 * world.getPlayer().getX() - world.getPlayerCamera().getX(),
		 * world.getPlayer().getY() - world.getPlayerCamera().getY(), null);
		 **/
	}
}
