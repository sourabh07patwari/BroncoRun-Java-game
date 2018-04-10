package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Dimensions
	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 240;
	public static final int SCALING_FACTOR = 2;
	
	//Game Thread
	private Thread t;
	private boolean working;
	private int FramesPerSec = 60;
	private long gameTime = 1000/FramesPerSec;
	
	//Images
	private BufferedImage img;
	private Graphics2D g;
	
	//Game State Manager
	private StateManager stateManager;
	
	public Screen() 
	{
		super();
		setPreferredSize(new Dimension(SCREEN_WIDTH*SCALING_FACTOR,SCREEN_HEIGHT*SCALING_FACTOR));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify()
	{
		super.addNotify();
		if(t == null)
		{
			t = new Thread(this);
			addKeyListener(this);
			t.start();
		}
	}
	
	private void initialize()
	{
		img = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) img.getGraphics();
		working = true;
		stateManager = new StateManager();
	}
	
	public void run()
	{
		initialize();
		long t0;
		long dt;
		long pause;
		// Loop of the Game
		while(working)
		{
			t0 = System.nanoTime();
			update();
			draw();
			drawAtScreen();
			
			dt = System.nanoTime() - t0;
			
			pause = gameTime - dt/1000000;
			if(pause<0){
				pause = 5;
			}
			
			try
			{
				Thread.sleep(pause);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	private void drawAtScreen() 
	{
		// TODO Auto-generated method stub
		Graphics gS = getGraphics();
		gS.drawImage(img, 0, 0, SCREEN_WIDTH * SCALING_FACTOR,SCREEN_HEIGHT * SCALING_FACTOR, null);
		gS.dispose();
	}

	private void draw() {
		// TODO Auto-generated method stub
		stateManager.draw(g);
	}

	private void update() 
	{
		// TODO Auto-generated method stub
		stateManager.update();
	}

	public void keyTyped(KeyEvent key)
	{
		
	}
	
	public void keyPressed(KeyEvent key)
	{
		stateManager.keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key)
	{
		stateManager.keyReleased(key.getKeyCode());
	}
}
