import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
public class MainMenu extends JFrame implements ActionListener{
	private static JFrame mainMenuF;
	private JPanel  backgroundP;
	private JLabel  lblTitle;
	private JButton play;
	private JButton stats;
	private JButton leaderboard;
	private JButton logout;
	private JButton exit;
	private JLabel  lblUser;

	String titleFontName = "fonts/titleFont.ttf";
	String textFontName  = "fonts/textFont.ttf";

	public MainMenu(String title) throws Exception{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(50f);
		ge.registerFont(titleFont);

		mainMenuF = new JFrame(title);
		backgroundP = new JPanel();
		lblTitle = new JLabel("ARCADE");
		lblUser = new JLabel("User: " + Login.getUser());
		play = new JButton("PLAY");
		stats = new JButton("STATS");
		leaderboard = new JButton("LEADERBOARD");
		logout = new JButton("LOGOUT");
		exit = new JButton("EXIT");

		mainMenuF.setLayout(null);
		backgroundP.setLayout(null);
		backgroundP.setSize(720, 470);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50, 50, 620, 50);
		lblUser.setBounds(600,0,100,50);
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		play.setBounds(210,180,300,60);
		stats.setBounds(30,270,315,60);
		leaderboard.setBounds(375, 270, 315, 60);
		logout.setBounds(30, 360, 315, 60);
		exit.setBounds(375, 360,315, 60);

		createButton(play);
		createButton(stats);
		createButton(leaderboard);
		createButton(logout);
		createButton(exit);

		backgroundP.add(lblTitle);
		backgroundP.add(lblUser);
		backgroundP.add(play);
		backgroundP.add(stats);
		backgroundP.add(leaderboard);
		backgroundP.add(logout);
		backgroundP.add(exit);
		mainMenuF.add(backgroundP);
		mainMenuF.setSize(720,470);
		mainMenuF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainMenuF.setLocationRelativeTo(null);
		mainMenuF.setVisible(true);
	}

	public void createButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(16f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == play) {
				mainMenuF.dispose();
				new GameOption("PLAY");
			}
			else if(e.getSource() == exit) {
				System.exit(0);
			}
			else if (e.getSource() == logout) {
				mainMenuF.dispose();
				new Login("LOGIN");
			}
			else if(e.getSource()==stats) {
				mainMenuF.dispose();
				new Stats("STATS");
			}
			
		}catch(Exception e1) {
			System.out.println("ERROR!");
		}

	}
}
