import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameOption extends JFrame{
	private static JFrame gameOptionF;
	private JPanel  backgroundP;
	private JLabel  lblTitle;
	private JButton pacMan;
	private JButton flappyBird;
	private JButton asteroid;
	private JButton runner;
	private JButton back;

	String titleFontName = "fonts/titleFont.ttf";
	String textFontName  = "fonts/textFont.ttf";

	public GameOption(String title) throws Exception{
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(50f);
		ge.registerFont(titleFont);
		
		gameOptionF = new JFrame(title);
		backgroundP = new JPanel();
		lblTitle = new JLabel("WELCOME TO THE ARCADE");
		pacMan = new JButton("PAC_MAN");
		flappyBird = new JButton("FLAPPY BIRD");
		asteroid = new JButton("ASTEROIDS");
		runner = new JButton("ASTRONAUT GAME");
		back = new JButton("BACK");
		
		gameOptionF.setLayout(null);
		backgroundP.setLayout(null);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50,50,620,50);
		pacMan.setBounds(30,180,315,60);
		flappyBird.setBounds(210,180,315,60);
		asteroid.setBounds(30,270,315,60);
		runner.setBounds(210,270,315,60);
		back.setBounds(210,360,315,60);
		
		
		backgroundP.add(lblTitle);
		backgroundP.add(pacMan);
		backgroundP.add(flappyBird);
		backgroundP.add(asteroid);
		backgroundP.add(runner);
		backgroundP.add(back);
		gameOptionF.add(backgroundP);
		
		gameOptionF.setSize(720,470);
		gameOptionF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gameOptionF.setLocationRelativeTo(null);
		gameOptionF.setVisible(true);
	}
	

	
}
