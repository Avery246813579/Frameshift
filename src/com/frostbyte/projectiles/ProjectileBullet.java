package com.frostbyte.projectiles;

import com.frostbyte.display.Animation;
import com.frostbyte.display.Location;
import com.frostbyte.display.Vector;

public class ProjectileBullet extends Projectile{
	public ProjectileBullet(Location location, Vector vector) {
		super(location, vector, new Animation(new String[]{"/Blocks/BULLET.png"}, 1));
		launchProjectile();
	}
}
