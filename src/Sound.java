import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Sound for the Flappy Bird Game
 * @author laiba
 *
 */
public class Sound {
	
	private AudioClip clip;
	
	public static Sound test = new Sound("");
	
	public Sound(String path) {
		clip = Applet.newAudioClip(getClass().getResource(null));
		
	}
	
	public void play() {
		new Thread() {
			public void run() {
				clip.play();
			}
		}.start();
	}
	
}
