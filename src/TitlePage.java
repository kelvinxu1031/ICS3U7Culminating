/**
 * Title page for arcade program
 * @author Laiba Y. & Kelvin X.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;


public class TitlePage extends JFrame implements ActionListener{
	//declaration of components for GUI
	private JFrame titlePageF;
	private JLabel background;
	private JLabel lblTitle;
	private JButton login;
	private JButton exit;
	private ImageIcon img;
	private String imgName = "images/titleBackground.jpg";

	//font files
	String titleFontName = "fonts/titleFont.ttf";
	String textFontName = "fonts/textFont.ttf";

	public TitlePage(String title) throws Exception{
		//import fonts
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(50f);
		ge.registerFont(titleFont);
		//instantiation of GUI components
		titlePageF = new JFrame(title);
		img = new ImageIcon(imgName);

		//resize image
		Image image = img.getImage();
		Image image2 = image.getScaledInstance(720, 450, java.awt.Image.SCALE_SMOOTH);
		img = new ImageIcon(image2);
		background = new JLabel(img);
		lblTitle = new JLabel("ARCADE");
		lblTitle.setForeground(Color.white);
		login = new JButton("LOGIN");
		exit = new JButton("EXIT");

		//giving functionality to buttons
		createButton(login);
		createButton(exit);

		//formatting of components
		titlePageF.setLayout(null);
		background.setSize(720,470);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50, 50, 620, 50);
		login.setBounds(30,325,320,50);
		exit.setBounds(370,325,320,50);

		//add components to JFrame & display
		background.add(lblTitle);
		background.add(login);
		background.add(exit);
		titlePageF.add(background);
		titlePageF.setSize(720,470);
		titlePageF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		titlePageF.setLocationRelativeTo(null);
		titlePageF.setVisible(true);
	}

	/**
	 * This method takes a button and adds an action listener 
	 * as well as sets the font for the button
	 * @param b A button
	 * @throws Exception font exception
	 */
	public void createButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(12f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}
	//test

	/**
	 * This method detects user actions
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource()==exit) {
				System.exit(0);
			}
			else {
				titlePageF.dispose();
				new Login("LOGIN");
			}
		}catch(Exception e1) {
			System.out.println("why");
		}
	}


}