
public class Projectile extends Character{

	public Projectile(Packman player,int x,int y)
	{
		this.setDir(player.getDir());
		this.setNewDir(player.getNewDir());
		cord = new Point(x/32,y/32,32,32);
		pixels = new Point(x,y,32,32);
		if(player.getMultiplierDuration()==0)
		speed=player.getSpeed()*2;
		else
		speed=player.getSpeed();
	}
}
