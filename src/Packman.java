
public class Packman extends Character {
	private int Points;
	private int LasersCounter;
	private int powerUpDuration;
	private int multiplierDuration;
	public Packman()
	{	setPoints(0);
		setPowerUpDuration(0);
		cord = new Point(1,1,32,32);
		pixels = new Point(32,32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
		setLasersCounter(2);
	}
	public int getPoints() {
		return Points;
	}
	public void setPoints(int points) {
		Points = points;
	}
	public int getLasersCounter() {
		return LasersCounter;
	}
	public void setLasersCounter(int lasersCounter) {
		LasersCounter = lasersCounter;
	}
	public int getPowerUpDuration() {
		return powerUpDuration;
	}
	public void setPowerUpDuration(int powerUpDuration) {
		this.powerUpDuration = powerUpDuration;
	}
	public int getMultiplierDuration() {
		return multiplierDuration;
	}
	public void setMultiplierDuration(int multiplierDuration) {
		this.multiplierDuration = multiplierDuration;
	}
	
	
}
