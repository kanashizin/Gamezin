package com.sgstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.Random;

import com.sgstudios.main.Game;
import com.sgstudios.main.Sound;
import com.sgstudios.world.Camera;
import com.sgstudios.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.3;
	private int maskx = 8,masky = 8,maskw = 8,maskh = 8;
	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 2;
	private BufferedImage[] sprites;
	
	private int life  = 1;
	private int counter = 0;
	Random rand = new Random();
	private boolean isDamaged = false;
	private int damagedFrames = 10,curFrame = 0;
	
	long startTimer, endTimer;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[3];
		sprites[0] = Game.spritesheet.getSprite(16*6, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(16*7, 16, 16, 16);
		sprites[2] = Game.spritesheet.getSprite(16*8, 16, 16, 16);
		
		
	}
	
	public void tick() {
		
		walking();
		
		collidingMana();
			
		destroySelf();
		
	}
	
	public void walking() {
		if(this.calculateDistance(this.getX(), this.getY(), Game.playerAtual.getX(), Game.playerAtual.getY()) < 100) {
			if(isHiting() == false) {
				if((int)x < Game.playerAtual.getX() && World.isFree((int)(x+speed),this.getY())
						&& !isColliding((int)(x+speed),this.getY())) {
					x+=speed;
				}else if((int)x > Game.playerAtual.getX() && World.isFree((int)(x-speed),this.getY())
						&& !isColliding((int)(x-speed),this.getY())) {
					x-=speed;
				}
				if(y < Game.playerAtual.getY() && World.isFree(this.getX(),(int)(y+speed))
						&& !isColliding(this.getX(),(int)(y+speed))) {
					y+=speed;
				}else if(y > Game.playerAtual.getY() && World.isFree(this.getX(),(int)(y-speed))
						&& !isColliding(this.getX(),(int)(y-speed))) {
					y-=speed;
				}
			}else {
				
				if(counter == 0) {
					
					startTimer = System.currentTimeMillis();
					counter = 1;
					
				}else if(counter == 1) {
					endTimer = System.currentTimeMillis();
					if((endTimer-startTimer) >= 1000) {
						Game.playerAtual.doDamage(10);
						counter = 0;
					}
				}
				
				
			}
		}
		
		
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		if(isDamaged) {
			this.curFrame++;
				if(this.curFrame == this.damagedFrames) {
					isDamaged = false;
					curFrame = 0;
				}
					
		}	
	}
	
	public void destroySelf() {
		if(life <= 0) {
                        
                        dropItem();
                        
                        if(Game.enemies.size() == 1){
                            
                            World.nextLevel();
                            
                        }
                    
			Game.enemies.remove(this);
			Game.entities.remove(this);
			return;
		}
	}
	
	public void collidingMana() {
		
		for(int i = 0; i < Game.manashoot.size(); i++) {
			Entity e = Game.manashoot.get(i);
			if(e instanceof ManaShoot) {
				if(Entity.isColidding(this, e)) {
					int colidiu = 0;
					colidiu++;
					
					if(colidiu == 1) {
						if(Game.playerAtual.shoot) {
							isDamaged = true;
							Sound.explosion.play();
							if(((ManaShoot)e).getPower() == 1) {
								System.out.println("Vida atual: " + this.life);
								this.life-=2;
								System.out.println("Dano tomado: - 2");
								System.out.println("Poder do mana: " + ((ManaShoot)e).getPower());
							}else if(((ManaShoot)e).getPower() == 2) {
								this.life-=4;
								System.out.println("Dano tomado: - 4");
								System.out.println("Poder do mana: " + ((ManaShoot)e).getPower());
							}if(((ManaShoot)e).getPower() == 3) {
								this.life-=6;
								System.out.println("Dano tomado: - 6");
								System.out.println("Poder do mana: " + ((ManaShoot)e).getPower());
							}if(((ManaShoot)e).getPower() == 4) {
								this.life-=10;
								System.out.println("Dano tomado: - 10");
								System.out.println("Poder do mana: " + ((ManaShoot)e).getPower());
							}
							
							Game.manashoot.remove(e);
							colidiu = 0;
						}
					}
					
					
				}
			}
			
				
			}
		}
		
	public boolean isHiting() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.playerAtual.getX(),Game.playerAtual.getY(),16,16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		for(int i = 0; i < Game.enemies.size();i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY() + masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
        
        private void dropItem(){
            
            int numRand = rand.nextInt(100);
			if( numRand >= 25 && numRand <= 50) {
				
				Game.entities.add(new Shield(this.getX(),this.getY(),16,16,rand.nextInt(100),Entity.SHIELD));
			}else if( numRand >= 50 && numRand <= 75){
                            	
                                Game.entities.add(new RingMana(this.getX(),this.getY(),10,6,rand.nextInt(100)*5,Entity.RINGMANA));
			}else if( numRand >= 75 && numRand <= 100){
                                
                                    Game.entities.add(new BootsComum(this.getX(), this.getY(),16,16,Entity.BOOTSCOMUM));
                                
                        }
            
        }
	
	public void render(Graphics g) {
		if(!isDamaged) {
		g.drawImage(sprites[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else {
			g.drawImage(Entity.ENEMY_DAMAGE,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	
}
