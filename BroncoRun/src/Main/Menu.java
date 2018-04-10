package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class Menu extends State 
{
	private Background bg;
	
	private int selection = 0;
	private String[] menu_options = {"RUN", "OPTIONS", "QUIT"};
	
	private Color headingColor;
	private Font headingFont;	
	private Font optionsFont;
	
	public Menu(StateManager gameStateManager)
	{
		this.stateManager = gameStateManager;
		
		try
		{
			bg = new Background("/Backgrounds/BG.png", 1);
			bg.setVector(-0.1, 0);
			
			headingColor = new Color(218,165,32);
			headingFont = new Font("Arial", Font.BOLD, 28);
			optionsFont = new Font("Century Gothic", Font.BOLD, 12);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void initialize()
	{
		
	}
	
	
	private void select() 
	{
		// TODO Auto-generated method stub
		if(selection == 0)
		{
			//BroncoRun Game Starts (User chooses RUN in the Menu)
			stateManager.setState(StateManager.LEVEL);
		}
		if(selection == 1)
		{
			//User chooses Options in the Menu
			@SuppressWarnings("unused")
			OptionWindow optionWindow = new OptionWindow();
		}
		if(selection == 2)
		{
			//User chooses Quit in the Menu
			System.exit(0);
		}
	}
	
	public void keyPressed(int key)
	{
		if(key == KeyEvent.VK_UP)
		{
			selection--;
			if(selection == -1)	selection = menu_options.length-1;
			
		}
		if(key== KeyEvent.VK_ENTER)
		{
			select();
		}
		if(key == KeyEvent.VK_DOWN)
		{
			selection++;
			if(selection == menu_options.length)	selection = 0;
		}
		
	}
	
	public void keyReleased(int key)
	{
		
	}
	
	
	public void update()
	{
		bg.update();
	}
	
	
	public void draw(Graphics2D g)
	{
		//draw bg
		bg.draw(g);
		
		//draw title
		g.setColor(headingColor);
		g.setFont(headingFont);
		g.drawString("BRONCO RUN", 80, 70); // automatically find center of screen
		
		//draw menu options
		g.setFont(optionsFont);
		for(int i = 0; i<menu_options.length; i++)
		{
			if(i == selection)
			{
				g.setColor(Color.RED);
			}
			else
			{
				g.setColor(Color.orange);
			}
			g.drawString(menu_options[i], 145, 140+i*15);
		}
	}
}
