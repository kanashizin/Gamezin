/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgstudios.entities;

import java.awt.image.BufferedImage;

/**
 *
 * @author SadBo
 */
public class BootsSpeed extends Entity{
    
    public int addSpeed;
    
    public BootsSpeed(int x, int y, int width, int addSpeed,int height,BufferedImage sprite){
        super(x, y, width, height, sprite);
        this.addSpeed = addSpeed;
    }
    
}
