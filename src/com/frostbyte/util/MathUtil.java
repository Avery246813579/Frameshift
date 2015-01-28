package com.frostbyte.util;

import com.frostbyte.display.Location;
import com.frostbyte.display.Vector;

public class MathUtil {
	public static boolean getRandomBoolean(int amount) {
		return Math.random() < (amount / 100.0);
	}
	
	public static Vector getSlope(Location firstLocation, Location secondLocation){
		int x = secondLocation.getX() - firstLocation.getX();
		int y = secondLocation.getY() - firstLocation.getY();
		
		return new Vector(x, y);
	}
	
	public static double getRatio(Vector vector){
		return vector.getX()/vector.getY();
	}
}
