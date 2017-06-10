import java.io.File;
import java.util.Random;

public class PowerUp extends Character{
	private int powerUpIndex;
	static int respawnTime;//w klatkach prawdopodobnie
	private int TimeToLive;//w klatkach prawdopodobnie
	private File powerUpFile;
	static int duration;
	/*
	 * 1-strza³ 30%
	 * 2-zmiana poziomu 10%
	 * 3-speed x2 30%
	 * 4-duchy speed/10%
	 * 5-score x2 20%
	 * 
	 */
	public PowerUp(int x,int y)
	{
		cord = new Point(x,y,32,32);
		pixels = new Point(x*32,y*32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 0;
		Random generator = new Random();
		int temp =	generator.nextInt(100);
		
		if(temp>=0 && temp<30)
		{
			setPowerUpIndex(1);
			powerUpFile = new File("Resources/Img/PowerUps/bullet.png");
			}
		if(temp>=30 && temp<40)
		{
			setPowerUpIndex(2);
			powerUpFile = new File("Resources/Img/PowerUps/map.png");
			}
		if(temp>=40 && temp<70)
		{
			setPowerUpIndex(3);
			powerUpFile = new File("Resources/Img/PowerUps/Speed.jpg");
			}
		if(temp>=70 && temp<80)
		{
			setPowerUpIndex(4);
			powerUpFile = new File("Resources/Img/PowerUps/slow2.png");
			}
		if(temp>=80 && temp<100)
		{
			setPowerUpIndex(5);
			powerUpFile = new File("Resources/Img/PowerUps/x2.png");
			}
		
	}
	public File getPowerUpFile() {
		return powerUpFile;
	}
	public void setPowerUpFile(File powerUpFile) {
		this.powerUpFile = powerUpFile;
	}
	public int getPowerUpIndex() {
		return powerUpIndex;
	}
	public void setPowerUpIndex(int powerUpIndex) {
		this.powerUpIndex = powerUpIndex;
	}
	public int getTimeToLive() {
		return TimeToLive;
	}
	public void setTimeToLive(int timeToLive) {
		TimeToLive = timeToLive;
	}
	
}
