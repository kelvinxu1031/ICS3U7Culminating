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
	private static String file = "accounts.txt";
	private static String[][] accounts = new String[6][1000];
	private String[] usernames;
	private String[] passwords;
	private String[]          pacManScores;
	private String[]          flappyBirdScores;
	private String[]          asteroidsScores;
	private String[]          runnerScores;
	private static int numOfUsers;

	private static JFrame  createAccF;
	private JPanel         backgroundP;
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
		BufferedReader in = new BufferedReader(new FileReader(file));
		usernames = in.readLine().split(" ");
		passwords = in.readLine().split(" ");
		pacManScores = in.readLine().split(" ");
		flappyBirdScores = in.readLine().split(" ");
		asteroidsScores = in.readLine().split(" ");
		runnerScores = in.readLine().split(" ");
		numOfUsers = usernames.length;
		for (int i = 0; i<numOfUsers;i++) {
			accounts[0][i] = usernames[i];
			accounts[1][i] = passwords[i];
			accounts[2][i] = pacManScores[i];
			accounts[3][i] = flappyBirdScores[i];
			accounts[4][i] = asteroidsScores[i];
			accounts[5][i] = runnerScores[i];
		}

		//instantiating components for GUI
		createAccF  = new JFrame(title);
		backgroundP = new JPanel();
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
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50, 50, 620, 50);
		lblUser.setFont(textFont);
		lblUser.setBounds(50,150, 150,25);
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
		in.close();
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
	 * This method adds a user to the accounts array and updates the text file
	 * @param username of the user
	 * @param password of the user
	 * @throws Exception IOexception
	 */
	public void createUser(String username, String password) throws Exception{
		String zero = "0";
		accounts[0][numOfUsers] = username;
		accounts[1][numOfUsers] = password;
		accounts[2][numOfUsers] = zero;
		accounts[3][numOfUsers] = zero;
		accounts[4][numOfUsers] = zero;
		accounts[5][numOfUsers] = zero;
		numOfUsers++;
		saveUsers();
	}

	/**
	 * This method updates the accounts.txt file 
	 * @throws Exception
	 */
	public void saveUsers() throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for(int i = 0; i<numOfUsers;i++) {
			out.write(accounts[0][i] + " ");
		}
		out.newLine();
		for (int i = 0; i<numOfUsers;i++){
			out.write(accounts[1][i] + " ");
		}
		out.newLine();

		for (int i = 0; i<numOfUsers;i++) {
			out.write(accounts[2][i]+ " ");
		}
		out.newLine();
		for (int i = 0; i<numOfUsers;i++) {
			out.write(accounts[3][i] + " ");
		}
		out.newLine();
		for (int i = 0; i<numOfUsers;i++) {
			out.write(accounts[4][i] + " ");
		}
		out.newLine();
		for (int i = 0; i<numOfUsers;i++) {
			out.write(accounts[5][i] + " ");
		}
		out.close();//save .txt file
	}

	/**
	 * This method returns whether an account has been registered with this username
	 * @param username entered by the user
	 * @return whether the username is already registered
	 * @throws Exception IOException
	 */
	public boolean isRegistered(String username) throws Exception{
		for(int i = 0; i<numOfUsers;i++) {
			if (accounts[0][i].equals(username)) {
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
					createUser(user, pass);
					createAccF.dispose();
					new Login("LOGIN");
				}



			}
			if(e.getSource() == back) {//if user clicked back button
				createAccF.dispose();
				new Login("LOGIN");
			} 
		}catch (Exception e1) {
			System.out.println("Error with file IO");
		}
	}

}
