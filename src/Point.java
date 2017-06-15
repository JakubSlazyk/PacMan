
/**Punkt, podstawowa jednostka. Zawiera wspolrzedne oraz rozmiary "bloczku" w celu poprawnego umieszczenia w siatce ramki.
 * 
 *
 */
public class Point {
	private int x;
	private int y;
	private int height;
	private int width;

	/** 
	 * @param x Ustalenie peirwszej wspolrzednej punktu.
	 * @param y Ustalenie drugiej wspolrzednej punktu.
	 * @param height Wysokosc jaka zajmuje punkt.
	 * @param width Szerokosc jaka zajmuje punkt.
	 */
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
