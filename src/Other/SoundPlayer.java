package Other;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class SoundPlayer {
	
	private static float level = 0.8f; // < 0.0; 1.0 >
	private Clip clip;
	private boolean isRunning = false;
	private String path;

	private boolean isTimer = false;
	private long bt;
	private boolean isLooped = false;
	
	public SoundPlayer() { }
	
	public SoundPlayer(String path) {
		load(path);
	}

	public void update() {
		if(isTimer) {
			if (System.currentTimeMillis() >= bt) {
				clip.start();
			}
		}
	}

	public void update(long bt) {
		if(isTimer) {
			reload();
			if (System.currentTimeMillis() >= this.bt) {
				clip.start();
				this.bt += bt;
			}
		}
	}

	public void setTimer(long bt) {
		this.isTimer = true;
		this.bt = System.currentTimeMillis() + bt;
	}

	public static boolean playNextSound(String path) {
		File f = new File(path);
		AudioInputStream audioIn;
		try {
			Clip clip;
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
			clip = AudioSystem.getClip();
			clip.open(audioIn);

			FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(limit(control,level));

			clip.start();

			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
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
	
	private static float limit(FloatControl control, float level) {
		level *= 86;
		level -= 70;
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
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void reload() {
		load(path);
	}
}
