package editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BMPFormatter extends Formatter{
	


	@Override
	public BufferedImage loadImage(String path) {
		// TODO Auto-generated method stub
		bmpFile = new File(path);
		try {
			image = ImageIO.read(bmpFile);
		} catch (IOException e) {
			System.out.println("Nije lepo procitana slika!");
		}
		
		return image;
	}

	public void exportImage(String path,BufferedImage bI) {
		try {
			
			System.out.println("Exportujes li wtf???");
			
			
			BufferedImage bmp24 = new BufferedImage(bI.getWidth(),bI.getHeight(),BufferedImage.TYPE_INT_RGB);
			
			int h = bI.getHeight();
			int w = bI.getWidth();

			for(int y = 0;y<h;y++)
			{
				for(int x = 0;x<w;x++)
				{
					Color c = new Color(bI.getRGB(x, y));
					
					
					int red = c.getRed();
					int blue = c.getBlue();
					int green = c.getGreen();
					
					
					bmp24.setRGB(x, y, (((int)(red)) << 16) | (((int)(green)) << 8) | ((int)(blue)));
					
				}
				
			}
			
			
			
			ImageIO.write(bmp24,"BMP",new File(path));

			//ImageIO.write(image,"BMP",new File(path));
		} catch (IOException e) {
			System.out.println("Nije lepo exportovana slika!");
		}
	}

	@Override
	public void exportImage(String path) {
		// TODO Auto-generated method stub
		
	}

	/*
	File bmpFile;
	BufferedImage image;
	
	
	
	public void loadImage(String path)
	{
		bmpFile = new File(path);
		try {
			image = ImageIO.read(bmpFile);
		} catch (IOException e) {
			System.out.println("Nije lepo procitana slika!");
		}
	}
	
	
	public void exportImage(String path)
	{
		try {
			ImageIO.write(image,"BMP",new File(path));
		} catch (IOException e) {
			System.out.println("Nije lepo exportovana slika!");
		}
	}
	*/
	
	
	
}




