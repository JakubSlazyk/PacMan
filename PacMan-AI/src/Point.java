
public class Point {
	private int x;
	private int y;
	private int height;
	private int width;

	public Point(int x,int y,int height,int width)
	{
		this.y=y;
		this.x=x;
		this.setHeight(height);
		this.setWidth(width);
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setX(int x)
	{
		this.x=x;
	}
	public void setY(int y)
	{
		this.y=y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
