package Main;

import java.awt.image.BufferedImage;

public class Animation 
{

	//This is a change for testing git
	//Hello 
	
	private BufferedImage[] imageFrames;
	private int currentImageFrame;
	private long t0,delay;
	
	private boolean animationUsedOnce;
	
	public  Animation() {
		animationUsedOnce = false;
		System.out.println("HELLOOOOO");
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.imageFrames=frames;
		currentImageFrame = 0;
		t0 = System.nanoTime();
		animationUsedOnce = false;
	}
	
	public void setFrame(int i)
	{
		currentImageFrame =i;
	}
	
	public void setDelay(long d) {
		delay = d;
	}
	
	public int getFrame() {
		return currentImageFrame;
	}

	public boolean isAnimationUsedOnce() {
		return animationUsedOnce;
	}
	
	public BufferedImage getImage() {
		return imageFrames[currentImageFrame];
	}
	
	public void update() {
		if(delay==-1) return;
		
		long dt = (System.nanoTime()- t0)/1000000;
		if(dt>delay) {
			currentImageFrame++;
			t0 = System.nanoTime();
		}
		if(currentImageFrame==imageFrames.length) {
			currentImageFrame=0;
			animationUsedOnce=true;
		}
		
	}
}
