package com.sgstudios.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sgstudios.inventory.Item;

public class Shield extends Entity{
	
	int armor;

	public Shield(int x, int y, int width,int height,int armadura,BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.armor = armadura;
		
	}
	
	public int getArmor() {
		
		return armor;
		
	}
	
}
