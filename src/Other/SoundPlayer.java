package Other;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class SoundPlayer {
	
	private float level = 6.0f; // < -80.0; 6.0 >
	private Clip clip;
	private boolean isRunning = false;
	private String path;
	
	public SoundPlayer() { }
	
	public SoundPlayer(String path) {
		load(path);
	}
	
	public boolean load(String path) {
		this.path = path;
		
		File f = new File(path);
	    AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			
			FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(limit(control,level));
		    		    
		    return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean playSound(String path) {
		boolean good = load(path);
		clip.start();
		
		return good;
	}
	
	private float limit(FloatControl control, float level) { 
		return Math.min(control.getMaximum(), Math.max(control.getMinimum(), level)); 
	}
	
	public void start() {
		if(isRunning) return;
		
		if(clip != null) {
			clip.start();
			isRunning = true;
		}
	}
	
	public void stop() {
		if(!isRunning) return;
		
		if(clip != null) {
			clip.stop();
			isRunning = false;
		}
	}
	
	public void reload() {
		load(path);
	}
}
