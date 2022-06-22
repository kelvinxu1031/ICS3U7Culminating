/**
 * Flappy Bird Game
 * @author laiba
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

public class FlappyBirdGame extends Canvas implements Runnable, KeyListener{
	
	public static final int WIDTH = 640, HEIGHT = 480;
	private boolean running = false;
	private Thread thread;
	
	public static double score = 0;
	
	public static Room room; 
	public Bird bird;
	
	public FlappyBirdGame() {
		Dimension d = new Dimension(FlappyBirdGame.WIDTH,FlappyBirdGame.HEIGHT);
		setPreferredSize(d);
		addKeyListener(this);
		room = new Room(80);
		bird = new Bird(20,FlappyBirdGame.HEIGHT / 2,room.tubes);
	}
	
	
	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//main method
	public static void main(String[]args) {
		JFrame Frame  = new JFrame("Flappy Bird");
		FlappyBirdGame flappy = new FlappyBirdGame();
		Frame.add(flappy);
		Frame.setResizable(false);
		Frame.pack();
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setLocationRelativeTo(null);
		Frame.setVisible(true);
		
		flappy.start();
		
	}

	@Override
	public void run() {
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double ns = 1000000000 / 60;
		double delta = 0;
		while(running) {
			long now = System.nanoTime();
			delta+= (now-lastTime) / ns;
			lastTime  = now;
			
			while(delta>=1) {
				update();
				render();
				fps++;
				delta--;
			}
			
			if(System.currentTimeMillis()-timer >= 1000) {
				System.out.println("FPS: "+fps);
				fps = 0;
				timer+=1000;
			}
	
		}
		
		stop();
	}
	
	private void update() {
		room.update();
		bird.update();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, FlappyBirdGame.WIDTH, FlappyBirdGame.HEIGHT);
		room.render(g);
		bird.render(g);
		g.setColor(Color.white);
		g.setFont(new Font(Font.DIALOG, Font.BOLD,19));
		g.drawString("Score: "+(int)score, 10, 20);
		g.dispose();
		bs.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			bird.isPressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			bird.isPressed = false;
		}
		
	}
	
}
/**
 * Room class
 * @author laiba
 *
 */
class Room{
	
	public ArrayList <Rectangle> tubes; 
	private int time;
	private int currentTime = 0;
	
	private int spd = 4;
	
	private Random random;
	private final int SPACE_TUBES = 96;
	
	private final int WIDTH_TUBES = 32;
	
	public Room(int time) {
		tubes = new ArrayList<>();
		this.time = time;
		
		random = new Random();
	}
	
	public void update() {
		
		currentTime++;
		if(currentTime == time) {
			//Create our new time
			currentTime = 0;


			int y1 = 0;
			int height1 = random.nextInt(FlappyBirdGame.HEIGHT/2);
			
			int y2 = height1+SPACE_TUBES;
			int height2 = FlappyBirdGame.HEIGHT-y2;
			
			tubes.add(new Rectangle(FlappyBirdGame.WIDTH,0,WIDTH_TUBES,height1));
			tubes.add(new Rectangle(FlappyBirdGame.WIDTH,y2,WIDTH_TUBES,height2));
		}
		
		boolean shouldPlaySound = false;
		for(int i=0; i<tubes.size();i++) {
			Rectangle rect = tubes.get(i);
			rect.x-= spd;
			
			if(rect.x+rect.width<=0) 
			{
				shouldPlaySound = true;
				tubes.remove(i--);
				FlappyBirdGame.score+=0.5;
				continue;
			}
		}
		
		if(shouldPlaySound) Sound.test.play();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		
		for(int i=0; i<tubes.size();i++) {
			Rectangle rect = tubes.get(i);
			g.fillRect(rect.x, rect.y,rect.width, rect.height);
		}
	}

}
/**
 * Bird class
 * @author laiba
 *
 */
class Bird extends Rectangle{
	
	private static final long serialVersionUID = 1L;

	private float spd = 4;
	public boolean isPressed = false;
	private BufferedImage sheet;
	private BufferedImage[] texture;
	private ArrayList <Rectangle> tubes;
	
	private int imageIndex = 0;
	private boolean isFalling = false;
	private int frames = 0;
	
	private float gravity = 0.3f;
	
	public Bird(int x, int y, ArrayList<Rectangle> tubes) {
		setBounds(x,y,32,32);
		this.tubes = tubes;
		
		try {
			sheet = ImageIO.read(new File("images/flappybird.jpg"));
			
			texture[0] = sheet.getSubimage(0, 0, 16, 16);
			texture[1] = sheet.getSubimage(16, 0, 16, 16);
			texture[2] = sheet.getSubimage(52, 0, 16, 16);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		isFalling = false;
		if(isPressed) {
			spd = 4;
			y-=(spd);
			imageIndex = 2;
			frames = 0;
			
		}else {
			isFalling = true;
			y+=spd;
			frames++;
			if(frames >20) frames=20;
		}
		
		
		if(isFalling) {
			spd+=gravity;
			if(spd > 8) spd = 8;
			if(frames >=20) imageIndex = 1;
			else imageIndex = 0;
		}
		
		for(int i=0; i<tubes.size();i++) {
			if(this.intersects(tubes.get(i))) System.exit(i);
				//Restart the game
				FlappyBirdGame.room = new Room(80);
				tubes = FlappyBirdGame.room.tubes;
				y = FlappyBirdGame.HEIGHT / 2;
				FlappyBirdGame.score = 0;
				spd = 4;
				break;
			
		}
	
		if(y >= FlappyBirdGame.HEIGHT) {
			//Restart the game
			FlappyBirdGame.room = new Room(80);
			tubes = FlappyBirdGame.room.tubes;
			y = FlappyBirdGame.HEIGHT / 2;
			FlappyBirdGame.score = 0;
			spd = 4;
		}
		
	}
	
	
	public void render(Graphics g) {
		g.drawImage(texture[imageIndex], x, y, width,height,null);
	}

}



