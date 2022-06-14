import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class AstronautGame {

}

class Player{
	
}
class Map{
	
}
class Obstacle{
	
}
class Images{
	private static BufferedImage img, run1, run2, jump;
	private static BufferedImage[] run = new BufferedImage[2];
	public static void loadImages() throws Exception {
		img = ImageIO.read(new File("images/dinosaurSprite.png")).getSubimage(11, 65, 528, 94);
		run1 = img.getSubimage(176, 0, 88, 94);//88x94   11x65
		run2 = img.getSubimage(264, 0, 88, 94);
		jump = img.getSubimage(0, 0, 88, 94);
		run[0]=run1;
		run[1]=run2;
	}
}
