import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class GameOver extends JFrame implements ActionListener{
	private JFrame f;
	private JLabel backgroundP;
	private JLabel lblTitle;
	private JButton mainMenu;
	private JButton leaderboard;

	String titleFontName = "fonts/titleFont.ttf";
	String textFontName  = "fonts/textFont.ttf";

	public GameOver() throws Exception {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(30f);
		ge.registerFont(textFont);
		ge.registerFont(titleFont);

		f = new JFrame();
		backgroundP = new JLabel(new ImageIcon("images/gameOverBackground.jpg"));
		lblTitle = new JLabel("GAME OVER");
		mainMenu = new JButton("MAIN MENU");
		leaderboard = new JButton("LEADERBOARD");

		f.setLayout(null);
		f.setSize(720,470); 
		backgroundP.setSize(720,470);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50, 50, 620, 50);
		mainMenu.setBounds(30,360,320,50);
		leaderboard.setBounds(370,360,320,50);

		createButton(mainMenu);
		createButton(leaderboard);

		f.add(backgroundP);
		backgroundP.add(lblTitle);
		backgroundP.add(mainMenu);
		backgroundP.add(leaderboard);

		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
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
			if (e.getSource()==mainMenu) {
				f.dispose();
				new MainMenu("ARCADE");
			}
			if (e.getSource()==leaderboard) {
				f.dispose();
				new Leaderboard("LEADERBOARD");
			}
		}catch(Exception e1) {
			System.out.println("ERROR");
		}
	}

}
