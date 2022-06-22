import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class AstronautGame  extends JFrame{
	private Map map;
	private static JFrame f;
	public AstronautGame() throws Exception {
		f = new JFrame();
		map = new Map();
		f.add(map);
		f.setSize(1920,1080);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void disposeF() {
		f.dispose();
	}
}

class Player{
	private int x;
	private int y;
	private int dy;
	private boolean jumping;
	private boolean dead;
	private boolean running;
	private boolean start;
	private int cnt = 0;
	private Image img;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		start = false;
		running = false;
		dead = false;
		jumping = false;
	}
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
	public void setStart(boolean b) {
		start = b;
	}
	public boolean getStart() {
		return start;
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
		if(!start) {

		}
		else if(jumping) {

			y-=dy;
			dy-=3;

			if(y>=444) {
				setY(444);
				jumping = false;
				running = true;

			}
		}

	}
	public void myDraw(Graphics g) throws Exception{
		if(jumping) {
			img = Images.getJump();
		}
		else if(dead) {
			img = Images.getDead();

		}
		else if(running) {
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
class Map extends JPanel implements ActionListener{
	private static int score;
	private JLabel lblScore;
	private int tickCnt;
	private Player player;
	private Timer timer;
	private Timer obsTimer;
	private Obstacle[] arr;
	private int cnt = 0;
	private int formCnt = 0;
	public boolean collide;
	public Map() throws Exception {
		arr = new Obstacle[10];
		Obstacle.setCreateNew(true);
		Obstacle.setDx(50);
		score = 0;
		tickCnt = 0;
		player = new Player(100,444);
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
		player.setStart(true);
		player.setRunning(true);

	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == obsTimer && Obstacle.getCreateNew()) {
			Obstacle obstacle = new Obstacle(2000, 440);
			arr[cnt%10] = obstacle;
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
			System.out.println("Error!");;
		}
		player.move();
		for (int i = 0; i<formCnt;i++) {
			arr[i].move();
		}
		repaint();
	}
	public void detectCollision() throws Exception {
		for (int i = 0; i<formCnt;i++) {
			collide = (new Rectangle(player.getX(), player.getY(), 35, 33).intersects(new Rectangle(arr[i].getX(), arr[i].getY(),30, 30)));
			if (collide) {
				player.setDead(true);
				player.setRunning(false);
				player.setJumping(false);
				player.setStart(false);
				if (score>Integer.parseInt(Login.getRunner())) {
					Login.init();
					Login.setRunner(String.valueOf(score));
					Login.saveUsers();
				}

			}
		}

	}
	class MyRunnerKeyListener extends KeyAdapter{//class to determine key events
		public void keyPressed(KeyEvent e) {
			if(player.getJumping() || player.getDead()) {

			}
			else if(e.getKeyChar() == ' ') {//space
				if(player.getJumping()) {
					player.setJumping(false);
					player.setRunning(true);
				}
				else {

					player.setJumping(true);
					player.setDy(30);
					player.setRunning(false);
				}
				repaint();
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateLabel();
		lblScore.repaint();
		for (int i = 0; i<formCnt;i++) {
			arr[i].drawCactus(g);
		}

		g.drawLine(0, 500, 1920, 500);
		try {
			player.myDraw(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(player.getDead()) {

			try {
				Thread.sleep(1000);
				AstronautGame.disposeF();
				new GameOver();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static int getScore() {
		return score;
	}
	public void updateLabel() {
		lblScore.setText("Score: " + score);
	}
}
class Obstacle{
	private int x;
	private int y;
	private static int dx;
	private static boolean createNew;
	public Obstacle(int x, int y) {
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
	public static void setCreateNew(boolean b) {
		createNew = b;
	}
	public static boolean getCreateNew() {
		return createNew;
	}
	public void drawCactus(Graphics g) {
		g.drawImage(Images.getCactus(), x, y, null);
	}
}
class Images{
	private static BufferedImage img, cactusImg;
	private static Image run1, run2, jump, cactus, dead;
	private static Image[] run = new Image[2];
	private static String strcactus = "images/cactus.png";
	public static void loadImages() throws Exception {
		img = ImageIO.read(new File("images/dinosaurSprite.png"));
		cactusImg = ImageIO.read(new File(strcactus));
		cactus = cactusImg;
		System.out.println(img.getWidth());
		run1 = img.getSubimage(170, 0, 57, 56);
		run2 = img.getSubimage(113, 0, 57, 56);
		jump = img.getSubimage(0, 0, 57, 56);
		dead = img.getSubimage(285, 0, 56, 56);
		run[0]=run1;
		run[1]=run2;
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
	public static Image getDead() {
		return dead;
	}
}
