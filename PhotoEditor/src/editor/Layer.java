package editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Layer {
	
	BMPFormatter bmpFormatter = new BMPFormatter();
	JPanel imagePanel;
	BufferedImage bufferedImage = null;
	
	static int cnt = 0;
	private int visibility = 100;
	private boolean active = true;
	private boolean inFinalPhoto = true;
	
	public void setInFinalPhoto(boolean b)
	{
		inFinalPhoto = b;
	}
	
	public boolean getInFinalPhoto()
	{
		return inFinalPhoto;
	}
	
	
	
	public Layer()
	{
		
	}
	
	public Layer(String path, Formatter formatter)
	{
		
		//cnt++;
		JFrame frame = new JFrame();
		
		File imageFile = new File(path);
		
		BufferedImage i = formatter.loadImage(path);;
	/*	try {
			i = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Ne postoji ovaj fajl!");
		}*/
		
		bufferedImage = i;
		/*
		ImageIcon icon = new ImageIcon(i);
		JLabel label = new JLabel(icon);
		
		JPanel panel = new JPanel();
		
		
		panel.setSize(200, 200);
		panel.add(label);
		
		imagePanel = panel;
		*/
	}
	
	
	public Layer(int x, int y) {
		// TODO Auto-generated constructor stub
		
		bufferedImage = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
		
	}

	public int getWidth()
	{
		return this.bufferedImage.getWidth();
	}
	
	public int getHeight()
	{
		return this.bufferedImage.getHeight();
	}
	
	public int getVisibility()
	{
		return this.visibility;
	}
	
	BufferedImage getBufferedImage()
	{
		if(bufferedImage == null)
		{
			System.out.println("Buffered image je null!");
		}
		return this.bufferedImage;
	}
	
	public void setActive(boolean b)
	{
		active = b;
	}

	public void setVisibility(int value) {
		// TODO Auto-generated method stub
		visibility = value;
	}

	public boolean getActive() {
		// TODO Auto-generated method stub
		return active;
	}
	

	
	
}
