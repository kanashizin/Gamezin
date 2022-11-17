package com.sgstudios.inventory;

public class ShieldItem extends Item{
	
	private int armor;

	public ShieldItem(int armor) {
		super();
		
		this.armor = armor;
		this.itemName = "Shield";
		this.utility = "ARMOR";
		this.typeEquipableItem = "SHIELD";
		this.type = "SINGLE";
		this.equipavel = true;
	}
	
	
	public int getArmor() {
		
		return this.armor;
		
	}
	
}
