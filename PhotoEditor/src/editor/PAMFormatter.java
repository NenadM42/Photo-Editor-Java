package editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PAMFormatter extends Formatter{

	
	private int index = 0;
	
	
	
	@Override
	public BufferedImage loadImage(String path) {
		// TODO Auto-generated method stub
		
		try {

			FileInputStream inputStream = new FileInputStream(path);
			
			File file = new File(path);
			
			
			
			long fileSize = file.length();
			
			byte[] pixels = new byte[(int)fileSize];
			
			inputStream.read(pixels);
			
			String width = "";
			String height = "";
			String depth = "";
			String channels = "";
			String max = "";
			String type = "";
			
			

			// w
			while(pixels[index++] != 0x20);	
			while(pixels[index] != 0x0a)
			{
				width += (pixels[index] - 48);
				index++;
			}
			

			
			// h
			while(pixels[index++] != 0x20);	
			while(pixels[index] != 0x0a)
			{
				height += (pixels[index] - 48);
				index++;
			}
			
			// depth
			
			while(pixels[index++] != 0x20);	
			while(pixels[index] != 0x0a)
			{
				depth += (pixels[index] - 48);
				index++;
			}
			
			
			while(pixels[index++] != 0x20);	
			while(pixels[index] != 0x0a)
			{
				max += (pixels[index] - 48);
				index++;
			}
			
			while(pixels[index++] != 0x20);	
			while(pixels[index] != 0x0a)
			{
				type += ((char)pixels[index]);
				index++;
			}
			
			index++;
			
			while(pixels[index++] != 0x0a);
			
			
			System.out.println(index + " " + pixels[index] + " " + type);
			
			System.out.println(width + "   " + height);

			int w = Integer.parseInt(width);
			int h = Integer.parseInt(height);
			


			BufferedImage bufferedImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			System.out.println("after img");

			System.out.println(index + "----" + pixels[index] + "----" + type);

			//System.out.println("pam ind: " + pixels[index]);
			
			System.out.println("W: " + w + " H: " + h);
			
			
			if(type.equals("RGB_ALPHA"))
			{
			System.out.println("KOAKO OKORAOER");
				
				
			for(int y = 0;y<bufferedImage.getHeight();y++)
			{
				for(int x = 0;x<bufferedImage.getWidth();x++)
				{
					//Color c = new Color(pixels[index],pixels[index+1],pixels[index+2],pixels[index+3]);
					
					
					//c.getRGB();
					//ARGB
					
					
					
					int b = 0xFF & pixels[index];
					int g = 0xFF & pixels[index+1];
					int r = 0xFF & pixels[index+2];
					
					System.out.println(r + " " + g + " " + b);
					
					
					bufferedImage.setRGB(x, y, (b << 16) | (g << 8) |  (r) | ((255) << 24));
					index+=4;
				}
			}
			
			}else
			{
				for(int y = 0;y<bufferedImage.getHeight();y++)
				{
					for(int x = 0;x<bufferedImage.getWidth();x++)
					{
						
						bufferedImage.setRGB(x, y, ((pixels[index]) << 16 ) | ((pixels[index + 1]) << 8) | (pixels[index+2] ) | (255 << 16));
						index+=3;
					}
				}
			}
			
			return bufferedImage;
		} catch (Exception e) {
			
		}
		
		
		
		return null;
		
	}

	
	public void exportImage(String path,BufferedImage img) {
		// TODO Auto-generated method stub
		System.out.println("Pam se eskoprtueeee");
		
		try 
		{
			FileOutputStream outputStream = new FileOutputStream(path);
			
			String fileName;
			fileName = "P7" + '\n' + "WIDTH " + img.getWidth() + '\n' + "HEIGHT " + img.getHeight() + '\n' + "DEPTH 4" + '\n' + "MAXVAL 255"
					+ '\n'  + "TUPLTYPE RGB_ALPHA" + '\n' + "ENDHDR" + '\n';
			
			outputStream.write(fileName.getBytes());
			
			
			byte[] pixels = new byte[img.getHeight()*4 * img.getWidth()];
			int index = 0;
			
			
			for(int y=  0;y<img.getHeight();y++)
			{
				for(int x = 0;x<img.getWidth();x++)
				{
					
					int color = img.getRGB(x, y);
					
					pixels[index] = (byte)((color & 0x00ff0000) >> 16);
					index+=1;
					
					pixels[index] = (byte)((color & 0x0000ff00) >> 8);
					
					index++;
					pixels[index] = (byte)((color & 0x000000ff));
					index++;
					pixels[index] = (byte)((color & 0xff000000) >> 24);
					index++;
				}
			}
			
			outputStream.write(pixels);
			outputStream.close();
			
			
		}catch(Exception e)
		{
			
		}
		
	}


	@Override
	public void exportImage(String path) {
		// TODO Auto-generated method stub
		
	}

}
