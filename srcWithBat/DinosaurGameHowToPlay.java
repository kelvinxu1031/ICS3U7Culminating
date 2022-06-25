/**
 * Instructions for the Runner game
 * @author Kelvin, Laiba
 *
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DinosaurGameHowToPlay extends JFrame implements ActionListener{
	//GUI Components
	private JFrame f;
	private JLabel backgroundP;
	private JLabel lblTitle;
	private String line;
	private JTextArea text;
	private JButton back;
	private JButton play;

	//input file
	private String file = "runnerHowToPlay.txt";

	//font file names
	String titleFontName = "fonts/titleFont.ttf";
	String textFontName  = "fonts/textFont.ttf";

	/**
	 * Constructor for DinosaurGameHowToPlay class
	 * @param title title of the page
	 */
	public DinosaurGameHowToPlay(String title) throws Exception{
		//initialize fonts
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(28f);
		ge.registerFont(titleFont);

		//instantiate components
		f = new JFrame(title);
		backgroundP = new JLabel(new ImageIcon("images/dinosaurBackground.jpg"));
		lblTitle = new JLabel("DINOSAUR GAME");
		text = new JTextArea();
		back = new JButton("BACK");
		play = new JButton("PLAY");

		//read input from file
		BufferedReader in = new BufferedReader(new FileReader(file));
		line = in.readLine();
		while (line!=null) {
			text.append(line + "\n");
			line = in.readLine();
		}
		//formatting for components
		createButton(play);
		createButton(back);
		f.setLayout(null);
		f.setSize(720,470);
		backgroundP.setLayout(null);
		backgroundP.setSize(720, 470);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(100, 50, 520, 50);
		text.setBounds(100,125,520,200);
		back.setBounds(30,360,320,50);
		play.setBounds(370,360,320,50);

		backgroundP.add(lblTitle);
		backgroundP.add(text);
		backgroundP.add(back);
		backgroundP.add(play);
		f.add(backgroundP);

		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		in.close();
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
			if(e.getSource()==back) {
				f.dispose();
				new GameOption("PLAY");
			}
			if(e.getSource()==play) {
				f.dispose();
				new DinosaurGame();
			}
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
	}
}