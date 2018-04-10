package Main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {
	
	//Animation State
	protected Animation animation;
	protected int currAct,prevAct;
	protected boolean isRightFacing;
	
	//tile-set objects
	protected TileMap tileMap;
	protected int tileSize;
	protected double mapX;
	protected double mapY;
	
	//Position and velocity component
	protected double x,y,velX,velY;
	
	//Movement of Player/Enemy
	protected boolean up,down,right,left,fall,jump;
	protected double moveSpeed,maxSpeed,stopSpeed,fallSpeed,maxFallSpeed,jumpStart,stopJumpSpeed;
	
	//Dimension of Map
	protected int width,height;
	protected int collisionWidth,collisionHeight;
	
	//Collision attributes
	protected int currentRow,currentColumn;
	protected double xdestination,ydestination;
	protected double xtemp,ytemp;
	protected boolean topLeft,topRight,bottomLeft,bottomRight;
	
	//Constructors
	public Entity(TileMap map) 
	{
		tileMap=map;
		tileSize=map.getTileSize();
	}
	
	public boolean intersects(Entity ent) 
	{
		Rectangle r1 = getRectangle();
		Rectangle r2 = ent.getRectangle();
		return r1.intersects(r2);
		
	}
	
	public Rectangle getRectangle() 
	{
		return new Rectangle((int)x-collisionWidth,(int)y-collisionHeight,collisionWidth,collisionHeight);
	}
	
	
	public void checkBoundaryCollision() 
	{
		xtemp = x;
		ytemp = y;
		xdestination = x + velX;
		ydestination = y + velY;
		currentColumn = (int)x/tileSize;
		currentRow = (int)y/tileSize;
		
		getCornerValues(xdestination, y);
		if(velX<0) 
		{
			if(topLeft || bottomLeft) 
			{
				velX = 0;
				xtemp = currentColumn*tileSize+collisionWidth/2;
			}
			else 
			{
				xtemp+=velX;
			}
		}
		if(velX>0) 
		{
			if(topRight || bottomRight) 
			{
				velX = 0;
				xtemp = (currentColumn+1) * tileSize - collisionWidth/2;
			}
			else xtemp+=velX;
		}
		if(!fall) 
		{
			getCornerValues(x,ydestination+1);
			if(!bottomLeft && !bottomRight) 
			{
				fall = true;
			}
		}

		getCornerValues(x, ydestination);
		if(velY<0) {
			if(topLeft || topRight) {
				velY =0;
				ytemp = currentRow * tileSize + collisionHeight/2;
			}
			else ytemp+=velY;
		}
		if(velY>0) {
			if(bottomLeft || bottomRight) {
				velY=0;
				fall = false;
				ytemp = (currentRow + 1) * tileSize -collisionHeight/2;
			}
			else ytemp+=velY;
		}
	}
	
	
	public void getCornerValues(double xPosition, double yPosition) 
	{
		int top = (int)(yPosition - collisionHeight / 2);
        int topTile = top/ tileSize;
        int bottom = (int)(yPosition + collisionHeight / 2 - 1);
        int bottomTile = bottom/ tileSize;
		int left = (int)(xPosition - collisionWidth / 2);
        int leftTile = left/ tileSize;
        int right = (int)(xPosition + collisionWidth / 2 - 1);
        int rightTile = right/ tileSize;
        
        
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
                topLeft = topRight = bottomLeft = bottomRight = false;
                return;
        }
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        
        if(tl == Tile.BLOCKED)
        {
        	topLeft = true;
        }
        else
        {
        	topLeft = false;
        }
        
        if(tr == Tile.BLOCKED)
        {
        	topRight = true;
        }
        else
        {
        	topRight = false;
        }
        
        if(bl == Tile.BLOCKED)
        {
        	bottomLeft = true;
        }
        else
        {
        	bottomLeft = false;
        }
        
        if(br == Tile.BLOCKED)
        {
        	bottomRight = true;
        }
        else
        {
        	bottomRight = false;
        }
	}
	
	
	public void setPosition(double x, double y) {
		this.x =x;
		this.y=y;
	}
	
	public void setMapPosition() {
		mapX = tileMap.getx();
		mapY = tileMap.gety();
	}
	
	public void setVector(double velX, double velY) {
		this.velX =velX;
		this.velY=velY;
	}
	
	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public double getVelX() {
		return velX;
	}
	
	public double getVelY() {
		return velY;
	}

	public void setLeft(boolean l) {
		left =l;
	}
	public void setRight(boolean r) {
		right =r;
	}
	public void setUp(boolean u) {
		up =u;
	}
	public void setDown(boolean d) {
		down =d;
	}
	public void setJumping(boolean j) {
		jump=j;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCollisionWidth() {
		return collisionWidth;
	}

	public int getCollisionHeight() {
		return collisionHeight;
	}
	
	
	public void draw(Graphics2D g) {
		if(isRightFacing) {
			g.drawImage(animation.getImage(),(int)(x+mapX -width/2),(int)(y+mapY-height/2),null);
		}
		else {
			g.drawImage(animation.getImage(), (int)(x+mapX -width/2+width), (int)(y+mapY-height/2),-width,height,null);
		}
	}

}
