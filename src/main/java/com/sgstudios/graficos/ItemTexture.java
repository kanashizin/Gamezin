/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgstudios.graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author SadBo
 */
public class ItemTexture{
    
    private BufferedImage sprite;
 
    public ItemTexture(String path){
        try {
			sprite = ImageIO.read(new File("res/textures"+path));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public BufferedImage getSprite(int posX, int posY){
        posX-=1;
        posY-=1;
        return this.sprite.getSubimage(posX*16, posY*16, 16, 16);
        
    }
    
}