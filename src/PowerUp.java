import java.io.File;
import java.util.Random;

/** Klasa dodatkow. Umozliwia postaci zdobycie dodatkowych umiejetnosci. Kazdy dodatek ma swoje pradopobienstwo wystapienia podczas losowania jaki perk ma sie pojawic na mapie.
 * 
 *
 */
public class PowerUp extends Character{
	private int powerUpIndex;
	static int respawnTime;//w klatkach prawdopodobnie
	private int TimeToLive;//w klatkach prawdopodobnie
	private File powerUpFile;
	static int duration;

	/**Tworzy dodatek na podstawie podanych wspolrzednych. Losuje sie liczba, ktora decyduje jakie umiejetnosci zostana przydzielone.
	 * @param x Pierwsza wspolrzedna punktu na ktorym ma sie pojawic dodatek.
	 * @param y Druga wspolrzedna punktu na ktorym ma sie pojawic dodatek.
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
		
		if(temp>=0 && temp<35)
		{
			setPowerUpIndex(1);
			powerUpFile = new File("Resources/Img/PowerUps/bullet.png");
			}
		if(temp>=35 && temp<36)
		{
			setPowerUpIndex(2);
			powerUpFile = new File("Resources/Img/PowerUps/map.png");
			}
		if(temp>=36 && temp<70)
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
	/**Stworzenie konkretnego dodatku na podstawie zadanej nazwy.
	 * @param x Pierwsza wspolrzedna punktu na ktorym ma sie pojawic dodatek.
	 * @param y Druga wspolrzedna punktu na ktorym ma sie pojawic dodatek.
	 * @param name Lancuch znakow, ktory okresla jaka umiejetnosc ma zostac przydzielone.
	 */
	public PowerUp(int x,int y, String name){
		cord = new Point(x,y,32,32);
		pixels = new Point(x*32,y*32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 0;
		if (name=="map"){
			setPowerUpIndex(2);
			powerUpFile = new File("Resources/Img/PowerUps/map.png");
		}
		else if(name=="x2"){
			setPowerUpIndex(5);
			powerUpFile = new File("Resources/Img/PowerUps/x2.png");
		}
		else if(name=="speed"){
			setPowerUpIndex(3);
			powerUpFile = new File("Resources/Img/PowerUps/Speed.jpg");
		}
		else if(name=="bullet"){
			setPowerUpIndex(1);
			powerUpFile = new File("Resources/Img/PowerUps/bullet.png");
			
		}
		else if(name=="slow2"){
			setPowerUpIndex(4);
			powerUpFile = new File("Resources/Img/PowerUps/slow2.png");
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
