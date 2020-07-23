
package Schiffeversenken.gui;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Die Klasse Music lädt eine Musikdatei, welche sich während des Spiels starten und stoppen lässt.
 */
public class Music {
	Clip clip;
	long clipTimePosition;

	/**
	 * Konstruktor, erstellt ein MainMenu-Objekt.
	 * @param musicLocation Verzeichnis der Musik-Datei.
	 */
	public Music(String musicLocation) {
		try {
			File musicPath = new File(musicLocation);
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
			} else {
				System.out.println("Can't find file");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Stoppt die Musikwiedergabe und speichert die Position.
	 */
	void stopMusic() {
		clipTimePosition = clip.getMicrosecondPosition();
		clip.stop();
	}

	/**
	 * Startet die Musikwiedergabe an der letzten gespeicherten Position, ansonsten von Anfang an.
	 */
	void restartMusic() {
		clip.setMicrosecondPosition(clipTimePosition);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
