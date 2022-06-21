import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class SpaceShooters extends JFrame{
	
	private static JFrame f;
	private Map map;
	public SpaceShooters() throws Exception {
		f = new JFrame();
		map = new Map();
		f.add(map);
		f.setSize(1280,720);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void disposeF() {
		f.dispose();
	}
	class Player{
		private int x;
		private int y;
		private int dx;
		private int dy;
		private boolean dead;
		private Image img;
		public Player(int x, int y) {
			this.x = x;
			this.y = y;
			dead = false;
		}
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
		public void move() {
			x += dx;
			y += dy;
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
		public void drawPlayer(Graphics g) {
			img = Images.getShip();
			g.drawImage(img, x, y, null);
		}

	}
	class Map extends JPanel implements ActionListener{
		private Player player;
		private static int score;
		private Timer obsTimer;
		private Timer playerTimer;
		private Obstacle[] arr;
		private int tickCnt, cnt, formCnt;
		public Map() throws Exception{
			Images.loadImages();
			player = new Player(0,0);
			score = 0;
			arr = new Obstacle[10];
			addKeyListener(new MyKeyListener());
			setFocusable(true);
			obsTimer = new Timer(800, this);
			playerTimer = new Timer(5, this);
			obsTimer.start();
			playerTimer.start();
			tickCnt = 0;
			cnt = 0;
			formCnt = 0;
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==playerTimer) {
				player.move();
				for(int i = 0; i<formCnt;i++) {
					arr[i].move();
				}
				tickCnt++;
				if(tickCnt%5==0) {
					score++;
				}
				try {
					detectCollision();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(e.getSource()==obsTimer) {
				arr[cnt%10] = new Obstacle(1200, Obstacle.genInt());
				if(tickCnt<500) {
					Obstacle.setDx(-2);
				}
				else if(tickCnt<1000) {
					Obstacle.setDx(-5);
				}
				cnt++;
				if(formCnt!=10) {
					formCnt++;
				}
			}
			repaint();
		}

		public void detectCollision() throws Exception {
			for(int i = 0; i<formCnt;i++) {
				if (new Rectangle(player.getX(), player.getY(), 52, 70).intersects(new Rectangle(arr[i].getX(), arr[i].getY(), 46, 20))) {
					Thread.sleep(1000);
					SpaceShooters.disposeF();
					new GameOver();
				}
			}


		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(Images.getBackground(), 0, 0, null);
			player.drawPlayer(g);
			for (int i = 0; i<formCnt;i++) {
				arr[i].drawObstacle(g);
			}
			g.drawString(String.valueOf(score), 700, 10);
		}

		class MyKeyListener extends KeyAdapter{//class to determine key events
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					player.setDy(3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_UP) {
					player.setDy(-3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					player.setDx(3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
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


	class Obstacle{
		private int x;
		private int y;
		private static int dx;
		private Image img;
		public Obstacle(int x, int y) {
			this.x = x;
			this.y = y;
		}
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
		public void move() {
			x+=dx;
		}
		public void drawObstacle(Graphics g) {
			img = Images.getRocket();
			g.drawImage(img, x, y, null);
		}
		public static int genInt() {
			return (int)(Math.random()*720);
		}
	}
	class Images{
		private static BufferedImage rocketImg, backgroundImg, shipImg;
		private static Image rocket, background, ship;
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


