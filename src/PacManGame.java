
/**
 * Pac-Man Game
 * @author laiba
 *
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Timer;

import javax.swing.JPanel;

public class PacManGame extends JPanel implements ActionListener{

	//Declare variables
	private Dimension d;
	private final Font smallFont = new Font("Calibri", Font.BOLD, 14);
	private boolean inGame = false;
	private boolean dead = false;
	
	private final int BLOCK_SIZE = 24;
	private final int BLOCKS = 15;
	private final int SCREEN_SIZE = BLOCKS*BLOCK_SIZE;
	private final int MAX_GHOSTS = 12;
	private final int PACMAN_SPEED = 6;
	
	private int GHOSTS = 6;
	private int lives, score;
	private int[] x, y;
	private int[] ghost_x, ghost_y, ghost_z, ghost_v;
	
	private Image heart, ghost;
	private Image up, down, left, right;
	
	private int pacman_x, pacman_y, pacman_z, pacman_v;
	private int req_dx, req_dy;
	
	private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
	private final int maxSpeed = 6;
	private int currentSpeed = 3;
	private short[] screenData;
	private Timer timer;
	
	private final short levelData[] = {
			//levels
	};
	
	private PacManGame() {
		loadImages();
		initVariables();
		addKeyListener(new TAdapter());
		setFocusable(true);
		initGame();
	}
	
	private void loadImage() {
		down = new ImageIcon(.getImage());
		up = new ImageIcon(.getImage());
		left = new ImageIcon(.getImage());
		right = new ImageIcon(.getImage());
		ghost = new ImageIcon(.getImage());
		heart = new ImageIcon(.getImage());
	}
	
	
	public void showIntroScreen(Graphics2D g2d){
		String start = "Press SPACE to start";
		g2d.setColor(Color.yellow);
		g2d.drawString(start, SCREEN_SIZE / 4, 150);
	}
	
	public void drawScore(Graphics2D g2d) {
		g2d.setFont(smallFont);
		g2d.setColor(new Color(5,151,79));
		String s = "Score: "+score;
		g2d.drawString(s, SCREEN_SIZE / 2 +96, SCREEN_SIZE+16);
		
		for(int i=0; i<lives; i++) {
			g2d.drawImage(heart, i*28 + 8, SCREEN_SIZE+1, this);
			
		}
	}
	
	
	
	private void initVariables() {
		screenData = new short[BLOCKS*BLOCKS];
		d = new Dimension(400,400);
		ghost_x = new int[MAX_GHOSTS];
		ghost_y = new int[MAX_GHOSTS];
		ghost_z = new int[MAX_GHOSTS];
		ghost_v = new int[MAX_GHOSTS];
		int[] ghostSpeed = new int[MAX_GHOSTS];
		ghost_z = new int[4];
		ghost_v = new int[4];
		
		timer = new Timer(40,this);
		timer.restart();
	}
	
	
	
	private void initGame() {
		lives = 3;
		score = 0;
		initLevel();
		GHOSTS = 6;
		currentSpeed = 3;
	}
	
	private void initLevel() {
		int i;
		for(i = 0;i<BLOCKS*BLOCKS;i++) {
			screenData[i] = levelData[i];
			
		}
	}
	
	
	private void playGame(Graphics2D g2d) {
		if(dead) {
			death();
		}
		else {
			movePacman();
			drawPacman(g2d);
			moveGhosts(g2d);
			checkMaze();
		}
	}
	
	public void movePacman() {
		int pos;
		short ch;
		
		if(pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
			pos = pacman_x / BLOCK_SIZE + BLOCKS * (int)(pacman_y / BLOCK_SIZE);
					ch = screenData(pos);
			if((ch & 16) !)0){
				screenData[pos] = (short)(ch & 15);
				score++;
			}
			if(req_dex != 0 || req_dy != 0) {
				if(!((req_dx == -1 && req_dy == 0 && (ch & 1 ) != 0) || (req_dy == 1 && req_dy == 0 && (ch&4) != 0) || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0) || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
					pacman_z = req_dx;
					pacman_v = req_dy;
				}
			}
			
			if(pacman_z == -1 && pacman_y ==0 && (ch &1) != 0)|| pacman_z == 1 && pacman_v == 0 && (ch &4) != 0) || pacman_z == 0 && pacman_v == -1 && (ch & 2) != 0) || pacman_z == 0 && pacman_v == 1 && (ch & 8) != 0){
				pacman_z = 0;
				pacman_v = 0;
			}
		}
		
		pacman_x = pacman_x + PACMAN_SPEED *pacman_z;
		pacman_y = pacman_y + PACMAN_SPEED *pacman_v;
	}
	
	public void drawPacman(Graphics2D g2d) {
		if(req_dx == -1) {
			g2d.drawImage(left, pacman_x+1, pacman_y+1, this);
			
		}
		else if(req_dx == 1) {
			g2d.drawImage(right, pacman_x+1, pacman_y+1, this);
		}
		else if(req_dy == -1) {
			g2d.drawImage(up, pacman_x+1, pacman_y+1, this);
		}
		else if(req_dy == 1) {
			g2d.drawImage(down, pacman_x+1, pacman_y+1, this);
		}
		
		
	}
	
	public void moveGhosts(Graphics2D g2d) {
		int pos;
		int countl
		for(int i=0; i<GHOSTS; i++) {
			if(ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
				pos = ghost_x[i] / BLOCK_SIZE + BLOCKS + (int) (ghost_y[i] / BLOCK_SIZE);
				
				count = 0;
				if((screenData[pos] & 1) == 0 && ghost_z[i] != 1) {
					dx[count] = -1;
					dy[count] = 0;
					count++;
				}
				if((screenData[pos] & 2) == 0 && ghost_v[i] != 1) {
					dx[count] = 0;
					dy[count] = -1;
					count++;
				}
				if((screenData[pos] & 4) == 0 && ghost_z[i] != -1) {
					dx[count] = 1;
					dy[count] = 0;
					count++;
				}
				if((screenData[pos] & 8) == 0 && ghost_z[i] != 1) {
					dx[count] = 0;
					dy[count] = 1;
					count++;
				}
				
				if(count==0) {
					if(screenData[pos] & 15) == 15){
						ghost_v[i] = 0;
						ghost_z[i] = 0;
					}
					else {
						ghost_v[i] = ghost_v[i];
						ghost_z[i] = ghost_z[i];
						
					}
					else {
						count = (int)(Math.random()*count);
						
						if(count>3) {
							count = 3;
						}
						ghost_z[i] = dx[count];
						ghost_v[i] = dy[count];
					}
				}
				
				ghost_x[i] = ghost_x[i]+(ghost_z[i]* ghostSpeed[i]);
				ghost_y[i] = ghost_y[i]+(ghost_v[i]* ghostSpeed[i]);
				drawGhost(g2d, ghost_x[i] +1, ghost_y[i] +1);
				
				if(pacman_x > ((ghost_x[i]-12) && pacman_x < ghost_x[i] + 12)
					&& pacman_y > (ghost_y[i] -12)&& pacman_y < (ghost_y[i] +12)
					&& inGame) {
					dead = true;
				}
				
			}
		}
	}
	
	public void drawGhost(Graphics2D g2d, int x, int y) {
		g2d.drawImage(ghost, x, y, this);
	}
	
	public void checkMaze()
	{
		int i=0;
		boolean finished = true;
		
		while(i<BLOCKS && finished) {
			if((screenData[i] & 48) !=0) {
				finished = false;
			}
		}i++;
				
	if(finished) {
		score += 50;
		
		if(GHOSTS < MAX_GHOSTS) {
			GHOSTS++;
		}
		if(currentSpeed < maxSpeed) {
			currentSpeed++;
		}
	}       initLevel();
	}
	
	private void death() {
		lives--;
		if(lives ==0) {
			inGame = false;
		}
		
		continueLevel();
	}
	
	
	
	private void continueLevel() {
		int dy = 1;
		int random;
		
		for(int i=0;i<GHOSTS;i++) {
			ghost_y[i] = 4*BLOCK_SIZE;
			ghost_x[i] = 4*BLOCK_SIZE;
			ghost_z[i] = 0;
			ghost_v[i] = dx;
			dx = -dx; //*******************
			random = (int)Math.random()*(currentSpeed+1);
			
			if(random>currentSpeed) {
				random = currentSpeed;
	
			}
			
			ghostSpeed[i] = validSpeeds[random];
			
		}
		
		pacman_x = 7*BLOCK_SIZE;
		pacman_y = 11*BLOCK_SIZE;
		pacman_z = 0;
		pacman_y = 0;
		req_dx = 0;
		req_dy = 0;
		dead = false;
	}
	
	public void drawMaze(Graphics2D g2d) {
		short i = 0;
		int x, y;
		
		for(y = 0;y < SCREEN_SIZE; y+= BLOCK_SIZE) {
			for(x = 0; x < SCREEN_SIZE; x+= BLOCK_SIZE) {
				
				g2d.setColor(new Color(0, 72, 251));
				g2d.setStroke(new BasicStroke(5));
				
				if((screenData[i] == 0)) {
					g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
				}
				if((screenData[i] & 1) !=0) {
					g2d.drawLine(x, y, x, y+ BLOCK_SIZE-1);
				}
				if((screenData[i] & 2) !=0) {
					g2d.drawLine(x, y, x+BLOCK_SIZE-1, y);
				}
				if((screenData[i] & 4) !=0) {
					g2d.drawLine(x + BLOCK_SIZE-1, y, x+BLOCK_SIZE-1, y+BLOCK_SIZE-1);
				}
				if((screenData[i] & 8) !=0) {
					g2d.drawLine(x, y + BLOCK_SIZE-1, x+BLOCK_SIZE-1, y+BLOCK_SIZE-1);
				}
				if((screenData[i] & 16) !=0) {
					g2d.setColor(new Color(255, 255, 255));
					g2d.fillOval(x+10, y+10, 6, 6);
				}
				
				i++;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);
		
		drawMaze(g2d);
		drawScore(g2d);
		
		if(inGame) {
			playGame(g2d);
			
		}
		else {
			showIntroScreen(g2d);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	class TAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if (inGame) {
				if(key == KeyEvent.VK_LEFT) {
					req_dx = -1;
					req_dy = 0;
				}
				else if(key == KeyEvent.VK_RIGHT) {
					req_dx = 1;
					req_dy = 0;
				}
				else if(key == KeyEvent.VK_UP) {
					req_dx = 0;
					req_dy = -1;
				}
				else if(key == KeyEvent.VK_DOWN) {
					req_dx = 0;
					req_dy = 1;
				}
				else if(key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
					inGame = false;
				}
				else {
					if(key == KeyEvent.VK_SPACE) {
						inGame = true;
						initGame();
					}
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
