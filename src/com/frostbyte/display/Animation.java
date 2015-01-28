package com.frostbyte.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Animation {
	private List<BufferedImage> animations = new ArrayList<BufferedImage>();
	private int currentAnimation, animationDelay, delayTick;
	private boolean paused;

	public Animation(String[] locations, int animationDelay) {
		for (String location : locations) {
			try {
				animations.add(ImageIO.read(getClass().getResourceAsStream(location)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		this.animationDelay = animationDelay;
	}

	public Animation(BufferedImage[] locations, int animationDelay) {
		for (BufferedImage location : locations) {
			animations.add(location);
		}

		this.animationDelay = animationDelay;
	}

	public void updateAnimation() {
		if (paused) {
			return;
		}

		if (delayTick < animationDelay) {
			delayTick++;
			return;
		}

		delayTick = 0;
		currentAnimation++;
		if (animations.size() <= currentAnimation) {
			currentAnimation = 0;
		}
	}

	public BufferedImage getAnimation() {
		return animations.get(currentAnimation);
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
