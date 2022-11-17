package com.sgstudios.inventory;

import java.awt.image.BufferedImage;

public class Item {
	
	protected int posX, posY;
	protected BufferedImage image;
	
	protected String type = "SINGLE";
	protected String itemName = "noName";
	protected boolean equipavel;
	protected String state = "INVENTORY";
	protected String utility;
	protected String typeEquipableItem;
	
	public Item() {
		
	}
	
	public void tick() {
		
	}
	
	public String getEquipableType() {
		
		return this.typeEquipableItem;
		
	}
	
	public void setState(String newState) {
		
		this.state = newState;
		
	}
	
	public String getState() {
		
		return this.state;
		
	}
	
	public String getUtility() {
		
		return this.utility;
		
	}
	
	public void setPos(int newX, int newY) {
		this.posX = newX;
		this.posY = newY;
	}
	
	public void setSprite(BufferedImage sprite) {
		
		this.image = sprite;
		
	}

	public BufferedImage getSprite() {
		
		return this.image;
		
	}
	
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
        public void setItemName(String name) {
		
		this.itemName = name;
		
	}
	
	public String getItemName() {
		
		return this.itemName;
		
	}
	
	public void setType(String new_type) {
		this.type = new_type;
	}
	
	public String getType() {
		return type;
	}
        
        public boolean canEquip(){
            return this.equipavel;
        }
	
}