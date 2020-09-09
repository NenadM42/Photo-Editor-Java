package editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Image {
	
	
	private List<Layer> layers = new ArrayList<Layer>();
	private int currentLayerIndex = 0;
	private List<Formatter> formatters = new ArrayList<Formatter>();
	private HashMap<String,Formatter> formattersMap = new HashMap<String,Formatter>();
	private List<Rectangle> rectangles = new ArrayList<Rectangle>();
	
	
	private HashMap<String,Selection> selectionMap = new HashMap<String,Selection>();
	private HashMap<String,CompositeOperation> compositeMap = new HashMap<String,CompositeOperation>();

	
	
	private BufferedImage currentImage = null;
	
	private List<Operation> operations = new ArrayList<Operation>();
	
	
	
	public Image()
	{
		
		formattersMap.put(".bmp", new BMPFormatter());
		formattersMap.put(".pam", new PAMFormatter());
	}
	
	public HashMap<String,Selection> getAllSelections()
	{
		return selectionMap;
	}
	
	public void addNewSelection(String name)
	{
		Selection sel = new Selection();
		
		
		
		
		for(Rectangle rect : rectangles)
		{
			sel.addRectangle(rect);
			//System.out.println(rect.getStartX());
		}
		
		//System.out.println(name + " ima ukupno: " + sel.rectangles.size());
		
		selectionMap.put(name,sel);
		
		Selection sTest = selectionMap.get(name);
		
		System.out.println(name + " ima ukupno: " + sTest.rectangles.size());

	}
	
	
	public void addNewOperation(String text) {
		// TODO Auto-generated method stub
		
		CompositeOperation comp = new CompositeOperation();
		
		for(Operation op : operations)
		{
			comp.addOperation(op);
		}
		
		compositeMap.put(text, comp);
	}
	
	
	public List<Operation> getOperationList()
	{
		return operations;
	}
	
	public void addOperation(Operation o)
	{
		operations.add(o);
	}
	
	
	JPanel gImage(String path)
	{
		JFrame frame = new JFrame();
		
		File imageFile = new File(path);
		
		BufferedImage i = null;
		try {
			i = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Ne postoji ovaj fajl!");
			return null;
		}
		
		ImageIcon icon = new ImageIcon(i);
		JLabel label = new JLabel(icon);
		
		JPanel panel = new JPanel();
		
		
		panel.setSize(200, 200);
		panel.add(label);
		//frame.setVisible(true);
		
		return panel;
	}
	
	public void addRectangle(int startX,int startY,int endX,int endY)
	{
		if(startX < endX && startY < endY)
		{
			rectangles.add(new Rectangle(startX,startY,endX,endY));
		}
	}
	
	
	public List<Rectangle> getCurrentSelection()
	{
		return rectangles;
	}
	
	public void saveXML(String path)
	{
		XMLFormatter form = new XMLFormatter();
		
		form.exportXML(this, path);
	}
	
	
	
	public void showImage(String path)
	{
		JFrame frame = new JFrame();
		
		File imageFile = new File(path);
		
		BufferedImage i = null;
		try {
			i = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageIcon icon = new ImageIcon(i);
		JLabel label = new JLabel(icon);
		
		
		frame.setSize(200, 200);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	
	
	
	
	public BufferedImage mergeLayers()
	{
		if(layers.size() == 0)
			return null;
		
		//BufferedImage img = layers.get(0).getBufferedImage();
		
		
		
		
		
		//Color c = new Color(layers.get(1).getBufferedImage().getRGB(x, y),true);
		//Color c2 = new Color(img.getRGB(x, y),true);				

		int h = this.getMaxHeight();
		int w = this.getMaxWidth();
		
		
		BufferedImage img = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);

		
		int cnt = 0;
		
		for(int y = 0;y<h;y++)
		{
			for(int x = 0;x<w;x++)
			{	
				
				double currentVisibility = 1.0;
				double finalRed = 0;
				double finalGreen = 0;
				double finalBlue = 0;
				double alpha = 2;
				
				for(Layer layer : layers)
				{
					if(layer.getActive() == false)
						continue;
					if(layer.getBufferedImage().getWidth() <= x || layer.getBufferedImage().getHeight() <= y)
					{
						continue;
					}
					Color c = new Color(layer.getBufferedImage().getRGB(x, y),true);
					//Color c2 = new Color(img.getRGB(x, y),true);				

					
					
					
					double layerVisibility = layer.getVisibility();
					layerVisibility/=100.0;
					alpha = c.getAlpha();
					alpha /= 255.f;
					
					double A = currentVisibility * layerVisibility * alpha;
					
					finalRed += A * (double)c.getRed();
					finalGreen+= A * (double)c.getGreen();
					finalBlue += A * (double)c.getBlue();
					currentVisibility -= A;
				}
				

				img.setRGB(x, y, (((int)(finalRed)) << 16) | (((int)(finalGreen)) << 8) | ((int)(finalBlue )) | (((int) ((1-currentVisibility)*255.0)) << 24));
			}
		}
		
		
		currentImage = img;
		return img;
	}
	
	public int getMaxHeight()
	{
		int max = 0;
		for(Layer layer : layers)
		{
			if(layer.getActive() == false)
				continue;
			max = Math.max(max, layer.getHeight());
		}
		
		return max;
	}
	
	public int getMaxWidth() 
	{
		int max = 0;
		for(Layer layer : layers)
		{
			if(layer.getActive() == false)
				continue;
			max = Math.max(max, layer.getWidth());
		}
		
		return max;
	}
	
	
	public void addLayer()
	{
		
	}
	
	
	public int getCurrentLayerVisibility()
	{
		return layers.get(currentLayerIndex).getVisibility();
	}
	
	public void addLayer(String path)
	{
		String pattern = "[.].*";
		
		Pattern r = Pattern.compile(pattern);
		
		Matcher m = r.matcher(path);
		
		if(m.find())
		{
			try {
				
			
			
				
			Layer l = new Layer(path,formattersMap.get(m.group(0)));
			
			
			if(l.getBufferedImage() != null)
			layers.add(l);
			System.out.println(m.group(0));
			
			}catch(Exception e)
			{
				 int confirmed = JOptionPane.showConfirmDialog(null, 
					        "Wrong format!", "Error",
					        JOptionPane.OK_OPTION);
				System.out.println("Los fajl");
			}
		}
	}
	
	
	
	
	
	
	
	public BufferedImage getImagePath(String path)
	{
		return formatters.get(0).loadImage(path);
		//return formatters.get(0).getBufferedImage();
	}
	
	public boolean exportImage(String path)
	{
		String pattern = "[.].*";
		
		Pattern r = Pattern.compile(pattern);
		
		Matcher m = r.matcher(path);
		
		if(m.find())
		{
			//formattersMap.get(m.group(0)).setBufferedImage(currentImage);
			//System.out.println("WTF??????");
			
			if(formattersMap.get(m.group(0)) == null)
				return false;
			
			System.out.println(m.group(0)); 

			
			
			formattersMap.get(m.group(0)).exportImage(path,this.currentImage);
			return true;
		}
		
		return false;
	}
	
	public void exportLayer(String path, Layer l)
	{
		formatters.get(0).exportImage(path,l.getBufferedImage());
	}
	
	/*
	
	public void exportAllLayers(String path)
	{
		int cnt = 0;
		
		for(Layer l : layers)
		{
			System.out.println("wtf men? " + layers.size());
			path = path + String.valueOf(cnt);
			cnt++;
			if(l.getInFinalPhoto())
				exportLayer(path,l);
		}
	}
	*/
	
	
	BufferedImage getCurrentLayerBufferedImage()
	{
		System.out.println("Trenutni index je: " + this.currentLayerIndex);
		Layer l = layers.get(currentLayerIndex);
		return l.getBufferedImage();
	}
	
	public int getCurrentLayerIndex()
	{
		return this.currentLayerIndex;
	}
	
	public void selectNextLayer()
	{
		if(this.currentLayerIndex < layers.size()-1)
		{
			this.currentLayerIndex++;
		}
	}
	
	public void deleteCurrentLayer()
	{
		if(layers.size() == 0)
			return;
		
		layers.remove(currentLayerIndex);
		if(currentLayerIndex > 0)
			currentLayerIndex--;
	}
	
	public int getLayersSize() {
		return layers.size();
	}
	
	public void selectPreviousLayer()
	{
		if(this.currentLayerIndex > 0)
		{
			this.currentLayerIndex--;
		}
	}

	public void setCurrentLayerVisibility(int value) {
		// TODO Auto-generated method stub
		layers.get(currentLayerIndex).setVisibility(value);
	}

	public void removeCurrentSelection() {
		rectangles.removeAll(rectangles);
	}


	public void removeAllOperations() {
		// TODO Auto-generated method stub
		operations.removeAll(operations);
	}


	public int getNumberOfLayers() {
		// TODO Auto-generated method stub
		return layers.size();
	}
	
	public List<Layer> getLayers()
	{
		return layers;
	}


	public void loadXML(String string) {
		// TODO Auto-generated method stub
		XMLFormatter f=  new XMLFormatter();
		f.importXML(this, string);
	}


	public void addLayer(Layer l) {
		// TODO Auto-generated method stub
		layers.add(l);
	}
	
	public void addRectangle(Rectangle r)
	{
		rectangles.add(r);
	}


	public void clearSelections() {
		// TODO Auto-generated method stub
		this.getCurrentSelection().clear();
	}


	public void setCurrentLayerActive(boolean b) {
		layers.get(currentLayerIndex).setActive(b);
	}


	public boolean getCurrentLayerActive() {
		// TODO Auto-generated method stub
		return layers.get(currentLayerIndex).getActive();
	}


	public void xmlOperation(String path) {
		// TODO Auto-generated method stub
		XMLFormatter form = new XMLFormatter();
		
		form.exportXMLOperation(this, path);

	}


	public void setCurrentImage(BufferedImage currentImage2) {
		// TODO Auto-generated method stub
		this.currentImage = currentImage2;
	}

	public void setNewSelection(String s) {
		rectangles.removeAll(rectangles);
		
	
		
		Selection sel = this.selectionMap.get(s);
		
		if(sel == null)
		{
			System.out.println("NULL JEE");

		}else
		{
			System.out.println(sel.rectangles.size());

		}
		
		System.out.println(s + " ovo je key");
		
		for(Rectangle rect : sel.rectangles)
		{
			rectangles.add(rect);
			System.out.println(rect.getStartX());
		}
		
		//rectangles = sel.rectangles;
		
	}

	public HashMap<String, CompositeOperation> getAllComposite() {
		// TODO Auto-generated method stub
		return this.compositeMap;
	}

	public void setNewOperation(String s) {
		
		operations.removeAll(operations);
		
		
		CompositeOperation comp = this.compositeMap.get(s);
		
		for(Operation op : comp.operations)
		{
			operations.add(op);
			
		}
		
	}

	public void addEmptyLayer(int x,int y) {
		// TODO Auto-generated method stub
		layers.add(new Layer(x,y));
	}

	
	
}
