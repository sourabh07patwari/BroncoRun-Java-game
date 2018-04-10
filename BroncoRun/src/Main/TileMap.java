package Main;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class TileMap 
{
	//Position
	private double x;
	private double y;
	
	//Boundary
	private int xMinimum;
	private int yMinimum;
	private int xMaximum;
	private int yMaximum;
	
	private double tween;
	
	//Map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//TileSet GIF
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	//Drawing Bounds (Dont need to draw entire tunnel map)
	private int rowPadding;
	private int colPadding;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize)
	{
		this.tileSize = tileSize;
		numRowsToDraw = Screen.SCREEN_HEIGHT/tileSize + 2;
		numColsToDraw = Screen.SCREEN_WIDTH/tileSize + 2;
		tween = 0.07;
	}
	
	public void loadTiles(String s)
	{
		try
		{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth()/tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col<numTilesAcross; col++)
			{
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.UNBLOCKED);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage,Tile.BLOCKED); // These tiles can be collided with
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s)
	{
		try
		{
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols*tileSize;
			height = numRows * tileSize;
			
			String delims = "\\s+";
			
			xMinimum=Screen.SCREEN_WIDTH - width;
			xMaximum=0;
			yMinimum = Screen.SCREEN_HEIGHT-height;
			yMaximum=0;
			for(int row = 0; row<numRows; row++)
			{
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col=0; col<numCols; col++)
				{
					map[row][col]=Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int getTileSize()
	{
		return tileSize;
	}
	
	public double getx()
	{
		return  x;
	}
	
	public double gety()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getType(int row,int col)
	{
		int rc = map[row][col];
		int r = rc/numTilesAcross;
		int c = rc%numTilesAcross;
		
		return tiles[r][c].getType();
	}
	
	public void setPosition(double x, double y)
	{
		this.x = this.x + (x-this.x)* tween;
		this.y = this.y + (y-this.y)* tween;
		fixBounds();
		
		colPadding = (int)-this.x/tileSize;
		rowPadding = (int)-this.y/tileSize;
	}
	
	public void draw(Graphics2D g)
	{
		for(int row=rowPadding; row<rowPadding + numRowsToDraw; row++ )
		{
			if(row >= numRows) break;
			for(int col=colPadding; col<colPadding + numColsToDraw; col++)
			{
				if(col >= numCols) break;
				
				if(map[row][col]==0) continue;
				
				int rc = map[row][col];
				int r = rc/numTilesAcross;
				int c = rc%numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), (int)x + col*tileSize, (int)y + row*tileSize, null);
			}
		}
	}
	
	public double setTween(double tw)
	{
		this.tween = tw;
		return this.tween;
	
	}
	
	private void fixBounds() 
	{
		// TODO Auto-generated method stub
		if(x<xMinimum)
		{
			x = xMinimum;
		}
		if(y<yMinimum) 
		{
			y = yMinimum;
		}
		if(x>xMaximum)
		{
			x = xMaximum;
		}
		if(y>yMaximum) 
		{
			y = yMaximum;
		}
	}
	
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	
}
