package com.sgstudios.main;

import com.sgstudios.entities.Player;
import com.sgstudios.inventory.Inventory;
import com.sgstudios.inventory.PersonalTab;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.sgstudios.world.World;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveUsers {
	
        public static ArrayList<Player> playersList;
        
        public static void setCurPlayer(Player player){
            
            
            Game.playerAtual = player;
            
        }
        
        public static Player createPlayer(String name){
            
            Player player = new Player(name);
            player.setInventory(new Inventory());
            player.setPersonalTab(new PersonalTab());
            return player;
            
        }
        
        public static void addPlayer(Player newPlayer){
            
            playersList.add(newPlayer);
            
        }
        
	public static void saveUsers() {
            
            try{
                
                File file = new File("res/saves/players.list");
                ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
                for (int i = 0; i < playersList.size(); i++) {
                    
                    objOut.writeObject(playersList.get(i));
                    
                }
                objOut.close();
                
            }catch(Exception e){
            
                
                
        }
            
	}
	
	public static void loadUsers() throws Exception{
            
            //Inicio dos bagulhos q ninguem sabe oq faz
            ObjectInputStream objInput;
            File file;
            
            try{
            
                file = new File("res/saves/players.list");
                objInput = new ObjectInputStream(new FileInputStream(file));
                
                 Player tempPlayer;
                 tempPlayer = (Player) objInput.readObject();
                 System.out.println("Loaded player name: " + tempPlayer.name);
                 while(tempPlayer != null){
                     
                     playersList.add(tempPlayer);
                     
                 }
                objInput.close();
            }catch(Exception e){System.out.println("loadUsers: ");System.out.println(e.getMessage());}
            
        }
        
        public static void deletePlayer(int pos){
            
                    try{
                        playersList.remove(playersList.get(pos));
                    }catch(Exception e){}
            
        }
        
        public static Player getPlayer(int pos){
            
            try{
                return playersList.get(pos);
            }catch(Exception e){
                return null;
            }
            
        }
        
        public static Player getPlayer(String name){
            
            try{
                for (int i = 0; i < playersList.size(); i++) {
                    if (playersList.get(i).name.equalsIgnoreCase(name)) {
                        return playersList.get(i);
                    }
                }
            }catch(Exception e){
                return null;
            }
            return null;
        }
	
	public static int getListSize(){
            
            return playersList.size();
            
        }

	
}