/**Abstrakcyjna klasa postaci, dziedzicza po niej "byty" pojawiajace sie w grze
 * 
 * 
 *
 */
public abstract class Character {
	protected Point cord;
	protected Point pixels;
	protected String dir;
	protected String NewDir;
	protected int speed;
	String test;
	public int getSpeed()
	{
		return speed;
	}
	public void setSpeed(int speed)
	{
		this.speed= speed;
	}
	
	public int getX()
	{
		return cord.getX();
	}
	public int getY()
	{
		return cord.getY();
	}
	public void setY(int y)
	{
		this.cord.setY(y);
	}
	public void setX(int x)
	{
		this.cord.setX(x);
	}
	public int getPixelsX() {
		return pixels.getX();
	}
	public int getPixelsY() {
		return pixels.getY();
	}
	public void setPixelsX(int x) {
		this.pixels.setX(x);
	}
	public void setPixelsY(int y) {
		this.pixels.setY(y);
	}
	public int getHeight()
	{
		return this.pixels.getHeight();
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getNewDir() {
		return NewDir;
	}
	public void setNewDir(String dir) {
		this.NewDir = dir;
	}
	/**Metoda aktualizuje wspolrzedne postaci, na podstawie zajmowanych przez nia pixeli.
	 * 
	 */
	public void UpdateCords()
	{
		//if (pixels.getX()/32 == (pixels.getX()+32)/32) 
			cord.setX(pixels.getX()/32);
		
		//if (pixels.getY()/32 == (pixels.getY()+32)/32)
			cord.setY(pixels.getY()/32);
	}
}
