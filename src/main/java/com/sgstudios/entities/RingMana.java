package com.sgstudios.entities;

import java.awt.image.BufferedImage;

public class RingMana extends Entity{
	
	private int mana;

	public RingMana(int x, int y, int width, int height, int mana,BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.mana = mana;
		
	}
	
	public int getMana() {
		
		return mana;
		
	}
	
	
}
