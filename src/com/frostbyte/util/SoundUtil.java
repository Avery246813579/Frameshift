package com.frostbyte.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.frostbyte.audio.Song;
import com.frostbyte.audio.Sound;

public class SoundUtil {
	private static Song songPlaying;
	private static Clip songClip;
	
	public static void playSong(Song song){
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(SoundUtil.class.getResourceAsStream("/Music/" + song.getName() + ".wav"));
			clip.open(inputStream);
		//	clip.start();
			
			songClip = clip;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void playSound(Sound sound){
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(SoundUtil.class.getResourceAsStream("/Sounds/" + sound.getName() + ".wav"));
			clip.open(inputStream);
			clip.start();
			
			songClip = clip;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stopMusic(){
		if(songClip != null){
			songClip.stop();
		}
	}
	
	public static boolean isSong(Song song){
		if(song.equals(songPlaying)){
			return true;
		}
		
		return false;
	}
}
