package com.sgstudios.inventory;

public class RingManaItem extends Item{
	
	public int mana;
	
	public RingManaItem(int mana) {
		
		this.mana = mana;
		this.itemName = "Anel de Mana";
		this.utility = "MANA";
		this.typeEquipableItem = "RING";
		this.type = "SINGLE";
		
	}
	
	public int getAdicionalMana() {
		
		return mana;
		
	}
}
