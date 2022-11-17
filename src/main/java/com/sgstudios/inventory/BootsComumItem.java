package com.sgstudios.inventory;

public class BootsComumItem extends Item{
	
	public int speedBase = 10;

	public BootsComumItem() {
		this.equipavel = true;
		this.type = "SINGLE";
		this.typeEquipableItem = "BOOTS";
		this.utility = "COMUM";
                this.setItemName("Botas Comum");
                this.equipavel = true;
	}
	
	public int getSpeedBase(){
            return this.speedBase;
        }
	
}
