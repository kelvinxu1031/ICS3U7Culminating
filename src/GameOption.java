import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameOption extends JFrame implements ActionListener{
	private static JFrame gameOptionF;
	private JPanel  backgroundP;
	private JLabel  lblTitle;
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
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(28f);
		ge.registerFont(titleFont);

		gameOptionF = new JFrame(title);
		backgroundP = new JPanel();
		lblTitle = new JLabel("WELCOME TO THE ARCADE!!");
		flappyBird = new JButton("FLAPPY BIRD");
		asteroid = new JButton("ASTEROIDS");
		runner = new JButton("ASTRONAUT GAME");
		back = new JButton("BACK");

		gameOptionF.setLayout(null);
		backgroundP.setLayout(null);
		backgroundP.setSize(720,470);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50,50,620,50);
		flappyBird.setBounds(210,180,315,60);
		asteroid.setBounds(30,270,315,60);
		runner.setBounds(375,270,315,60);
		back.setBounds(210,360,315,60);

		createButton(flappyBird);
		createButton(asteroid);
		createButton(runner);
		createButton(back);

		backgroundP.add(lblTitle);
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

	public void createButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(12f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == back) {
				gameOptionF.dispose();
				new MainMenu("ARCADE");
			}
			if(e.getSource()==flappyBird) {
				gameOptionF.dispose();
				new FlappyBirdHowToPlay("FLAPPY BIRD");
			}
			if(e.getSource()==runner) {
				gameOptionF.dispose();
				new DinosaurGameHowToPlay("DINOSAUR GAME");
			}
			if(e.getSource()==asteroid) {
				gameOptionF.dispose();
				new AsteroidsHowToPlay("ASTEROIDS");
			}
		}catch(Exception e1) {
			System.out.println("error");
		}
	}

}
