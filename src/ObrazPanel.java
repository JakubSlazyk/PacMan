import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**Klasa z teksturami kazdego obiektu wykorzystywanego w grze. 
 * Zapisuje pozycje wystepowania danych obiektow i rysuje je w opdpowiendim miejscu.
 * 
 *
 */
public class ObrazPanel extends JPanel{
	private static int blockPixels=32;
	private static int playerPixels=32;
	private BufferedImage imageWall,imagePlayer,imageMonster,imageCoin,imageProjectile;
	private Set <Point> mapPoints;
	private Set <Point> mapCoins;
	private Set <Projectile> mapProjectiles;
	private Packman Player;
	private Creep Monster;
	private Map map,coins;
	private Set <Creep> MonstersList;
	private Set <PowerUp> powerUpsList;
	/** Wrzuca do mapy wspolrzedne z punktow blokow. Na te wspolrzedne nanoszony jest obraz blokow.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * @throws FileNotFoundException Wrzycany wyjatek jesli nie znajdzie pliku.
	 */
	public void drawMap(Graphics g) throws FileNotFoundException
	{	Graphics2D g2d = (Graphics2D) g;
		
		mapPoints=map.get_mapPoints();
		for(Point punkt : mapPoints)
		{
			g2d.drawImage(imageWall, blockPixels*punkt.getX(), blockPixels*punkt.getY(), this);
		}
		
	}
	/** Wrzuca do mapy wspolrzedne z punktow monet. Na te wspolrzedne nanoszony jest obraz monet.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * @throws FileNotFoundException Wrzycany wyjatek jesli nie znajdzie pliku.
	 */
	public void drawCoins(Graphics g) throws FileNotFoundException
	{	Graphics2D g2d = (Graphics2D) g;
		
		mapCoins=coins.get_mapPoints();
	
		for(Point punkt : mapCoins)
		{
			g2d.drawImage(imageCoin, blockPixels*punkt.getX(), blockPixels*punkt.getY(), this);
		}
		
	}
	/** Ustawia teksture pacmana w zaleznosci od jego pozycji pixeli oraz kierunku.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * 
	 */
	public void drawPackman(Graphics g) 
	{
		int x;
		Graphics2D g2d = (Graphics2D) g;
		File tempPacman = null;;
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
	/** Ustawia teksturepotwora w zaleznosci od jego jego.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * 
	 */
	public void drawMonster(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		File tempCreep;
		switch(Monster.getDir())
		{
			case "Up":
				tempCreep=new File("Resources/Img/ghost.png");
				try {
					imageMonster = ImageIO.read(tempCreep);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Down":
				tempCreep=new File("Resources/Img/ghost2.png");
				try {
					imageMonster = ImageIO.read(tempCreep);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Left":
				tempCreep=new File("Resources/Img/ghost.png");
				try {
					imageMonster = ImageIO.read(tempCreep);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Right":
				tempCreep=new File("Resources/Img/ghost2.png");
				try {
					imageMonster = ImageIO.read(tempCreep);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
		}
		g2d.drawImage(imageMonster,Monster.getPixelsX(),Monster.getPixelsY(), this);
		
	}
	
	/** Konstruktor. Przyjmuje za parametry obiekty potrzebne do gry, i kopiuje je do swoich pol, w celu dogrania im tekstur.
	 * @param Player Obiekt gracza.
	 * @param map Mapa, gdzie ulokowane sa sciany.
	 * @param Monster Obiekt potwora.
	 * @param coins Mapa, gdzie ulokowane sa monety
	 * @param MonstersList Kolekcja potworow.
	 * @param powerUpsList Kolekcja bonusow. 
	 * @param mapProjectiles Kolekcja strzlakow.
	 * @throws FileNotFoundException W przypadku nie znalezienia pliku.
	 */
	public ObrazPanel(Packman Player,Map map, Creep Monster,Map coins,Set <Creep> MonstersList,Set<PowerUp> powerUpsList,Set <Projectile> mapProjectiles) throws FileNotFoundException {
		super();
		this.Player = Player;
		this.mapProjectiles=mapProjectiles;
		this.map=map;
		this.coins=coins;
		this.Monster = Monster;
		this.MonstersList=MonstersList;
		this.powerUpsList=powerUpsList;
		File imageCoinFile = new File("Resources/Img/berry.png");
		File imageWallFile = new File("Resources/Img/Blue.png");
		File imagePlayerFile = new File("Resources/Img/Pacman45R.png");
		File imageMonsterFile = new File("Resources/Img/creep_right.png");
		File imageProjectileFile = new File("Resources/Img/PowerUps/ProjectileUp.png");
		try {
			imageWall = ImageIO.read(imageWallFile);
			imagePlayer = ImageIO.read(imagePlayerFile);
			imageMonster = ImageIO.read(imageMonsterFile);
			imageCoin = ImageIO.read(imageCoinFile);
			imageProjectile = ImageIO.read(imageProjectileFile);
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
		}
		
		Dimension dimension = new Dimension(1000,640);
		setPreferredSize(dimension);
		
	}

	/** Rysuje wynik, zmienia postac komputerowa na obrazkowa
	 * @param scoreLength Dlugosc napisu.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 */
	public void drawScore(int scoreLength,Graphics g)
	{	
		Graphics2D g2d = (Graphics2D) g;
		int temp=Player.getPoints();
		int wykl=(int) Math.pow(10, (double)scoreLength-1);
		int modulo=1;
		
		for(int i=0;i<scoreLength;i++)
		{
		int number=(temp/wykl)%10;
		
		wykl/=10;
		
		File imageNumberFile = null;
		
			switch(number)
			{
			case 0:
				imageNumberFile = new File("Resources/Img/Numbers/0.png");
				break;
			case 1:
				imageNumberFile = new File("Resources/Img/Numbers/1.png");
				break;
			case 2:
				imageNumberFile = new File("Resources/Img/Numbers/2.png");
				break;
			case 3:
				imageNumberFile = new File("Resources/Img/Numbers/3.png");
				break;
			case 4:
				imageNumberFile = new File("Resources/Img/Numbers/4.png");
				break;
			case 5:
				imageNumberFile = new File("Resources/Img/Numbers/5.png");
				break;
			case 6:
				imageNumberFile = new File("Resources/Img/Numbers/6.png");
				break;
			case 7:
				imageNumberFile = new File("Resources/Img/Numbers/7.png");
				break;
			case 8:
				imageNumberFile = new File("Resources/Img/Numbers/8.png");
				break;
			case 9:
				imageNumberFile = new File("Resources/Img/Numbers/9.png");
				break;
			}
		
			try {
				BufferedImage imageNumber = ImageIO.read(imageNumberFile);
				g2d.drawImage(imageNumber, 865+(i*15), 75, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	/** Rysuje GUI po prawej stronie ekranu, w ktorym znajduja sie zycia oraz ilosc punktow gracza.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 */
	public void drawGUI(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(8));
		g2d.drawLine(804, 0, 804, 640);
		g2d.drawLine(804, 4, 996, 4);
		g2d.drawLine(996, 4, 996, 636);
		g2d.drawLine(801, 636, 996, 636);
		
		//////////
		File imageScoreFile = new File("Resources/Img/score.png");
		File lifeFile =new File("Resources/Img/Pacman45R.png");
		try {
			BufferedImage imageScore = ImageIO.read(imageScoreFile);
			BufferedImage life = ImageIO.read(lifeFile);
			if (Player.getLife() == 3)  g2d.drawImage(life, 850, 120, this);
			if (Player.getLife() >= 2)  g2d.drawImage(life, 884, 120, this);
			if (Player.getLife() >= 1)  g2d.drawImage(life, 918, 120, this);
			g2d.drawImage(imageScore, 848, 50, this);
			drawScore(4,g);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/** Metoda pobierajaca plik z obrazem pocisku a nastepnie go rysujaca.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * @param powerUp powerUP ktory ma byc narysowany.
	 */
	public void drawPowerUp(Graphics g,PowerUp powerUp)
	{
		Graphics2D g2d = (Graphics2D) g;
		try {
			BufferedImage imagePowerUp = ImageIO.read(powerUp.getPowerUpFile());
			g2d.drawImage(imagePowerUp, powerUp.getPixelsX(), powerUp.getPixelsY(), this);	
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	/** Wywoluje metode rysowania PowerUp na kazdym bonusie w kolekcji.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * 
	 */
	public void drawPowerUps(Graphics g) {
		
			for(PowerUp powerUp: powerUpsList)
			{
				drawPowerUp(g, powerUp);
			}

	}
	@Override
	public void paintComponent(Graphics g) {
		try {
			drawCoins(g);
			drawMap(g);
			drawPackman(g);
			drawGUI(g);
			drawPowerUps(g);
			for(Creep monster : MonstersList)
			{
			Monster=monster;
			drawMonster(g);
			}
			drawProjetciles(g);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** Ustawia teksture pocisku w zaleznosci od jego kierunku. Wywoluje sie dla kazdego pocisku, w kolekcji.
	 * @param g Moze byc rozumiany jako obiekt urzadzenia wyjsciowego, tworzony samoistnie.
	 * 
	 */
	public void drawProjetciles(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		File imageProjectileFile = null;
		for(Projectile bullet: mapProjectiles)
		{
			switch (bullet.getDir()) {
			case "Left":
				imageProjectileFile = new File("Resources/Img/PowerUps/ProjectileLeft.png");
				break;

			case "Right":
				imageProjectileFile = new File("Resources/Img/PowerUps/ProjectileRight.png");
				break;
				
			case "Up":
				imageProjectileFile = new File("Resources/Img/PowerUps/ProjectileUp.png");
				break;
				
			case "Down":
				imageProjectileFile = new File("Resources/Img/PowerUps/ProjectileDown.png");
				break;
			}
			
			try {
				imageProjectile = ImageIO.read(imageProjectileFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g2d.drawImage(imageProjectile, bullet.getPixelsX(), bullet.getPixelsY(), this);
		}
	}
	
	
}