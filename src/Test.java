import java.awt.EventQueue;
import java.io.FileNotFoundException;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				//System.out.print("Najnowszy ostatni test");
				new ObrazFrame();
			}
		});
	}
}