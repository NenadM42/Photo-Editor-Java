package editor;

import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Formatter {

	protected File bmpFile;
	protected BufferedImage image = null;

	
	public abstract BufferedImage loadImage(String path);
	
	public abstract void exportImage(String path);
	
	
	public BufferedImage getBufferedImage()
	{
		return image;
	}
	
	public void setBufferedImage(BufferedImage img)
	{
		image = img;
	}

	public void exportImage(String path, BufferedImage bI) {
		// TODO Auto-generated method stub
		
	}
}
