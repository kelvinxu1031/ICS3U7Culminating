import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Leaderboard extends JFrame implements ActionListener{

	public static void main (String[] args) throws Exception {
		new Leaderboard("GAMES");
	}
	private static JFrame     leaderboardF;
	private JPanel            backgroundP;
	private JButton           back;
	private JButton           play;
	private JLabel            lblTitle;
	private JLabel            lblFlappyBird;
	private JTable            leaderboardFlappyBird;
	private JLabel            lblAsteroids;
	private JTable            leaderboardAsteroids;
	private JLabel            lblRunner;
	private JTable            leaderboardRunner;
	private String[][]        accounts;
	private String[][]        flappyBirdScores = new String[10][2];
	private String[][]        asteroidsScores = new String[10][2];
	private String[][]        runnerScores = new String[10][2];
	private String[]          colNames = {"USERNAME", "SCORE"};
	private JScrollPane       flappyBirdPane;
	private JScrollPane       asteroidsPane;
	private JScrollPane       runnerPane;
	//font files
	String titleFontName = "fonts/titleFont.ttf";
	String textFontName = "fonts/textFont.ttf";


	public Leaderboard(String title) throws Exception{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File(textFontName)).deriveFont(12f);
		ge.registerFont(textFont);
		Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(28f);
		ge.registerFont(titleFont);

		//initialize 2D arrays and sort them in ascending order;
		Login.init();
		accounts = Login.getAccounts();
		for(int i = 0; i<10;i++) {
			if(accounts[0][i]==null) {
				flappyBirdScores[i][0] = "";
				flappyBirdScores[i][1] = "0";
				asteroidsScores[i][0] = "";
				asteroidsScores[i][1] = "0";
				runnerScores[i][0] = "";
				runnerScores[i][1] = "0";
			}
			else{
				flappyBirdScores[i][0] = accounts[0][i];
				flappyBirdScores[i][1] = accounts[2][i];
				asteroidsScores[i][0] = accounts[0][i];
				asteroidsScores[i][1] = accounts[3][i];
				runnerScores[i][0] = accounts[0][i];
				runnerScores[i][1] = accounts[4][i];
			}
			
			
		}
		sort(flappyBirdScores);
		sort(asteroidsScores);
		sort(runnerScores);
		
		leaderboardF = new JFrame(title);
		backgroundP = new JPanel();
		back = new JButton("BACK");
		play = new JButton("PLAY");
		lblTitle = new JLabel("LEADERBOARDS");
		lblFlappyBird = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
		lblAsteroids = new JLabel("ASTEROIDS", SwingConstants.CENTER);
		lblRunner = new JLabel("ASTRONAUT GAME", SwingConstants.CENTER);
		leaderboardFlappyBird  = new JTable(flappyBirdScores, colNames);
		leaderboardAsteroids  = new JTable(asteroidsScores, colNames);
		leaderboardRunner  = new JTable(runnerScores, colNames);
		flappyBirdPane = new JScrollPane(leaderboardFlappyBird);
		asteroidsPane = new JScrollPane(leaderboardAsteroids);
		runnerPane = new JScrollPane(leaderboardRunner);
		leaderboardF.setLayout(null);
		leaderboardF.setSize(720,470);
		backgroundP.setLayout(null);
		backgroundP.setSize(720,470);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(titleFont);
		lblTitle.setBounds(50,50,620,50);
		lblFlappyBird.setBounds(45,100,165,50);
		lblAsteroids.setBounds(255,100,165,50);
		lblRunner.setBounds(465,100,165,50);
		flappyBirdPane.setBounds(45,150,165,185);
		asteroidsPane.setBounds(255,150, 165,185);
		runnerPane.setBounds(465,150,165,185);
		back.setBounds(30,360,320,50);
		play.setBounds(370,360,320,50);
		
		createButton(play);
		createButton(back);
		
		backgroundP.add(lblTitle);
		backgroundP.add(lblFlappyBird);
		backgroundP.add(lblAsteroids);
		backgroundP.add(lblRunner);
		backgroundP.add(flappyBirdPane);
		backgroundP.add(asteroidsPane);
		backgroundP.add(runnerPane);
		backgroundP.add(play);
		backgroundP.add(back);
		leaderboardF.add(backgroundP);
		
		leaderboardF.setLocationRelativeTo(null);
		leaderboardF.setDefaultCloseOperation(EXIT_ON_CLOSE);
		leaderboardF.setVisible(true);
	}
	
	public void createButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(16f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}
	
	public void sort(String[][] arr){
		int i, j;
		String temp1, temp2;
		for(i = 0; i<10;i++) {
			for(j = 1;j<(10-i);j++) {
				if (Integer.parseInt(arr[j-1][1])<Integer.parseInt(arr[j][1])) {
					temp1 = arr[j-1][0];
					temp2 = arr[j-1][1];
					arr[j-1][0] = arr[j][0];
					arr[j-1][1] = arr[j][1];
					arr[j][0] = temp1;
					arr[j][1] = temp2;
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource()==back) {
				leaderboardF.dispose();
				new MainMenu("ARCADE");
			}
			else if(e.getSource()==play) {
				leaderboardF.dispose();
				new GameOption("PLAY");
			}
			
		}catch(Exception e1) {
			System.out.println("ERROR!");
		}

	}

}
