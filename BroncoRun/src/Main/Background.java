package Main;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Background 
{
	private BufferedImage image;
	
	private double x;
	private double y;
	private double velX;
	private double velY;
	
	private double bgMoveScale;
	
	public Background(String s, double ms)
	{
		try
		{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			bgMoveScale = ms;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setVector(double velX, double velY)
	{
		this.velX = velX;
		this.velY = velY;
	}
	
	public void setCoordinates(double x, double y)
	{
		this.x = (x * bgMoveScale) % Screen.SCREEN_WIDTH;
		this.y = (y * bgMoveScale) % Screen.SCREEN_HEIGHT;
	}
	
	public void update()
	{
		x = x + velX;
		y = y + velY;
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, (int)x, (int)y, null);
		if(x>0)
		{
			g.drawImage(image, (int)x - Screen.SCREEN_WIDTH, 0, null);
		}
		if(x<0)
		{
			g.drawImage(image, (int)x + Screen.SCREEN_WIDTH, 0, null);
		}
	}
	
}
