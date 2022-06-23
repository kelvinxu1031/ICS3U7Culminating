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
		private int difficulty;
		private Player player;//player object
		private static int score; // score
		private Timer obsTimer; //timer to track how often obstacles should be spawned
		private Timer playerTimer;//timer to track movement of player and obstacles
		private Obstacle[] arr, arr2, arr3;//array containing obstacles
		private int tickCnt, cnt, cnt2, cnt3, formCnt, formCnt2, formCnt3;//counters to keep track of progress in the game
		private static boolean paused, started;
		private JLabel lblPause = new JLabel("PAUSED");
		private JLabel lblStart = new JLabel("PRESS SPACE TO START");
		private JLabel lblScore = new JLabel("SCORE: 0");

		/**
		 * Constructor for Map class
		 */
		public Map() throws Exception{
			//load images for later use
			Images.loadImages();
			//instantiate player
			player = new Player(20,350);
			score = 0;// initialize score to 0
			arr = new Obstacle[20];//initialize Obstacle array
			arr2 = new Obstacle[20];
			arr3 = new Obstacle[20];
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
			//initialize difficulty to easy(0)
			difficulty = 0;
			paused = true;
			started = false;
		}
		/**
		 * Method to track user input actions
		 */
		public void actionPerformed(ActionEvent e) {
			if(!paused) {
				if(e.getSource()==playerTimer) {//if player timer goes off
					player.move();//update player location

					for(int i = 0; i<formCnt;i++) {//update all obstacles
						arr[i].move();
					}
					if (difficulty >= 1) {

						for(int i = 0; i<formCnt2;i++) {//update all obstacles
							arr2[i].move();
						}
					}
					if(difficulty >= 2) {
						for(int i = 0; i<formCnt3;i++) {//update all obstacles
							arr3[i].move();
						}
					}
					tickCnt++;//increment tick count
					if(tickCnt%25==0) {
						score++;//update score every 50 ticks
					}
					try {
						detectCollision();//test to see if user crashed into a missile
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				if(e.getSource()==obsTimer) {//if obstacle timer goes off
					arr[cnt%20] = new Obstacle(1300, Obstacle.genInt());//spawn new obstacle at random y-coordinate
					cnt++;//increment cnt
					if(formCnt!=20) {//keep formCnt under 10
						formCnt++;
					}
					if(difficulty>=1) {
						arr2[cnt2%20] = new Obstacle(1400, Obstacle.genInt());
						cnt2++;
						if(formCnt2!=20) {
							formCnt2++;
						}
					}
					if(difficulty>=2) {
						arr3[cnt3%20] = new Obstacle(1500, Obstacle.genInt());
						cnt3++;
						if(formCnt3!=20) {
							formCnt3++;
						}
					}
					if(tickCnt==10000) {
						formCnt2 = 0;
						cnt2 = 0;
					}
					if(tickCnt==15000) {
						formCnt3 = 0;
						cnt3 = 0;
					}
					if(tickCnt<2500) {//first speed level
						Obstacle.setDx(-2);
					}
					else if(tickCnt<5000) {//second speed level
						difficulty = 1;
						Obstacle.setDx(-5);
					}
					else if(tickCnt<7500) {//third speed level
						difficulty = 2;
						Obstacle.setDx(-8);
					}

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
					playerTimer.stop();
					obsTimer.stop();
					if (score>Integer.parseInt(Login.getAsteroids())) {//update user's personal score
						Login.init();
						Login.setAsteroids(String.valueOf(score));
						Login.saveUsers();
					}
				}
			}
			for(int i = 0; i<formCnt2;i++) {//go through obstacle array to check each rocket individually
				if (new Rectangle(player.getX()+5, player.getY()+5, 42, 60).intersects(new Rectangle(arr2[i].getX()+5, arr2[i].getY()+5, 36, 10))) {
					player.setDead(true);
					playerTimer.stop();
					obsTimer.stop();
					if (score>Integer.parseInt(Login.getAsteroids())) {//update user's personal score
						Login.init();
						Login.setAsteroids(String.valueOf(score));
						Login.saveUsers();
					}
				}
			}
			for(int i = 0; i<formCnt3;i++) {//go through obstacle array to check each rocket individually
				if (new Rectangle(player.getX()+5, player.getY()+5, 42, 60).intersects(new Rectangle(arr3[i].getX()+5, arr3[i].getY()+5, 36, 10))) {
					player.setDead(true);
					playerTimer.stop();
					obsTimer.stop();
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
			try {
				super.paintComponent(g);
				Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/textFont.ttf")).deriveFont(20f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);

				//draw background
				g.drawImage(Images.getBackground(), 0, 0, null);
				//draw player
				player.drawPlayer(g);
				//draw obstacles
				for (int i = 0; i<formCnt;i++) {
					arr[i].drawObstacle(g);
				}
				for (int i = 0; i<formCnt2;i++) {
					arr2[i].drawObstacle(g);
				}
				for (int i = 0; i<formCnt3;i++) {
					arr3[i].drawObstacle(g);
				}
				//draw score
				lblScore.setText("SCORE: " + String.valueOf(score));
				lblScore.setBounds(1000,0,1200,50);
				lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
				lblScore.setForeground(Color.WHITE);
				lblScore.paint(g);
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
				if(!started) {
					lblStart.setBounds(0,0,1280,720);
					lblStart.setVerticalAlignment(SwingConstants.CENTER);
					lblStart.setHorizontalAlignment(SwingConstants.CENTER);
					lblStart.setFont(font);
					lblStart.setForeground(Color.WHITE);
					lblStart.paint(g);

				}
				else if(paused) {
					lblPause.setBounds(0,0,1280,720);
					lblPause.setVerticalAlignment(SwingConstants.CENTER);
					lblPause.setHorizontalAlignment(SwingConstants.CENTER);
					lblPause.setFont(font);
					lblPause.setForeground(Color.WHITE);
					lblPause.paint(g);
				}

			}catch(Exception e1) {
				System.out.println(e1.getLocalizedMessage());
			}
		}
		public static void setPaused(boolean b)
		{
			paused = b;
		}
		public static boolean getPaused() {
			return paused;
		}
		public static boolean getStarted() {
			return started;
		}
		public static void setStarted(boolean b) {
			started = b;
		}
		class MyKeyListener extends KeyAdapter{//class to determine key events
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==32) {
					if(!Map.getStarted()) {
						Map.setStarted(true);
					}
					if(Map.getPaused()==true) {
						Map.setPaused(false);
					}
					else {
						Map.setPaused(true);
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN) {//down arrow
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
			img = Images.getAsteroid();
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
	class Images{//class containing methods to access images
		private static BufferedImage asteroidImg, backgroundImg, shipImg;
		private static Image asteroid, background, ship;
		/**
		 * method to initialize images
		 * @throws Exception
		 */
		public static void loadImages() throws Exception {
			asteroidImg = ImageIO.read(new File("images/asteroid.png"));
			shipImg = ImageIO.read(new File("images/spaceship.png"));
			backgroundImg = ImageIO.read(new File("images/spaceBackground.jpg"));
			asteroid = asteroidImg;
			ship = shipImg;
			background = backgroundImg;
		}
		public static Image getAsteroid() {
			return asteroid;
		}
		public static Image getShip() {
			return ship;
		}
		public static Image getBackground() {
			return background;
		}
	}

}


