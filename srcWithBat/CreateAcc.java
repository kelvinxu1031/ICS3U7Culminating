import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * This program allows for the user to create an account to access the game
 * Author: Kelvin Xu
 * Date: June 7, 2022
 */
public class CreateAcc extends JFrame implements ActionListener{

	//file IO variables
	private static JFrame  createAccF;
	private JLabel        backgroundP;
	private JLabel         lblTitle;
	private JLabel         lblUser;
	private JLabel         lblPass;
	private JTextField     uText;
	private JPasswordField pText;
	private JButton        enter;
	private JButton        back;

	String titleFontName = "fonts/titleFont.ttf";
	String textFontName  = "fonts/textFont.ttf";

	public CreateAcc(String title) throws Exception {
		//import fonts
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(30f);
		ge.registerFont(textFont);
		ge.registerFont(titleFont);

		//read input from "accounts.txt"
		Login.init();

		//instantiating components for GUI
		createAccF  = new JFrame(title);
		backgroundP = new JLabel(new ImageIcon("images/mainMenuBackground.jpg"));
		lblTitle    = new JLabel("CREATE YOUR ACCOUNT");
		lblUser     = new JLabel("USERNAME: ");
		lblPass     = new JLabel("PASSWORD: ");
		uText       = new JTextField();
		pText       = new JPasswordField();
		enter       = new JButton("ENTER");
		back        = new JButton("BACK");

		//formatting of the components
		createAccF.setLayout(null);
		backgroundP.setLayout(null);
		backgroundP.setSize(720,470);
		lblTitle.setFont(titleFont);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50, 50, 620, 50);
		lblUser.setForeground(Color.WHITE);
		lblUser.setFont(textFont);
		lblUser.setBounds(50,150, 150,25);
		lblPass.setForeground(Color.WHITE);
		lblPass.setFont(textFont);
		lblPass.setBounds(50,250,150,25);
		uText.setBounds(200,150,470,25);
		pText.setBounds(200,250,470,25);
		enter.setBounds(30,325,320,50);
		back.setBounds(370,325,320,50);

		//add functionality to buttons
		createButton(enter);
		createButton(back);

		backgroundP.add(lblTitle);
		backgroundP.add(lblUser);
		backgroundP.add(lblPass);
		backgroundP.add(uText);
		backgroundP.add(pText);
		backgroundP.add(enter);
		backgroundP.add(back);
		createAccF.add(backgroundP);
		createAccF.setSize(720, 470);
		createAccF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		createAccF.setLocationRelativeTo(null);
		createAccF.setVisible(true);
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

	
	
	

	

	/**
	 * This method returns whether an account has been registered with this username
	 * @param username entered by the user
	 * @return whether the username is already registered
	 * @throws Exception IOException
	 */
	public boolean isRegistered(String username) throws Exception{
		for(int i = 0; i<Login.getUsers();i++) {
			if (Login.getAccounts()[0][i].equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that detects actions by the user
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		try {
			if (e.getSource() == enter) {//if user clicked enter button

				//get username and password entered
				String user = uText.getText();
				String pass = pText.getText();

				if (pass.length()==0||user.length()==0) {
					uText.setText("");
					pText.setText("");
					JOptionPane.showMessageDialog(this, "Error: Fill out both username and password fields.");
				}
				else if(isRegistered(user)) {
					uText.setText("");
					pText.setText("");
					JOptionPane.showMessageDialog(this, "Error: An account with this username already exists.");
				}
				else {
					Login.createUser(user, pass);
					createAccF.dispose();
					new Login("LOGIN");
				}



			}
			if(e.getSource() == back) {//if user clicked back button
				createAccF.dispose();
				new Login("LOGIN");
			} 
		}catch (Exception e1) {
			System.out.println(e1.getLocalizedMessage());
		}
	}

}
