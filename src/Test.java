import java.awt.EventQueue;
import java.io.FileNotFoundException;

/** Glowna klasa, ktora odpowiada za uruchomienie aplikacji.
 *  Jedyna funkcja, to uruchomienie 
 *
 */
public class Test {
	/** Glowna metoda odpowiadajaca za powolanie do zycia watku. Jej jedynym zadaniem
	 * jest przekazanie do watku Swing zadan interfejsu graficznego.
	 * @param args Tablica argumentow przekazana do projektu.
	 * @throws FileNotFoundException Wyjatek w wypadku nie znalezienia potrzebnych plikow.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		EventQueue.invokeLater(
		new Runnable() {
			@Override
			public void run() {
				//System.out.print("Najnowszy ostatni test");
				//new ObrazFrame();
				new MenuFrame();
			}
		});
	}
}