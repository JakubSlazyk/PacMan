
public class Creep extends Character {
	private int oldX;
	private int oldY;
	private int tempX;
	private int tempY;
	private int powerUpDuration;
	public Creep()
	{
		setPowerUpDuration(0);
		cord = new Point(10,10,32,32);
		pixels = new Point(320,320,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
		oldX=pixels.getX();
		oldY=pixels.getY();
	}
	public void UpdateCords()
	{
		if(pixels.getX()>oldX)
		{
			tempX+=speed;
		}
		else
		{
			tempX-=speed;
		}
		
		if(pixels.getY()>oldY)
		{
			tempY+=speed;
		}
		else
		{
			tempY-=speed;
		}
		if(tempY/32==1 || tempY/32==-1)
		{
			cord.setY(cord.getY()+(tempY/32));
			tempY%=32;
		}
		if(tempX/32==1 || tempX/32==-1)
		{
			cord.setX(cord.getX()+(tempX/32));
			tempX%=32;
		}
	}
	public int getPowerUpDuration() {
		return powerUpDuration;
	}
	public void setPowerUpDuration(int powerUpDuration) {
		this.powerUpDuration = powerUpDuration;
	}

	
}