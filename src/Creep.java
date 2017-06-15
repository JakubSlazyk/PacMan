
/**Klasa dziedziczaca po abstrakcyjnej postaci. Przeciwnik poruszajacy sie po mapie w okreslonych trybach.
 * 
 *
 */
public class Creep extends Character {
	//private int oldX;
	//private int oldY;
	//private int tempX;
	//private int tempY;
	private int powerUpDuration;
	/**
	 * Okreslenie czy jest to tryb strozujacy, czy tez pogodni za graczem.
	 */
	private String mode;
	private boolean reach;
	private int Time;
	public Creep()
	{
		setPowerUpDuration(0);
		cord = new Point(10,10,32,32);
		pixels = new Point(320,320,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
		//oldX=pixels.getX();
		//oldY=pixels.getY();
		setMode("Guard");
		setReach(false);
		setTime(0);
	}
	public int getPowerUpDuration() {
		return powerUpDuration;
	}
	public void setPowerUpDuration(int powerUpDuration) {
		this.powerUpDuration = powerUpDuration;
	}
	public void setMode(String mode){
		this.mode = mode;
	}
	public String getMode(){
		return mode;
	}
	public boolean getReach() {
		return reach;
	}
	public void setReach(boolean reach) {
		this.reach = reach;
	}
	public int getTime() {
		return Time;
	}
	public void setTime(int time) {
		Time = time;
	}
	
}