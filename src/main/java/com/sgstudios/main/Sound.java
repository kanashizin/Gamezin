package com.sgstudios.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	private AudioClip clip;
	
	public static final Sound hurt = new Sound("/hurt.wav");
	public static final Sound explosion = new Sound("/explosion.wav");
	public static final Sound shoot = new Sound("/shoot.wav");
	public static final Sound menuSelect = new Sound("/menuSelect.wav");
	public static final Sound selected = new Sound("/selected.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource("/sounds" + name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
}
