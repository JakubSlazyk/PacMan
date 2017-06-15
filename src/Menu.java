import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import com.sun.xml.internal.ws.Closeable;

import javafx.animation.AnimationTimer;
import javafx.collections.ListChangeListener.Change;
import sun.awt.RepaintArea;

/** Klasa odpowiedzialna za stworzenie menu gry.
 * 
 * 
 * 
*/
public class Menu extends JPanel implements ActionListener{
	private JButton newGameButton;
	private JButton ExitButton;
	private JFrame Frame;
	private Packman Player;
	private int choice;
	/** Konstruktor klasy
	 * 
	 * @param frame ramka w której powstaje menu
	 * @param player Pacman poruszaj¹cy siê w okó³ napisów
	 * @param cchoice mo¿liwoœæ wyboru miêdzy NEW GAME a EXIT
	 */
	public Menu(JFrame frame,Packman player,int cchoice){
		Frame=frame;
		this.choice=cchoice;
		Player=player;
		showGUI();
		
		
	}
	/** Metoda zmieniaj¹ca wyœwietlany napis
	 * 
	 */
	public void ChangeGGG()
	{
		if(choice==1)
			choice=2;
		else
			choice=1;
	}
	/** Ustawienie widocznosci GUI oraz koloru tla na czarny.
	 * 
	 */
	private void showGUI()
	{
		
		
		
		setVisible(true);
		setBackground(Color.black);
		
		
	}
	@Override
	/** Metoda s³u¿¹ca do dalszej interakcji z aplikacj¹
	 * 
	 *  @param e
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==newGameButton)
		{
			Frame.dispose();
			new ObrazFrame();
		}
		if(source==ExitButton)
		{
			Frame.dispose();
		}
		
	}
	/** Metoda sluzaca do rysowania ikony Pacmana.
	 * @param g domyœlny parametr graficzny
	 */
	public void drawPackman(Graphics g) 
	{
		int x;
		Graphics2D g2d = (Graphics2D) g;
		File tempPacman = null;
		BufferedImage imagePlayer = null;
		switch(Player.getDir())
		{
			case "Up":
				x=Player.getPixelsY() %32;
				if ((x>=0) && (x<=7))	tempPacman=new File("Resources/Img/Pacman90U.png");
				if ((x>=8) && (x<=15))	tempPacman=new File("Resources/Img/Pacman45U.png");
				if ((x>=16) && (x<=23))	tempPacman=new File("Resources/Img/Pacman0.png");
				if ((x>=24) && (x<=31))	tempPacman=new File("Resources/Img/Pacman45U.png");
				try {
					imagePlayer = ImageIO.read(tempPacman);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Down":
				x=Player.getPixelsY()%32;
				if ((x>=0) && (x<=7))	tempPacman=new File("Resources/Img/Pacman90D.png");
				if ((x>=8) && (x<=15))	tempPacman=new File("Resources/Img/Pacman45D.png");
				if ((x>=16) && (x<=23))	tempPacman=new File("Resources/Img/Pacman0.png");
				if ((x>=24) && (x<=31))	tempPacman=new File("Resources/Img/Pacman45D.png");
				try {
					imagePlayer = ImageIO.read(tempPacman);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Left":
				x=Player.getPixelsX()%32;
				if ((x>=0) && (x<=7))	tempPacman=new File("Resources/Img/Pacman90L.png");
				if ((x>=8) && (x<=15))	tempPacman=new File("Resources/Img/Pacman45L.png");
				if ((x>=16) && (x<=23))	tempPacman=new File("Resources/Img/Pacman0.png");
				if ((x>=24) && (x<=31))	tempPacman=new File("Resources/Img/Pacman45L.png");
				try {
					imagePlayer = ImageIO.read(tempPacman);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Right":
				x=Player.getPixelsX()%32;
				if ((x>=0) && (x<=7))	tempPacman=new File("Resources/Img/Pacman90R.png");
				if ((x>=8) && (x<=15))	tempPacman=new File("Resources/Img/Pacman45R.png");
				if ((x>=16) && (x<=23))	tempPacman=new File("Resources/Img/Pacman0.png");
				if ((x>=24) && (x<=31))	tempPacman=new File("Resources/Img/Pacman45R.png");
				try {
					imagePlayer = ImageIO.read(tempPacman);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
		}
		g2d.drawImage(imagePlayer,Player.getPixelsX(),Player.getPixelsY(), this);
		
	}
	
	@Override
	/** Metoda s³u¿y do wyœwietlenia napisów w oknie menu
	 * 
	 *  @param g domyœlny parametr graficzny
	 */
	public void paintComponent(Graphics g) {
		
		try {
			//System.out.println(choice);
			BufferedImage nowagraImage = null;
			BufferedImage koniecImage = null; 
			drawPackman(g);
			BufferedImage pacmanImage = ImageIO.read(new File("Resources/Img/Menu/PACMAN.png"));
			BufferedImage k5x4Image = ImageIO.read(new File("Resources/Img/Menu/K5X4.png"));
			if(choice==1){
			nowagraImage = ImageIO.read(new File("Resources/Img/Menu/NOWAGRAGLOW.png"));
			koniecImage = ImageIO.read(new File("Resources/Img/Menu/KONIEC.png"));
			}
			if(choice==2){
			nowagraImage = ImageIO.read(new File("Resources/Img/Menu/NOWAGRA.png"));	
			koniecImage = ImageIO.read(new File("Resources/Img/Menu/KONIECGLOW.png"));
			}
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(pacmanImage, 100, 0, this);
			g2d.drawImage(k5x4Image, 70, 50, this);
			g2d.drawImage(nowagraImage, 120, 200, this);
			g2d.drawImage(koniecImage, 185, 350, this);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
