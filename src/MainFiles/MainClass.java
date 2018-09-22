package MainFiles;

import java.awt.*;
import java.awt.image.BufferStrategy;

import Other.*;

public class MainClass extends Canvas implements Runnable {

	private static final long serialVersionUID = 1275741336205243443L;
	private boolean isRunning = false;
	private Thread thread;
	public static int WW, WH;

	private Handler handler;
	private KeyInput keyInput;
	public static Textures tex;
	
	private int fps = 0;

	public static float margin = 64;
	
	public static STATE state = STATE.game;
	public enum STATE {
		game(),
		menu();
	}
	
	public MainClass() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		MainClass.WW = (int)tk.getScreenSize().getWidth();
		MainClass.WH = (int)tk.getScreenSize().getHeight();

		MainClass.WW = 1340;
		MainClass.WH = (int)(WW * 0.618);

		if(MainClass.WW < 1400) {
			margin = 48;

			if(MainClass.WW < 1280) {
				margin = 32;
			}
		}

		tex = new Textures();
		
		handler = new Handler(this);
		keyInput = new KeyInput(handler, this);
				
		this.addKeyListener(keyInput);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}

	private void update(float et) {
		handler.update(et);
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		/////////////////////////////////////////////////
		g.setColor(new Color(94, 93, 89));
		g.fillRect(0, 0, WW, WH);

		handler.render(g);

		g.setFont(new Font("arial", 0, 25));
		g.setColor(Gamecol.LIGHT);
		g.drawString("FPS: "+this.fps, 50, 50);
		
		/////////////////////////////////////////////////
		
		g.dispose();
		bs.show();
	}
	
	public void run() {
		this.requestFocus();
		
		long start = 0;
		long stop = 0;
		float elapsedTime = 0.0f;
		
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(isRunning) {			
			start = System.currentTimeMillis();

			this.update(elapsedTime);
			this.render();
			
			stop = System.currentTimeMillis();
			elapsedTime = (stop - start) / 1000.0f;

			frames++;
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				this.fps = frames;
				//System.out.println(frames);
				frames = 0;
			}
		}
	}
	
	public synchronized void start() {
		if(isRunning) return;
		
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public static void main(String[] args) {
		new Window("Warhammer Battle Simulator", new MainClass());
	}
}
