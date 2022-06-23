/**
 * Flappy Bird Game
 * @author laiba
 *
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

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
	
	
	public void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
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
