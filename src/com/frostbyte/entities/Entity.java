package com.frostbyte.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.display.Animation;
import com.frostbyte.display.Location;
import com.frostbyte.display.Vector;
import com.frostbyte.display.World;

public class Entity {
	protected List<Animation> animations = new ArrayList<Animation>();
	protected int width, height, moveSpeed, jumpSpeed, fallSpeed, maxSpeed, maxJump, maxFall;
	private int x, y, currentAnimation;
	private Vector velocity;
	private World world;
	
	public Entity(World world, int x, int y){
		this.world = world;
		this.x = x;
		this.y = y;
		
		this.velocity = new Vector(0, 0);
	}
	
	public void update(){
		if(animations.isEmpty()){
			return;
		}
		
		for(Animation animation : animations){
			animation.updateAnimation();
		}
	}
	
	public void checkMovement(){
		/**if(!velocity.isMoving()){
			return;
		}
		
		if(velocity.isMovingHorizontal()){
			if(velocity.isGoingRight()){
				if(velocity.getX() >= maxSpeed || velocity.getX() + moveSpeed >= maxSpeed){
					velocity.setX(maxSpeed);
				}else{
					velocity.setX(velocity.getX() + moveSpeed);
				}
			}
			
			if(velocity.isGoingLeft()){
				if(velocity.getX() <= -maxSpeed || velocity.getX() - moveSpeed <= maxSpeed){
					velocity.setX(-maxSpeed);
				}else{
					velocity.setX(velocity.getX() - moveSpeed);
				}
			}
		}
		
		if(velocity.isMovingVertically()){
			if(velocity.isGoingUp()){
				if(velocity.getY() >= maxJump || velocity.getY() + jumpSpeed >= maxJump){
					velocity.setY(maxJump);
				}else{
					velocity.setY(velocity.getY() + jumpSpeed);
				}
			}
			
			if(velocity.isGoingDown()){
				if(velocity.getY() <= -fallSpeed || velocity.getY() - fallSpeed <= maxFall){
					velocity.setY(maxFall);
				}else{
					velocity.setY(velocity.getY() - fallSpeed);
				}
			}
		}**/
	}
	
	public void updateLocation(){
		int targetX = x + velocity.getX();
		int targetY = y + velocity.getY();
		
		if(targetY < ((world.getBlocks()[0].length * 20) - height)){
			y = targetY;
		}
		
		if(targetX < ((world.getBlocks().length * 20) - width)){
			x = targetX;
		}
	}
	
	public List<Entity> checkEntityCollisions(){
		List<Entity> entities = new ArrayList<Entity>();
		
		for(Entity entity : world.getEntities()){
			if(entity != this){
				Rectangle2D currentEntityT = new Rectangle(x, y, width, height);
				Rectangle2D entityT = new Rectangle(entity.getX(), entity.getY(), entity.width, entity.height);
				
				if(currentEntityT.intersects(entityT)){
					entities.add(entity);
				}
			}
		}
		
		return entities;
	}
	
	public Location getLocation(){
		return new Location(getWorld(), getX(), getY());
	}
	
	public void draw(Graphics g){
		if(!animations.isEmpty()){
			g.drawImage(animations.get(currentAnimation).getAnimation(), x, y, null);
		}
	}
	
	public Animation getCurrentFrame(){
		return animations.get(currentAnimation);
	}
	
	public List<Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(List<Animation> animations) {
		this.animations = animations;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
}
