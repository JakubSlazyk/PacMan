/**Moneta jako nierochumy byt, sluzy do zdobywania punktow. 
 * 
 * 
 *
 */
public class Coin extends Character{
	String test;
	/**
	 * @param x Pierwsza wspolrzedna monety.
	 * @param y Druga wspolrzedna monety.
	 */
	public Coin(int x,int y){
		speed=0;
		cord=new Point(10,10,8,8);
		pixels= new Point(320,320,8,8);
	}
	
}
