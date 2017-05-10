
public class Packman extends Character {

	public Packman()
	{
		cord = new Point(1,1,32,32);
		pixels = new Point(32,32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
	}
	
	
}
