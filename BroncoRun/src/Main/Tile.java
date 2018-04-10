package Main;

import java.awt.image.BufferedImage;

public class Tile 
{
	private BufferedImage img;
	private int tileType;
	
	//tile types
	public static final int UNBLOCKED = 0;
	public static final int BLOCKED = 1;
	
	public Tile(BufferedImage image, int type)
	{
		this.img = image;
		this.tileType = type;
	}
	
	public BufferedImage getImage()
	{
		return img;
	}
	
	public int getType()
	{
		return tileType;
	}
	
}
