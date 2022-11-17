package com.sgstudios.entities;

import com.sgstudios.inventory.BootsComumItem;
import com.sgstudios.inventory.BootsSpeedItem;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sgstudios.inventory.Inventory;
import com.sgstudios.inventory.PersonalTab;
import com.sgstudios.inventory.RingManaItem;
import com.sgstudios.inventory.ShieldItem;
import com.sgstudios.main.Game;
import com.sgstudios.main.Sound;
import com.sgstudios.world.Camera;
import com.sgstudios.world.World;
import java.io.Serializable;

public class Player extends Entity  implements Serializable{
    
        public String name;
        
	public boolean right,left,up,down;
	public double speed = 1;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
        	
	public int CUR_STAGE = 1;
	public int CUR_LEVEL = 1;
        
	public boolean moving = true;
	
	private int frames = 0, maxFrames = 7, index = 0, maxIndex = 2;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private Inventory inventario;
	private PersonalTab personalTab;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false,casting = false,teleport = false;
	
	public int hasMana = 0;
	public int mx,my,isActive = 0;
	public int mana = 50, manaMaximaBase = 100;
	public int timer = 0,reloadTimer = 60;
	public double tempArmor;
	public int damagedHit;
	public int powerCount;
        public final int maxPowerCount = 4;
	public long startTimer, endTimer;
	
	public static double angle;
	
	public double life = 100, maxLife = 100;
	
	public int armadura, agilidade, poder, manaMaxima, manaAdicional, castSpeed, velocidade;
	private int adicionalSpeed;
	
	
	public Player(String name) {
		super(16, 16);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for(int i = 0;i < 3;i++) {
                    rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
                    leftPlayer[i] = Game.spritesheet.getSprite(64 - (i*16), 16, 16, 16);
		}
		
	}
	
	public void tick() {
		
		walk();
		
		updateCamera();
		
		damaged();
		
		manaShoot();
		
		gameOver();
		
		manaTeleport();
		
		checkMana();
		
		checkManaGun();
		
		checkLifepack();
		
		checkItem();
		
		}
	
	public void doDamage(int damage) {
		
		tempArmor = 0.01*armadura;
		this.damagedHit = (int)((damage-(damage*tempArmor)));
		life-= (damage-(damage*tempArmor));
		
	}

        public PersonalTab getPersonalTab() {
            return personalTab;
        }

        public void setPersonalTab(PersonalTab personalTab) {
            this.personalTab = personalTab;
        }
        
        
        
        public Inventory getInventory(){
            
            return this.inventario;
            
        }
        
        public void setInventory(Inventory inv){
            
            this.inventario = inv;
            
        }
	
	public void checkItem() {
		
		for(int i = 0; i < Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
				if(atual instanceof Shield) {
					if(isColidding(atual, Game.playerAtual)) {
						
						
						ShieldItem item = new ShieldItem(((Shield)atual).getArmor());
						
						inventario.newItem(item);
						Game.entities.remove(atual);
					}
				}else if(atual instanceof RingMana) {
					if(isColidding(atual, Game.playerAtual)) {
						RingManaItem item = new RingManaItem(((RingMana)atual).getMana());
						
						inventario.newItem(item);
						Game.entities.remove(atual);
					}
				}else if(atual instanceof BootsComum){
                                    
                                    if(isColidding(atual, Game.playerAtual)) {
						BootsComumItem item = new BootsComumItem();
						
						inventario.newItem(item);
						Game.entities.remove(atual);
					}
                                    
                                }else if(atual instanceof BootsSpeed){
                                    
                                    if(isColidding(atual, Game.playerAtual)) {
                                                
						BootsSpeedItem item = new BootsSpeedItem(1);
						
						inventario.newItem(item);
						Game.entities.remove(atual);
					}
                                    
                                }
			
		}
		
	}

	public void gameOver() {
		if(Game.playerAtual.life <= 0) {
			Game.gameState = "GAME_OVER";
		}
		
	}
	
	public void checkManaGun() {

		for(int i = 0; i < Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(Game.playerAtual, atual)) {
					hasMana = 1;
					Sound.selected.play();
					Game.entities.remove(atual);
					
				}
			}
			
		}
	}
	
	public void checkMana() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Mana) {
				if(Entity.isColidding(this, atual)) {
					mana+=30;
					Sound.selected.play();
					Game.entities.remove(atual);
				}
			}
		}
		
	}
	
	public void checkLifepack() {
		
		for(int i = 0; i < Game.entities.size();i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this, atual)) {
					if(life < 90) {
					life+=10;
					Sound.selected.play();
					Game.entities.remove(atual);
					}
				}
			}
			
		}
		
	}
	
	public void walk() {
		
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY()) && moving && !casting) {
			moved = true;
			x+=speed;
			dir = right_dir;
		}else if(left && World.isFree((int)(x-speed),this.getY()) && moving && !casting) {
			moved = true;
			x-=speed;
			dir = left_dir;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed)) && moving && !casting) {
			moved = true;
			y-=speed;
		}else if(down && World.isFree(this.getX(),(int)(y+speed)) && moving && !casting) {
			moved = true;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}

	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void damaged() {
		
		if(isDamaged) {
			Sound.hurt.play();
			this.damageFrames++;
			if(this.damageFrames == 10) {
				isDamaged = false;
				damageFrames = 0;
			}
			
		}
		
	}
	
	public void manaShoot() {
		if((casting || shoot)&& hasMana == 1 && mana > 4) {
			if(casting)
			endTimer = System.currentTimeMillis();
			
			if(shoot) {
				casting = false;
				
				timer++;	
				
				if(timer <= 1) {
                                        
					if((endTimer-startTimer) < 1000) {
						powerCount = 1;
						mana-=1;
					}else if((endTimer-startTimer) < 2000 &&
							(endTimer-startTimer) > 1000) {
						
						powerCount = 2;
						mana-=2;
					}else if((endTimer-startTimer) < 3000 &&
							(endTimer-startTimer) > 2000) {
						
						powerCount = 3;
						mana-=3;
					}else if((endTimer-startTimer) > 3000) {
						powerCount = 4;
						mana-=4;
                                                
					}
					
				int px = 0;
				int py = 2;
				
				if(dir == right_dir) {
					px = 9;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}else {
					px = -9;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				ManaShoot manashoot = new ManaShoot(this.getX() + px,this.getY() + py,12,12,dx,dy,powerCount,null);
				Game.manashoot.add(manashoot);
				
				}else if(timer > reloadTimer) {
					timer = 0;
					shoot = false;				
				}
			}
		}
	}
	
	public void manaTeleport() {
		
		if(teleport && hasMana == 1&& mana > 2) {
			if(isActive == 0)
				isActive = 1;
			
			if(isActive == 1) {
				isActive++;
				Sound.shoot.play();
				int px = 0;
				int py = 2;
				
				if(dir == right_dir) {
					px = 9;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}else {
					px = -9;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				ManaTeleport teleport = new ManaTeleport(this.getX() + px,this.getY() + py,12,12,dx,dy,null);
				Game.teleport.add(teleport);
				mana-=3;
				
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				if(hasMana == 1) {
					g.drawImage(Entity.MANA_LEFT,this.getX() +  - Camera.x,this.getY() - Camera.y,null);
				}
			}else if(dir== left_dir) {
				g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				if(hasMana == 1) {
					g.drawImage(Entity.MANA_RIGHT,this.getX() - 2 - Camera.x,this.getY() - Camera.y,null);
				}
			}
		}else {
			if(dir == right_dir) {
				g.drawImage(Entity.PLAYER_DAMAGE_RIGHT,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(dir== left_dir) {
				g.drawImage(Entity.PLAYER_DAMAGE_LEFT,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}
	}


	public int getArmor() {
		return this.armadura;
	}
	
	public int getAgility() {
		return this.agilidade;
	}
	
	public int getAditionalSpeed() {
		
		return this.velocidade;
		
	}
	
	public double getSpeed() {
		
		return this.speed;
		
	}
	
	public int getCastSpeed() {
		return this.castSpeed;
	}
	
	public int getAP() {
		return this.poder;
	}
	
	public int getMaxMana() {
		return this.manaMaxima;
	}
	
	public void setArmor(int newArmor) {
		
		this.armadura = newArmor;
		
	}
	
	public void setAditionalMana(int mana) {
		
		manaAdicional= mana;
		manaMaxima = manaMaximaBase+manaAdicional;
		
	}
	
	public void addAditionalSpeed(int adicionalSpeed) {
		
		this.adicionalSpeed += adicionalSpeed;
		this.speed+= (velocidade*0.01);
		
	}
	
	public void addBaseSpeed(int speed) {
		
		this.velocidade+=speed;
		this.speed+= (velocidade*0.01);
	}
        
        public void decreaseAditionalSpeed(int speed){
                this.adicionalSpeed-=speed;
        }

}