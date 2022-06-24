import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
/**
 * DinosaurGame class to create JFrame for the game
 * @author Kelvin Xu
 *
 */
public class DinosaurGame  extends JFrame{
	private Map map;
	private static JFrame f;
	/**
	 * Constructor for DinosaurGame
	 */
	public DinosaurGame() throws Exception {
		f = new JFrame();//instantiate JFrame
		map = new Map();//instantiate JPanel
		f.add(map);
		f.setSize(1280,720);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	/**
	 * Method to dispose of JFrame
	 */
	public static void disposeF() {
		f.dispose();
	}
}
/**
 * Player class to set position of the player
 * @author Kelvin Xu
 *
 */
class Player{
	//current x and y coordinates
	private int x;
	private int y;
	//change in y
	private int dy;
	//booleans to set current condition of player
	private boolean jumping;
	private boolean dead;
	private boolean running;
	//count to determine which running image to use
	private int cnt = 0;
	private Image img;
	/**
	 * Constructor for player class
	 * @param x starting x-coordinate
	 * @param y starting y-coordinate
	 */
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		running = false;
		dead = false;
		jumping = false;
	}
	//Getter and setter methods
	public void setJumping(boolean b) {
		jumping = b;
	}
	public boolean getJumping() {
		return jumping;
	}
	public void setRunning(boolean b) {
		running = b;
	}
	public boolean getDead() {
		return dead;
	}
	public void setDead(boolean b) {
		dead = b;
	}
	public void setDy(int y) {
		dy = y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
	public int getX() {
		return x;
	}

	/**
	 * Method to move player
	 */
	public void move() {
		if(jumping) {

			y-=dy;//change y position of player
			dy-=5;//change the amount y changes

			if(y>=530) {//if dinosaur hit the ground
				setY(530);
				jumping = false;
				running = true;

			}
		}

	}
	/**
	 * Method to draw player
	 */
	public void myDraw(Graphics g) throws Exception{
		if(jumping) {//display jumping image
			img = Images.getJump();
		}
		else if(dead) {//display dead image
			img = Images.getDead();

		}
		else if(running) {//display running images
			if(cnt == 0) {
				img = Images.getRun()[cnt];
				cnt=1;
			}
			else {
				img = Images.getRun()[cnt];
				cnt=0;
			}
		}
		g.drawImage(img, x, y, null);
	}	
}
/**
 * Map class to create JPanel containing the game
 * @author Kelvin Xu
 *
 */
class Map extends JPanel implements ActionListener{
	private static int score;//keep track of score
	//JLabels
	private JLabel lblPause = new JLabel("PAUSED");
	private JLabel lblStart = new JLabel("PRESS SPACE TO START");
	private JLabel lblScore = new JLabel("SCORE: 0");
	private int tickCnt;//int variable to keep track of number of timer ticks
	private Player player;//create new player
	private Timer timer; // timer to update screen
	private Timer obsTimer; // timer to spawn obstacles
	private Obstacle[] arr; // array of obstacles
	private int[] picCnt; // array of int to determine which cactus image to display
	private int cnt = 0;
	private int formCnt = 0;
	public boolean collide;
	private static boolean paused, started;
	/**
	 * Constructor for Map class
	 */
	public Map() throws Exception {
		//initialize arrays
		arr = new Obstacle[10];
		picCnt = new int[10];
		//set Obstacle settings
		Obstacle.setDx(25);
		//set score and count to 0
		score = 0;
		tickCnt = 0;
		//instantiate player
		player = new Player(100,530);
		//load images for future use
		Images.loadImages();

		//addKeyListener to allow user to control the dinosaur
		addKeyListener(new MyRunnerKeyListener());
		setFocusable(true);
		//add score label
		lblScore.setBounds(0, 0, 100, 25);
		this.add(lblScore);

		//initialize timers
		timer = new Timer(50, (ActionListener) this);
		timer.start();
		obsTimer = new Timer(1000, (ActionListener) this);
		obsTimer.start();
		player.setRunning(true);

		
		paused = false;
		started = false;

	}
	/**
	 * Method to detect user input actions
	 */
	public void actionPerformed(ActionEvent e) {
		if(!paused) {//if paused: nothing gets updated(maintain the position of everything)
			if(e.getSource() == obsTimer) {//spawn obstacle
				Obstacle obstacle = new Obstacle(2000, 536);
				arr[cnt%10] = obstacle;
				picCnt[cnt%10] =(int)(Math.random()*3)+1;
				cnt++;
				if (cnt<10) {
					formCnt++;
				}
			}
			if(tickCnt>500) {
				Obstacle.setDx(50);//level 2 speed
			}
			tickCnt++;
			if(tickCnt%5==0 && !player.getDead()) {
				score+=1;//update score
			}
			try {
				detectCollision();//check to see if user has crashed into an obstacle
			} catch (Exception e1) {
				System.out.println(e1.getLocalizedMessage());
			}
			player.move();//update player location
			for (int i = 0; i<formCnt;i++) {//update cactus location
				arr[i].move();
			}
			repaint();//repaint the screen
		}
	}
	/**
	 * Method to detect collision
	 */
	public void detectCollision() throws Exception {
		for (int i = 0; i<formCnt;i++) {
			//check each obstacle individually 
			collide = (new Rectangle(player.getX(), player.getY(), 35, 33).intersects(new Rectangle(arr[i].getX(), arr[i].getY(),30, 30)));
			if (collide) {
				player.setDead(true);
				player.setRunning(false);
				player.setJumping(false);
				if (score>Integer.parseInt(Login.getRunner())) {//update score
					Login.init();
					Login.setRunner(String.valueOf(score));
					Login.saveUsers();
				}

			}
		}

	}


	//getter and setter methods
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
	/**
	 * Class containing key listener methods
	 * @author Kelvin Xu
	 *
	 */
	class MyRunnerKeyListener extends KeyAdapter{//class to determine key events
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()=='p') {//pause the game
				if(Map.getPaused()==true) {
					Map.setPaused(false);
				}
				else {
					Map.setPaused(true);
				}
			}
			else if(player.getJumping() || player.getDead()) {

			}

			else if(e.getKeyChar() == ' ') {//space

				if(!Map.getStarted()) {
					Map.setStarted(true);
				}
				if(player.getJumping()) {
					player.setJumping(false);
					player.setRunning(true);
				}
				else {

					player.setJumping(true);
					player.setDy(30);
					player.setRunning(false);
				}

			}
			repaint();
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	/**
	 * Method to update screen
	 */
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			//initialize font
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/textFont.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			//draw background
			g.drawImage(Images.getBackground(), 0, 0, null);
			updateLabel();
			lblScore.repaint();//draw label
			//draw obstacles
			for (int i = 0; i<formCnt;i++) {
				if(picCnt[i]==1) {
					arr[i].drawCactus1(g);
				}
				else if(picCnt[i]==2) {
					arr[i].drawCactus2(g);
				}
				else {
					arr[i].drawCactus3(g);
				}

			}

			//draw player
			try {
				player.myDraw(g);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//draw score
			lblScore.setText("SCORE: " + String.valueOf(score));
			lblScore.setBounds(1000,0,1200,50);
			lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
			lblScore.setForeground(Color.WHITE);
			lblScore.paint(g);
			if(player.getDead()) {//end game

				try {
					Thread.sleep(1000);
					DinosaurGame.disposeF();
					new GameOver();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//draw started and pause labels
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

	/**
	 * method to retrieve score
	 * @return score
	 */
	public static int getScore() {
		return score;
	}
	/**
	 * Method to update content of label
	 */
	public void updateLabel() {
		lblScore.setText("Score: " + score);
	}
}
/**
 * Obstacle class to determine location of obstacles
 * @author Kelvin Xu
 *
 */
class Obstacle{
	//current x and y coordinates
	private int x;
	private int y;
	//change in x
	private static int dx;
	/**
	 * Constructor for Obstacle class
	 * @param x starting x-coordinate
	 * @param y starting y-coordinate
	 */
	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Method to update obstacle location
	 */
	public void move() {
		x-=dx;
	}
	//getter and setter methods
	public static void setDx(int x) {
		dx = x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	//Methods to draw obstacles
	public void drawCactus1(Graphics g) {
		g.drawImage(Images.getCactus(), x, y, null);
	}
	public void drawCactus2(Graphics g) {
		g.drawImage(Images.getCactus2(), x, y, null);
	}
	public void drawCactus3(Graphics g) {
		g.drawImage(Images.getCactus3(), x, y, null);
	}

}

/**
 * Images class to load and get images
 * @author Kelvin Xu
 *
 */
class Images{
	private static BufferedImage img, cactusImg, cactusImg2, cactusImg3, backgroundImg;
	private static Image run1, run2, jump, cactus, cactus2, cactus3, dead, background;
	private static Image[] run = new Image[2];
	private static String strcactus = "images/cactus.png";
	/**
	 * Method to load images(initialize images)
	 */
	public static void loadImages() throws Exception {
		img = ImageIO.read(new File("images/dinosaurSprite.png"));
		backgroundImg = ImageIO.read(new File("images/dinosaurBackground.jpg"));
		cactusImg2 = ImageIO.read(new File("images/cactus2.png"));
		cactusImg3 = ImageIO.read(new File("images/cactus3.png"));
		background = backgroundImg;
		cactusImg = ImageIO.read(new File(strcactus));
		cactus = cactusImg;
		cactus2 = cactusImg2;
		cactus3 = cactusImg3;
		run1 = img.getSubimage(172, 0, 55, 56);
		run2 = img.getSubimage(114, 0, 55, 56);
		jump = img.getSubimage(0, 0, 55, 56);
		dead = img.getSubimage(285, 0, 56, 56);
		run[0]=run1;
		run[1]=run2;
	}
	//getter and setter methods
	public static Image getBackground() {
		return background;
	}
	public static Image getJump() {
		return jump;
	}
	public static Image[] getRun() {
		return run;
	}
	public static Image getCactus() {
		return cactus;
	}
	public static Image getCactus2() {
		return cactus2;
	}
	public static Image getCactus3() {
		return cactus3;
	}
	public static Image getDead() {
		return dead;
	}
}
