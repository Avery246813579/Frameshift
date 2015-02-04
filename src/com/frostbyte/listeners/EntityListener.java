package com.frostbyte.listeners;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.frostbyte.entities.types.Entity;
import com.frostbyte.entities.types.LivingEntity;
import com.frostbyte.main.GameManager;
import com.frostbyte.player.Player;

public class EntityListener {
	public GameManager gameManager;

	public EntityListener(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public Entity doesHitEntity(int x, int y){
		Rectangle2D hitRect = new Rectangle(x, y, 1, 1);
		
		for(Entity entity : gameManager.world.getEntities()){
			if(!(entity instanceof Player) && entity instanceof LivingEntity){
				Rectangle2D entityRect = new Rectangle(entity.getX() - gameManager.world.getPlayerCamera().getX(), entity.getY() - gameManager.world.getPlayerCamera().getY(), entity.getWidth(), entity.getHeight());
				
				if(hitRect.intersects(entityRect)){
					return entity;
				}
			}
		}
		
		return null;
	}
	
	public void damageEntity(Entity entity){
		LivingEntity livingEntity = (LivingEntity) entity;
		
		livingEntity.damage(gameManager.world.getPlayer().getDamage());
	}
}
