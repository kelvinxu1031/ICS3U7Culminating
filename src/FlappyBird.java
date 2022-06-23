import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class FlappyBird extends JFrame{
	public static void main (String[] args) throws Exception {
		new FlappyBird();
	}
	private static JFrame f;
	private Map map;
	/**
	 * Constructor for Space Shooters JFrame
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
		public Player(int x, int y) {
			this.x = x;
			this.y = y;
			this.dy = 30;
			dead = false;
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
		public void move() {
			y-=dy;
			dy-=3;
		}
		public void drawPlayer(Graphics g) {
			img = Images.getFlappy();
			g.drawImage(img, x, y, null);
		}
		
	}
	class TopObstacle{
		private int x, y;
		private static int dx;
		public TopObstacle(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void move() {
			x-=dx;
		}
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
		
		public void drawObstacle(Graphics g) {
			g.drawImage(Images.getTop(), x, y, null);
		}
		public static int genY() {
			return (int)(Math.random()*-301);
		}
	}
	class BotObstacle{
		private int x, y;
		private static int dx;
		public BotObstacle(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void move() {
			x-=dx;
		}
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
		
		public void drawObstacle(Graphics g) {
			g.drawImage(Images.getBot(), x, y, null);
		}
	}
	class Map extends JPanel implements ActionListener{
		private static int score, rand;
		private JLabel lblScore;
		private int tickCnt;
		private Player player;
		private Timer timer;
		private Timer obsTimer;
		private TopObstacle[] topArr;
		private BotObstacle[] botArr;
		public boolean collide;
		private int cnt, formCnt;
		public Map() throws Exception {
			topArr = new TopObstacle[10];
			botArr = new BotObstacle[10];
			TopObstacle.setDx(25);
			BotObstacle.setDx(25);
			score = 0;
			tickCnt = 0;
			player = new Player(50,300);
			Images.loadImages();

			addKeyListener(new MyRunnerKeyListener());
			setFocusable(true);
			lblScore = new JLabel("Score: " + score);
			lblScore.setBounds(0, 0, 100, 25);
			this.add(lblScore);

			timer = new Timer(50, (ActionListener) this);
			timer.start();
			obsTimer = new Timer(2000, (ActionListener) this);
			obsTimer.start();
			
			cnt = 0;
			formCnt = 0;

		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(player.getX());
			System.out.println(player.getY());
			if(e.getSource() == obsTimer) {
				rand = TopObstacle.genY();
				TopObstacle obstacle = new TopObstacle(2000, rand);
				topArr[cnt%10] = obstacle;
				BotObstacle obstacle2 = new BotObstacle(2000, rand+600);
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
			repaint();
		}
		
		public void detectCollision() throws Exception {
			for (int i = 0; i<formCnt;i++) {
				collide = ((new Rectangle(player.getX(), player.getY(), 35, 33).intersects(new Rectangle(topArr[i].getX(), topArr[i].getY(),69, 400))|| (new Rectangle(player.getX(), player.getY(), 35, 33).intersects(new Rectangle(botArr[i].getX(), botArr[i].getY(),69, 400))) || player.getY()<0 || player.getY()>650));
				if (collide) {
					player.setDead(true);
					if (score>Integer.parseInt(Login.getRunner())) {
						Login.init();
						Login.setFlappyBird(String.valueOf(score));
						Login.saveUsers();
					}

				}
			}

		}
		class MyRunnerKeyListener extends KeyAdapter{//class to determine key events
			public void keyPressed(KeyEvent e) {
				if(player.getDead()) {

				}
				else if(e.getKeyChar() == ' ') {//space
					player.setDy(20);
					repaint();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
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
			if(player.getDead()) {

				try {
					Thread.sleep(1000);
					FlappyBird.disposeF();
					new GameOver();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
