package com.sgstudios.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.sgstudios.entities.Entity;
import com.sgstudios.entities.SavePoint;
import com.sgstudios.world.World;

public class Save {
	
	public static boolean saveExist = false;
	public static boolean saveGame = false;
	public static File file;
	
	public Save() {
		
	}
	
	public void tick() {
		
	}
	
	public void applySave(String str) {
		
		
			
		
	}
	
	public void saveGame(String[] val1,int[] val2, String user, int encode) {
		
		
	}
	
	public String loadGame(String user, int encode) {
		
		String line = "";
		file = new File(user + ".txt");
		if(file.exists()) {
			
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader(user+".txt"));
				
				try {
					while((singleLine = reader.readLine()) != null) {
						
						String[] input = singleLine.split(":");
						char[] val = input[1].toCharArray();
						input[1] = "";
						for(int i = 0; i < val.length; i++) {
							
							val[i]-=encode;
							input[1]+= val[i];
							
						}
						
						line+=input[0];
						line+=":";
						line+=input[1];
						line+="/";
						
					}
				}catch(IOException e) {}
				
			}catch(FileNotFoundException e) {}
			
		}
		return line;
	}

	
}