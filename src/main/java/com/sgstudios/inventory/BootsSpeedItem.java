package com.sgstudios.inventory;

public class BootsSpeedItem extends Item{
	
	private int aditionalSpeed;

	public BootsSpeedItem(int perCentAditionalSpeed) {
		
		this.aditionalSpeed = perCentAditionalSpeed;
		this.equipavel = true;
		this.type = "SINGLE";
		this.typeEquipableItem = "BOOTS";
		this.utility = "SPEED";
	}
	
	public int getAddSpeed() {
		
		return this.aditionalSpeed;
		
	}
	
}
