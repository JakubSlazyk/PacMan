
/** Klasa pocisku. Na podstawie pozycji gracza, powolywany do zycia jest strzal ktory otrzymuje
 *  kierunek gracza oraz jego dwukrotna predkosc.
 *  
 *  Wchodzi w kolizje z potworami, niszczac je.
 *
 *  Przy napotkaniu sciany - znika.
 *  {@inheritDoc}
 */
public class Projectile extends Character{
	private int lastX;
	private int lastY;
	public Projectile(Packman player,int x,int y)
	{	speed=player.getSpeed()*2;
		this.setDir(player.getDir()); 
		this.setNewDir(player.getNewDir());
		
		pixels = new Point(player.getPixelsX(),player.getPixelsY(),32,32);
		while(pixels.getX()%speed!=0)
		{	if(player.getDir()=="Right")
			pixels.setX(pixels.getX()-1);
			else
			pixels.setX(pixels.getX()+1);
		}
		while(pixels.getY()%speed!=0)
		{
			if(player.getDir()=="Down")
				pixels.setY(pixels.getY()-1);
				else
				pixels.setY(pixels.getY()+1);
		}
		cord = new Point(pixels.getX()/32,pixels.getY()/32,32,32);
		lastX=pixels.getX()-1;
		lastY=pixels.getY()-1;

		
	}
	public int getLastX() {
		return lastX;
	}
	public void setLastX(int lastX) {
		this.lastX = lastX;
	}
	public int getLastY() {
		return lastY;
	}
	public void setLastY(int lastY) {
		this.lastY = lastY;
	}
}
