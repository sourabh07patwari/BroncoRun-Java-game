package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Snail extends Enemy{

	private BufferedImage[] sprites;
	
	
	public Snail(TileMap tm) {
		super(tm);
		moveSpeed=0.6;
		maxSpeed=0.6;
		fallSpeed =0.2;
		maxFallSpeed=10.0;
		
		width =30;
		height = 30;
		collisionWidth = 20;
		collisionHeight =20;
		
		health=maxHealth =2;
		damage=1;
		
		//load sprites
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
			
			sprites = new BufferedImage[3];
			for(int i=0;i<sprites.length;i++) {
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);	
			}		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		animation=new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		isRightFacing=true;
	}
	
	public void getNextPosition() {
		
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
		if(fall) {
			velY+=fallSpeed;
			
		}
	}
	
	public void update() {
		getNextPosition();
		checkBoundaryCollision();
		setPosition(xtemp, ytemp);
		
		//check flinching
		if(flinching) {
			long elapsed = (System.nanoTime()-flinchTimer)/1000000;
			
			if(elapsed>400) {
				flinching=false;
			}
		}
		
		//if it hits a wall, it will go other direction
		if(right && velX == 0) {
			right =false;
			left = true;
			isRightFacing=false;
		}
		else if(left && velX == 0) {
			left =false;
			right = true;
			isRightFacing=true;
		}
		//update animation
		
		animation.update();
	}
	public void draw(Graphics2D g) {
		
	
		setMapPosition();
		super.draw(g);
	}

}
