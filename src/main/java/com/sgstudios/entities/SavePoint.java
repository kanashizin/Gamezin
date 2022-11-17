package com.sgstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sgstudios.main.Game;
import com.sgstudios.main.Save;
import com.sgstudios.main.SaveUsers;
import com.sgstudios.world.Camera;

public class SavePoint extends Entity{
	
	private BufferedImage[] animation = new BufferedImage[5];;
	private static int isSaved = 0;
	
	private int cur_frame = 0,max_frames = 13,index = 0,max_index = 4;

	public SavePoint(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		for(int i = 0; i <= 4; i++) {
			
			animation[i] = Game.spritesheet.getSprite(16*(i), 16*3, 16, 16);
			
		}
		
	}
	public void tick() {
		if(isColidding(this, Game.playerAtual) && isSaved == 0) {
			isSaved = 1;
			
                        SaveUsers.saveUsers();
                        
		}
		if(isSaved == 1) {
			
			if(index <= max_index) {
				cur_frame++;
			}
			if(cur_frame > max_frames) {
				cur_frame = 0;
				index++;
				if(index >= max_index)
					isSaved = 3;
			}
		}
		
	}
	
	public static void setSaved(int num) {
		
		isSaved = num;
		
	}
	
	public static int getSaved() {
		
		return isSaved;
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(animation[index], this.getX() - Camera.x , this.getY() - Camera.y, null);
	}

}