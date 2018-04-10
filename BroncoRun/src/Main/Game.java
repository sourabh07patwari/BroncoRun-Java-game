package Main;

import javax.swing.JFrame;

public class Game extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static JFrame frame;
	public static void main(String[] args)
	{
		boolean resizable = false;
		boolean visible = true;
		frame = new JFrame("BroncoRun");
		frame.setContentPane(new Screen());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(resizable);
		frame.pack();
		frame.setVisible(visible);
	}

}
