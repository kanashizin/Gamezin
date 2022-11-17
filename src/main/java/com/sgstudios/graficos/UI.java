package com.sgstudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.sgstudios.main.Game;
import com.sgstudios.world.Camera;

public class UI {
	
	private int timer1 = 0,speed1 = 2,timer2 = 0,speed2 = 2;
	
	public void tick() {
		
		tickGameOver();
		
	}
	
	public void render(Graphics g) {
		
		life(g);
		
		mana(g);
		
		gameOver(g);
		
		//renderDamage(g);
		
	}
	
	public void gameOver(Graphics g) {
		if(Game.gameState.contentEquals("GAME_OVER")) {
			Graphics2D  g2 = (Graphics2D) g;
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g2.setColor(new Color(255,255,255,timer1));
			g2.drawString("Game Over", Game.WIDTH/2-33, Game.HEIGHT/2-10);
			g2.setColor(new Color(255,255,255,timer2));
			g2.drawString(">Press Enter for Restart!<", Game.WIDTH/2-73, Game.HEIGHT/2+5);
		}
	}
	
	public void renderDamage(Graphics g) {
		if(Game.playerAtual.isDamaged) {
			g.setFont(Game.menu.fontGame);
			g.setColor(Color.RED);
			g.drawString("-"+ Game.playerAtual.damagedHit,Game.playerAtual.getX() - Camera.x, Game.playerAtual.getY() - Camera.y);
		}
	}
	
	public void tickGameOver() {
		
		timer1+=speed1;
		
		if(timer1 > 250) {
			speed1 = 0;
		}
		
		timer2+=speed2;
		
		if(timer2 <= 50) {
			speed2 = 2;
		}else if(timer2 >= 250) {
			speed2 = -2;
		}
		
	}
	
	public void life(Graphics g) {
		if(Game.gameState.contentEquals("NORMAL")) {
			g.setColor(Color.BLACK);
			g.fillRect(9, 4, 52, 12);
			//HP color!
			if(Game.playerAtual.life > 60) {
				g.setColor(Color.GREEN);
			}else if(Game.playerAtual.life > 30) {
				g.setColor(Color.ORANGE);
			}else {
				g.setColor(Color.RED);
			}
			//HP
			g.fillRect(10, 5, (int)((Game.playerAtual.life/Game.playerAtual.maxLife)*50), 10);
			g.setFont(new Font("Arial",Font.BOLD,10));
			//HP String
			g.setColor(Color.WHITE);
			g.drawString((int)Game.playerAtual.life + "/"+(int)Game.playerAtual.maxLife, 15, 14);
		}

	}
	
	public void mana(Graphics g) {
		if(Game.gameState.contentEquals("NORMAL")) {
			//Background Mana Countdown
			g.setColor(Color.DARK_GRAY);
			g.fillRect(195, 5, Game.playerAtual.reloadTimer/2, 7);
			//Mana Countdown
			g.setColor(Color.CYAN);
			g.fillRect(195, 5, (Game.playerAtual.timer/2), 7);
		}
		
	}
}