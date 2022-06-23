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
	private JLabel            backgroundP;
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
	private static String[][] accounts = new String[5][CAP];
	private static String[]   usernames;
	private static String[]          passwords;
	private static String[]          flappyBirdScores;
	private static String[]          asteroidsScores;
	private static String[]          runnerScores;
	private static int currI;
	private static int        numOfUsers;
	private static String     currUser;
	private static String     currPass;
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
		flappyBirdScores = in.readLine().split(" ");
		asteroidsScores = in.readLine().split(" ");
		runnerScores = in.readLine().split(" ");
		numOfUsers = usernames.length;
		for (int i = 0; i<numOfUsers;i++) {
			accounts[0][i] = usernames[i];
			accounts[1][i] = passwords[i];
			accounts[2][i] = flappyBirdScores[i];
			accounts[3][i] = asteroidsScores[i];
			accounts[4][i] = runnerScores[i];
		}

		//instantiating components for GUI
		loginF      = new JFrame(title);
		backgroundP = new JLabel(new ImageIcon("images/mainMenuBackground.jpg"));
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
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(100, 50, 520, 50);
		lblUser.setForeground(Color.WHITE);
		lblUser.setFont(textFont);
		lblUser.setBounds(50,150, 150,25);
		lblPass.setForeground(Color.WHITE);
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

	public static void init() throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		usernames = in.readLine().split(" ");
		passwords = in.readLine().split(" ");
		flappyBirdScores = in.readLine().split(" ");
		asteroidsScores = in.readLine().split(" ");
		runnerScores = in.readLine().split(" ");
		numOfUsers = usernames.length;
		for (int i = 0; i<numOfUsers;i++) {
			accounts[0][i] = usernames[i];
			accounts[1][i] = passwords[i];
			accounts[2][i] = flappyBirdScores[i];
			accounts[3][i] = asteroidsScores[i];
			accounts[4][i] = runnerScores[i];
		}
	}
	
	/**
	 * This method adds a user to the accounts array and updates the text file
	 * @param username of the user
	 * @param password of the user
	 * @throws Exception IOexception
	 */
	public static void createUser(String username, String password) throws Exception{
		String zero = "0";
		accounts[0][numOfUsers] = username;
		accounts[1][numOfUsers] = password;
		accounts[2][numOfUsers] = zero;
		accounts[3][numOfUsers] = zero;
		accounts[4][numOfUsers] = zero;
		numOfUsers++;
		Login.saveUsers();
	}
	
	/**
	 * This method updates the accounts.txt file 
	 * @throws Exception
	 */
	public static void saveUsers() throws IOException{
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
		out.close();//save .txt file
	}
	
	/**
	 * This method updates the password for a given username
	 * @param username entered by the user
	 * @param password entered by the user
	 * @throws IOException 
	 */
	public static void updateInfo(String username, String password) throws IOException {
		for (int i = 0; i<numOfUsers;i++) {
			if(username.equals(accounts[0][i])) {
				accounts[1][i] = password;
			}
		}
		Login.saveUsers();
	}
	
	public boolean isUser(String user, String pass) {
		for (int i = 0; i<numOfUsers;i++) {
			if (accounts[0][i].equals(user.trim()) && accounts[1][i].equals(pass.trim())) {
				Login.setI(i);
				Login.setUser(accounts[0][i]);
				Login.setPass(accounts[1][i]);
				Login.setFlappyBird(accounts[2][i]);
				Login.setAsteroids(accounts[3][i]);
				Login.setRunner(accounts[4][i]);
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

	public static int getI() {
		return currI;
	}
	public static void setI(int i) {
		currI = i;
	}
	public static String getUser() {
		return currUser;
	}

	public static void setUser(String user) {
		currUser = user;
	}
	public static String getPass() {
		return currPass;
	}

	public static void setPass(String pass) {
		currPass = pass;
	}

	public static String getFlappyBird() {
		return currFlappyBirdScore;
	}
	public static void setFlappyBird(String score) {
		currFlappyBirdScore = score;
		accounts[2][currI] = score;
	}
	
	public static String getAsteroids() {
		return currAsteroidsScore;
	}
	public static void setAsteroids(String score) {
		currAsteroidsScore = score;
		accounts[3][currI] = score;
	}

	public static String getRunner() {
		return currRunnerScore;
	}
	public static void setRunner(String score) {
		currRunnerScore = score;
		accounts[4][Login.getI()] = score;
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
		try {
			if (e.getSource() == enter) {
				String user = uText.getText();
				String pass = pText.getText();
				
				if(isUser(user,pass)) {
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
			System.out.println(e1.getLocalizedMessage());
		}
	}

}
