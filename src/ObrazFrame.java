import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.lang.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.HashSet;
import java.util.Random;
public class ObrazFrame extends JFrame implements KeyListener{
	Packman Player;
	Creep Monster;
	private BufferedImage imageWall,imagePlayer;
	private int ImagePixels;
	private JPanel obrazPanel;
	private Timer timer;
	private int TimeInterval;
	private Set <Point> mapPoints,coinPoints;
	private Set <Creep> MonstersList;
	private Map map,coins;
	private Point eatenCoinId;
	private int MonstersQuantity;
	private boolean isGamePaused;
	public void SetVariables()
	{	isGamePaused=true;
		ImagePixels=32;
		Player=new Packman();
		MonstersQuantity=4;
		MonstersList = new HashSet<Creep>();
		for(int i=0;i<MonstersQuantity;i++)
		{
		Monster = new Creep();
		MonstersList.add(Monster);
		}
		try {
			map= new Map(ImagePixels,ImagePixels,"Resources/Maps/map.txt");
			coins = new Map(ImagePixels,ImagePixels,"Resources/Maps/coins.txt");
			mapPoints=map.get_mapPoints();
			coinPoints=coins.get_mapPoints();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			obrazPanel = new ObrazPanel(Player,map,Monster,coins,MonstersList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		add(obrazPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		TimeInterval=1000/32;
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
	boolean collision2(String dir, Character figure,Set <Point> mapPoints,int offset)
	{
		
		for(Point punkt : mapPoints)
		{		
			
				
				if(inside2(figure.getPixelsX()+offset,figure.getPixelsY(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}
				
			
			
				
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+offset,punkt))
				{
				
					eatenCoinId=punkt;
					return false;
				}
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}	
				
			
			
				if(inside2(figure.getPixelsX()+offset,figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}
				if(inside2(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}	
			
			
				if(inside2(figure.getPixelsX(),figure.getPixelsY()+offset,punkt))
				{
				
					eatenCoinId=punkt;
					return false;
				}
				if(inside2(figure.getPixelsX(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
				
					eatenCoinId=punkt;
					return false;
				}
				
			
		}
		return true;
	
	}
	boolean collision(String dir, Character figure,Set <Point> mapPoints,int offset)
	{
		
		for(Point punkt : mapPoints)
		{		
			if (dir=="Up"){
				
				if(inside(figure.getPixelsX()+offset,figure.getPixelsY(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}
				
			}
			if (dir=="Right"){
				
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+offset,punkt))
				{
				
					eatenCoinId=punkt;
					return false;
				}
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}	
				
			}
			if (dir=="Down"){
				
				if(inside(figure.getPixelsX()+offset,figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}
				if(inside(figure.getPixelsX()+figure.getHeight(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
					
					eatenCoinId=punkt;
					return false;
				}	
			}
			if (dir=="Left"){
				
				if(inside(figure.getPixelsX(),figure.getPixelsY()+offset,punkt))
				{
				
					eatenCoinId=punkt;
					return false;
				}
				if(inside(figure.getPixelsX(),figure.getPixelsY()+figure.getHeight(),punkt))
				{
				
					eatenCoinId=punkt;
					return false;
				}
				
			}
		}
		return true;
	
	}
	public void Move(Character figure)
	{				
			switch(figure.getDir())
			{			
			case "Up":
				
				figure.setPixelsY(figure.getPixelsY()-figure.getSpeed());	
				
				if(!collision(figure.getDir(),figure,mapPoints,1)){
					
					figure.setPixelsY(figure.getPixelsY()+figure.getSpeed());	
				}
				if(figure.getPixelsY()==0)
					figure.setPixelsY(640);
				
				break;
			case "Down":
				
				figure.setPixelsY((figure.getPixelsY()+figure.getSpeed())%640);	
				if(!collision(figure.getDir(),figure,mapPoints,1)){
					
					figure.setPixelsY(figure.getPixelsY()-figure.getSpeed());
				}
				
				break;
			case "Right":
				
				figure.setPixelsX((figure.getPixelsX()+figure.getSpeed())%800);	
				if(!collision(figure.getDir(),figure,mapPoints,1)){
					figure.setPixelsX(figure.getPixelsX()-figure.getSpeed());	
				}
				
				
				break;
			case "Left":
				figure.setPixelsX(figure.getPixelsX()-figure.getSpeed());	
				if(!collision(figure.getDir(),figure,mapPoints,1)){
					figure.setPixelsX(figure.getPixelsX()+figure.getSpeed());	
				}
				if(figure.getPixelsX()==0)
					figure.setPixelsX(800);
				
				break;
			
			}
			if  (ChangeDirectionAvailable(figure.getNewDir(),figure)){
			//	Direction = NewDirection; 
				figure.setDir(figure.getNewDir());
			}
		
		
		
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
	public void eatCoins()
	{
		if(!collision(Player.getDir(),Player,coinPoints,1))
		{
			Player.setPoints(Player.getPoints()+1);
			coinPoints.remove(eatenCoinId);
			coins.set_mapPoints(coinPoints);
			System.out.println(Player.getPoints());
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
		if(!collision2(Player.getDir(),Player,MonsterPositions,1))
		{
			isGamePaused=true;
			System.out.println("KONIEC");
		}
		MonsterPositions.removeAll(MonsterPositions);
	}
	public ObrazFrame() {	
		super("Pacman");
		addKeyListener(this);
		SetVariables();		
				timer = new Timer(TimeInterval,new ActionListener(){
				
					@Override
					public void actionPerformed(ActionEvent arg0) {	
							if(!isGamePaused){
							
							Move(Player);
							for(Creep monster : MonstersList)
							{
							CrazyAI(monster);
							Move(monster);
							monster.UpdateCords();
							}
							Ghost_Player_Collision_Check();
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
			
			}
		
		//System.out.println(Player.dir);
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