import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class AstronautGame  extends JFrame{
	private Map map;
	private JFrame f;
	public AstronautGame() throws Exception {
		f = new JFrame();
		map = new Map();
		f.add(map);
		f.setSize(1920,1080);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public static void main (String[] args) throws Exception {
		new AstronautGame();
	}
}

class Player{
	private int x;
	private int y;
	private int dy = 10;
	private int dx = 10;
	private boolean jumping;
	private boolean falling;
	private boolean dead;
	private boolean running;
	private boolean start;
	private int cnt = 0;
	private Image img;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		start = false;
		running = true;
		dead = false;
		falling = false;
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
	public void setDead(boolean b) {
		dead = b;
	}
	public void setStart(boolean b) {
		start = b;
	}
	public boolean getStart() {
		return start;
	}
	public void move() {
		if(!start) {
			
		}
		else if(jumping) {
			y+=dy;
		}
		else {
			x+=dx;
		}
		
	}
	public void myDraw(Graphics g){
		if(jumping) {
			img = Images.getJump();
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
class Map extends JPanel implements ActionListener, MouseListener{
	private Player player;
	private Timer timer;
	public Map() throws Exception {
		player = new Player(50,410);
		Images.loadImages();
		Images.resize();
		timer = new Timer(100, this);
		timer.start();

		this.addMouseListener(this);
		
	}
	public void actionPerformed(ActionEvent e) {
		player.move();
		repaint();
		System.out.println(player.getJumping());
	}
	public void mouseClicked(MouseEvent e) {
		if (!player.getStart()) {
			player.setStart(true);
		}
		if(player.getJumping()) {
			player.setJumping(false);
			player.setRunning(true);
		}
		else {

			player.setJumping(true);
			player.setRunning(false);
		}
		repaint();
	}
	public void mousePressed( MouseEvent e ){   }
	public void mouseReleased( MouseEvent e ){   }
	public void mouseEntered( MouseEvent e ) {   }
	public void mouseExited( MouseEvent e )  {   }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 500, 1920, 500);
		player.myDraw(g);
		
	}
}
class Obstacle{
	
}
class Images{
	private static BufferedImage img;
	private static Image run1, run2, jump;
	private static Image[] run = new Image[2];
	public static void loadImages() throws Exception {
		img = ImageIO.read(new File("images/dinosaurSprite.png"));
		run1 = img.getSubimage(176, 0, 88, 94);
		run2 = img.getSubimage(264, 0, 88, 94);
		jump = img.getSubimage(0, 0, 88, 94);
		run[0]=run1;
		run[1]=run2;
	}
	public static Image getJump() {
		return jump;
	}
	public static Image[] getRun() {
		return run;
	}
	public static void resize() {
		run1 = run1.getScaledInstance(44, 47, java.awt.Image.SCALE_SMOOTH);
		run2 = run2.getScaledInstance(44, 47, java.awt.Image.SCALE_SMOOTH);
		jump = jump.getScaledInstance(44, 47, java.awt.Image.SCALE_SMOOTH);
	}
}
