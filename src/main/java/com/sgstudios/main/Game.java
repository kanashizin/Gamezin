package com.sgstudios.main;



import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sgstudios.entities.Enemy;
import com.sgstudios.entities.Entity;
import com.sgstudios.entities.ManaShoot;
import com.sgstudios.entities.ManaTeleport;
import com.sgstudios.entities.Player;
import com.sgstudios.entities.SavePoint;
import com.sgstudios.graficos.ItemTexture;
import com.sgstudios.graficos.TexturePack;
import com.sgstudios.graficos.UI;
import com.sgstudios.inventory.Inventory;
import com.sgstudios.inventory.Item;
import com.sgstudios.inventory.PersonalTab;
import com.sgstudios.world.Camera;
import com.sgstudios.world.World;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private Boolean isRunning;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	public static boolean load_user = true;
        public static boolean loading = false;
	
	public static int fps = 0;
        
        public final static int MAX_LEVEL = 2;
        public final static int MAX_STAGE = 1;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<ManaShoot> manashoot;
	public static List<ManaTeleport> teleport;
	public static List<Item> items;
	
	public static TexturePack spritesheet;
	public static ItemTexture itemsSpritesheet;
	private int mouseX, mouseY;
	public static World world;
	
	public static Player playerAtual;
	
	public static UI ui;
	
	public static Random rand;
	
	public static Menu menu;
	
	public static Pause pause;
	
	public static String gameState = "MENU";
	
	public static boolean restartGame = false;
	
	public Game() {
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		SaveUsers.playersList = new ArrayList<Player>();
                
		//Inicializando objetos
		rand = new Random();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		menu = new Menu();
		pause = new Pause();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		manashoot = new ArrayList<ManaShoot>();
		teleport = new ArrayList<ManaTeleport>();
		items = new ArrayList<Item>();
		spritesheet = new TexturePack("/spritesheet.png");
		itemsSpritesheet = new ItemTexture("/spritesheet16x.png");
		ui = new UI();
                
                
               
                
                try{
                SaveUsers.loadUsers();
               
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                for (int i = 0; i < SaveUsers.getListSize(); i++) {
                    System.out.println(SaveUsers.playersList.get(i).name);
            }
		/*********************/
		
	}
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		
			if(gameState.contentEquals("NORMAL")) {
				restartGame = false;
				if(!loading){
                                    
                                   

                                    settingEntities();
                                    
				}
			}else if(gameState.contentEquals("GAME_OVER")) {
				if(Game.restartGame) {
					World.restartLevel(playerAtual.CUR_STAGE, playerAtual.CUR_LEVEL);
					gameState = "NORMAL";
				}
				
			}else if(gameState.contentEquals("MENU")) {
				menu.tick();
				
				
			}else if(gameState.contentEquals("PAUSE")) {
				pause.tick();
				
			}else if(gameState.contentEquals("BACKPACK")) {
				playerAtual.getInventory().tick();
				for(int i = 0; i < items.size(); i++) {
					Item e = items.get(i);
					e.tick();
				}
			}else if(gameState.contentEquals("PERSONALTAB")) {
				
				playerAtual.getPersonalTab().tick();
				
			}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*Renderiza��o do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		
			if(gameState.contentEquals("NORMAL") && !loading) {
				world.render(g);
                                if(!loading){
                                    for(int i = 0; i < entities.size();i++) {
                                            Entity e = entities.get(i);
                                            e.render(g);
                                    }
                                    for(int i = 0; i < manashoot.size();i++) {
                                            manashoot.get(i).render(g);
                                    }

                                    for(int i = 0; i < teleport.size();i++) {
                                            teleport.get(i).render(g);
                                    }
				
                                }else {
                                    menu.render_loading(g);
                                }
			}
			if(!loading)
			ui.render(g);
	
			/***/
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE,null);
			if(gameState.contentEquals("NORMAL") && !loading) {
			
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial",Font.BOLD,5*SCALE));
				g.drawString("Mana: " + playerAtual.mana, 200*SCALE, 30);
				
				
			}else if(gameState.contentEquals("MENU")) {
				menu.render(g);
				
			}else if(gameState.contentEquals("PAUSE")) {
				pause.render(g);
				
			}else if(gameState.contentEquals("BACKPACK")) {
				playerAtual.getInventory().render(g);
				
			}else if(gameState.contentEquals("PERSONALTAB")) {		
				playerAtual.getPersonalTab().render(g);
				
			}
			
			bs.show();
		
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		double timer = System.currentTimeMillis();
		
		requestFocus();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				if(fps < 62) {
				tick();
				render();
				}
				fps++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				fps = 0;
				timer+=1000;
			}
			stop();
			
		}	
	}
	
	public void settingEntities() {
		
		for(int i = 0; i < entities.size();i++) {
			Entity e = entities.get(i);
			e.tick();
		}
	
		for(int i = 0; i < manashoot.size();i++) {
		manashoot.get(i).tick();
		}
		for(int i = 0; i < teleport.size();i++) {
			teleport.get(i).tick();
			}
		
	}
        
        
        
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_I && (gameState == "NORMAL" || gameState == "BACKPACK")) {
			if(!playerAtual.getInventory().isOpen) {
				gameState = "BACKPACK";
				playerAtual.getInventory().isOpen = true;
			}else{
				gameState = "NORMAL";
				playerAtual.getInventory().isOpen = false;
			}
			
		}
                
                if(e.getKeyCode() == KeyEvent.VK_SPACE && gameState.equalsIgnoreCase("NORMAL")&& loading){
                    
                    loading = false;
                    
                }
		
		if(e.getKeyCode() == KeyEvent.VK_D) {
			playerAtual.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			playerAtual.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			playerAtual.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			playerAtual.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(gameState.contentEquals("MENU")) {
				menu.up = true;
				}else if(gameState.contentEquals("PAUSE")) {
					pause.up = true;
				}else if(gameState.contentEquals("BACKPACK")){
					playerAtual.getInventory().up = true;
				}else if(gameState.contentEquals("PERSONALTAB")) {
					playerAtual.getPersonalTab().up = true;
					
					
				}
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ) {
			if(gameState.contentEquals("MENU")) {
			menu.down = true;
			}else if(gameState.contentEquals("PAUSE")) {
				pause.down = true;
			}else if(gameState.contentEquals("BACKPACK")) {
				playerAtual.getInventory().down = true;
			}else if(gameState.contentEquals("PERSONALTAB")) {
				playerAtual.getPersonalTab().down = true;
				
				
			}
			
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(gameState.contentEquals("BACKPACK")) {
				playerAtual.getInventory().left = true;
			}else if(gameState.contentEquals("PERSONALTAB")) {
				playerAtual.getPersonalTab().left = true;
				
				
			}
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(gameState.contentEquals("BACKPACK")) {
				playerAtual.getInventory().right = true;
			}else if(gameState.contentEquals("PERSONALTAB")) {
				playerAtual.getPersonalTab().right = true;
				
				
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			if(!gameState.contentEquals("MENU") && !gameState.contentEquals("BACKPACK") && !gameState.contentEquals("PERSONALTAB")) {
				gameState = "PAUSE";
			}else if(gameState.contentEquals("MENU") && menu.load_game) {
				menu.cur_menu = 0;
				menu.load_game = false;
			}
			if(gameState.equalsIgnoreCase("PERSONALTAB") || gameState.equalsIgnoreCase("BACKPACK")) {
				gameState = "NORMAL";
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState.contentEquals("GAME_OVER")) {
				restartGame = true;
			}else if(gameState.contentEquals("MENU")) {
				menu.select = true;
			}else if(gameState.contentEquals("PAUSE")) {
				pause.select = true;
			}else if(gameState.contentEquals("BACKPACK")) {
				
				playerAtual.getInventory().selectCheck();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DELETE) {
			if(menu.load_game) {
				System.out.println("Delete");
				menu.delete_user = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			
			if(gameState.equalsIgnoreCase("BACKPACK")) {
				
				playerAtual.getInventory().equipItem();
				
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_P) {
			
			if(gameState.equalsIgnoreCase("NORMAL")) {
				
				gameState = "PERSONALTAB";
				
			}else if(gameState.equalsIgnoreCase("PERSONALTAB")) {
				
				gameState = "NORMAL";
				
			}
			
		}

		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			playerAtual.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			playerAtual.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			playerAtual.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			playerAtual.down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1) {
                    if(gameState.equalsIgnoreCase("NORMAL")){
                        if(playerAtual.hasMana == 1) {
                            playerAtual.casting = true;
                            playerAtual.startTimer = System.currentTimeMillis();
                            }
                        }else if(e.getButton() == MouseEvent.BUTTON3){
                                if(playerAtual.hasMana == 1) {
                                        if(Game.playerAtual.isActive == 0) {
                                                playerAtual.teleport = true;
                                                playerAtual.mx = (e.getX() / SCALE);
                                                playerAtual.my = (e.getY() / SCALE);
                                        }else 
                                                playerAtual.isActive++;
                                }
                        }
                }
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
            if(gameState.equalsIgnoreCase("NORMAL")){
		if(e.getButton() == MouseEvent.BUTTON1) {
			
			playerAtual.shoot = true;
			playerAtual.mx = (e.getX() / SCALE);
			playerAtual.my = (e.getY() / SCALE);
			
		}
            }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}
}