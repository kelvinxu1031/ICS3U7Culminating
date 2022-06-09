import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ResetPass extends JFrame implements ActionListener{

	private static String file = "accounts.txt";
	private static String[][] accounts = new String[2][1000];
	private String[] usernames;
	private String[] passwords;
	private static int numOfUsers;
	private String user, pass, confirmPass;
	private static String message;


	private static JFrame  resetPassF;
	private JPanel         backgroundP;
	private JLabel         lblTitle;
	private JLabel         lblUser;
	private JLabel         lblPass;
	private JLabel         lblConfirmPass;
	private JTextField     uText;
	private JPasswordField pText;
	private JPasswordField confirmPassText;
	private JButton        enter;
	private JButton        back;

	String titleFontName = "fonts/titleFont.ttf";
	String textFontName  = "fonts/textFont.ttf";

	public ResetPass(String title) throws Exception{
		//import fonts
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(30f);
		ge.registerFont(titleFont);
		//read input from "accounts.txt"
		BufferedReader in = new BufferedReader(new FileReader(file));
		usernames = in.readLine().split(" ");
		passwords = in.readLine().split(" ");
		numOfUsers = usernames.length;
		for (int i = 0; i<numOfUsers;i++) {
			accounts[0][i] = usernames[i];
			accounts[1][i] = passwords[i];
		}

		//instantiating components for GUI
		resetPassF      = new JFrame(title);
		backgroundP     = new JPanel();
		lblTitle        = new JLabel("RESET YOUR PASSWORD");
		lblUser         = new JLabel("USERNAME: ");
		lblPass         = new JLabel("PASSWORD: ");
		lblConfirmPass  = new JLabel("CONFIRM PASSWORD: ");
		uText           = new JTextField();
		pText           = new JPasswordField();
		confirmPassText = new JPasswordField();
		enter           = new JButton("ENTER");
		back            = new JButton("BACK");

		//formatting of components
		resetPassF.setLayout(null);
		backgroundP.setLayout(null);
		backgroundP.setSize(720,470);
		lblTitle.setFont(titleFont);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(50, 50, 620, 50);
		lblUser.setFont(textFont);
		lblUser.setBounds(50,150, 250,25);
		lblPass.setFont(textFont);
		lblPass.setBounds(50,200,250,25);
		lblConfirmPass.setFont(textFont);
		lblConfirmPass.setBounds(50,250,250,25);
		uText.setBounds(300,150,370,25);
		pText.setBounds(300, 200, 370, 25);
		confirmPassText.setBounds(300,250,370,25);
		enter.setBounds(30,325,320,50);
		back.setBounds(370,325,320,50);

		//add functionality to buttons
		createButton(enter);
		createButton(back);

		backgroundP.add(lblTitle);
		backgroundP.add(lblUser);
		backgroundP.add(lblPass);
		backgroundP.add(lblConfirmPass);
		backgroundP.add(uText);
		backgroundP.add(pText);
		backgroundP.add(confirmPassText);
		backgroundP.add(enter);
		backgroundP.add(back);
		resetPassF.add(backgroundP);
		resetPassF.setSize(720, 470);
		resetPassF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		resetPassF.setLocationRelativeTo(null);
		resetPassF.setVisible(true);
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
	 * This method returns a string describing whether a password is valid or not
	 * @param username entered by the user
	 * @param password entered by the user
	 * @param confirmPass entered by the user
	 * @return a string describing the error in the password
	 */
	public String isValid(String username, String password, String confirmPass) {
		int index = -1; //initialize index to -1 to determine whether user entered a valid username
		//traverse accounts list to find the index of the username the user entered
		for (int i = 0; i < numOfUsers;i++) {
			if(username.equals(accounts[0][i])) {
				index = i;
				break;
			}
		}
		if(index == -1) {
			return "username not found";
		}
		else if (accounts[1][index].equals(password)) {
			return "same pass";
		}
		else if(!password.equals(confirmPass)) {
			return "pass not equal";
		}
		else {
			return "valid";
		}
	}


	/**
	 * This method updates the password for a given username
	 * @param username entered by the user
	 * @param password entered by the user
	 * @throws IOException 
	 */
	public void updateInfo(String username, String password) throws IOException {
		for (int i = 0; i<numOfUsers;i++) {
			if(username.equals(accounts[0][i])) {
				accounts[1][i] = password;
			}
		}
		saveUsers();
	}

	/**
	 * This method saves the "accounts.txt" file
	 * @throws IOException
	 */
	public static void saveUsers() throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for(int i = 0; i<numOfUsers;i++) {
			out.write(accounts[0][i]);
			out.write(" ");
		}
		out.newLine();
		for (int i = 0; i<numOfUsers;i++){
			out.write(accounts[1][i]);
			out.write(" ");
		}
		out.close();
	}

	/**
	 * This method detects user actions
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		try {
			if(e.getSource()==enter) {
				user = uText.getText();
				pass = pText.getText();
				confirmPass = confirmPassText.getText();
				message = isValid(user,pass,confirmPass);
				if(message.equals("username not found")) {
					uText.setText("");
					pText.setText("");
					confirmPassText.setText("");
					JOptionPane.showMessageDialog(this, "Error: No account is linked to this username");
				}
				else if(message.equals("same pass")){
					uText.setText("");
					pText.setText("");
					confirmPassText.setText("");
					JOptionPane.showMessageDialog(this, "Error: New password must be different from current password");
				}
				else if(message.equals("pass not equal")) {
					uText.setText("");
					pText.setText("");
					confirmPassText.setText("");
					JOptionPane.showMessageDialog(this, "Error: Password and confirm password are different");
				}
				else {
					updateInfo(user, pass);
					resetPassF.dispose();
					new Login("LOGIN");
				}
			}
			else {
				saveUsers();
				resetPassF.dispose();
				new Login("LOGIN");
			}
		}catch(Exception e1) {
			System.out.println("Error with file IO");
		}

	}
}
