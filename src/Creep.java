
public class Creep extends Character {
	private int oldX;
	private int oldY;
	private int tempX;
	private int tempY;
	private int powerUpDuration;
	private String mode;
	private boolean reach;
	private Graph graph;
	private int Time;
	public Creep(Map coins)
	{
		setPowerUpDuration(0);
		cord = new Point(10,10,32,32);
		pixels = new Point(320,320,32,32);
		setDir("Right");
		setNewDir("Right");
		speed = 4;
		oldX=pixels.getX();
		oldY=pixels.getY();
		setMode("Guard");
		setReach(false);
		graph = new Graph(coins);
		setGraph(graph);
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
	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	public int getTime() {
		return Time;
	}
	public void setTime(int time) {
		Time = time;
	}
	
}