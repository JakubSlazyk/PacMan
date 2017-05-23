
public class Packman extends Character {
	private int Points;
	private int LasersCounter;
	public Packman()
	{	setPoints(0);
		cord = new Point(1,1,32,32);
		pixels = new Point(32,32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
		LasersCounter=2;
	}
	public int getPoints() {
		return Points;
	}
	public void setPoints(int points) {
		Points = points;
	}
	public void getPowered(PowerUp power)
	{
		switch (power.getPowerUpIndex())
		{
		case 1:
			LasersCounter++;
		break;
		case 3:
			speed*=2;
		break;
			
		}
	}
	
	
}
