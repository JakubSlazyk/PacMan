import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**Graf zawierajacy wszystkie pola na ktorych nie wystepuja kolizja, czyli mozliwe do przejscia.
 * 
 *
 */
public class Graph {
	private Set <Node> Nodes;
	private Set <Point> coinPoints;
	public Set <Node> GetSet(){
		return this.Nodes;
	}
	
	/**Konstruktor. Po pobraniu wszystkich punktow monet, sprawdza czy wspolrzedne naokolo naleza do grafu - jezeli tak, to dodaje je jako sasiadow aktualnie rozpatrywanego wierzcholka.
	 * @param coins Zbior monet na podstawie ktorych tworzony jest graf.
	 */
	public Graph(Map coins){
		Nodes = new HashSet<>();
		
		coinPoints=coins.get_mapPoints();
		for(Point punkt : coinPoints){
			Node walker = new Node(punkt.getX(),punkt.getY());
			Nodes.add(walker);		
		}
		Node nod = new Node(1,1);
		Nodes.add(nod);
		
		for (Node wezel : Nodes){
			int x,y;
			x = wezel.getX();
			y = wezel.getY();
		//	System.out.print("X: " + x + "  Y:  " + y + "          ");
			for (Node wezel2 : Nodes){
				int x2,y2;
				x2 = wezel2.getX();
				y2 = wezel2.getY();
				if(((x2==x+1) && (y2==y)) || ((x2==x) && (y2==y+1)) || ((x2==x-1) && (y2==y)) || ((x2==x) && (y2==y-1)) ) {
					wezel.setNeighbour(wezel2);
				//	System.out.print(x2 + "/" + y2 + "  ");
				}			
			}
		//	System.out.println();
		}		
	}
	/**Dodawanie wierzcholka do grafu na podstawie wspolrzednych.
	 * @param a X-wspolrzedna dodawanego wierzcholka.
	 * @param b Y-wspolrzedna dodawanego wierzcholka.
	 */
	public void addNode(int a, int b){
		Node newNode = new Node(a,b);		
		Nodes.add(newNode);
		for (Node wezel : Nodes){
			int x,y;
			x = wezel.getX();
			y = wezel.getY();
			if(((x==a+1) && (y==b)) || ((x==a) && (y==b+1)) || ((x==a-1) && (y==b)) || ((x==a) && (y==b-1)) ) {
				newNode.setNeighbour(wezel);
				wezel.setNeighbour(newNode);
			}
		}		
	}
	
	/**Algorytm przeszukiwania grafu wszerz. Znajduje najkrotsza droge miedzy wspolrzednymi zadanych punktow
	 * @param x1 Pierwsza wspolrzedna pierwszego punktu.
	 * @param y1 Druga wspolrzedna pierwszego punktu.
	 * @param x2 Pierwsza wspolrzedna celu.
	 * @param y2 Druga wspolrzedna celu.
	 * @return Zwraca liste zawierajace wierzcholki grafu, ktore zapewniaja najkrotsza sciezke.
	 */
	public List<Node> BFS(int x1, int y1, int x2, int y2){
		Node root=null;
		Node walker = null;
		for (Node wezel : Nodes){
			if ((wezel.getX() == x1) && (wezel.getY() == y1)){
				root = wezel;
				break;
			}
		}
		List <Node> queue = new LinkedList<>();
		List <Node> path = new LinkedList<>();
		queue.add(root);
		root.setColor(1);
		while (!queue.isEmpty()){
			walker = queue.remove(0);
			for (Node wezel : walker.getNeighbours()){
				if (wezel.getColor()==0){
					wezel.setColor(1);
					wezel.setParent(walker);
					queue.add(wezel);
				}
			
			}
			walker.setColor(2);
			if ((walker.getX()==x2) && (walker.getY()==y2)){
				break;
			}
		}
		
		while (walker.getParent()!=null){
			path.add(walker);
			walker = walker.getParent();
		}
		
		Collections.reverse(path);
		//for (Node wezel : path){
		//	System.out.print(wezel.getX() + "  " + wezel.getY() + " \n");
		//}
		//System.out.print(path.get(0).getX() + "  " + path.get(0).getY() + "   OL\n");
		ClearNodes();
		return path;
	}
	/**Metoda czysci kazdy wezel ze swojego koloru oraz rodzica - pol potrzebnych do przeszukiwania BFS.
	 * 
	 */
	private void ClearNodes(){
		for (Node walker : Nodes){
			walker.setColor(0);
			walker.setParent(null);
		}
	}
	

}
