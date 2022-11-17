package com.sgstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sgstudios.entities.*;
import com.sgstudios.graficos.TexturePack;
import com.sgstudios.graficos.UI;
import com.sgstudios.main.Game;
import java.io.File;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(int estagio, int fase) {
		try {
                        System.out.println("res/levels"+"/estagio"+estagio+"/level"+fase+".png");
			BufferedImage map = ImageIO.read(new File("res/levels"+"/estagio"+estagio+"/level"+fase+".png"));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			
			for(int xx = 0; xx < map.getWidth();xx++) {
				for(int yy = 0;yy < map.getHeight();yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy *WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					
					if(pixelAtual == 0xFF000000) {
						//Floor
						tiles[xx + (yy *WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
						
					}else if(pixelAtual == 0xFFFFFFFF) {
						//WallOut
						tiles[xx + (yy *WIDTH)] = new OutWallTile(xx*16,yy*16,Tile.TILE_WALL);
						
					}else if(pixelAtual == 0xFF808080) {
						//WallIn
						tiles[xx + (yy *WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
						
					}else if(pixelAtual == 0xFFFFFF00) {
						//Muni��o
						Game.entities.add(new Mana(xx*16,yy*16,16,16,Entity.BULLET_EN));
						
					}else if(pixelAtual == 0xFFFF0000) {
						//Enemy
						Enemy en = new Enemy(xx*16,yy*16,16,16,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					}else if(pixelAtual == 0xFF7F3300) {
						//Weapon
						Game.entities.add(new Weapon(xx*16,yy*16,16,16,Entity.WEAPON_EN));
						
					}else if(pixelAtual == 0xFFFF6A00) {
						//LifePack
						Game.entities.add(new Lifepack(xx*16,yy*16,16,16,Entity.LIFEPACK_EN));
						
					}else if(pixelAtual == 0xFFFF008C) {
						//ChekingPoint
						Game.entities.add(new SavePoint(xx*16,yy*16,16,16,Entity.FLAG_EN));
					}else if (pixelAtual == 0xFF0000FF) {
						//Player
						Game.playerAtual.setX(xx*16);
						Game.playerAtual.setY(yy*16);
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
                        System.out.println("Deu erro no loading de mundo");
		}
	}
        
        public static int[] getNextLevel(){
            
            int lvls[] = new int[2];
            
            if(Game.playerAtual.CUR_STAGE == Game.MAX_STAGE){
                
                if(Game.playerAtual.CUR_LEVEL == Game.MAX_LEVEL){
                    
                    //Zerou o game
                    System.out.println("Parabéns vc acaba de zerar o game");
                    return null;
                    
                }else{
                    
                    lvls[0] = Game.playerAtual.CUR_STAGE;
                    lvls[1] = Game.playerAtual.CUR_LEVEL+1;
                    return lvls;
                    
                }
                
            }else {
                
                lvls[0] = Game.playerAtual.CUR_STAGE+1;
                lvls[1] = 1;
                return lvls;
                
            }
            
        }
	
	public static void nextLevel() {
		
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.entities.add(Game.playerAtual);
                int[] lvl = getNextLevel();
		Game.world = new World(lvl[0],lvl[1]);
		return;
		
	}
	
	public static void restartLevel(int estagio, int fase) {
			
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.entities.add(Game.playerAtual);
			Game.world = new World(estagio,fase);
			return;
		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;;
		
		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
		
		return !(
				tiles[x1 +(y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 +(y2*World.WIDTH)] instanceof WallTile ||
				tiles[x3 +(y3*World.WIDTH)] instanceof WallTile ||
				tiles[x4 +(y4*World.WIDTH)] instanceof WallTile ||
				
				tiles[x1 +(y1*World.WIDTH)] instanceof OutWallTile ||
				tiles[x2 +(y2*World.WIDTH)] instanceof OutWallTile ||
				tiles[x3 +(y3*World.WIDTH)] instanceof OutWallTile ||
				tiles[x4 +(y4*World.WIDTH)] instanceof OutWallTile
				);
	}
	
	public static boolean isOutWall(int xnext, int ynext) {
			int x1 = xnext / TILE_SIZE;
			int y1 = ynext / TILE_SIZE;
			
			int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
			int y2 = ynext / TILE_SIZE;
			
			int x3 = xnext / TILE_SIZE;
			int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;;
			
			int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
			int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
			
			return !(
					tiles[x1 +(y1*World.WIDTH)] instanceof OutWallTile ||
					tiles[x2 +(y2*World.WIDTH)] instanceof OutWallTile ||
					tiles[x3 +(y3*World.WIDTH)] instanceof OutWallTile ||
					tiles[x4 +(y4*World.WIDTH)] instanceof OutWallTile
					);
		
	}
	
	public static boolean isWall(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;;
		
		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
		
		return !(
				tiles[x1 +(y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 +(y2*World.WIDTH)] instanceof WallTile ||
				tiles[x3 +(y3*World.WIDTH)] instanceof WallTile ||
				tiles[x4 +(y4*World.WIDTH)] instanceof WallTile
				);
	
}
	
	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;
		
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);
		
		for(int xx = xStart;xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}