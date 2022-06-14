import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class AstronautGame  extends JFrame{
	public AstronautGame() throws Exception {
		JFrame f = new JFrame();
		Images.loadImages();
		f.add(new JLabel(new ImageIcon(Images.getRun2())));
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
	
}
class Map{
	
}
class Obstacle{
	
}
class Images{
	private static BufferedImage img, run1, run2, jump;
	private static BufferedImage[] run = new BufferedImage[2];
	public static void loadImages() throws Exception {
		img = ImageIO.read(new File("images/dinosaurSprite.png"));
		run1 = img.getSubimage(176, 0, 88, 94);//88x94   11x65
		run2 = img.getSubimage(264, 0, 88, 94);
		jump = img.getSubimage(0, 0, 88, 94);
		run[0]=run1;
		run[1]=run2;
	}
	public static BufferedImage getJump() {
		return jump;
	}
	public static BufferedImage getRun1() {
		return run1;
	}
	public static BufferedImage getRun2() {
		return run2;
	}
}
