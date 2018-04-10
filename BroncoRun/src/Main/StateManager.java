package Main;

import java.awt.Graphics2D;
import java.util.*;

public class StateManager 
{
	private ArrayList<State> states;
	private int currentState;
	
	public static final int MENU = 0;
	public static final int LEVEL = 1;
	
	public StateManager()
	{
		currentState = MENU;
		states = new ArrayList<State>();
		
		states.add(new Menu(this));
		states.add(new GameLevel(this));
	}
	
	public void setState(int state)
	{
		currentState = state;
		states.get(currentState).initialize();
	}
	
	public void keyPressed(int key)
	{
		states.get(currentState).keyPressed(key);
	}
	
	public void keyReleased(int key)
	{
		states.get(currentState).keyReleased(key);
	}
	
	public void update()
	{
		states.get(currentState).update();
	}
	
	public void draw(Graphics2D g)
	{
		states.get(currentState).draw(g);
	}
}
