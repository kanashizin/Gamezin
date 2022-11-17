package com.sgstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sgstudios.main.Game;
import com.sgstudios.main.Sound;
import com.sgstudios.world.Camera;
import com.sgstudios.world.World;

public class ManaShoot extends Entity{

	private double dx;
	private double dy;
	private double speed = 2.5;
	private int counter = 0,powerCounter;
	
	public ManaShoot(int x, int y, int width, int height, double dx, double dy,int powerCount,BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		this.powerCounter = powerCount;

	}

	public void tick() {
		
		flying();

		inScreen();
	}
	
	public void flying() {
		if(counter == 0) {
			Sound.shoot.play();
			counter = 1;
		}
		x+=dx*speed;
		y+=dy*speed;
			
	}
	
	public void inScreen() {
		
			if(!World.isOutWall(this.getX(), this.getY())) {
				counter = 0;
				Sound.explosion.play();
				Game.playerAtual.shoot = false;
				Game.manashoot.remove(this);
			}
			
		}
	
	public int getPower() {
		
		return powerCounter;
		
	}
	
	public void render(Graphics g) {
		
		
		switch (powerCounter){
			
		case 2:
			 g.drawImage(Entity.MANA_EXPLOSION1, this.getX() - Camera.x, this.getY() - Camera.y,10,10,null);
		break;
		case 3:
			 g.drawImage(Entity.MANA_EXPLOSION2, this.getX() - Camera.x, this.getY() - Camera.y,12,12,null);
		break;
		case 4:
			 g.drawImage(Entity.MANA_EXPLOSION3, this.getX() - Camera.x, this.getY() - Camera.y,16,16,null);
		break;
		default:
			g.drawImage(Entity.MANA_EXPLOSION0, this.getX() - Camera.x, this.getY() - Camera.y,5,5,null);
			break;
		
		}
		
		
		

		
	}
	
}