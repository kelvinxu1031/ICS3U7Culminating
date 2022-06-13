import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This program allows the user to login to access the game, create an account, or reset a password
 * Author: Kelvin Xu
 *Date: June 7, 2022
 */
public class Login extends JFrame implements ActionListener{
	//DECLARATION OF VARIABLES

	//components for GUI
	private static JFrame     loginF;
	private JPanel            backgroundP;
	private JLabel            lblTitle;
	private JButton           enter;
	private JButton           createAcc;
	private JButton           resetPass;
	private JTextField        uText;
	private JPasswordField    pText;
	private JLabel            lblUser;
	private JLabel            lblPass;
	//file IO variables
	private final static int  CAP = 1000;
	private static String     file = "accounts.txt";
	private static String[][] accounts = new String[6][CAP];
	private String[]          usernames;
	private String[]          passwords;
	private String[]          pacManScores;
	private String[]          flappyBirdScores;
	private String[]          asteroidsScores;
	private String[]          runnerScores;
	private static int        numOfUsers;
	private static String     currUser;
	private static String     currPass;
	private static String     currPacManScore;
	private static String     currFlappyBirdScore;
	private static String     currAsteroidsScore;
	private static String     currRunnerScore;
	
	//font files
	String titleFontName = "fonts/titleFont.ttf";
	String textFontName = "fonts/textFont.ttf";
	
	public Login(String title) throws Exception{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
	    ge.registerFont(textFont);
	    Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(50f);
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
		loginF      = new JFrame(title);
		backgroundP = new JPanel();
		lblTitle    = new JLabel("ARCADE");
		lblUser     = new JLabel("Username: ");
		lblPass     = new JLabel("Password:");
		uText       = new JTextField();
		pText       = new JPasswordField();
		enter       = new JButton("ENTER");
		createAcc   = new JButton("CREATE ACCOUNT");
		resetPass   = new JButton("RESET PASSWORD");
		
		//formatting for components
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(100, 50, 520, 50);
		lblUser.setFont(textFont);
		lblUser.setBounds(50,150, 150,25);
		lblPass.setFont(textFont);
		lblPass.setBounds(50,250,150,25);
		uText.setBounds(200,150,470,25);
		pText.setBounds(200,250,470,25);
		enter.setBounds(30,325,200,50);
		createAcc.setBounds(250,325,200,50);
		resetPass.setBounds(470,325,200,50);
		
		//add functionality to buttons
		createButton(enter);
		createButton(createAcc);
		createButton(resetPass);

		//adding components to JFrame
		backgroundP.setLayout(null);
		backgroundP.setSize(720, 470);
		backgroundP.add(lblTitle);
		backgroundP.add(lblUser);
		backgroundP.add(uText);
		backgroundP.add(lblPass);
		backgroundP.add(pText);
		backgroundP.add(resetPass);
		backgroundP.add(createAcc);
		backgroundP.add(enter);
		loginF.add(backgroundP);
		loginF.setSize(720,470);
		loginF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		loginF.setLayout(null);
		loginF.setLocationRelativeTo(null);
		loginF.setVisible(true);
		in.close();
	}

	public boolean isUser(String user, String pass) {
		for (int i = 0; i<numOfUsers;i++) {
			if (accounts[0][i].equals(user.trim()) && accounts[1][i].equals(pass.trim())) {
				return true;
			}
		}
		return false;
	}

	public void createButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(12f);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}
	
	public boolean foundUser(String user) {
		for (int i = 0; i<numOfUsers;i++) {
			if (accounts[0][i].equals(user.trim())){
				return true;
			}
		}
		return false;
	}

	public static String getUser() {
		return currUser;
	}

	public void setUser(String user) {
		currUser = user;
	}
	public static String getPass() {
		return currPass;
	}

	public void setPass(String pass) {
		currPass = pass;
	}
	
	public static String getPacMan() {
		return currPacManScore;
	}
	public void setPacMan(String score) {
		currPacManScore = score;
	}

	public static String getFlappyBird() {
		return currFlappyBirdScore;
	}
	public void setFlappyBird(String score) {
		currFlappyBirdScore = score;
	}
	
	public static String getAsteroids() {
		return currAsteroidsScore;
	}
	public void setAsteroids(String score) {
		currAsteroidsScore = score;
	}

	public static String getRunner() {
		return currRunnerScore;
	}
	public void setRunner(String score) {
		currRunnerScore = score;
	}
	
	public int findIndex(String user) {
		for (int i = 0; i<numOfUsers;i++) {
			if(user.equals(accounts[0][i])) {
				return i;
			}
		}
		return 0;
	}
	
	public static String[][] getAccounts(){
		return accounts;
	}
	public static int getUsers() {
		return numOfUsers;
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		int index;
		try {
			if (e.getSource() == enter) {
				String user = uText.getText();
				String pass = pText.getText();
				
				if(isUser(user,pass)) {
					index = findIndex(user);
					setUser(user);
					setPass(pass);
					setPacMan(accounts[2][index]);
					setFlappyBird(accounts[3][index]);
					setAsteroids(accounts[4][index]);
					setRunner(accounts[5][index]);
					JOptionPane.showMessageDialog(this, "Access Granted!");
					loginF.dispose();
					new MainMenu("WELCOME TO THE ARCADE");
				}
				else if(foundUser(user)) {
					uText.setText("");
					pText.setText("");
					JOptionPane.showMessageDialog(this, "Error: Incorrect password");
				}
				else {
					uText.setText("");
					pText.setText("");
					JOptionPane.showMessageDialog(this, "Error: Account not created with this username");
				}

			}
			if(e.getSource() == createAcc) {
				loginF.dispose();
				new CreateAcc("CREATE ACCOUNT");
			}
			if(e.getSource() == resetPass) {
				loginF.dispose();
				new ResetPass("RESET PASSWORD");
			}
		}
		catch (Exception e1) {
			System.out.println("Error with file io!");
		}
	}

}
