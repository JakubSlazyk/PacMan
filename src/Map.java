import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**Klasa umieszczajaca obraz w konkretnych miejscach w ramce.
 * 
 *
 */
public class Map {

	Set<Point> mapPoints;
	int WallHeight;
	int WallWidth;
	/**Na podstawie zadanego pliku zczytywane sa wspolrzedne, na ktorych ma byc umiejscowiony obrazek zadany w parametrze.
	 * @param height Wysokosc obrazu.
	 * @param width Szerokosc obrazu.
	 * @param directory Sciezka do pliku obrazu.
	 * @throws FileNotFoundException Wyjatek wyrzucany w przypadku nieznalezielnia pliku.
	 */
	public Map(int height,int width,String directory) throws FileNotFoundException
	{
		mapPoints = new HashSet<Point>();
		File mapFile = new File(directory);
		Scanner fileScanner = new Scanner(mapFile);
		String cord = fileScanner.nextLine();
		int tempX,tempY;
			while(true)
			{
				try{
				
				int temp=cord.indexOf(':');
				
				String tempStringX=cord.substring(0, temp);
				String tempStringY=cord.substring(temp+1, cord.length());
				tempX=Integer.valueOf(tempStringX);
				tempY=Integer.valueOf(tempStringY);
				
				cord = fileScanner.nextLine();
				WallHeight=height;
				WallWidth=width;
				mapPoints.add(new Point(tempX,tempY,WallHeight,WallWidth));
				if (temp==-1)
					break;
				}
				
				catch(NoSuchElementException e)
				{	int temp=cord.indexOf(':');
				
				String tempStringX=cord.substring(0, temp);
				String tempStringY=cord.substring(temp+1, cord.length());
				tempX=Integer.valueOf(tempStringX);
				tempY=Integer.valueOf(tempStringY);
					mapPoints.add(new Point(tempX,tempY,WallHeight,WallWidth));
					break;
					
				}
				
			}
			//ShowMap();
			
	}
	/**Metoda iterujaca po wszystkich punktach mapy i wypisujaca wspolrzedne do niej nalezace.
	 * 
	 */
	public void ShowMap()
	{	int i=0;
		for(Point punkt : mapPoints)
		{
			System.out.println(punkt.getX()+" "+punkt.getY());
			i++;
		}
			System.out.println("Razem "+i+" punktow");
	}
	
	Set<Point> get_mapPoints()
	{
		return this.mapPoints;
	}
	void set_mapPoints(Set<Point> Points)
	{
		this.mapPoints=Points;
	}
}

