import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Leaderboard extends JFrame implements ActionListener{
	private static JFrame     leaderboardF;
	private JLabel            backgroundP;
	private JButton           back;
	private JButton           play;
	private JButton           erase;
	private JLabel            lblTitle;
	private JLabel            lblFlappyBird;
	private JTable            leaderboardFlappyBird;
	private JLabel            lblAsteroids;
	private JTable            leaderboardAsteroids;
	private JLabel            lblRunner;
	private JTable            leaderboardRunner;
	private String[][]        accounts;
	private String[][]        flappyBirdScores = new String[1000][2];
	private String[][]        sortedFlappyBirdScores = new String[10][2];
	private String[][]        asteroidsScores = new String[1000][2];
	private String[][]        sortedAsteroidScores = new String[10][2];
	private String[][]        runnerScores = new String[1000][2];
	private String[][]        sortedRunnerScores = new String[10][2];
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
		for(int i = 0; i<1000;i++) {
			if(accounts[0][i]==null) {
				flappyBirdScores[i][0] = "";
				flappyBirdScores[i][1] = "0";
				asteroidsScores[i][0] = "";
				asteroidsScores[i][1] = "0";
				runnerScores[i][0] = "";
				runnerScores[i][1] = "0";
			}
			else {
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
		for(int i = 0; i<10;i++) {
			if(flappyBirdScores[i][0]==null) {
				sortedFlappyBirdScores[i][0] = "";
				sortedFlappyBirdScores[i][1] = "0";
			}
			else {
				sortedFlappyBirdScores[i][0] = flappyBirdScores[i][0];
				sortedFlappyBirdScores[i][1] = flappyBirdScores[i][1];
			}

			if(asteroidsScores[i][0]==null) {
				sortedAsteroidScores[i][0] = "";
				sortedAsteroidScores[i][1] = "0";
			}
			else {
				sortedAsteroidScores[i][0] = asteroidsScores[i][0];
				sortedAsteroidScores[i][1] = asteroidsScores[i][1];
			}

			if(runnerScores[i][0]==null) {
				sortedRunnerScores[i][0] = "";
				sortedRunnerScores[i][1] = "0";
			}
			else {
				sortedRunnerScores[i][0] = runnerScores[i][0];
				sortedRunnerScores[i][1] = runnerScores[i][1];
			}
		}
		leaderboardF = new JFrame(title);
		backgroundP = new JLabel(new ImageIcon("images/statsBackground.jpg"));
		back = new JButton("BACK");
		play = new JButton("PLAY");
		erase = new JButton("ERASE SCORES");
		lblTitle = new JLabel("LEADERBOARDS");
		lblFlappyBird = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
		lblAsteroids = new JLabel("SPACE SHOOTERS", SwingConstants.CENTER);
		lblRunner = new JLabel("DINOSAUR GAME", SwingConstants.CENTER);
		leaderboardFlappyBird  = new JTable(sortedFlappyBirdScores, colNames);
		leaderboardAsteroids  = new JTable(sortedAsteroidScores, colNames);
		leaderboardRunner  = new JTable(sortedRunnerScores, colNames);
		flappyBirdPane = new JScrollPane(leaderboardFlappyBird);
		asteroidsPane = new JScrollPane(leaderboardAsteroids);
		runnerPane = new JScrollPane(leaderboardRunner);

		leaderboardF.setLayout(null);
		leaderboardF.setSize(720,470);
		backgroundP.setLayout(null);
		backgroundP.setSize(720,470);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(titleFont);
		lblTitle.setBounds(50,50,620,50);
		lblFlappyBird.setForeground(Color.WHITE);
		lblFlappyBird.setBounds(45,100,165,50);
		lblAsteroids.setForeground(Color.WHITE);
		lblAsteroids.setBounds(255,100,165,50);
		lblRunner.setForeground(Color.WHITE);
		lblRunner.setBounds(465,100,165,50);
		flappyBirdPane.setBounds(45,150,165,185);
		asteroidsPane.setBounds(255,150, 165,185);
		runnerPane.setBounds(465,150,165,185);
		back.setBounds(30,380,320,50);
		play.setBounds(370,380,320,50);
		erase.setBounds(260, 340, 200, 25);

		createButton(play);
		createButton(back);
		createMiniButton(erase);
		backgroundP.add(lblTitle);
		backgroundP.add(lblFlappyBird);
		backgroundP.add(lblAsteroids);
		backgroundP.add(lblRunner);
		backgroundP.add(flappyBirdPane);
		backgroundP.add(asteroidsPane);
		backgroundP.add(runnerPane);
		backgroundP.add(play);
		backgroundP.add(back);
		backgroundP.add(erase);
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
	public void createMiniButton(JButton b) throws Exception{
		//import fonts
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(titleFontName)).deriveFont(12f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		b.addActionListener(this);
		b.setFont(font);
	}

	public void sort(String[][] arr){
		int i, j;
		String temp1, temp2;
		for(i = 0; i<arr.length;i++) {
			for(j = 1;j<(arr.length-i);j++) {
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
			else if(e.getSource()==erase) {
				Login.eraseScores();
				leaderboardF.dispose();
				new Leaderboard("LEADERBOARD");
			}

		}catch(Exception e1) {
			System.out.println("ERROR!");
		}

	}

}
