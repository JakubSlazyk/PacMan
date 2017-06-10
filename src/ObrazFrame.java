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


import java.util.List;
import java.util.HashSet;
import java.util.Random;
public class ObrazFrame extends JFrame implements KeyListener{
	Packman Player;
	Creep Monster;
	private BufferedImage imageWall,imagePlayer;
	private int monsterSpeed;
	private int playerSpeed;
	private int ImagePixels;
	private JPanel obrazPanel;
	private Timer timer;
	private int TimeInterval;
	private Set <Point> mapPoints,coinPoints,powerUpPoints;
	private Set <Creep> MonstersList;
	private Set <Projectile> ProjectilesList;
	private Set <PowerUp> powerUpsList;
	private Set <Wall> wallsList;
	private Set <Point> tempDestroyedWalls;
	private Map map,coins;
	//private Graph graph;
	private int Timing;
	private int GameTime;
	private long Iterations;
	private Point eatenCoinId;
	private Point eatenPowerUp;
	private int MonstersQuantity;
	private boolean isGamePaused;
	private int scoreMultiplier;
	public void SetVariables()
	{	
		tempDestroyedWalls=new HashSet<Point>();
		scoreMultiplier=1;
		Timing=0;
		isGamePaused=true;
		ImagePixels=32;
		Player=new Packman();
		playerSpeed=Player.getSpeed();
		MonstersQuantity=4;
		MonstersList = new HashSet<Creep>();
		powerUpsList = new HashSet<PowerUp>();
		ProjectilesList = new HashSet<Projectile>();
		

		try {
			map= new Map(ImagePixels,ImagePixels,"Resources/Maps/map.txt");
			coins = new Map(ImagePixels,ImagePixels,"Resources/Maps/coins.txt");
			mapPoints=map.get_mapPoints();
			coinPoints=coins.get_mapPoints();
			//graph = new Graph(coins);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<MonstersQuantity;i++)
		{
			//graph = new Graph(coins);
			Monster = new Creep(coins);
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
		add(obrazPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		TimeInterval=1000/32;
		PowerUp.respawnTime=TimeInterval*5;
		PowerUp.duration=TimeInterval*5;
		System.out.println(PowerUp.respawnTime);
		setBackground(Color.black);
		setVisible(true);
		
		
	}
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
	public void addPowerUp()
	{
		int random=new Random().nextInt(coinPoints.size());
		int i=0;
		int x=0,y=0;
		for(Point point: coinPoints)
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
		//System.out.println("Spawn power up :"+powerUp.getPowerUpIndex());
	}
	public void deletePowerUp(PowerUp power)
	{
		powerUpsList.remove(power);
	}
	
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
	public void CrazyAI(Creep monster){
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
		
				
		
		
		
		
	}
	public boolean WaypointAI(Creep monster, int waypointX, int waypointY)throws IndexOutOfBoundsException{
		try{
			Graph graph = monster.getGraph();
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
		}catch(IndexOutOfBoundsException e){
			if 		(!ChangeDirectionAvailable("Up" ,monster)) 		    monster.setDir("Up");
			else if (!ChangeDirectionAvailable("Down" ,monster))    	monster.setDir("Down");
			else if (!ChangeDirectionAvailable("Right" ,monster))	 	monster.setDir("Right");
			else if (!ChangeDirectionAvailable("Left" ,monster))	    monster.setDir("Left");
			return true;
		}
		
	}
	public void eatCoins()
	{
		if(!collision(Player.getDir(),Player,coinPoints,1,1))
		{
			Player.setPoints(Player.getPoints()+1*scoreMultiplier);
			coinPoints.remove(eatenCoinId);
			coins.set_mapPoints(coinPoints);
			//System.out.println(Player.getPoints());
		}
	}
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
			//isGamePaused=true;
			//System.out.println("KONIEC");
		}
		MonsterPositions.removeAll(MonsterPositions);
	}
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
			System.out.println("Eaten:"+eatenPowerUp.getX()+" "+eatenPowerUp.getY());
			for(PowerUp powerUp : powerUpsList)
			{
				if(eatenPowerUp.getX()==powerUp.getX() && powerUp.getY()==eatenPowerUp.getY())
				{
					getPowered(powerUp);
					powerUpsList.remove(powerUp);
					break;
				}
				System.out.println("PowerUp:"+powerUp.getX()+" "+powerUp.getY());
			}
			
		}
		
		
		powerUpsList.removeAll(PowerUpsPosition);
	}
	public void DestroyWall()
	{
		
	}
	public void Ghost_Projectile_Collision_Check()
	{
		Set <Point> MonsterPositions=new HashSet<Point>();
		for(Creep monster : MonstersList)
		{
		Point tempPunkt=new Point(monster.getPixelsX()+monster.getHeight()/2,monster.getPixelsY()+monster.getHeight()/2,16,16);
		MonsterPositions.add(tempPunkt);
		
		}
		Set <Point> ProjectilesPositions = new HashSet<Point>();

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
				ProjectilesList.remove(projectile);
			}
		}
		for(Creep monster: deletedMonsters)
		{
			MonstersList.remove(monster);
		}
		
		MonsterPositions.removeAll(MonsterPositions);
	}
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
			if(!collision2(projectile.getDir(), projectile, tempMapPoints, 1, 4))
			{
				
				destroyedProjectiles.add(projectile);
				System.out.println("Collision");
			}
		}
		for(Point punkt : tempDestroyedWalls)
		{
			
			
			for(Wall wall:wallsList)
			{
				if(wall.getX()==punkt.getX() && wall.getY()==punkt.getY() && wall.isDestroyable)
					{destroyedWalls.add(wall);
					mapPoints.remove(punkt);}
			}
		}
		for(Wall wall:destroyedWalls)
		{
			if(wall.isDestroyable)
			wallsList.remove(wall);
		}
		tempDestroyedWalls.clear();
		for(Projectile projectile:destroyedProjectiles)
		{
			ProjectilesList.remove(projectile);
		}
		
		
	}
	public void changeMap()
	{
		
	}
	public void getPowered(PowerUp power)
	{
		switch (power.getPowerUpIndex())
		{
		case 1:
			Player.setLasersCounter(Player.getLasersCounter()+1);
		break;
		case 2:
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
	
	public void Shot(Packman player)
	{
		if(player.getLasersCounter()>0)
		{
			ProjectilesList.add(new Projectile(player, player.getPixelsX(), player.getPixelsY()));
			player.setLasersCounter(player.getLasersCounter()-1);
		}
	}
	
	
	public ObrazFrame() {	
		super("Pacman");
		addKeyListener(this);
		SetVariables();		

		timer = new Timer(TimeInterval,new ActionListener(){
				
					@Override
					public void actionPerformed(ActionEvent arg0) {	
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
								if ((monster.getMode()=="Guard") && (monster.getTime() <=10)){
									if (!monster.getReach()){
										switch(index){
											case 0:
												monster.setReach(WaypointAI(monster,1,1));
												break;
											case 1:
												monster.setReach(WaypointAI(monster,23,1));
												break;
											case 2:
												monster.setReach(WaypointAI(monster,23,18));
												break;
											case 3:
												monster.setReach(WaypointAI(monster,1,18));
												break;
										}
										
									}else{
										CrazyAI(monster);
									}
									if (monster.getTime()==10) {
										monster.setTime(0);
										monster.setMode("Chase");
										System.out.println("CHAAAASEEE");
									}
	
								}
								if ((monster.getMode()=="Chase") && (monster.getTime() <=10)){
									WaypointAI(monster,Player.getX(),Player.getY());
									if (monster.getTime()==10) {
										monster.setTime(0);
										monster.setMode("Guard");
										monster.setReach(false);
										System.out.println("GUAAAARD");
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
									System.out.println(monster.getTime());
								}
							}
							for(Projectile bullet : ProjectilesList)
							{
								Move(bullet);
							}
							Ghost_Player_Collision_Check();
							PowerUp_Player_Collision_Check();
							Ghost_Projectile_Collision_Check();
							Projectile_Wall_Collision_Check();
							eatCoins();
							
							
						}
					}
					
					
				});
				
				
		timer.start();
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