import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class DinosaurGame  extends JFrame{
	private Map map;
	private static JFrame f;
	public DinosaurGame() throws Exception {
		f = new JFrame();
		map = new Map();
		f.add(map);
		f.setSize(1280,720);
		f.setLocationRelativeTo(null);
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
			dy-=5;

			if(y>=530) {
				setY(530);
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
	private JLabel lblPause = new JLabel("PAUSED");
	private JLabel lblStart = new JLabel("PRESS SPACE TO START");
	private JLabel lblScore = new JLabel("SCORE: 0");
	private int tickCnt;
	private Player player;
	private Timer timer;
	private Timer obsTimer;
	private Obstacle[] arr;
	private int[] picCnt;
	private int cnt = 0;
	private int formCnt = 0;
	public boolean collide;
	private static boolean paused, started;
	public Map() throws Exception {
		arr = new Obstacle[10];
		picCnt = new int[10];
		Obstacle.setCreateNew(true);
		Obstacle.setDx(50);
		score = 0;
		tickCnt = 0;
		player = new Player(100,530);
		Images.loadImages();


		addKeyListener(new MyRunnerKeyListener());
		setFocusable(true);
		lblScore.setBounds(0, 0, 100, 25);
		this.add(lblScore);

		timer = new Timer(50, (ActionListener) this);
		timer.start();
		obsTimer = new Timer(2000, (ActionListener) this);
		obsTimer.start();
		player.setStart(true);
		player.setRunning(true);


		paused = false;
		started = false;

	}
	public void actionPerformed(ActionEvent e) {
		if(!paused) {
			if(e.getSource() == obsTimer && Obstacle.getCreateNew()) {
				Obstacle obstacle = new Obstacle(2000, 536);
				arr[cnt%10] = obstacle;
				picCnt[cnt%10] =(int)(Math.random()*3)+1;
				cnt++;
				if (cnt<10) {
					formCnt++;
				}
			}
			if(tickCnt>500) {
				Obstacle.setDx(75);
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
				arr[i].move();
			}
			repaint();
		}
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
	class MyRunnerKeyListener extends KeyAdapter{//class to determine key events
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()=='p') {
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
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/textFont.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			g.drawImage(Images.getBackground(), 0, 0, null);
			updateLabel();
			lblScore.repaint();
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

			try {
				player.myDraw(g);
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
					DinosaurGame.disposeF();
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
class Images{
	private static BufferedImage img, cactusImg, cactusImg2, cactusImg3, backgroundImg;
	private static Image run1, run2, jump, cactus, cactus2, cactus3, dead, background;
	private static Image[] run = new Image[2];
	private static String strcactus = "images/cactus.png";
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
