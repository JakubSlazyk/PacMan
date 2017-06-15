import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



/** Klasa dziedziczaca po JFrame.
 * Odpowiada za 
 * @author Pawe³
 *
 */
public class MenuFrame extends JFrame implements KeyListener{
	private int UPLimit;
	private int DOWNLimit;
	private int LEFTLimit;
	private int RIGHTLimit;
	private Packman Player;
	private int choice;
	private boolean choiceChanged;
	private Menu menu;
	/** Konstruktor klasy. Tworzy ramkê w której wyœwietlane jest menu.
	 * 
	 */
	public MenuFrame()
	{
		super("Pacman Enhanced Edition");	
		choice=1;
		addKeyListener(this);
		choiceChanged=false;
		Player= new Packman();
		Player.setPixelsX(120-32);
		Player.setPixelsY((250/32)*32+32);
		Player.setSpeed(8);
		Player.setDir("Up");
		menu = new Menu(this,Player,choice);
		setSize(800,600);
		setBackground(Color.black);
		add(menu);
		setVisible(true);
		UPLimit=192-32;
		RIGHTLimit=(680/8)*8;
		DOWNLimit=(270/8)*8;
		LEFTLimit=(90/8)*8;
		
		Timer timer = new Timer(1000/32,new ActionListener(){
			
			/** Metoda odczytuj¹ca poruszanie kursora po ekranie menu, oraz kontroluj¹ca ruch Pacmana po menu.
			 * @param arg0 parametr domyœlny
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				//System.out.println(Player.getDir());
				if(choiceChanged==true)
				{
					if(choice==1)
					{
						Player.setPixelsX(96);
						Player.setPixelsY(280);
						Player.setDir("Up");
						choiceChanged=false;
						repaint();	
					}
					else
					{
						
						Player.setPixelsX(160);
						Player.setPixelsY(400);
						Player.setDir("Up");
						choiceChanged=false;
						repaint();	
					}
					menu.ChangeGGG();
				}
				if(choice==1){
					UPLimit=176;
					RIGHTLimit=672;
					DOWNLimit=264;
					LEFTLimit=96;
				}
				else
				{
					
					UPLimit=320;
					RIGHTLimit=592;
					DOWNLimit=416;
					LEFTLimit=160;
				}
				
				switch (Player.getDir()) {
				case "Up":
					if(Player.getPixelsY()>UPLimit)
					{	
						Player.setPixelsY(Player.getPixelsY()-Player.getSpeed());
						
						repaint();
					}
					else {
						Player.setDir("Right");
					}
					break;
				case "Right":
					if(Player.getPixelsX()<RIGHTLimit)
					{	
						Player.setPixelsX(Player.getPixelsX()+Player.getSpeed());
						repaint();
					}
					else {
						Player.setDir("Down");
					}
					break;
				case "Down":
					if(Player.getPixelsY()<DOWNLimit)
					{	
						Player.setPixelsY(Player.getPixelsY()+Player.getSpeed());
						repaint();
					}
					else {
						Player.setDir("Left");
					}
					break;
				case "Left":
					if(Player.getPixelsX()>LEFTLimit)
					{	
						Player.setPixelsX(Player.getPixelsX()-Player.getSpeed());
						repaint();
					}
					else {
						Player.setDir("Up");
					}break;
					
				}
				
				repaint();	
			}
			});
		
		timer.start();
		
	}
	/** Metoda realizuj¹ca akcjê, w przypadku naciœniêcia wybranych klawiszey.
	 *  @param e parametr domyœlny
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					//System.out.println("GAME");
					if(choice==1)
						choice=2;
					else
						choice=1;
					choiceChanged=true;
					break;
				case KeyEvent.VK_DOWN:
					//if(CheckCollision("Down"))
					//System.out.println("EXIT");
					if(choice==2)
						choice=1;
					else
						choice=2;
					choiceChanged=true;
					break;
				case KeyEvent.VK_SPACE:
					if(choice==1)
					{
						this.dispose();
						new ObrazFrame();
					}
					else {
						this.dispose();
					}
					
					break;
			
			}
		
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
