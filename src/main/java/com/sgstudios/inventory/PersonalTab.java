package com.sgstudios.inventory;

import java.awt.Color;
import java.awt.Graphics;

import com.sgstudios.entities.Entity;
import com.sgstudios.main.Game;

public class PersonalTab {
	
	public static int BACK_X = Game.WIDTH;
	public static int BACK_Y = 80;
	public static int BACK_WIDTH = Game.WIDTH;
	public static int BACK_HEIGHT = Game.HEIGHT*2;
	public boolean up,down,left,right;
	public int cur_x = 2,cur_y = 0;
	public int min_x = 0,min_y = 0;
	public int max_x = 4,max_y = 2;
	
	public Item[] items;
	private Null nulo;
	public PersonalTab() {
		nulo = new Null();
		items = new Item[7];
		for(int x = 0; x < 7;x++) {
			items[x] = nulo;
		}
	}
	
	public void tick() {
		
		directions();
		
	}
	
	public void directions() {
		
		if(up) {
			up = false;
			if(cur_x == 2) {
				cur_y--;
				if(cur_y < min_y) {
					cur_y = max_y;
				}
			}
		}else if(down) {
			down = false;
			if(cur_x == 2) {
				cur_y++;
				if(cur_y > max_y) {
					cur_y = min_y;
				}
			}
		}else if(left) {
			left = false;
			if(cur_y == 1) {
				System.out.println("Cur_X: " + cur_x);
				cur_x--;
				
				if(cur_x < min_x) {
					cur_x = max_x;
				}
			}
		}else if(right) {
			right = false;
			if(cur_y == 1) {
				System.out.println("Cur_X: " + cur_x);
				cur_x++;
				
				if(cur_x > max_x) {
					cur_x = min_x;
				}
			}
		}
		
		
	}
	
	public boolean isEmpty(int posX, int posY) {
		
		if(items[posX+(posY*2)] == nulo) {
			
			return true;
		}
		
		return false;
		
	}

	public void equipItem(Item item) {
		
		
		if(item.getEquipableType().equalsIgnoreCase("RING")) {
			if(isEmpty(0,1)) {
				
				items[0+(1*2)] = item;
				
			}else if(isEmpty(4,1)) {
				
				items[4+(1*2)] = item;
				
			}
			Game.playerAtual.setAditionalMana(((RingManaItem)item).getAdicionalMana());
			
		}else if(item.getEquipableType().equalsIgnoreCase("SHIELD")) {
			items[2+(1*2)] = item;
			Game.playerAtual.setArmor(((ShieldItem)item).getArmor());
			
			
			
			
		}else if(item.getEquipableType().equalsIgnoreCase("BOOTS")) {
			
                        Game.playerAtual.addBaseSpeed(((BootsComumItem)item).getSpeedBase());
                        
                        if(item.getItemName().equalsIgnoreCase("Botas da Rapidez")){
                            Game.playerAtual.addAditionalSpeed(((BootsSpeedItem)item).getAddSpeed());
                        }
			items[2+(2*2)] = item;
			
		}
		
	}
	
	public void slots() {
		
		
		
	}
	
	public void render(Graphics g) {
		
		renderBackGround(g);
		renderSelect(g);
		renderDivs(g);
		renderItems(g);
	}
	
	public void renderBackGround(Graphics g) {
		
		g.setColor(Color.darkGray);
		g.fillRect(BACK_X-96, BACK_Y-20, BACK_WIDTH+192, BACK_HEIGHT-48);
		
	}
	
	public void renderDivs(Graphics g) {
		
		g.setColor(Color.LIGHT_GRAY);
		
		for(int i = 0; i < 3; i++) {
			
			g.fillRect((BACK_X+(BACK_WIDTH/2))-32, (80*(i+1)), 64, 64);
			
		}
		
		g.fillRect((BACK_X+(BACK_WIDTH/2))-112, (80*(2)), 64, 64);
		g.fillRect((BACK_X+(BACK_WIDTH/2))+48, (80*(2)), 64, 64);
		g.fillRect((BACK_X+(BACK_WIDTH/2))+128, (80*(2)), 64, 64);
		g.fillRect((BACK_X+(BACK_WIDTH/2))-192, (80*(2)), 64, 64);
		
		g.setColor(Color.WHITE);
		
		for(int i = 0; i < 3; i++) {
			
			g.fillRect(((BACK_X+(BACK_WIDTH/2))-32)+4, (80*(i+1)+4), 56, 56);
			
		}
		
		g.fillRect(((BACK_X+(BACK_WIDTH/2))-112)+4, (80*(2)+4), 56, 56);
		g.fillRect(((BACK_X+(BACK_WIDTH/2))+48)+4, (80*(2)+4), 56, 56);
		g.fillRect(((BACK_X+(BACK_WIDTH/2))+128)+4, (80*(2)+4), 56, 56);
		g.fillRect(((BACK_X+(BACK_WIDTH/2))-192)+4, (80*(2)+4), 56, 56);
		
		
	}
	
	public void renderItems(Graphics g) {
		
		
			
			if(items[0+(1*2)] instanceof RingManaItem) {
				
				g.drawImage(Entity.RINGMANA, (BACK_X+(BACK_WIDTH/2))-192, (80*(1+1)), 64, 64, null);
				
			}
			if(items[4+(1*2)] instanceof RingManaItem){
				
				g.drawImage(Entity.RINGMANA, (BACK_X+(BACK_WIDTH/2))+128, (80*(1+1)), 64, 64, null);
				
			}
			if(items[2+(1*2)] instanceof ShieldItem) {
				
				g.drawImage(Entity.SHIELD, (BACK_X+(BACK_WIDTH/2))-32, (80*(1+1)), 64, 64, null);
				
			}
			if(items[2+(2*2)] instanceof BootsComumItem){
                            g.drawImage(Entity.BOOTSCOMUM, (BACK_X+(BACK_WIDTH/2))-32, (80*(2+1)), 64, 64, null);
                        }
				
				
			
			
			
		
		
	}

	public void renderSelect(Graphics g) {
		
		int xx = cur_x*80;
		
		g.setColor(Color.BLACK);
		g.fillRect(((BACK_X+(BACK_WIDTH/2)-(32*6))-6)+xx, (80*(cur_y+1))-6, 76, 76);
		
	}
}