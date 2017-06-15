import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.applet.*;
import java.net.URL;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.List;
import java.util.HashSet;
import java.util.Random;
/**Ramka w ktorej pojawia sie gra. Dodany jest do niej panel ObrazPanel.
 * W tej klasie zainicjowane sa wszystkie obikety potrzebne do gry, oraz przesuwania obiektow gracza/potworow/pociskow.
 * @author Pawel
 * {@inheritDoc}
 */
public class ObrazFrame extends JFrame implements KeyListener{
	Packman Player;
	Creep Monster;
	private boolean isGameOver;
	private boolean musicFlag;
	private BufferedImage imageWall,imagePlayer;
	private int monsterSpeed;
	private int playerSpeed;
	private int ImagePixels;
	private JPanel obrazPanel;
	private Timer timer;
	private int TimeInterval;
	private Set <Point> mapPoints,coinPoints,powerUpPoints,powerupSpace;
	private Set <Creep> MonstersList;
	private Set <Projectile> ProjectilesList;
	private Set <PowerUp> powerUpsList;
	private Set <Wall> wallsList;
	private Set <Point> tempDestroyedWalls;
	private Map map,coins;
	private Graph graph;
	private int Timing;
	private long Iterations;
	private Point eatenCoinId;
	private Point eatenPowerUp;
	private int MonstersQuantity;
	private boolean isGamePaused;
	private int scoreMultiplier;
	private int musicTimer;
	private int monsterRespawnTimer;
	private Set<PowerUp> destroyedPowerUp;
	
	
	
	/**Ustawienie danych. Wywoluje setMap, ktora ustawia parametry mapy oraz dodaje obrazPanel:JPanel.
	 * Kolor tla - czarny.
	 * Zamyka okno przy nacisnieciu krzyzyka.
	 * Dostosowuje rozmiar do obrazu.
	 * 
	 */
	public void SetVariables()
	{	
		setMap();
		
		add(obrazPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//System.out.println(PowerUp.respawnTime);
		setBackground(Color.black);
		pack();
		repaint();
		setVisible(true);
		
		
	}
	/**Ustawianie argumentow mapy. Tworzy nowego Pacmana, wszystkie kolekcje potworow/scian/monet/pociskow/powerUpow.
	 * Uruchamia dzwiek poczatku gry.
	 * Zapelnia przestrzen scian za pomoca pliku "Resources/Maps/map.txt".
	 * Zapelnia przestrzen monet za pomoca pliku "Resources/Maps/coins.txt".
	 * Tworzy graf sciezek na podstawie rozmieszczonych monet.
	 * Ustawia czestotliwosc odswiezania na 32fps.
	 * Ustawia czestotliwosc pojawiania sie powerUpow oraz ich czas trwania.
	 */
	
	void setMap()
	{
		
		isGameOver=false;
		monsterRespawnTimer=0;
		musicTimer=0;
		musicFlag=false;
		tempDestroyedWalls=new HashSet<Point>();
		scoreMultiplier=1;
		Timing=0;
		isGamePaused=true;
		ImagePixels=32;
		if(Player==null)
			Player=new Packman();
		else{
			Player.setPixelsX(32);
			Player.setPixelsY(32);
		}
		playerSpeed=Player.getSpeed();
		//MonstersQuantity=MonsterAmount;
		if(powerUpsList!=null)
			powerUpsList.clear();
		MonstersList = new HashSet<Creep>();
		powerUpsList = new HashSet<PowerUp>();
		ProjectilesList = new HashSet<Projectile>();
		destroyedPowerUp = new HashSet<PowerUp>();
		powerupSpace = new HashSet<Point>();
		File Clap = new File("Resources/Sounds/wav/start.wav");
		PlaySound(Clap);
		
		try {
			map= new Map(ImagePixels,ImagePixels,"Resources/Maps/map.txt");
			coins = new Map(ImagePixels,ImagePixels,"Resources/Maps/coins.txt");
			
			mapPoints=map.get_mapPoints();
			coinPoints=coins.get_mapPoints();
			for (Point punkt: coinPoints)
			{
				powerupSpace.add(punkt);
			}
			graph = new Graph(coins);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<MonstersQuantity;i++)
		{
			Monster = new Creep();
			MonstersList.add(Monster);
			monsterSpeed=Monster.getSpeed();
		}
		Wall.CoordinatesList=mapPoints;
		wallsList=Wall.addObjects();
		try {
			obrazPanel = new ObrazPanel(Player,map,Monster,coins,MonstersList,powerUpsList,ProjectilesList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		TimeInterval=1000/32;
		PowerUp.respawnTime=TimeInterval*5;
		PowerUp.duration=TimeInterval*5;
	}
	
	
	
	/** Sprawdza czy dane pixele leza w punkcie.
	 * @param x Pierwsza wspolrzedna pixela.
	 * @param y Druga wspolrzedna pixela.
	 * @param punkt Punkt do sprawdzenia
	 * @return True/false czy lezy.
	 */
	boolean inside(int x,int y,Point punkt)
	{	boolean flag = (x > punkt.getX()*ImagePixels && x < punkt.getX()*ImagePixels+punkt.getWidth()) &&
	        (y > punkt.getY()*ImagePixels && y < punkt.getY()*ImagePixels+punkt.getHeight());
		
		return flag;
		       
		        
		        
	}
	
	boolean inside2(int x,int y,Point punkt)
	{	boolean flag = (x > punkt.getX()-punkt.getWidth() && x < punkt.getX()+punkt.getWidth()) &&
	        (y > punkt.getY()-punkt.getHeight() && y < punkt.getY()+punkt.getHeight());
		
		return flag;
		       
		        
		        
	}
	/** Sprawdzenie czy dana figura moze wykonac zmiane kierunku podana w argumencie. 
	 * Iteruje po wszystkich punktach scian, a nastepnie dla kazdego przypadku ruchu postaci (Up,Down,Right,Left) sprawdza czy nastapila kolizja.
	 * Jezeli nastpila, to zwraca false, nie mozna zmienic kierunku.
	 * @param dir Kierunek postaci, ktory ma zostac sprawdzony.
	 * @param figure Postac dla jakiej sprawdza sie mozliwosc zmiany kierunku.
	 * @return Zwraca true/false w zaleznosci czy mozna zmienic kierunek.
	 */
	boolean ChangeDirectionAvailable(String dir, Character figure){
		int x,y;
		
		for (Point punkt:mapPoints){
			switch(dir){
				case "Up":
					x = figure.getPixelsX();
					y = figure.getPixelsY() - figure.getSpeed();
					if(inside(x+1,y,punkt))
					{
						return false;
					}
					if(inside(x+figure.getHeight(),y,punkt))
					{
						return false;
					}					
				break;
				case "Down":
					x = figure.getPixelsX();
					y = figure.getPixelsY() + figure.getSpeed();
					if(inside(x+1,y+figure.getHeight(),punkt))
					{
						return false;
					}
					if(inside(x+figure.getHeight(),y+figure.getHeight(),punkt))
					{
						return false;
					}	
				break;
				case "Right":
					x = figure.getPixelsX() + figure.getSpeed();
					y = figure.getPixelsY();
					if(inside(x+figure.getHeight(),y+1,punkt))
					{
						return false;
					}
					if(inside(x+figure.getHeight(),y+figure.getHeight(),punkt))
					{
						return false;
					}	
				break;
				case "Left":
					x = figure.getPixelsX() - figure.getSpeed();
					y = figure.getPixelsY();
					if(inside(x,y+1,punkt))
					{
						return false;
					}
					if(inside(x,y+figure.getHeight(),punkt))
					{
						return false;
					}
				break;
			}		
		}
		
		return true;
	}
	boolean collision2(String dir, Character figure,Set <Point> mapPoints,int offset,int which)
	{
		
		for(Point punkt : mapPoints)
		{		
			
				
				if(inside2(figure.getPixelsX()+offset,figure.getPixelsY(),punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					
					
					
					return false;
				}
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY(),punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}
				
			
			
				
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+offset,punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}	
				
			
			
				if(inside2(figure.getPixelsX()+offset,figure.getPixelsY()+figure.getHeight(),punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}	
			
			
				if(inside2(figure.getPixelsX(),figure.getPixelsY()+offset,punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}
				if(inside2(figure.getPixelsX(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					}
					return false;
				}
				
			
		}
		return true;
	
	}
	
	boolean collision(String dir, Character figure,Set <Point> mapPoints,int offset,int which)
	{
		
		for(Point punkt : mapPoints)
		{		
			if (dir=="Up"){
				
				if(inside(figure.getPixelsX()+offset,figure.getPixelsY(),punkt))
				{
					
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY(),punkt))
				{
					
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}
				
			}
			if (dir=="Right"){
				
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+offset,punkt))
				{
				
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}	
				
			}
			if (dir=="Down"){
				
				if(inside(figure.getPixelsX()+offset,figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}	
			}
			if (dir=="Left"){
				
				if(inside(figure.getPixelsX(),figure.getPixelsY()+offset,punkt))
				{
				
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}
				if(inside(figure.getPixelsX(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
				
					switch(which)
					{
					case 1:
						eatenCoinId=punkt;
						break;
					case 2:
						eatenPowerUp=punkt;
						break;
					case 4:
						if(!tempDestroyedWalls.contains(punkt))
						tempDestroyedWalls.add(punkt);
						break;
					}
					return false;
				}
				
			}
		}
		return true;
	
	}
	
	/**Dodanie powerUpu. Losuje nowy powerUp oraz jego miejsce. 
	 * 
	 */
	public void addPowerUp()
	{
		int random=new Random().nextInt(powerupSpace.size());
		int i=0;
		int x=0,y=0;
		for(Point point: powerupSpace)
		{
			if(random==i)
			{
				x=point.getX();
				y=point.getY();
				break;
			}
			else
				{i++;
				}
			
		}
	//	System.out.println(random+" "+x+" "+y);
		PowerUp powerUp = new PowerUp(x,y);
		powerUpsList.add(powerUp);
		File Clap = new File("Resources/Sounds/wav/bonus_spawn.wav");
		PlaySound(Clap);
		//System.out.println("Spawn power up :"+powerUp.getPowerUpIndex());
	}
	/** Zdjecie powerUpu z kolekcji powerUpow.
	 * @param power Jaki powerUp nalezy zdjac.
	 */
	public void deletePowerUp(PowerUp power)
	{
		powerUpsList.remove(power);
	}
	
	/** Ruch. W zaleznosci w jaka strone porusza sie figura, dodaje sie do jej wspolrzednych wartosc predkosci a nastepnie sprawdza czy wystapila
	 * kolizja z murem. Jezeli tak(nie mozna isc) to zostaje przywrocona stara wartosc, zeby postac nie przechodzila przez sciany.
	 * @param figure Jaka postac wykonuje ruch.
	 */
	public void Move(Character figure)
	{				
			switch(figure.getDir())
			{			
			case "Up":
				
				figure.setPixelsY(figure.getPixelsY()-figure.getSpeed());	
				
				if(!collision(figure.getDir(),figure,mapPoints,1,3)){
					
					figure.setPixelsY(figure.getPixelsY()+figure.getSpeed());	
				}
				if(figure.getPixelsY()==0)
					figure.setPixelsY(640);
				
				break;
			case "Down":
				
				figure.setPixelsY((figure.getPixelsY()+figure.getSpeed())%640);	
				if(!collision(figure.getDir(),figure,mapPoints,1,3)){
					
					figure.setPixelsY(figure.getPixelsY()-figure.getSpeed());
				}
				
				break;
			case "Right":
				
				figure.setPixelsX((figure.getPixelsX()+figure.getSpeed())%800);	
				if(!collision(figure.getDir(),figure,mapPoints,1,3)){
					figure.setPixelsX(figure.getPixelsX()-figure.getSpeed());	
				}
				
				
				break;
			case "Left":
				figure.setPixelsX(figure.getPixelsX()-figure.getSpeed());	
				if(!collision(figure.getDir(),figure,mapPoints,1,3)){
					figure.setPixelsX(figure.getPixelsX()+figure.getSpeed());	
				}
				if(figure.getPixelsX()==0)
					figure.setPixelsX(800);
				
				break;
			
			}
			if  ((ChangeDirectionAvailable(figure.getNewDir(),figure)) && (figure.getPixelsX()%32==0) && (figure.getPixelsY()%32==0)){
			//	Direction = NewDirection; 
				figure.setDir(figure.getNewDir());
			}
		if ((figure.getPixelsX()%32==0) && (figure.getPixelsY()%32==0))figure.UpdateCords();
		
		
		repaint();
	}
	/**Losowe AI. Potwor sprawdza, w ilu kierunkach moze sie poruszyc w danej chwili. Nastepnie dla kazdego przypadku 1/2/3/4 losuje nastepny kierunek, 
	 * z zastrzezeniem, ze nie cofa sie na prostej drodze (*).
	 * 
	 * (*) Jezeli potwor ma wejsc w teleport po bokach mapy, nastepuje ponowne losowanie kierunku, co moze spowodowac cofniecie.
	 * @param monster Dla jakiego potwora wykonuje sie algorytm.
	 */
	public void CrazyAI(Creep monster){
		boolean flag=true;
		do{
			Random rand = new Random();
			int RandomisedDir;
			RandomisedDir = rand.nextInt(4);
			int i=0;
			if(ChangeDirectionAvailable("Up",monster))
				i++;
			if(ChangeDirectionAvailable("Down",monster))
				i++;
			if(ChangeDirectionAvailable("Left",monster))
				i++;
			if(ChangeDirectionAvailable("Right",monster))
				i++;
			//System.out.println(i);
			switch(i)
			{
			case 1:
				if(ChangeDirectionAvailable("Up",monster))
					monster.setDir("Up");
				if(ChangeDirectionAvailable("Down",monster))
					monster.setDir("Down");
				if(ChangeDirectionAvailable("Left",monster))
					monster.setDir("Left");
				if(ChangeDirectionAvailable("Right",monster))
					monster.setDir("Right");
			break;
			case 2:
	
				if(!ChangeDirectionAvailable(monster.getDir(),monster))
				{	
					switch(monster.getDir())
					{
					case "Up":
						if(ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					case "Down":
						if(ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					case "Left":
						if(ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						break;
					case "Right":
						if(ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						break;
					}
				}
				
			break;
			case 3:
				if(!ChangeDirectionAvailable(monster.getDir(),monster))
				{	
					if(monster.getDir()=="Up" || monster.getDir()=="Down")
					{
						RandomisedDir = rand.nextInt(2);
						if(RandomisedDir==0)
							monster.setDir("Left");
						else
							monster.setDir("Right");
					}
					else
					{
						RandomisedDir = rand.nextInt(2);
						if(RandomisedDir==0)
							monster.setDir("Up");
						else
							monster.setDir("Down");
					}
					
				}
				else
				{	RandomisedDir = rand.nextInt(3);
					switch(monster.getDir())
					{
					case "Up":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					case "Down":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					case "Left":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						break;
					case "Right":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					}
				}
				break;
				case 4:
					RandomisedDir = rand.nextInt(3);
					switch(monster.getDir())
					{
					case "Up":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					case "Down":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					case "Left":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Left",monster))
							monster.setDir("Left");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						break;
					case "Right":
						if(RandomisedDir==0 && ChangeDirectionAvailable("Up",monster))
							monster.setDir("Up");
						if(RandomisedDir==1 && ChangeDirectionAvailable("Down",monster))
							monster.setDir("Down");
						if(RandomisedDir==2 && ChangeDirectionAvailable("Right",monster))
							monster.setDir("Right");
						break;
					}
			}
			
			monster.setNewDir(monster.getDir());
			String dir = monster.getDir();
			Set <Node> Nodes = graph.GetSet();
			int x = monster.getX();
			int y = monster.getY();
			if (dir == "Right"){
				for(Node nod: Nodes){
					if ((nod.getX() == x+1) && (nod.getY() == y)){
						flag = false;
					}
				}
			}
			else if (dir == "Down"){
				for(Node nod: Nodes){
					if ((nod.getX() == x) && (nod.getY() == y+1)){
						flag = false;
					}
				}
			}
			else if (dir == "Left"){
				for(Node nod: Nodes){
					if ((nod.getX() == x-1) && (nod.getY() == y)){
						flag = false;
					}
				}
			}
			else if (dir == "Up"){
				for(Node nod: Nodes){
					if ((nod.getX() == x) && (nod.getY() == y-1)){
						flag = false;
					}
				}
			}
		}while(flag);	
		
	}
	
	/** Algorytm chodzenia do punktu. Potwor dostaje wspolrzedne na ktore ma isc, a nastepnie oblicza najkrotsza sciezke
	 * do tego miejsca za pomoca algorytmu BFS.
	 * @param monster Potwor dla ktorego zachodzi funkcja.
	 * @param waypointX Pierwsza wspolrzedna celu
	 * @param waypointY Druga wspolrzedna celu.
	 * @return Zwraca true/false w zaleznosci czy potwor juz dotarl do danego punktu. 
	 * @throws IndexOutOfBoundsException Probujemy sie dostac do elementu, ktorego nie ma w kolekcji.
	 */
	public boolean WaypointAI(Creep monster, int waypointX, int waypointY)throws IndexOutOfBoundsException{
		try{
			//Graph graph = monster.getGraph();
			List <Node> path = graph.BFS(monster.getX(), monster.getY(), waypointX, waypointY);
			int targetX = (path.get(0)).getX();
			int targetY = (path.get(0)).getY();
			int sourceX = monster.getX();
			int sourceY = monster.getY();
			if ((targetX > sourceX) && (targetY == sourceY)){
				if (ChangeDirectionAvailable("Right" ,monster)) monster.setDir("Right");
			}
			if ((targetX < sourceX) && (targetY == sourceY)){
				if (ChangeDirectionAvailable("Left" ,monster)) monster.setDir("Left");
			}
			if ((targetX == sourceX) && (targetY > sourceY)){
				if (ChangeDirectionAvailable("Down" ,monster)) monster.setDir("Down");
			}
			if ((targetX == sourceX) && (targetY < sourceY)){
				if (ChangeDirectionAvailable("Up" ,monster)) monster.setDir("Up");
			}
			return false;
		}catch(Exception e){
			if 		(!ChangeDirectionAvailable("Up" ,monster)) 		    monster.setDir("Up");
			else if (!ChangeDirectionAvailable("Down" ,monster))    	monster.setDir("Down");
			else if (!ChangeDirectionAvailable("Right" ,monster))	 	monster.setDir("Right");
			else if (!ChangeDirectionAvailable("Left" ,monster))	    monster.setDir("Left");
			return true;
		}
		
	}
	
	/** Zjedzenie monety. W momencie wystapienia kolizji gracza z moneta zostaja przyznane punkty graczowi,
	 * a nastepnie moneta znika z kolekcji monet.
	 * 
	 * W przypadku, gdy kolekcja monet jest pusta, pojawia sie powerUp nowej mapy na srodku.
	 * 
	 * Po zjedzeniu monety, odtwarza sie dzwiek zjedzenia monety.
	 * 
	 */
	public void eatCoins()
	{
		if(!collision(Player.getDir(),Player,coinPoints,1,1))
		{
			Player.setPoints(Player.getPoints()+1*scoreMultiplier);
			coinPoints.remove(eatenCoinId);
			coins.set_mapPoints(coinPoints);
			File Clap = new File("Resources/Sounds/wav/coin_eat.wav");
			PlaySound(Clap);
			if (coinPoints.isEmpty()){
				PowerUp powerUp = new PowerUp(12,9,"map");
				powerUpsList.add(powerUp);
			}
		}
	}
	/**Sprawdzenie kolizji miedzy potworem a graczem. Wystepuje iteracja po kolekcji potworow i jezeli 
	 * wystapila kolizja miedzy pozycja gracza, a potwora to zostaje odtworzony dzwiek smierci, zostaje odjete
	 * jedno zycie, a nastepnie powstaje reset mapy i wywolanie metody ChangeMap().
	 * 
	 * 
	 * 
	 */
	public void Ghost_Player_Collision_Check()
	{
		Set <Point> MonsterPositions=new HashSet<Point>();
		
		for(Creep monster : MonstersList)
		{
		Point tempPunkt=new Point(monster.getPixelsX()+monster.getHeight()/2,monster.getPixelsY()+monster.getHeight()/2,16,16);
		MonsterPositions.add(tempPunkt);
		//System.out.println(monster.getPixelsX()/32+" "+monster.getPixelsY()/32);
		}
		if(!collision2(Player.getDir(),Player,MonsterPositions,1,3))
		{
			Player.setLife(Player.getLife()-1);
			isGamePaused=true;
			//System.out.println("KONIEC");
			
			File Clap = new File("Resources/Sounds/wav/death.wav");
			PlaySound(Clap);
			if(Player.getLife()!=0)
			changeMap();
			else
				{
				System.out.print("KONIEC");
				isGamePaused=true;
				}
			
		}
		MonsterPositions.removeAll(MonsterPositions);
	}
	/**Sprawdzenie czy gracz zebral PowerUpa. Wystepuje iteracja po kolekcji powerUpow i jezeli 
	 * wystapila kolizja miedzy pozycja gracza, a bonusem to zostaje odtworzony dzwiek zebrania bonusu
	 * oraz bonus zostaje zdjety z kolekcji. Gracz otrzymuje podany bonus.
	 * 
	 */
	public void PowerUp_Player_Collision_Check()
	{
		Set <Point> PowerUpsPosition = new HashSet<Point>();
		for(PowerUp powerUp : powerUpsList)
		{
			Point tempPunkt=new Point(powerUp.getX(),powerUp.getY(),32,32);
			PowerUpsPosition.add(tempPunkt);
			
		}
		if(!collision(Player.getDir(), Player, PowerUpsPosition, 1,2))
		{	
			//System.out.println("Eaten:"+eatenPowerUp.getX()+" "+eatenPowerUp.getY());
			for(PowerUp powerUp : powerUpsList)
			{
				if(eatenPowerUp.getX()==powerUp.getX() && powerUp.getY()==eatenPowerUp.getY())
				{
					getPowered(powerUp);
					powerUpsList.remove(powerUp);
					File Clap = new File("Resources/Sounds/wav/bonus_eat.wav");
					PlaySound(Clap);
					break;
				}
				//System.out.println("PowerUp:"+powerUp.getX()+" "+powerUp.getY());
			}
			
		}
		
		
		powerUpsList.removeAll(PowerUpsPosition);
	}
	public void DestroyWall()
	{
		
	}
	/** Sprawdzenie czy pocisk trafil potwora. Pobierana jest lista potworow i sprawdzenie miejsca pocisku.
	 *  Jezeli sie pokrywaja to potwor oraz pocisk znikaja ze swoich kolekcji.
	 * 
	 */
	public void Ghost_Projectile_Collision_Check()
	{
		Set <Point> MonsterPositions=new HashSet<Point>();
		for(Creep monster : MonstersList)
		{
		Point tempPunkt=new Point(monster.getPixelsX()+monster.getHeight()/2,monster.getPixelsY()+monster.getHeight()/2,16,16);
		MonsterPositions.add(tempPunkt);
		
		}
		Set <Point> ProjectilesPositions = new HashSet<Point>();
		Set<Projectile> deleteProjectiles = new HashSet<Projectile>();
		Set <Creep> deletedMonsters= new HashSet<Creep>();
		for(Projectile projectile : ProjectilesList)
		{
			ProjectilesPositions.add(new Point(projectile.getPixelsX()+projectile.getHeight()/2, projectile.getPixelsY()+projectile.getHeight()/2, 16, 16));
			if(!collision2(projectile.getDir(), projectile, MonsterPositions, 1, 3))
			{
				
				for(Creep monster : MonstersList)
				{
					if(!collision2(monster.getDir(), monster, ProjectilesPositions, 1, 3))
					{
						if(!deletedMonsters.contains(monster))
						deletedMonsters.add(monster);
					}
				}
				deleteProjectiles.add(projectile);
				//
			}
		}
		for(Projectile projectile:deleteProjectiles)
		{
			ProjectilesList.remove(projectile);
		}
		
		for(Creep monster: deletedMonsters)
		{
			MonstersList.remove(monster);
		}
		
		MonsterPositions.removeAll(MonsterPositions);
	}
	
	/** Sprawdza ktory blok zostal uderzony przez pocisk.
	 * @param projectile Pocisk ktory zostaje poddany sprawdzeniu.
	 * @param destroyedWalls Zbior scian, z ktorymi zostaje sprawdzony pocisk.
	 */
	public void whichWallBlocks(Projectile projectile,Set <Wall> destroyedWalls)
	{
		//System.out.println(projectile.getPixelsX()+" "+projectile.getPixelsY());
		projectile.setX(projectile.getPixelsX()/32);
		projectile.setY(projectile.getPixelsY()/32);
		//System.out.println(projectile.getX()+" "+projectile.getY());
		Point tempPoint = null;
		switch(projectile.getDir())
		{
		case "Up":
			tempPoint=new Point(projectile.getX(), projectile.getY()-1, 32, 32);
			break;
		case "Down":
			tempPoint=new Point(projectile.getX(), projectile.getY()+1, 32, 32);
			break;
		case "Left":
			tempPoint=new Point(projectile.getX()-1, projectile.getY(), 32, 32);
			break;
		case "Right":
			tempPoint=new Point(projectile.getX()+1, projectile.getY(), 32, 32);	
			break;
		}
		//System.out.println(tempPoint.getX()+" "+tempPoint.getY());
		Point temp = null;
		for(Point punkt : mapPoints)
		{
			if(punkt.getX()==tempPoint.getX() && punkt.getY()==tempPoint.getY())
			{
				temp=punkt;
			}
		}
			
		for(Wall wall: wallsList)
		{
			if(wall.getPixelsX()==tempPoint.getX()*32 && wall.getPixelsY()==tempPoint.getY()*32)
			{	
				if(wall.isDestroyable){
				destroyedWalls.add(wall);
				if(temp!=null)
					mapPoints.remove(temp);
				}
			}
		}
		for(Wall wall:destroyedWalls)
		{	if(wall.isDestroyable)
		wallsList.remove(wall);
		}
		
		
	}
	
	/** Sprawdza czy wystapila kolizja miedzy zbiorek scian, a pociskiem.
	 * 
	 */
	public void Projectile_Wall_Collision_Check(){
		Set <Wall> destroyedWalls = new HashSet<Wall>();
		Set <Projectile> destroyedProjectiles = new HashSet<Projectile>();
		Set <Point> tempMapPoints= new HashSet<Point>();
		for(Point punkt:mapPoints)
		{
			tempMapPoints.add(new Point(punkt.getX()*32, punkt.getY()*32, 32, 32));
		}
		for(Projectile projectile : ProjectilesList)
		{
			if(projectile.getLastX() == projectile.getPixelsX() && projectile.getLastY()==projectile.getPixelsY()){
				
				//System.out.println("Collision");
				whichWallBlocks(projectile, destroyedWalls);
				destroyedProjectiles.add(projectile);
			}
		}
		for(Projectile projectile:destroyedProjectiles)
		{
			ProjectilesList.remove(projectile);
		}
		
		
	}
	
	/** Resetuje mape. Zbior potworow, scian, monet, pociskow zostaje zresetowany.
	 *  Zwieksza sie ilosc potworow o 2, a nastepnie wworzona jest mapa.
	 * 
	 */
	public void changeMap()
	{
		MonstersList.clear();
		wallsList.clear();
		coinPoints.clear();
		ProjectilesList.clear();
		Player.setPixelsX(10000);
		Player.setSpeed(4);
		MonstersQuantity +=2;
		SetVariables();
		
		
	}
	/** Dodaje do postaci Player umiejetnosci przekazane przez argument.
	 *  Zebranie Pocisku dodaje +1 mozliwosci strzalow.
	 *  Zebranie Globusa powoduje reset mapy.
	 *  Zebranie Baterii powoduje podwojenie predkosci gracza.
	 *  Zebranie Slimaka powoduje zmniejsze predkosci potworow dwukrotnie.
	 *  Zebranie X2 powoduje podwojenie zdobywanej ilosci punktow.
	 *  
	 *  Wszystko na okreslony czas, przez pole duration.
	 * @param power Przekazywany PowerUp, ktory ma zostac dodany graczowi.
	 */
	public void getPowered(PowerUp power)
	{
		switch (power.getPowerUpIndex())
		{
		case 1:
			Player.setLasersCounter(Player.getLasersCounter()+1);
		break;
		case 2:
			destroyedPowerUp.add(power);
			changeMap();
		break;
		case 3:
			if(Player.getSpeed()==playerSpeed)
			{	
				if(Player.getPixelsX()%(2*playerSpeed)!=0)
				Player.setPixelsX(Player.getPixelsX()+playerSpeed);
				if(Player.getPixelsY()%(2*playerSpeed)!=0)
					Player.setPixelsY(Player.getPixelsY()+playerSpeed);
				Player.setSpeed(Player.getSpeed()*2);
				Player.setPowerUpDuration(PowerUp.duration);
				
			}
			else {
				Player.setPowerUpDuration(Player.getPowerUpDuration()+PowerUp.duration);
			}
		break;
		case 4:
			for(Creep monster : MonstersList)
			{
				if(monster.getSpeed()==monsterSpeed)
				{
					monster.setSpeed(monster.getSpeed()/2);
					monster.setPowerUpDuration(PowerUp.duration);
				}
				else
					monster.setPowerUpDuration(monster.getPowerUpDuration()+PowerUp.duration);
			}
			break;
		case 5:
			scoreMultiplier=2;
			Player.setMultiplierDuration(Player.getMultiplierDuration()+PowerUp.duration);
			break;
		}
	}
	
	/** Strzal. Zostaje wystrzelony pocisk z pozycji gracza, jesli ma odpowiednia ilosc pociskow.
	 *  Po kazdorazowym wykonaniu funkcji, ilosc strzalow zmniejsza sie o 1.
	 * @param player Ktory gracz wykonuje strzal.
	 */
	public void Shot(Packman player)
	{
		if(player.getLasersCounter()>0)
		{
			ProjectilesList.add(new Projectile(player, player.getPixelsX(), player.getPixelsY()));
			player.setLasersCounter(player.getLasersCounter()-1);
		}
	}
	/** Odtwarza dzwiek w formacie .wav. Po podaniu typu File uzywa klasy AudioSystem i uruchamia dzwiek.
	 * @param Sound Obiekt dzwieku do odtworzenia.
	 */
	static void PlaySound(File Sound){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		}catch(Exception e){
			
		}
	}
	
	
	
	/** Konstruktor ramki gry. Nadaje jej tytul "Pacman".
	 * Ustawia wszystkie zmienne oraz ustawia wskaznik KeyListenera na this.
	 * Ustawia wszystkie zmienne gry.
	 * Powoluje do zycia nowy Timer, z odswiezaniem zadanym przez TimeInteval, akcja kodem do ActionListera.
	 * 
	 * W przechwytywaniu zdarzen dzialaja wszystkie stopery, sprawdzajace czas gry, muzyki oraz czasy trwania PowerUpow.
	 * 
	 * Dla kazdego potwora z kolekcji potworow liczony jest sposob poruszania sie i tryb.
	 * Wywolywana jest metoda move dla gracza, potworow i pociskow.
	 * Pod koniec sprawdza jest kazda istotna kolizja: z moneta, z potworem, ze sciana, pocisku z potworem, oraz czy moneta zostala zjedzona.
	 * 
	 * 
	 */
	public ObrazFrame() {	
		super("Pacman");
		addKeyListener(this);
		MonstersQuantity = 4;
		SetVariables();		

		timer = new Timer(TimeInterval,new ActionListener(){
				
					@Override
					public void actionPerformed(ActionEvent arg0) {	
						musicTimer++;
						if(musicFlag){
						if(!isGamePaused){
								Iterations++;
								Timing++;
								if(Timing==PowerUp.respawnTime)
								{
									if(coinPoints.size()!=0)
									addPowerUp();
									Timing=0;
								}
								if(Player.getPowerUpDuration()!=0)
								{
									Player.setPowerUpDuration(Player.getPowerUpDuration()-1);
								}
								else
								{
									Player.setSpeed(playerSpeed);
								}
								if(Player.getMultiplierDuration()!=0)
								{
									Player.setMultiplierDuration(Player.getMultiplierDuration()-1);
								}
								else
								{
									scoreMultiplier=1;
								}
								
							Move(Player);			
							int index = 0;
							for(Creep monster : MonstersList)
							{
								index=index%4;
								if ((monster.getMode()=="Guard") && (monster.getTime() <=10)){
									if (!monster.getReach()){
										switch(index){
											case 0:
												monster.setReach(WaypointAI(monster,5,3));
												break;
											case 1:
												monster.setReach(WaypointAI(monster,19,5));
												break;
											case 2:
												monster.setReach(WaypointAI(monster,3,16));
												break;
											case 3:
												monster.setReach(WaypointAI(monster,21,16));
												break;
										}
										
									}else{
										CrazyAI(monster);
									}
									if (monster.getTime()==10) {
										monster.setTime(0);
										monster.setMode("Chase");
										//System.out.println("CHAAAASEEE");
									}
	
								}
								if ((monster.getMode()=="Chase") && (monster.getTime() <=20)){
									WaypointAI(monster,Player.getX(),Player.getY());
									if (monster.getTime()==20) {
										monster.setTime(0);
										monster.setMode("Guard");
										monster.setReach(false);
										//System.out.println("GUAAAARD");
									}
									
									
								}
								Move(monster);
								
								if(monster.getPowerUpDuration()!=0)
								{
									monster.setPowerUpDuration(monster.getPowerUpDuration()-1);
								}
								else
								{
									monster.setSpeed(monsterSpeed);
								}
								index++;
								if (Iterations%32==0) {
									monster.setTime(monster.getTime()+1);
									//System.out.println(monster.getTime());
								}
							}
							for(Projectile bullet : ProjectilesList)
							{
								bullet.setLastX(bullet.getPixelsX());
								bullet.setLastY(bullet.getPixelsY());
								Move(bullet);
							}
							Ghost_Player_Collision_Check();
							PowerUp_Player_Collision_Check();
							Ghost_Projectile_Collision_Check();
							Projectile_Wall_Collision_Check();
							eatCoins();
							MonsterRespawnTimer();
							
						}
					}
					if(musicTimer==130)
						{musicFlag=true;
						isGamePaused=false;}
					//System.out.println(musicTimer);
					
					}
					
				
				});
				
				
		timer.start();
	}
	/**Jezeli jest mniej potworow niz powinno byc, sprawdzany jest czas przez jaki potwora nie ma. Jezeli dobije 500 obrotow petli Timera, dodaje potwora do kolekcji.
	 * 
	 */
	public void MonsterRespawnTimer() {
		if(MonstersList.size()<MonstersQuantity)
		{
			monsterRespawnTimer++;
			if(monsterRespawnTimer==500)
			{
				MonstersList.add(new Creep());
				monsterRespawnTimer=0;
			}
		}

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					//if(CheckCollision("Up"))
							
					Player.setNewDir("Up");
					break;
				case KeyEvent.VK_DOWN:
					//if(CheckCollision("Down"))
				
					Player.setNewDir("Down");
					break;
				case KeyEvent.VK_LEFT:
					//if(CheckCollision("Left"))
				
					Player.setNewDir("Left");
					break;
				case KeyEvent.VK_RIGHT:
					//if(CheckCollision("Right"))
				
					Player.setNewDir("Right");
					break;
				case KeyEvent.VK_P:
					isGamePaused=!isGamePaused;
					break;
				case KeyEvent.VK_SPACE:
					if(Player.getLife()==0){
						this.dispose();
						new MenuFrame();
					}
					Shot(Player);
					
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