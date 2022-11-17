package com.sgstudios.main;

import com.sgstudios.entities.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import com.sgstudios.inventory.Inventory;
import com.sgstudios.world.World;

public class Menu {
	
	public int cur_menu = 0;
	public int min_menu = 0;
	public int max_menu = 2;
	
	public int cur_load_menu = cur_menu;
	public int next_load_menu = cur_menu+1;
	public int previus_load_menu = cur_menu-1;
	
	public int arrow_cur_up_frame = 0, arrow_min_up_frame = 0, arrow_max_up_frames = 10;
	public int arrow_cur_down_frame = 0, arrow_min_down_frame = 0, arrow_max_down_frames = 10;
	public boolean arrow_up_animation = false, arrow_down_animation = false;
	
	public int exit_timer = 0,max_exit_timer = 254;
	public boolean select,exit;
	public Font fontGame = new Font("arial",Font.BOLD,12*Game.SCALE);
	public Font fontMenu = new Font("arial",Font.BOLD,8*Game.SCALE);
	
	public boolean up,down;
	public boolean load_game,new_game;
	public int min_user = 0;
	
	public boolean delete_user;
	
	public void tick() {
		
		tickMenuDirections();
		
		tick_load_game_dir();
		
		tickMenuActions();
		
		tickExit();
		
		tick_arrow_animation();
		
		tick_new_game();
		
		tick_load_game();
		
		
	}
	
	public void render(Graphics g) {
		
		renderGameName(g);
		
		//Options Menu
		
		renderMenus(g);
		
		renderSelectMenus(g);
		
		renderExit(g);
		
		render_load_game(g);
		
	}
	
	private void tickExit() {
		if(exit) {
			exit_timer+=2;
			if(exit_timer >= max_exit_timer) {
				
				System.exit(1);
			}
		}
	}
	
	private void renderExit(Graphics g) {
		if(exit) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255,255,255,255-exit_timer));
			g2.setFont(fontGame);
			g2.drawString("Thanks for play",((Game.WIDTH*Game.SCALE) / Game.SCALE) - (9*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE));
			g2.drawString("My Game",((Game.WIDTH*Game.SCALE) / Game.SCALE) - (Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (20*Game.SCALE));
		}
	}
	
		private void tick_arrow_animation() {
		
		if(arrow_up_animation) {
			arrow_cur_up_frame++;
			if(arrow_cur_up_frame > arrow_max_up_frames) {
				arrow_cur_up_frame = 0;
				arrow_up_animation = false;
			}
		}
		
		if(arrow_down_animation) {
			arrow_cur_down_frame++;
			if(arrow_cur_down_frame > arrow_max_down_frames) {
				arrow_cur_down_frame = 0;
				arrow_down_animation = false;
			}
		}
		
	}
	
	private void tickMenuDirections() {
		if(!exit && !new_game && !load_game) {
			if(up) {
				Sound.menuSelect.play();
				up = false;
				
				cur_menu--;
				if(cur_menu < min_menu) {
					cur_menu = max_menu;
				}
			}else if(down) {
				Sound.menuSelect.play();
				down = false;
				
				cur_menu++;
				if(cur_menu > max_menu) {
					cur_menu = min_menu;
				}
			}
			
		}
		
	}
	
	private void tick_new_game() {
		
		if(new_game) {
			while(new_game) {
				String name = JOptionPane.showInputDialog("Insert Name");
				if(name != null) {
					new_game = false;
                                        select = false;
                                        Game.loading = true;
                                        Player temp = SaveUsers.createPlayer(name);
					SaveUsers.addPlayer(temp);
                                        SaveUsers.setCurPlayer(temp);
                                        System.out.println("Cur_Stage " + Game.playerAtual.CUR_STAGE + "/ Cur_level " + Game.playerAtual.CUR_LEVEL);
					World.restartLevel(Game.playerAtual.CUR_STAGE, Game.playerAtual.CUR_LEVEL);
					Game.gameState = "NORMAL";
				}else{
					new_game = false;
                                        select=false;
                                }
			}
		}
		
	}
	
	private void tick_load_game() {
		if(load_game) {
			if(SaveUsers.getListSize() == 0) {
				load_game = false;
			}
			
			if(delete_user) {
				System.out.println("Deletando o player: " + SaveUsers.getPlayer(cur_menu));
				
				delete_user = false;
			}
			
			if(select) {
				Sound.menuSelect.play();
				load_game = false;
				select = false;
                                Game.loading = true;
				SaveUsers.setCurPlayer(SaveUsers.getPlayer(cur_menu));
				
				SaveUsers.addPlayer(Game.playerAtual);
				
                                Game.gameState = "NORMAL";
				
			}
		}
		
	}
	
	private void tick_load_game_dir() {
		
		if(load_game) {
			
			if(up) {
				Sound.menuSelect.play();
				up = false;
				arrow_up_animation = true;
				cur_menu--;
				if(cur_menu < min_user) {
					cur_menu = SaveUsers.getListSize()-1;
				}
			}else if(down) {
				Sound.menuSelect.play();
				down = false;
				arrow_down_animation = true;
				cur_menu++;
				if(cur_menu >= SaveUsers.getListSize()) {
					cur_menu = min_user;
				}
			}
		}
		
	}
	
	private void tickMenuActions() {
		if(!exit && !new_game && !load_game) {
			if(cur_menu == 0 && select) {
				//New Game
				Sound.menuSelect.play();
				new_game = true;
				Game.loading = true;
				select = false;
			}else if (cur_menu == 1 && select) {
				//Load Game
				cur_menu = 0;
				Sound.menuSelect.play();
				load_game = true;
				select = false;
			}else if(cur_menu == 2 && select) {
				//Exit Game
				Sound.menuSelect.play();
				exit = true;
				select = false;
			}
		}
	}
	
	private void renderGameName(Graphics g) {
		if(!exit) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setColor(Color.WHITE);
			g.setFont(fontGame);
			g.drawString(">Made by Magic<", ((Game.WIDTH*Game.SCALE) / Game.SCALE) - (9*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) - (25*(Game.SCALE)+1));
		}
	}
	
	private void renderMenus(Graphics g) {
		if(!exit && !new_game && !load_game) {
			g.setFont(fontMenu);
			
			g.drawString("New game", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (8*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (7*(Game.SCALE)+1));
			
			g.drawString("Load Game", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (6*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (25*(Game.SCALE)+5));
			
			g.drawString("Exit Game", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (7*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (45*(Game.SCALE)+5));
		}
	}
	
	private void renderSelectMenus(Graphics g) {
		if(!exit && !new_game && !load_game) {
			if(cur_menu == 0) {
				g.drawString(">", ((Game.WIDTH*Game.SCALE) / Game.SCALE) + (Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (7*(Game.SCALE)+1));
				
			}else if(cur_menu == 1) {
				g.drawString(">", ((Game.WIDTH*Game.SCALE) / Game.SCALE) - (Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (25*(Game.SCALE)+5));
				
			}else {
				g.drawString(">",((Game.WIDTH*Game.SCALE) / Game.SCALE) - (2-Game.SCALE),
						((Game.HEIGHT*Game.SCALE) / Game.SCALE) + (45*(Game.SCALE)+5));
				
			}
		}
	}
	
	private void render_load_game(Graphics g) {
		
		if(load_game) {
			
			cur_load_menu = cur_menu;
			next_load_menu = cur_menu+1;
			previus_load_menu = cur_menu-1;
			
			if(next_load_menu > SaveUsers.getListSize())
				next_load_menu = 0;
			if(previus_load_menu < 0)
				previus_load_menu = SaveUsers.getListSize()-1;
			
			g.setColor(Color.white);
			
			g.drawString(1+cur_menu+ ": " + SaveUsers.getPlayer(cur_menu),(Game.WIDTH+60), 250);
			Graphics2D g2 = (Graphics2D)(g);
			int down_arrow_x = ((Game.WIDTH*3)/2)-20,down_arrow_y = 271;
			double angle = Math.toRadians(180);
			g2.rotate(-angle, down_arrow_x+25, down_arrow_y+25);
			
				g.drawImage(Game.spritesheet.getSprite(16*9, 16*3, 16, 16), down_arrow_x, down_arrow_y-(int)(arrow_cur_down_frame*1.5), 50, 50, null);
				
			g2.rotate(angle, down_arrow_x+25, down_arrow_y+25);
			
				g2.drawImage(Game.spritesheet.getSprite(16*9, 16*3, 16, 16), ((Game.WIDTH*3)/2)-20, 160-(int)(arrow_cur_up_frame*1.5), 50, 50, null);
			
			
			
		}
		
	}
        
        public void render_loading(Graphics g){
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
            g.setColor(Color.WHITE);
			g.setFont(fontMenu);
			g.drawString("> Press Space <", ((Game.WIDTH) / Game.SCALE) - (16*Game.SCALE),
					((Game.HEIGHT*Game.SCALE) / Game.SCALE) - (25*(Game.SCALE)+1));
            
            
        }
}