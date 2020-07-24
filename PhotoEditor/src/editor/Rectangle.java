package editor;

public class Rectangle {
	
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;
	
	private int width;
	private int height;
	
	public Rectangle(int sX,int sY, int eX,int eY)
	{
		startX = sX;
		startY = sY;
		endX = eX;
		endY = eY;
		
		
		width = eX - sX;
		height = eY - sY;
		
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight() 
	{
		return height;
	}


	public int getStartX() {
		return startX;
	}


	public void setStartX(int startX) {
		this.startX = startX;
	}


	public int getStartY() {
		return startY;
	}


	public void setStartY(int startY) {
		this.startY = startY;
	}


	public int getEndX() {
		return endX;
	}


	public void setEndX(int endX) {
		this.endX = endX;
	}


	public int getEndY() {
		return endY;
	}


	public void setEndY(int endY) {
		this.endY = endY;
	}
	
}
