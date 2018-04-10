package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionWindow extends JFrame
{

	/**
	 * 
	 */
	public static JFrame frame_option = new JFrame();
	public static JPanel panel_option;
	private static final long serialVersionUID = 1L;
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	private int tileSize = 30;
	
	public OptionWindow()
	{
		frame_option.setSize(400,400);
		frame_option.setLocation(600,300);
		frame_option.setVisible(true);
		frame_option.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame_option.getContentPane().setLayout(null);
		
		JButton BtnHelp = new JButton("Get Key Information");
		BtnHelp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Graphics g = frame_option.getGraphics();
				draw((Graphics2D)g);
			}
		});
		BtnHelp.setFont(new Font("AppleGothic", Font.PLAIN, 16));
		BtnHelp.setBounds(25, 10, 175, 50);
		frame_option.getContentPane().add(BtnHelp);
		
		JButton BtnClose = new JButton("Close");
		BtnClose.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				frame_option.dispose();
			}
		});
		BtnClose.setFont(new Font("AppleGothic", Font.PLAIN, 16));
		BtnClose.setBounds(300, 10, 75, 50);
		frame_option.getContentPane().add(BtnClose);
		
		loadTiles("/Tilesets/grasstileset.gif");
		
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
	
	public void draw(Graphics2D g)
	{
		int pos = 100;
		for(int col=12; col<16; col++)
		{
			g.drawImage(tiles[0][col].getImage(), 100, pos, null);
			pos = pos + 50;
		}
		g.setColor(Color.BLACK);
		g.drawString("Move: left/right", 200, 120);
		g.drawString("Jump", 200, 170);
		g.drawString("Jump + Glide", 200, 220);
		g.drawString("Scratch", 200, 270);
		
	}
	
}

