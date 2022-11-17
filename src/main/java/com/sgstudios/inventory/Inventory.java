package com.sgstudios.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.sgstudios.entities.Entity;
import com.sgstudios.entities.Shield;
import com.sgstudios.main.Game;
import static com.sgstudios.main.Game.playerAtual;

public class Inventory {
	
	public static int BACK_X = 144;
	public static int BACK_Y = 140;
	public static int BACK_WIDTH = Game.WIDTH*3 - BACK_X*2;
	public static int BACK_HEIGHT = (Game.HEIGHT*3 - BACK_Y*2) + 80;
	
	public static int[] itemPerSlot;
	public static Item [] items; 
	public boolean up,down,left,right;
	public int cur_x = 0,cur_y = 0;
	public int min_x = 0,min_y = 0;
	public int max_x = 7,max_y = 4;
	
	private Item item_selected;
	private int quant_selected = 0;
	
	public boolean isOpen = false;
	public boolean select = false;
	public boolean selected = false;
	public boolean soltar = false;
	private boolean nule;
	
	public int mx,my;
	
	
	private static Item nulo;
	
	public Inventory() {
		nulo = new Null();
		itemPerSlot = new int[48];
		items = new Item[48];
		if(!nule) {
			for(int i = 0; i < items.length;i++) {
				items[i] = nulo;
			}
		}
		
	}
	
	public void tick() {
		
		if(isOpen) {
			
			selectDirections();
			
			
		}
		
	}
	
	public void render(Graphics g) {
		
		renderBackGround(g);
		
		renderSelect(g);
		
		renderDivs(g);
		
		renderItems(g);
		
		renderUI(g);
		
	}
	
	public void selectDirections() {
		
		if(up) {
			up = false;
			cur_y--;
			if(cur_y < min_y) {
				cur_y = max_y;
			}
		}else if(down) {
			down = false;
			cur_y++;
			if(cur_y > max_y) {
				cur_y = min_y;
			}
		}else if(left) {
			left = false;
			cur_x--;
			if(cur_x < min_x) {
				cur_x = max_x;
			}
		}else if(right) {
			right = false;
			cur_x++;
			if(cur_x > max_x) {
				cur_x = min_x;
			}
		}
		
	}
	
	public void renderSelect(Graphics g) {
		int off;
		if(selected) {
			off = 4;
			g.setColor(Color.red);
		}else {
			off = 0;
			g.setColor(Color.black);
		}
		
		g.fillRect((cur_x*48) + BACK_X + 30-4-(off/2), (cur_y*48) + BACK_Y+25-4-(off/2), 44+off, 44+off);
		
	}
	
	public static Item getItem(int posX, int posY) {
		
		return items[posX+(posY*8)];
		
	}
	
	public void selectCheck() {
		
		if(!selected && !isEmpty(cur_x,cur_y)) {
			
			quant_selected = itemPerSlot[cur_x+(cur_y*8)];
			item_selected = items[cur_x+(cur_y*8)];
			
			items[cur_x+(cur_y*8)] = nulo;
			itemPerSlot[cur_x+(cur_y*8)] = 0;
			
			System.out.println("O item selecionado foi um: " + item_selected.getItemName() + " na posiss�o: " +
			(cur_x+1)+" / " + (1+cur_y));
			
			selected = true;
		}else if(selected && (isEmpty(cur_x,cur_y) || isEqual(item_selected, cur_x,cur_y))) {
			
			putItem(item_selected, cur_x, cur_y, quant_selected);
			
			item_selected= null;
			quant_selected = 0;
			
			selected = false;
		}
			
			
			
		
		
	}
	
	public void putItem(Item item,int posX, int posY, int quant) {
		
		items[posX+(posY*8)] = item;
		itemPerSlot[posX+(posY*8)]+=quant;
		
	}
	
	public void createRectMask() {
		
		Rectangle[] rect = new Rectangle[48];
		for(int yy = 0; yy < 5;yy++) {
			for(int xx = 0; xx < 8;xx++) {
				rect[xx+(yy*8)] = new Rectangle((xx*48)+BACK_X+30, (yy*48)+BACK_Y+25);
			}
		}
	}
	
	public void newItem(Item e) {
			
		for(int yy = 0; yy < 5;yy++) {
			for(int xx = 0; xx < 8;xx++) {
				
				if(isEqual(e,xx,yy)) {
					itemPerSlot[xx+(yy*8)]++;
				return;
				}else if(isEmpty(xx,yy)) {
					itemPerSlot[xx+(yy*8)]++;
					items[xx+(yy*8)] = e;
					
					return;
				}
				
			}
		}
		
	}
	
	public boolean isEmpty(int posX, int posY) {
		
		if(items[posX+(posY*8)] == nulo) {
			
			return true;
		}
		
		return false;
		
	}
	
	public boolean isEqual (Item e,int posX, int posY) {
		
		
		if(items[posX+(posY*8)].getItemName().equalsIgnoreCase(e.getEquipableType()) && e.getType() != "SINGLE") {
			
			
			
			return true;
		} 
			
		
		
		return false;
		
	}
	
	public void equipItem() {
		
		if(!isEmpty(cur_x,cur_y) && items[cur_x+(cur_y*8)].canEquip()) {
			
			
				Game.playerAtual.getPersonalTab().equipItem(items[cur_x+(cur_y*8)]);
				itemPerSlot[cur_x+(cur_y*8)] = 0;
				items[cur_x+(cur_y*8)] = nulo;
			
		}
		
	}

	public void renderUI(Graphics g) {
		
		Graphics2D  g2 = (Graphics2D) g;
		g2.setFont(Game.menu.fontMenu);
		g2.setColor(Color.WHITE);
		g2.drawString("Armadura: " + Game.playerAtual.getArmor(), BACK_X, BACK_Y-15);
		g2.drawString("Agilidade: " + Game.playerAtual.getSpeed(), BACK_X, BACK_Y - 45);
		g2.drawString("Poder: " + Game.playerAtual.getAP(), BACK_X, BACK_Y - 75);
		g2.drawString("Speel Cast Speed: " + Game.playerAtual.getCastSpeed(), BACK_X, BACK_Y - 105);
		g2.drawString("Max Mana: " + Game.playerAtual.getMaxMana(), BACK_X + 260, BACK_Y - 105);
	}

	public void renderBackGround(Graphics g) {
		
		g.setColor(Color.gray);
		g.fillRect(BACK_X, BACK_Y, BACK_WIDTH, BACK_HEIGHT);
		
		
	}
	
	public void renderDivs(Graphics g) {
		
		int posX = (Game.WIDTH*3)-(100+33),
			posY = (Game.HEIGHT*3)-(140+18)-5,
			width = 36,
			height = 36;
			
		for(int i = 0, x = -1, xx = 0, y = -1,yy = 0; i < 47;i++) {
			
			x++;
			xx = x*48;
			
			if(x >= 7) {
				x = -1;
				y++;
				yy = y*48;
			}
			g.setColor(Color.white);
			g.fillRect(xx + BACK_X + 30-2, yy + BACK_Y + 25-2, 40,40);
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(xx + BACK_X + 30, yy + BACK_Y + 25, 36,36);
			
		}	
		
	}
	
	public void renderItems(Graphics g) {
		
		if(isOpen) {
			for(int xx = 0; xx < 8;xx++) {
				for(int yy = 0; yy < 5;yy++) {
					
					
					
					if(items[xx+(yy*8)] instanceof ShieldItem && (items[xx+(yy*8)].getState().equalsIgnoreCase("INVENTORY"))) {
						g.drawImage(Entity.SHIELD, (xx*48)+BACK_X+30, (yy*48)+BACK_Y+25,36,36, null);
						
					}else if(items[xx+(yy*8)] instanceof RingManaItem && (items[xx+(yy*8)].getState().equalsIgnoreCase("INVENTORY"))) {
						
						g.drawImage(Entity.RINGMANA, (xx*48)+BACK_X+30, (yy*48)+BACK_Y+25,36,36, null);
						
						
					}else if(items[xx+(yy*8)] instanceof BootsComumItem && (items[xx+(yy*8)].getState().equalsIgnoreCase("INVENTORY"))){
                                            
                                            g.drawImage(Entity.BOOTSCOMUM, (xx*48)+BACK_X+30, (yy*48)+BACK_Y+25,36,36, null);
                                            
                                        }
					
					if(itemPerSlot[xx+(yy*8)] > 0) {
						
						g.setColor(Color.black);
						g.setFont(new Font("arial",Font.BOLD,20));
						g.drawString(""+itemPerSlot[xx+(yy*8)], (xx*48)+BACK_X+30, (yy*48)+BACK_Y+38);
						
					}
					
				}
			}
		}
		
	}


}