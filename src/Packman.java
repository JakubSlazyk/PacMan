
public class Packman extends Character {
	private int Points;
	public Packman()
	{	setPoints(0);
		cord = new Point(1,1,32,32);
		pixels = new Point(32,32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
	}
	public int getPoints() {
		return Points;
	}
	public void setPoints(int points) {
		Points = points;
	}
	
	
}
