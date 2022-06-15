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
		Images.loadImages();
		f.add(map);
		f.setSize(720,470);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
		f.getContentPane().setBackground(Color.CYAN);
		f.setForeground(Color.CYAN);
	}
	public static void main (String[] args) throws Exception {
		new AstronautGame();
	}
}

class Player{
	private int x;
	private int y;
	private boolean jumping = false;
	private boolean dead = false;
	private boolean running = false;
	private boolean start = false;
	private int cnt = 0;
	
	public void setJumping(boolean b) {
		jumping = b;
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
	public void move() {
		x+=10;
		
	}
	public void myDraw(Graphics g){
		if(cnt == 0) {
			g.drawImage(Images.getRun()[0], 0, 0, null);
		}
	}	
}
class Map extends JPanel implements KeyListener, ActionListener{
	private Player player;
	private Timer timer;
	public Map() throws Exception {
		player = new Player();
		Images.loadImages();
		Images.resize();
		addKeyListener(this);
		
		
	}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==32) {
			player.setJumping(true);
			player.setRunning(false);
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 300, 720, 300);
		
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
