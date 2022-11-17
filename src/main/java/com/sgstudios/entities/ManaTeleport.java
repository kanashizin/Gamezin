package com.sgstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sgstudios.main.Game;
import com.sgstudios.world.Camera;
import com.sgstudios.world.FloorTile;
import com.sgstudios.world.WallTile;
import com.sgstudios.world.World;

public class ManaTeleport extends Entity{

	private double dx;
	private double dy;
	private double speed = 2.5;
	private boolean out = false;
	
	private int px,py;
	
	private BufferedImage[] tpSprites;
	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 4;
	
	public ManaTeleport(int x, int y, int width, int height, double dx, double dy,BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
		tpSprites = new BufferedImage[5];
		for(int i = 0; i < tpSprites.length;i++) {
			tpSprites[i] = Game.spritesheet.getSprite((16*5)+(16*i), 16*2, 16, 16);
		}

	}

	public void tick() {
		
		flying();

		inScreen();
		
		teleport();
	}
	
	public void flying() {

			if(Game.playerAtual.isActive == 2) {
				x+=dx*speed;
				y+=dy*speed;
			}
		
	}
	
	public void inScreen() {
		
			if(!World.isOutWall(this.getX(), this.getY())) {

			Game.playerAtual.isActive = 0;
			Game.playerAtual.teleport = false;
			out = false;
			Game.teleport.remove(this);
			}
			
		}
	
	public void teleport() {
		
		if(Game.playerAtual.isActive == 4) {
			px = Game.playerAtual.mx*3;
			py = Game.playerAtual.my*3;
			Game.playerAtual.moving = false;
			
			frames++;
			if(frames > maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					frames = 0;
					index = 0;
					Game.playerAtual.isActive = 5;
					
				}
			}
			
			
		}else if (Game.playerAtual.isActive == 5) {
			
			Game.playerAtual.setX(this.getX());
			Game.playerAtual.setY(this.getY());
			maxFrames = 10;
			frames++;
			if(frames > maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					frames = 0;
					Game.playerAtual.isActive = 6;
					
				}
			}
			
		}else if(Game.playerAtual.isActive == 6) {
			Game.playerAtual.moving = true;
			Game.playerAtual.isActive = 0;
			Game.playerAtual.teleport = false;
			Game.teleport.remove(this);
			
		}
	}
	
	
	
	
	public void render(Graphics g) {
	if (Game.playerAtual.teleport) {
		if(Game.playerAtual.isActive > 1 && Game.playerAtual.isActive < 3)	
		g.drawImage(Entity.MANA_TELEPORT, this.getX() - Camera.x, this.getY() - Camera.y,5,5,null);
			if(Game.playerAtual.isActive == 4)  {
				g.drawImage(tpSprites[index],Game.playerAtual.getX() - Camera.x, Game.playerAtual.getY() - Camera.y, 16, 16, null);
				
			}else if(Game.playerAtual.isActive == 5) {
				g.drawImage(tpSprites[4-index],this.getX() - Camera.x, this.getY() - Camera.y, 16, 16, null);
			}
		}
		
	}
	
}