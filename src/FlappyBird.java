import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
/**
 * This program contains the flappy bird game
 * @author Kelvin and Laiba
 *
 */
public class FlappyBird extends JFrame{
	private static JFrame f;
	private Map map;
	/**
	 * Constructor for Flappy Bird JFrame
	 */
	public FlappyBird() throws Exception {
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



	class Player{
		private int x, y, dy;
		private boolean dead;
		private Image img;
		/**
		 * Constructor for creating a new player
		 * @param x starting x-coordinate
		 * @param y starting y-coordinate
		 */
		public Player(int x, int y) {
			this.x = x;
			this.y = y;
			this.dy = 30;
			dead = false;
		}

		//getter and setter methods
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
		 * Method to update position of player
		 */
		public void move() {
			y-=dy;
			dy-=3;
		}
		/**
		 * Method to draw player onto the screen
		 */
		public void drawPlayer(Graphics g) {
			img = Images.getFlappy();
			g.drawImage(img, x, y, null);
		}

	}

	class TopObstacle{
		private int x, y;
		private static int dx;
		/**
		 * Constructor for TopObstacle
		 * @param x starting x-coordinate
		 * @param y starting y-coordinate
		 */
		public TopObstacle(int x, int y) {
			this.x = x;
			this.y = y;
		}
		/**
		 * Method to update location of top obstacle
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

		/**
		 * Method to draw obstacles
		 */
		public void drawObstacle(Graphics g) {
			g.drawImage(Images.getTop(), x, y, null);
		}
		/**
		 * Method to generate a random y-coordinate
		 * @return int between 0 and -300
		 */
		public static int genY() {
			return (int)(Math.random()*-301);
		}
	}

	class BotObstacle{
		private int x, y;
		private static int dx;
		/**
		 * Constructor for BotObstacle
		 * @param x starting x-coordinate
		 * @param y starting y-coordinate
		 */
		public BotObstacle(int x, int y) {
			this.x = x;
			this.y = y;
		}
		/**
		 * Method to update location of obstacle
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

		/**
		 * Method to draw obstacles
		 */
		public void drawObstacle(Graphics g) {
			g.drawImage(Images.getBot(), x, y, null);
		}
	}
	
	class Map extends JPanel implements ActionListener{
		private static int score, rand;
		private int tickCnt;
		private Player player;
		private Timer timer;
		private Timer obsTimer;
		private TopObstacle[] topArr;
		private BotObstacle[] botArr;
		public boolean collide;
		private int cnt, formCnt, botCnt;
		private static boolean paused, started;
		private JLabel lblPause = new JLabel("PAUSED");
		private JLabel lblStart = new JLabel("PRESS SPACE TO START");
		private JLabel lblScore = new JLabel("SCORE: 0");
/**
 * Constructor for Map class
 */
		public Map() throws Exception {
			//initialize arrays
			topArr = new TopObstacle[10];
			botArr = new BotObstacle[10];
			//set conditions for the obstacles
			TopObstacle.setDx(10);
			BotObstacle.setDx(10);
			
			//instantiate player
			player = new Player(50,300);
			//load images
			Images.loadImages();

			addKeyListener(new MyKeyListener());
			setFocusable(true);
			lblScore.setBounds(0, 0, 100, 25);
			this.add(lblScore);

			//start timers
			timer = new Timer(50, (ActionListener) this);
			timer.start();
			obsTimer = new Timer(1500, (ActionListener) this);
			obsTimer.start();

			//set counts to 0
			score = 0;
			tickCnt = 0;
			cnt = 0;
			formCnt = 0;
			botCnt = 0;

			paused = false;
			started = false;

		}

		/**
		 * Method for detecting user input actions as well as timer ticks
		 */
		public void actionPerformed(ActionEvent e) {
			if(!paused && started) {
				if(e.getSource() == obsTimer) {
					rand = TopObstacle.genY();
					TopObstacle obstacle = new TopObstacle(2000-botCnt, rand);
					topArr[cnt%10] = obstacle;
					BotObstacle obstacle2 = new BotObstacle(2000-botCnt, rand+600-botCnt);
					if(botCnt<50) {
						botCnt+=5;
					}
					botArr[cnt%10] = obstacle2;
					cnt++;
					if (cnt<10) {
						formCnt++;
					}
				}
				tickCnt++;
				if(tickCnt%5==0 && !player.getDead()) {
					score+=1;
				}
				try {
					detectCollision();
				} catch (Exception e1) {
					System.out.println(e1.getLocalizedMessage());
				}
				player.move();
				for (int i = 0; i<formCnt;i++) {
					topArr[i].move();
					botArr[i].move();
				}
			}
			repaint();
		}

		/**
		 * Method to detect collision between player and obstacles
		 * @throws Exception
		 */
		public void detectCollision() throws Exception {
			for (int i = 0; i<formCnt;i++) {
				collide = ((new Rectangle(player.getX(), player.getY(), 35, 33).intersects(new Rectangle(topArr[i].getX(), topArr[i].getY(),69, 400))|| (new Rectangle(player.getX(), player.getY(), 35, 33).intersects(new Rectangle(botArr[i].getX(), botArr[i].getY(),69, 400))) || player.getY()<0 || player.getY()>650));
				if (collide) {
					player.setDead(true);
					if (score>Integer.parseInt(Login.getRunner())) {//update score
						Login.init();
						Login.setFlappyBird(String.valueOf(score));
						Login.saveUsers();
					}

				}
			}

		}
		class MyKeyListener extends KeyAdapter{//class to determine key events
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()=='p') {
					if(Map.getPaused()==true) {
						Map.setPaused(false);
					}
					else {
						Map.setPaused(true);
					}
				}
				else if(e.getKeyChar() == ' ') {//space
					if(!Map.getStarted()) {
						Map.setStarted(true);
					}
					player.setDy(20);
					repaint();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		}
		public void paintComponent(Graphics g) {
			try {
				super.paintComponent(g);
				Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/textFont.ttf")).deriveFont(20f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(font);
				g.drawImage(Images.getBackground(), 0, 0, null);
				for (int i = 0; i<formCnt;i++) {
					topArr[i].drawObstacle(g);
					botArr[i].drawObstacle(g);

				}

				try {
					player.drawPlayer(g);
				} catch (Exception e) {
					e.printStackTrace();
				}
				lblScore.setText("SCORE: " + String.valueOf(score));
				lblScore.setBounds(1000,0,1200,50);
				lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
				lblScore.setForeground(Color.WHITE);
				lblScore.paint(g);
				if(player.getDead()) {

					try {
						Thread.sleep(1000);
						FlappyBird.disposeF();
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

	}

	class Images{
		private static BufferedImage flappyImg, topObsImg, botObsImg, backgroundImg;
		private static Image flappy, topObs, botObs, background;
		public static void loadImages() throws Exception {
			flappyImg = ImageIO.read(new File("images/flappyBird.png"));
			topObsImg = ImageIO.read(new File("images/topObs.png"));
			botObsImg = ImageIO.read(new File("images/botObs.png"));
			backgroundImg = ImageIO.read(new File("images/flappyBackground.jpg"));
			flappy = flappyImg;
			topObs = topObsImg;
			botObs = botObsImg;
			background = backgroundImg;
		}
		public static Image getFlappy() {
			return flappy;
		}
		public static Image getTop() {
			return topObs;
		}
		public static Image getBot() {
			return botObs;
		}
		public static Image getBackground() {
			return background;
		}
	}


}
