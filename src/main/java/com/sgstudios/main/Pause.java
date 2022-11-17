package com.sgstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sgstudios.world.World;

public class Pause {
	
	public int curMenu = 0;
	public int minMenu = 0;
	public int maxMenu = 1;
	public boolean select,exitMenu;
	public boolean up,down;
	
	Font fontGame = new Font("arial",Font.BOLD,12*Game.SCALE);
	Font fontMenu = new Font("arial",Font.BOLD,8*Game.SCALE);
	
	public void tick() {
		
		menuDirections();
		
		menuActions();
		
	}
	
	public void render(Graphics g) {
		
		renderGameName(g);
		
		renderOptions(g);
		
		renderSelect(g);
		
	}
	
	public void menuDirections() {
		
			if(up) {
				Sound.menuSelect.play();
				up = false;
				curMenu--;
				if(curMenu < minMenu) {
					curMenu = maxMenu;
				}
				
			}else if(down) {
				Sound.menuSelect.play();
				down = false;
				curMenu++;
				if(curMenu > maxMenu) {
					curMenu = minMenu;
				}
				
			}
		
	}

	public void menuActions() {
		if(!exitMenu) {
			if(curMenu == 0 && select) {
				Sound.menuSelect.play();
				Game.gameState = "NORMAL";
				select = false;
			}else if (curMenu == 1 && select) {
				Sound.menuSelect.play();
				exitMenu = true;
				select = false;
			
			}
			
		}else {
			if(curMenu == 0 && select) {
				Sound.menuSelect.play();
				World.restartLevel(1,1);
				Game.gameState = "MENU";
				exitMenu = false;
				select = false;
			}else if (curMenu == 1 && select) {
				Sound.menuSelect.play();
				exitMenu = false;
				select = false;
			}
		}
	}
	
	public void renderGameName(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		if(!exitMenu) {

			g.setColor(Color.WHITE);
			g.setFont(fontGame);
			g.drawString(">Made by Magic<", ((Game.WIDTH*Game.SCALE) / Game.SCALE) - (9*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) - (25*(Game.SCALE)+1));
		}else {
			g.setColor(Color.WHITE);
			g.setFont(fontGame);
			g.drawString("You Sure?", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (10*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) - (25*(Game.SCALE)+1));
		}
	}
	
	public void renderOptions(Graphics g) {
		
		g.setFont(fontMenu);
		if(!exitMenu) {
		g.drawString("Resume", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (8*Game.SCALE),
				((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (15*(Game.SCALE)+1));
		
		
		g.drawString("Exit to Menu", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (2*Game.SCALE),
				((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (35*(Game.SCALE)+5));
		
		
		}else {
			g.drawString("YES", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (25*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (15*(Game.SCALE)+1));
			
			
			g.drawString("NO", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (25*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (35*(Game.SCALE)+5));
		}
	}
	
	public void renderSelect(Graphics g) {
		if(!exitMenu) {
			if(curMenu == 0) {
				g.drawString(">", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (15*(Game.SCALE)+1));
				
			}else {
				g.drawString(">", ((Game.WIDTH*Game.SCALE) / Game.SCALE) - (5*Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (35*(Game.SCALE)+5));
			
			}
		}else {
			if(curMenu == 0) {
				g.drawString(">", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (17*Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (15*(Game.SCALE)+1));
				
			}else {
				g.drawString(">", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (17*Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (35*(Game.SCALE)+5));
			
			}
		}
	
}
	
}