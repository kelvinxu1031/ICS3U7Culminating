import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Leaderboard extends JFrame implements ActionListener{
	private static JFrame     leaderboardF;
	private JPanel            backgroundP;
	private JButton           back;
	private JButton           play;
	private JLabel            lblTitle;
	private JLabel            lblPacMan;
	private JTable            leaderboardPacMan;
	private JLabel            lblFlappyBird;
	private JTable            leaderboardFlappyBird;
	private JLabel            lblAsteroids;
	private JTable            leaderboardAsteroids;
	private JLabel            lblRunner;
	private JTable            leaderboardRunner;
	private String[][]        accounts = Login.getAccounts();
	private String[][]        pacManScores = new String[10][2];
	private String[][]        flappyBirdScores = new String[10][2];
	private String[][]        asteroidsScores = new String[10][2];
	private String[][]        runnerScores = new String[10][2];
	private String[]          colNames = {"USERNAME", "SCORE"};
	private JScrollPane       pacManPane;
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
		for(int i = 0; i<10;i++) {
			if(accounts[0][i]==null) {
				pacManScores[i][0] = "";
				pacManScores[i][1] = "0";
				flappyBirdScores[i][0] = "";
				flappyBirdScores[i][1] = "0";
				asteroidsScores[i][0] = "";
				asteroidsScores[i][1] = "0";
				runnerScores[i][0] = "";
				runnerScores[i][1] = "0";
			}
			else{
				pacManScores[i][0] = accounts[0][i];
				pacManScores[i][1] = accounts[2][i];
				flappyBirdScores[i][0] = accounts[0][i];
				flappyBirdScores[i][1] = accounts[3][i];
				asteroidsScores[i][0] = accounts[0][i];
				asteroidsScores[i][1] = accounts[4][i];
				runnerScores[i][0] = accounts[0][i];
				runnerScores[i][1] = accounts[5][i];
			}
			
			
		}
		sort(pacManScores);
		sort(flappyBirdScores);
		sort(asteroidsScores);
		sort(runnerScores);
		
		leaderboardF = new JFrame(title);
		backgroundP = new JPanel();
		back = new JButton("BACK");
		play = new JButton("PLAY");
		lblTitle = new JLabel("LEADERBOARDS");
		lblPacMan = new JLabel("PAC-MAN", SwingConstants.CENTER);
		lblFlappyBird = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
		lblAsteroids = new JLabel("ASTEROIDS", SwingConstants.CENTER);
		lblRunner = new JLabel("ASTRONAUT GAME", SwingConstants.CENTER);
		leaderboardPacMan  = new JTable(pacManScores, colNames);
		leaderboardFlappyBird  = new JTable(flappyBirdScores, colNames);
		leaderboardAsteroids  = new JTable(asteroidsScores, colNames);
		leaderboardRunner  = new JTable(runnerScores, colNames);
		pacManPane = new JScrollPane(leaderboardPacMan);
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
		lblPacMan.setBounds(15,100,165,50);
		lblFlappyBird.setBounds(190,100,165,50);
		lblAsteroids.setBounds(365,100,165,50);
		lblRunner.setBounds(540,100,165,50);
		pacManPane.setBounds(15, 150, 165, 185);
		flappyBirdPane.setBounds(190,150,165,185);
		asteroidsPane.setBounds(365,150, 165,185);
		runnerPane.setBounds(540,150,165,185);
		back.setBounds(30,360,320,50);
		play.setBounds(370,360,320,50);
		
		createButton(play);
		createButton(back);
		
		backgroundP.add(lblTitle);
		backgroundP.add(lblPacMan);
		backgroundP.add(lblFlappyBird);
		backgroundP.add(lblAsteroids);
		backgroundP.add(lblRunner);
		backgroundP.add(pacManPane);
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
