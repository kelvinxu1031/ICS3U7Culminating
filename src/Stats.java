import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Stats extends JFrame implements ActionListener{
	private static JFrame     statsF;
	private JPanel            backgroundP;
	private JButton           back;
	private JButton           play;
	private JLabel            lblStats;
	private JLabel            lblPacMan;
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
	    Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(50f);
	    ge.registerFont(titleFont);
	    
		statsF = new JFrame(title);
		backgroundP = new JPanel();
		back = new JButton("BACK");
		play = new JButton("PLAY NOW!");
		lblStats = new JLabel("HIGH SCORES");
		lblPacMan = new JLabel("PAC MAN: " + Login.getPacMan());
		lblFlappyBird = new JLabel("Flappy Bird: " + Login.getFlappyBird());
		lblAsteroids = new JLabel("Asteroids: " + Login.getAsteroids());
		lblRunner = new JLabel("Runner Game: " + Login.getRunner());
		
		statsF.setLayout(null);
		backgroundP.setLayout(null);
		back.setBounds(30,325,320,50);
		play.setBounds(370,325,320,50);
		lblStats.setFont(titleFont);
		lblStats.setHorizontalAlignment(SwingConstants.CENTER);
		lblStats.setBounds(50,50,620,50);
		lblPacMan.setBounds(100,200,200,100);
		lblFlappyBird.setBounds(420,200,200,100);
		lblAsteroids.setBounds(100,325,200,100);
		lblRunner.setBounds(420,325,200,100);
		lblPacMan.setFont(textFont);
		lblFlappyBird.setFont(textFont);
		lblAsteroids.setFont(textFont);
		lblRunner.setFont(textFont);
		
		createButton(play);
		createButton(back);
		
		backgroundP.add(lblStats);
		backgroundP.add(lblPacMan);
		backgroundP.add(lblFlappyBird);
		backgroundP.add(lblAsteroids);
		backgroundP.add(lblRunner);
		backgroundP.add(back);
		backgroundP.add(play);
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
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		try {
			if(e.getSource()==play) {
				statsF.dispose();
				new MainMenu("ARCADE");
			}
			else {
				statsF.dispose();
				new MainMenu("ARCADE");
			}
		}
		catch (Exception e1) {
			System.out.println("Error with file io!");
		}
	}
}
