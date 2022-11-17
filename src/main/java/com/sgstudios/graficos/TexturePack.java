package com.sgstudios.graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TexturePack {
	
	BufferedImage spritesheet;
	
	public TexturePack(String path) {
		
		try {
                              
			spritesheet = ImageIO.read(new File("res/textures"+path));
                        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
        
        
}