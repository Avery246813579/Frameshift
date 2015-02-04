package com.frostbyte.projectiles;

import com.frostbyte.display.Animation;
import com.frostbyte.display.Location;
import com.frostbyte.display.Vector;
import com.frostbyte.entities.types.Entity;

public abstract class Projectile extends Entity{
	Vector vector;
	
	public Projectile(Location location, Vector vector, Animation animation) {
		super(location.getWorld(), location.getX(), location.getY());
		this.vector = vector;
		this.animations.add(animation);
	}
	
	public void launchProjectile(){
		setVelocity(vector);
	}
}
