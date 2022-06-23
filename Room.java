import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Room class
 * @author laiba
 *
 */
public class Room {
	
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
