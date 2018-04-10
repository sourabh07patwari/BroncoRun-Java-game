package Main;

import java.awt.Graphics2D;

public abstract class State 
{
	protected StateManager stateManager;
	
	public abstract void initialize();
	public abstract void keyPressed(int key);
	public abstract void keyReleased(int key);
	public abstract void update();
	public abstract void draw(Graphics2D g);
}
