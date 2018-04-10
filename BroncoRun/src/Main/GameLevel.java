package Main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GameLevel extends State 
{
	private ScreenInfo screen;
	private Player bronco;
	private ArrayList<Enemy> enemyList;
	private TileMap tm;
	private Background background;
	
	public GameLevel(StateManager stateManager)
	{
		this.stateManager = stateManager;
		initialize();
	}
	
	
	public void initialize()
	{
		tm = new TileMap(30);
		tm.loadTiles("/Tilesets/grasstileset.gif");
		tm.loadMap("/Maps/level1-1.map");
		tm.setPosition(0, 0);
		tm.setTween(0.07);
		
		background = new Background("/Backgrounds/BG.png", 0.1);
		bronco = new Player(tm);
		bronco.setPosition(100, 100);
		
		createEnemies();
		
		screen = new ScreenInfo(bronco);
	}
	
	
	public void keyPressed(int key) 
	{
		if(key == KeyEvent.VK_UP)	bronco.setUp(true);
		
		if(key == KeyEvent.VK_DOWN)	bronco.setDown(true);
		
		if(key == KeyEvent.VK_LEFT)	bronco.setLeft(true);
		
		if(key == KeyEvent.VK_RIGHT)bronco.setRight(true);
		
		if(key == KeyEvent.VK_E)	bronco.setFlying(true);
		
		if(key == KeyEvent.VK_W)	bronco.setJumping(true);
		
		if(key == KeyEvent.VK_R)	bronco.setPunching();
	}
	
	
	public void createEnemies() 
	{	
		enemyList = new ArrayList<Enemy>();
		Snail snails ;
		Point[] enemyPosition = new Point[] 
		{
			new Point(260, 200),
			new Point(750,200),
			new Point(1780,200),
			new Point(2680,200),
			new Point(3050,200)
		};
		
		for(int num=0;num<enemyPosition.length;num++) 
		{
			snails = new Snail(tm) ;
			snails.setPosition(enemyPosition[num].x, enemyPosition[num].y);
			enemyList.add(snails);
		}		
	}
	
	
	public void keyReleased(int key) 
	{
		if(key == KeyEvent.VK_UP)	bronco.setUp(false);
		
		if(key == KeyEvent.VK_DOWN)	bronco.setDown(false);
		
		if(key == KeyEvent.VK_LEFT)	bronco.setLeft(false);
		
		if(key == KeyEvent.VK_RIGHT)bronco.setRight(false);
		
		if(key == KeyEvent.VK_E)	bronco.setFlying(false);
		
		if(key == KeyEvent.VK_W)	bronco.setJumping(false);
	} 
	
	
	public void update()
	{
		//player position is updated now
		bronco.update();
		double positionX = Screen.SCREEN_WIDTH/2 -bronco.getx();
		double positionY = Screen.SCREEN_HEIGHT/2-bronco.gety();
		tm.setPosition(positionX, positionY);
	
		//background position is updated now
		background.setCoordinates(tm.getx(), tm.gety());
		
		//Check if the attack is hit on enemies
		bronco.ifPlayerAttacks(enemyList);
		
		//remove the enemy if enemy is dead
		for(int j=0; j< enemyList.size();j++){
			enemyList.get(j).update();
			if(enemyList.get(j).isDead()){
				enemyList.remove(j);
				j--;
			}		
		}
	}
	

	@Override
	public void draw(Graphics2D g) 
	{	
		//draw background
		background.draw(g);
				
		//draw tilemap
		tm.draw(g);
		
		//draw player
		bronco.draw(g);
		
		if(bronco.gety()>270) {
			JOptionPane.showMessageDialog(Game.frame, "   Game Over!!  ");
			System.exit(0);
		}
		if(bronco.getx() >3169) {
			JOptionPane.showMessageDialog(Game.frame, "Congratulations!!! You Won The Game!!");
			System.exit(0);
		}
		if(bronco.getHealth() == 0) {
			JOptionPane.showMessageDialog(Game.frame, "   Player is Dead!!   Game Over!!  ");
			System.exit(0);
		}
		
		//draw enemies
		for(int i=0;i<enemyList.size();i++)	enemyList.get(i).draw(g);
		
		//Drawing the health bar
		screen.draw(g);
		
	}
	
}
