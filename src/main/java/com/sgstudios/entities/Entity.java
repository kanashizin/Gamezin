package com.sgstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.sgstudios.main.Game;
import com.sgstudios.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(16*5, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(16*6, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(16*5, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(16*6, 16, 16, 16);
	public static BufferedImage ENEMY_DAMAGE = Game.spritesheet.getSprite(16*9, 16*1, 16, 16);
	public static BufferedImage PLAYER_DAMAGE_RIGHT = Game.spritesheet.getSprite(0*1, 16*1, 16, 16);
	public static BufferedImage PLAYER_DAMAGE_LEFT = Game.spritesheet.getSprite(16*1, 16*1, 16, 16);
	public static BufferedImage MANA_RIGHT = Game.spritesheet.getSprite(16*7, 0, 16, 16);
	public static BufferedImage MANA_LEFT = Game.spritesheet.getSprite(16*8, 0, 16, 16);
	public static BufferedImage MANA_SHOOT = Game.spritesheet.getSprite(16*0, 16*2, 5, 5);
	public static BufferedImage MANA_EXPLOSION0 = Game.spritesheet.getSprite(16*0, 16*2, 5, 5);
	public static BufferedImage MANA_EXPLOSION1 = Game.spritesheet.getSprite(16*1, 16*2, 10, 10);
	public static BufferedImage MANA_EXPLOSION2 = Game.spritesheet.getSprite(16*2, 16*2, 12, 12);
	public static BufferedImage MANA_EXPLOSION3 = Game.spritesheet.getSprite(16*3, 16*2, 16, 16);
	public static BufferedImage MANA_TELEPORT = Game.spritesheet.getSprite(16*4, 16*2, 5, 5);
	public static BufferedImage FLAG_EN = Game.spritesheet.getSprite(16*0, 16*3, 16, 16);
	public static BufferedImage SHIELD = Game.itemsSpritesheet.getSprite(15, 18);
	public static BufferedImage RINGMANA = Game.itemsSpritesheet.getSprite(5,17);
        public static BufferedImage BOOTSCOMUM = Game.itemsSpritesheet.getSprite(16,4);
        public static BufferedImage SPEEDBOOTS = Game.itemsSpritesheet.getSprite(1,5);
        
	protected int slotX;
	protected int slotY;
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public int maskx,masky,mwidth,mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
        
        public Entity(int width, int height) {
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight){
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setPosX(int new_X) {
		this.slotY = new_X*48;
	}
	
	public void setPosY(int new_Y) {
		this.slotY = new_Y*48;
	}
	
	
	public int getPosX() {
		return (int)this.slotX;
	}
	
	public int getPosY() {
		return (int)this.slotY;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}

	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.RED);
		//g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, mwidth, mheight);
	}
	
}