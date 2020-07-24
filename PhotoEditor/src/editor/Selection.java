package editor;

import java.util.ArrayList;
import java.util.List;

public class Selection {

	List<Rectangle> rectangles = new ArrayList<Rectangle>();
	
	
	public Selection()
	{
		
	}
	
	public Selection(List<Rectangle> rects)
	{
		rectangles = rects;
	}
	
	public void addRectangle(Rectangle rect)
	{
		rectangles.add(rect);
	}
	
	public void removeRectangle(Rectangle rect)
	{
		rectangles.remove(rect);
	}
	
	
}
