package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity{
	
	
	
	private int health,maxHealth;
	@SuppressWarnings("unused")
	private boolean isDead;
	private boolean flinching;
	private long flinchTimer;
	
	//Punch Attack
	private boolean punching;
	private int punchDamage,punchRange;
	
	//Flying
	private boolean flying;
	
	//Animation
	private ArrayList<BufferedImage[]> playerAnimation;
	private final int[] numAnimation= {2,8,1,2,4,2,5};
	
	//Animation Action
	private static final int IDLE =0;
	private static final int WALKING =1;
	private static final int JUMPING =2;
	private static final int FALLING =3;
	private static final int FLYING =4;
	private static final int PUNCHING =6;
	
	
	public Player(TileMap tm) {
		super(tm);
		// TODO Auto-generated constructor stub
		width =30;
		height = 30;
		collisionWidth = 20;
		collisionHeight = 20;
		moveSpeed= 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		jumpStart=-4.8;
		stopJumpSpeed = 0.3;
		isRightFacing = true;
		health=10;
		maxHealth=10;
		punchDamage=8;
		punchRange=40;
		fallSpeed=0.15;
		maxFallSpeed=4.0;
		
		//Animation of player to be added from playersprites
		try {
			BufferedImage playersprites = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
			playerAnimation=new ArrayList<BufferedImage[]>();
			for(int i=0;i<7;i++) {
				BufferedImage[] image = new BufferedImage[numAnimation[i]];
				for(int j=0;j<numAnimation[i];j++) {
					if(i!=PUNCHING) image[j]=playersprites.getSubimage(j*width, i*height, width, height);
					else image[j]=playersprites.getSubimage(j*width*2, i*height, width*2, height);
				}
			playerAnimation.add(image);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		currAct=IDLE;
		animation = new Animation();
		animation.setFrames(playerAnimation.get(IDLE));
		animation.setDelay(400);
	}


	public void ifPlayerAttacks(ArrayList<Enemy> enemies)
	{
		// Check through all enemies
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy enemy = enemies.get(i);			
			//check if punched
			if(punching)
			{
				if(isRightFacing)
				{
					double xLow = x;
					double xHigh = x + punchRange;
					double yLow = y - height / 2;
					double yHigh = y + height / 2;
					if(enemy.getx() > xLow &&
						enemy.getx() < xHigh && 
						enemy.gety() > yLow && 
						enemy.gety() < yHigh)	enemy.hit(punchDamage);		
				}
				else 
				{
					double xLow = x - punchRange;
					double xHigh = x;
					double yLow = y - height / 2;
					double yHigh = y + height / 2;
					if(enemy.getx() < xHigh && 
						enemy.getx() > xLow && 
						enemy.gety() > yLow && 
						enemy.getx() < yHigh)	enemy.hit(punchDamage);	
				}				
			}			
			
			// check if collided with enemy
			if(intersects(enemy))
			{
				hit(enemy.getDamage());
			}
			
		}
	}
		
	
	public boolean isPunching() 
	{
		return punching;
	}

	public void setPunching() 
	{
		punching = true;
	}
	
	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}

	public boolean isFlying() 
	{
		return flying;
	}

	public void setFlying(boolean flying) 
	{
		this.flying = flying;
	}

	public int getMaxHealth() 
	{
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) 
	{
		this.maxHealth = maxHealth;
	}
	
	
	private void getNextCoordinates()
	{
		// player movement
		if(left)
		{
			velX -= moveSpeed;
			if(velX < -maxSpeed)
			{
				velX = -maxSpeed;
			}
		}
		else if(right)
		{
			velX += moveSpeed;
			if(velX > maxSpeed)
			{
				velX = maxSpeed;
			}
		}
		else 
		{
			if(velX > 0)
			{
				velX -= stopSpeed;
				if(velX < 0)
				{
					velX = 0;
				}
			}
			else if(velX < 0) 
			{
				velX += stopSpeed;
				if(velX > 0)
				{
					velX = 0;
				}
			}
		}
		
		// Only in air, player can move and attack
		if((currAct == PUNCHING) && !(jump || fall))	velX = 0;
		
		// if player is jumping
		if(jump  && !fall)
		{
			fall = true;
			velY = jumpStart;
		}
		
		// if player is falling
		if(fall)
		{
			if(velY > 0 && flying) velY = velY + fallSpeed * 0.1;
			
			else velY = velY + fallSpeed; 
			
			if(velY > 0) jump = false;
			
			if(velY < 0 && !jump) velY = velY + stopJumpSpeed;
			
			if(velY > maxFallSpeed) velY = maxFallSpeed;
			
		}
	}
	
	
	
	public void update() 
	{
		// update position
		getNextCoordinates();
		checkBoundaryCollision();
		setPosition(xtemp, ytemp);
		
		// if attack has been done, change the flag of punching
		if(currAct == PUNCHING)
		{
			if(animation.isAnimationUsedOnce())	punching = false;
			
		}
		
		// if elapsed the timing of flinching then stop
		if(flinching)
		{
			long dt = (System.nanoTime() - flinchTimer) / 1000000;
			if(dt > 1000)
			{
				flinching = false;
			}
		}
		
		
		// set the chosen animation of player
		if(punching)
		{
			if(currAct != PUNCHING)
			{
				currAct = PUNCHING;
				animation.setFrames(playerAnimation.get(PUNCHING));
				width = 60;
				animation.setDelay(50);	
			}
		}
		else if(velY > 0)		
		{
			if(flying) 
			{
				if(currAct != FLYING)
				{
					currAct = FLYING;
					animation.setFrames(playerAnimation.get(FLYING));
					width = 30;
					animation.setDelay(100);	
				}
			}
			else if(currAct != FALLING)
			{
				currAct = FALLING;
				animation.setFrames(playerAnimation.get(FALLING));
				width = 30;
				animation.setDelay(100);
			}
		}
		else if(velY < 0)
		{
			if(currAct != JUMPING)
			{
				currAct = JUMPING;
				animation.setFrames(playerAnimation.get(JUMPING));
				width = 30;
				animation.setDelay(-1);
			}
		}
		else if(left || right)
		{
			if(currAct != WALKING)
			{
				currAct = WALKING;
				animation.setFrames(playerAnimation.get(WALKING));
				width = 30;
				animation.setDelay(40);
			}
		}
		else 
		{
			if(currAct != IDLE)
			{
				currAct = IDLE;
				animation.setFrames(playerAnimation.get(IDLE));
				width = 30;	
				animation.setDelay(400);	
			}
		}		
		
		animation.update();
		
		// set direction 
		if(currAct != PUNCHING)
		{
			if(right) isRightFacing = true;			
			
			if(left) isRightFacing = false;
		}
	}
	
	
	public void hit(int damage)
	{
		if(flinching)	return;
		
		health = health - damage;
		
		if(health < 0)	health = 0;
		if(health == 0)	isDead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	
	public void draw(Graphics2D g)
	{
		setMapPosition();	
		// Player to be Drawn...
		if(flinching)
		{
			long dt = (System.nanoTime() - flinchTimer) / 1000000;
			int checkTiming = (int) dt / 100 % 2;
			if (checkTiming == 0)
			{
				return;
			}
		}
		
		super.draw(g);	
	}
}
