import java.util.HashSet;
import java.util.Set;

/** Klasa scian w ramce, definiuje mape.
 *  Sklada sie z pola, okreslajacego czy sciana moze zostac zniszczona, oraz listy wspolrzednych
 *  na ktorych bloki wystepuja.
 *
 */
public class Wall extends Character{
	boolean isDestroyable;
	static Set<Point> CoordinatesList;
	public Wall(int x,int y,boolean Destroy)
	{
		cord=new Point(x, y, 32, 32);
		pixels = new Point(x*32, y*32, 32, 32);
		speed=0;
		isDestroyable=Destroy;
	}
	static Set <Wall> addObjects()
	{
		Set <Wall> temp = new HashSet<Wall>();
		for(Point punkt:CoordinatesList)
		{
			temp.add(new Wall(punkt.getX(), punkt.getY(), false));
		}
		return temp;
	}
}
