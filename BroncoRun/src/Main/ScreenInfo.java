package Main;

import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class ScreenInfo 
{
	private Player bronco;
	private BufferedImage healthImage;
	private Font informationFont;
	
	public ScreenInfo(Player bronco) 
	{
		this.bronco=bronco;
		try {
			healthImage= ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			informationFont=new Font("Century",Font.PLAIN,11);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.setFont(informationFont);
		g.drawImage(healthImage, 0,5,null);
		g.drawString(bronco.getHealth()+ "/" + bronco.getMaxHealth(),30,20);
		g.drawString("0/0", 30, 40);
	}

}
