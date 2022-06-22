import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Bird class
 * @author laiba
 *
 */
public class Bird extends Rectangle{
	
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
			sheet = ImageIO.read(getClass().getResource("\"C:\\Users\\laiba\\OneDrive\\Desktop\\LaibaWorkspace\\GameInstructions\\flappybird.jpg\""));
			
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
