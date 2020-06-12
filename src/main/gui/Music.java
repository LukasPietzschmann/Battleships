
package main.gui;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	Clip clip;
	long clipTimePosition;
	
	public Music(String musicLocation) {
		try {
			File musicPath = new File(musicLocation);
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
			} 
			else {
				System.out.println("Can't find file");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
//	void startMusic() {
//		try {
//			File musicPath = new File(musicLocation);
//			if (musicPath.exists()) {
//				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//				clip = AudioSystem.getClip();
//				clip.open(audioInput);
//				Thread.sleep(300);
//				clip.start();
//				clip.loop(Clip.LOOP_CONTINUOUSLY);
//			} 
//			else {
//				System.out.println("Can't find file");
//			}
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
	
	void stopMusic() {
		clipTimePosition = clip.getMicrosecondPosition();
		clip.stop();
	}
	
	void restartMusic() {
		clip.setMicrosecondPosition(clipTimePosition);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
