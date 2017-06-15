import java.util.HashSet;
import java.util.Set;

/**Wierzcholek grafu. Posiada swoje wspolrzedne, zbior sasiadow tego samego typu, oraz kolor/rodzica potrzebne do przechodzenia grafu wszerz.
 * 
 *
 */
public class Node {
	private int x,y;
	private int color;
	private Node Parent;
	private Set <Node> Neighbours;
	/**
	 * @param x Pierwsa wspolrzedna tworzonego wierzcholka.
	 * @param y Druga wspolrzedna tworzonego wierzcholka.
	 */
	public Node (int x, int y){
		this.x=x;
		this.y=y;
		color = 0;
		Parent = null;
		Neighbours = new HashSet<>();
	}
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Node getParent() {
		return Parent;
	}

	public void setParent(Node parent) {
		Parent = parent;
	}

	public void setNeighbour (Node bla){
		Neighbours.add(bla);
	}
	
	public Set <Node> getNeighbours(){
		return Neighbours;
	}
	
	

	
}
