/**
 * Instructions for the Asteroids game
 * @author Kelvin, Laiba
 *
 */
	import java.io.*;
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;

	public class AsteroidsHowToPlay extends JFrame implements ActionListener{
		//GUI Components
		private JFrame f;
		private JLabel backgroundP;
		private JLabel lblTitle;
		private String line;
		private JTextArea text;
		private JButton back;
		private JButton play;

		//input file name
		private String file = "asteroidsHowToPlay.txt";

		//font file name
		String titleFontName = "fonts/titleFont.ttf";
		String textFontName  = "fonts/textFont.ttf";

		/**
		 * Constructor for AsteroidsHowToPlay Class
		 * @param title title of the page
		 */
		public AsteroidsHowToPlay(String title) throws Exception{
			//initialize fonts
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
			ge.registerFont(textFont);
			Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(28f);
			ge.registerFont(titleFont);

			//instantiate GUI Components
			f = new JFrame(title);
			backgroundP = new JLabel(new ImageIcon("images/spaceBackground.jpg"));
			lblTitle = new JLabel("SPACE SHOOTERS");
			text = new JTextArea();
			back = new JButton("BACK");
			play = new JButton("PLAY");

			//read input from text file
			BufferedReader in = new BufferedReader(new FileReader(file));
			line = in.readLine();
			while (line!=null) {
				text.append(line + "\n");
				line = in.readLine();
			}

			//formatting for GUI
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

			//add components to screen
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
		/**
		 * Method to add functionality to buttons
		 * @param b a JButton
		 */
		public void createButton(JButton b) throws Exception{
			//import fonts
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			b.addActionListener(this);
			b.setFont(font);
		}
		/**
		 * Method to detect user input actions
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				if(e.getSource()==back) {
					f.dispose();
					new GameOption("PLAY");
				}
				if(e.getSource()==play) {
					f.dispose();
					new SpaceShooters();
				}
			}catch(Exception e1) {
				System.out.println(e1.getLocalizedMessage());
			}
		}
	}