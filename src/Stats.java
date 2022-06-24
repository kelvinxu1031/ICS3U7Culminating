import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Stats extends JFrame implements ActionListener{
	private static JFrame     statsF;
	private JLabel            backgroundP;
	private JButton           back;
	private JButton           play;
	private JButton           erase;
	private JLabel            lblStats;
	private JLabel            lblUser;
	private JLabel            lblFlappyBird;
	private JLabel            lblAsteroids;
	private JLabel            lblRunner;

	//font files
	String titleFontName = "fonts/titleFont.ttf";
	String textFontName = "fonts/textFont.ttf";

	public Stats(String title) throws Exception{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(40f);
		ge.registerFont(titleFont);

		statsF = new JFrame(title);
		backgroundP = new JLabel(new ImageIcon("images/statsBackground.jpg"));
		back = new JButton("BACK");
		play = new JButton("PLAY NOW!");
		erase = new JButton("ERASE SCORES");
		lblStats = new JLabel("PERSONAL STATS");
		lblUser = new JLabel("USER: " + Login.getUser());
		lblFlappyBird = new JLabel("Flappy Bird: " + Login.getFlappyBird());
		lblAsteroids = new JLabel("Space Shooters: " + Login.getAsteroids());
		lblRunner = new JLabel("Dinosaur Game: " + Login.getRunner());

		statsF.setLayout(null);
		backgroundP.setLayout(null);
		backgroundP.setSize(720,470);
		back.setBounds(30,375,320,50);
		play.setBounds(370,375,320,50);
		erase.setBounds(260, 340, 200, 25);
		lblStats.setForeground(Color.WHITE);
		lblStats.setFont(titleFont);
		lblStats.setHorizontalAlignment(SwingConstants.CENTER);
		lblStats.setBounds(50,50,620,50);
		lblUser.setForeground(Color.WHITE);
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(50,150,620,50);
		lblFlappyBird.setForeground(Color.WHITE);
		lblFlappyBird.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlappyBird.setBounds(50,230, 620,50);
		lblAsteroids.setForeground(Color.WHITE);
		lblAsteroids.setBounds(100,300,300,50);
		lblRunner.setForeground(Color.WHITE);
		lblRunner.setBounds(420,300,300,50);
		lblUser.setFont(textFont);
		lblFlappyBird.setFont(textFont);
		lblAsteroids.setFont(textFont);
		lblRunner.setFont(textFont);

		createButton(play);
		createButton(back);
		createMiniButton(erase);
		
		backgroundP.add(lblStats);
		backgroundP.add(lblUser);
		backgroundP.add(lblFlappyBird);
		backgroundP.add(lblAsteroids);
		backgroundP.add(lblRunner);
		backgroundP.add(back);
		backgroundP.add(play);
		backgroundP.add(erase);
		statsF.add(backgroundP);

		statsF.setSize(720,470);
		statsF.setLocationRelativeTo(null);
		statsF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		statsF.setVisible(true);
	}

	public void createButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(16f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}
	public void createMiniButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(12f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}

	public void actionPerformed(ActionEvent e){
		try {
			if(e.getSource() == play) {
				statsF.dispose();
				new GameOption("PLAY");
			}
			else if(e.getSource() == back){
				statsF.dispose();
				new MainMenu("ARCADE");
			}
			else if(e.getSource()==erase) {
				Login.eraseScores();
				statsF.dispose();
				new Stats("STATS");
			}
		}
		catch (Exception e1) {
			System.out.println("Error with file io!");
		}
	}
}
