
/**Klasa gracza. W klasie przetrzymywane sa wszystkie pola, ktore nie powinny zostac skasowane po resecie gry.
 * Dodatkowo przetrzymwane zycia oraz dodatki - ich rodzaj i ew. ilosc
 *
 */
public class Packman extends Character {
	private int Points;
	private int Life;
	private int LasersCounter;
	private int powerUpDuration;
	private int multiplierDuration;
	public Packman()
	{	setLife(3);
		setPoints(0);
		setPowerUpDuration(0);
		cord = new Point(1,1,32,32);
		pixels = new Point(32,32,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
		setLasersCounter(0);
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
	
	/** Ustawia mnoznik czasu trwania bonusu.
	 * @param multiplierDuration Ustawia mnoznik czasu na zawartosc tego argummentu.
	 */
	public void setMultiplierDuration(int multiplierDuration) {
		this.multiplierDuration = multiplierDuration;
	}
	public int getLife() {
		return Life;
	}
	public void setLife(int life) {
		Life = life;
	}
	
	
}
