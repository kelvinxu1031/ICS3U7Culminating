import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
/**
 * Author: Kelvin Xu
 * This program contains the space shooters game.
 */
public class SpaceShooters extends JFrame{
	public static void main (String[] args) throws Exception {
		new SpaceShooters();
	}
	private static JFrame f;
	private Map map;
	/**
	 * Constructor for Space Shooters JFrame
	 */
	public SpaceShooters() throws Exception {
		f = new JFrame();
		map = new Map();//new JPanel
		f.add(map);
		f.setSize(1280,720);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	/**
	 * Method to dispose of current JFrame
	 */
	public static void disposeF() {
		f.dispose();
	}
	//player class used for determining location and direction of player
	class Player{
		//current coordinates
		private int x;
		private int y;
		//change in x and y
		private int dx;
		private int dy;
		private boolean dead;
		private Image img;

		/**
		 * Constructor for Player class
		 * @param x initial x-coordinate of player
		 * @param y initial y-coordinate of player
		 */
		public Player(int x, int y) {
			this.x = x;
			this.y = y;
			dead = false;
		}

		//getter and setter methods
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public boolean getDead() {
			return dead;
		}
		public int getDy() {
			return dy;
		}
		public int getDx() {
			return dx;
		}
		public void setX(int x) {
			this.x = x;
		}
		public void setY(int y) {
			this.y = y;
		}
		public void setDead(boolean b) {
			dead = b;
		}
		public void setDx(int dx) {
			this.dx = dx;
		}
		public void setDy(int dy) {
			this.dy = dy;
		}

		/**
		 * Method to move the player
		 */
		public void move() {
			setX(x+dx);
			setY(y+dy);
			//edge cases: If player is bordering the edge of the screen
			if(x<0) {
				x = 0;
			}
			if (y<0) {
				y = 0;
			}
			if(y>650) {
				y = 650;
			}
			if(x>1200) {
				x = 1200;
			}
		}

		/**
		 * Method to draw the player
		 * @param g
		 */
		public void drawPlayer(Graphics g) {
			img = Images.getShip(); //get image from Images class
			g.drawImage(img, getX(), getY(), null);//draw image at current x and y coordinates
		}
	}
	//map class: JPanel containing the content of the game (interaction between player and obstacles)
	class Map extends JPanel implements ActionListener{
		private Player player;//player object
		private static int score; // score
		private Timer obsTimer; //timer to track how often obstacles should be spawned
		private Timer playerTimer;//timer to track movement of player and obstacles
		private Obstacle[] arr;//array containing obstacles
		private int tickCnt, cnt, formCnt;//counters to keep track of progress in the game
		/**
		 * Constructor for Map class
		 */
		public Map() throws Exception{
			//load images for later use
			Images.loadImages();
			//instantiate player
			player = new Player(20,350);
			score = 0;// initialize score to 0
			arr = new Obstacle[10];//initialize Obstacle array
			addKeyListener(new MyKeyListener());//add key listener to allow user to control the spaceship
			setFocusable(true);
			obsTimer = new Timer(800, this); //spawn obstacle every 0.8 seconds(800 ms)
			playerTimer = new Timer(5, this);//update screen every 5 ms
			//start timers
			obsTimer.start();
			playerTimer.start();
			//initialize counts to 0
			tickCnt = 0;
			cnt = 0;
			formCnt = 0;
		}
		/**
		 * Method to track user input actions
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==playerTimer) {//if player timer goes off
				player.move();//update player location

				for(int i = 0; i<formCnt;i++) {//update all obstacles
					arr[i].move();
				}
				tickCnt++;//increment tick count
				if(tickCnt%50==0) {
					score++;//update score every 50 ticks
				}
				try {
					detectCollision();//test to see if user crashed into a missile
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			if(e.getSource()==obsTimer) {//if obstacle timer goes off
				arr[cnt%10] = new Obstacle(1200, Obstacle.genInt());//spawn new obstacle at random y-coordinate
				if(tickCnt<5000) {//first speed level
					Obstacle.setDx(-2);
				}
				else if(tickCnt<10000) {//second speed level
					Obstacle.setDx(-5);
				}
				else if(tickCnt<15000) {//third speed level
					Obstacle.setDx(-8);
				}
				cnt++;//increment cnt
				if(formCnt!=10) {//keep formCnt under 10
					formCnt++;
				}
			}
			repaint();//update screen
		}

		/**
		 * method to check if player and rocket collide
		 */
		public void detectCollision() throws Exception {
			for(int i = 0; i<formCnt;i++) {//go through obstacle array to check each rocket individually
				if (new Rectangle(player.getX()+5, player.getY()+5, 42, 60).intersects(new Rectangle(arr[i].getX()+5, arr[i].getY()+5, 36, 10))) {
					player.setDead(true);
					if (score>Integer.parseInt(Login.getAsteroids())) {//update user's personal score
						Login.init();
						Login.setAsteroids(String.valueOf(score));
						Login.saveUsers();
					}
				}
			}


		}

		/**
		 * Method to update screen
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//draw background
			g.drawImage(Images.getBackground(), 0, 0, null);
			//draw player
			player.drawPlayer(g);
			//draw obstacles
			for (int i = 0; i<formCnt;i++) {
				arr[i].drawObstacle(g);
			}
			//draw score
			g.drawString("SCORE: " + String.valueOf(score), 1000, 20);
			if(player.getDead()) {//if player collided with rocket
				try {
					Thread.sleep(1000);//brief 1 second pause
					//switch screens
					SpaceShooters.disposeF();
					new GameOver();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		class MyKeyListener extends KeyAdapter{//class to determine key events
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {//down arrow
					player.setDy(3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_UP) {//up arrow
					player.setDy(-3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {//right arrow
					player.setDx(3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT) {//left arrow
					player.setDx(-3);
				}

			}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					player.setDy(0);
				}
				else if(e.getKeyCode()==KeyEvent.VK_UP) {
					player.setDy(0);
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					player.setDx(0);
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					player.setDx(0);
				}
			}
			public void keyTyped(KeyEvent e) {}
		}
	}


	class Obstacle{//class for rockets
		//current location
		private int x;
		private int y;
		//change in x (no change in y as there is no vertical movement of rockets)
		private static int dx;
		private Image img;
		/**
		 * Constructor for obstacle class
		 * @param x starting x-coordinate
		 * @param y starting y-coordinate
		 */
		public Obstacle(int x, int y) {
			this.x = x;
			this.y = y;
		}
		//getter and setter methods
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getDx() {
			return dx;
		}
		public void setX(int x) {
			this.x = x;
		}
		public void setY(int y) {
			this.y = y;
		}
		public static void setDx(int dx) {
			Obstacle.dx = dx;
		}
		/**
		 * Method to move rockets
		 */
		public void move() {
			x+=dx;
		}
		/**
		 * Method to draw obstacles
		 * @param g
		 */
		public void drawObstacle(Graphics g) {
			img = Images.getRocket();
			g.drawImage(img, x, y, null);//draw image at current x and y location
		}
		/**
		 * method that generates a random y-coordinate
		 * @return random number between 0 and 720 inclusive
		 */
		public static int genInt() {
			return (int)(Math.random()*720);
		}
	}
	class Images{//class containing methods to access iamges
		private static BufferedImage rocketImg, backgroundImg, shipImg;
		private static Image rocket, background, ship;
		/**
		 * method to initialize images
		 * @throws Exception
		 */
		public static void loadImages() throws Exception {
			rocketImg = ImageIO.read(new File("images/rocket.png"));
			shipImg = ImageIO.read(new File("images/spaceship.png"));
			backgroundImg = ImageIO.read(new File("images/spaceBackground.jpg"));
			rocket = rocketImg;
			ship = shipImg;
			background = backgroundImg;
		}
		public static Image getRocket() {
			return rocket;
		}
		public static Image getShip() {
			return ship;
		}
		public static Image getBackground() {
			return background;
		}
	}

}


