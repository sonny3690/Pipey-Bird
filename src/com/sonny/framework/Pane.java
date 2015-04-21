package com.sonny.framework;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.sonny.characters.Bird;
import com.sonny.characters.Pipe;

public class Pane extends JPanel implements ActionListener, KeyListener,
		MouseListener {

	private int screenWidth = 1366;
	private int screenHeight = 768;
	private int camSpeed = 8;
	private int score = 0;
	public static JOptionPane endGameDialog;
	private JFrame frame;
	private int highScore = 0;
	private BufferedImage bgImage;

	private int camTotal = 0;
	private boolean isPause = false;

	private Bird bird;
	private Pipe pipe;
	private KeyHandler kh;
	private Timer t1;
	private MouseHandler mouseHandler;
	private Scanner fileScanner;
	private boolean frameSet = false;

	public BufferedImage menuBGImage;
	public BufferedImage[] bgImages;

	public LinkedList<Pipe> pipeList = new LinkedList<Pipe>();

	public Pane() {
		setUI();
		loadHighScore();
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setMinimumSize(new Dimension(screenWidth, screenHeight));
		setMaximumSize(new Dimension(screenWidth, screenHeight));
		setDoubleBuffered(true);

		bird = new Bird(1366 * 1 / 3 - 16, 768 * 1 / 2 - 16, this);

		for (int i = 0; i < 200; i++)
			addPipe(700 * i, 250, camSpeed);

		kh = new KeyHandler(this);

		if (!frameSet)
			setFrame();
		frameSet = true;

		setBackgroundImage();

		t1 = new Timer(1000 / 60, this);
		t1.start();

		mouseHandler = new MouseHandler();

		frame.addKeyListener(this);
		frame.addMouseListener(this);
	}

	public void setBackgroundImage() {
		try {
			bgImage = ImageIO
					.read(new File(
							"E://Programming//Java Workspace//Pipey Bird//background extended.png"));
			menuBGImage = ImageIO
					.read(new File(
							"E://Programming//Java Workspace//Pipey Bird//Menu Button Background.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Pipey Bird");
		frame.add(this);
		frame.setSize(screenWidth, screenHeight);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (!isPause) {
			tick();
		}
		render();
	}

	public void tick() {
		bird.tick();

		if (highScore < 10)
			bird.setBirdColor(0);
		else if (highScore >= 10) {
			bird.setBirdColor(1);
			//System.out.println("success");
		}

		// else if (highScore >=40 && highScore < 100) bird.setBirdColor(2);
		// else golden bird*/

		for (int i = 0; i < pipeList.size(); i++) {
			pipeList.get(i).tick();
		}

		camTotal += camSpeed;
	}

	public void render() {
		this.repaint();
	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		// g.fillRect (0,0, screenWidth, screenHeight);
		Graphics2D g2d = (Graphics2D) g;
		try {
			if (camTotal <= 4800) {
				g2d.drawImage(bgImage.getSubimage(camTotal / 3, 0, screenWidth,screenHeight), 0, 0, null);
			} else
				camTotal = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.PLAIN, 50));
		g.drawString(String.valueOf(score), screenWidth / 2, 50);

		bird.render(g);

		for (int i = 0; i < pipeList.size(); i++) {
			pipeList.get(i).drawPipe(g);
		}

		if (isPause) {
			g2d.setColor(Color.BLACK);
			AlphaComposite acomp = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.8F);
			g2d.setComposite(acomp);
			g2d.fillRect(0, 0, screenWidth, screenHeight);

			// inner contents
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Times New Roman", Font.PLAIN, 60));
			g2d.drawImage(menuBGImage, screenWidth / 2 - 165, 10, null);
			g2d.setFont(new Font("Times New Roman", Font.PLAIN, 50));
			g2d.drawString("MENU", screenWidth / 2 - 80, 90);
			g2d.setColor(new Color(153, 102, 0));
			g2d.fillRect(screenWidth / 2 - 130, 90 + 150, 240, 80);
			g2d.fillRect(screenWidth / 2 - 110, 90 + 300, 190, 80);
			g2d.fillRect(screenWidth / 2 - 90, 90 + 450, 150, 80);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Characters", screenWidth / 2 - 120, 90 + 200);
			g2d.drawString("Settings", screenWidth / 2 - 100, 90 + 350);
			g2d.drawString("Exit", screenWidth / 2 - 60, 90 + 500);

			if (mouseHandler.getRectangle().intersects(
					new Rectangle(screenWidth / 2 - 130, 90 + 150, 240, 80))) {

			} else if (mouseHandler.getRectangle().intersects(
					new Rectangle(screenWidth / 2 - 110, 90 + 300, 190, 80))) {

			} else if (mouseHandler.getRectangle().intersects(
					new Rectangle(screenWidth / 2 - 90, 90 + 450, 150, 80))) {
				System.exit(0);
			}
		}

	}

	public void addPipe(int x, int y, int camSpeed) {
		pipeList.add(new Pipe(x, y, camSpeed, this, bird));
	}

	public void addScore() {
		score++;
	}

	public static void main(String[] args) {
		new Pane();

	}

	public void endGame() {
		if (score > highScore) {
			highScore = score;
			JOptionPane.showMessageDialog(null, "New High Score!\n" + "Score: "
					+ score + "\n" + "High Score: " + highScore);
		} else {

			JOptionPane.showMessageDialog(null, "Score: " + score + "\n"
					+ "High Score: " + highScore);
		}

		saveFiles();
		scoreBoard();

		for (int i = 0; i < pipeList.size(); i++)
			pipeList.remove();

		frame.remove(this);
		frame.dispose();
		new Pane();

	}

	public void saveFiles() {
		try {
			File file = new File("res.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(Integer.toString(highScore));
			out.close();
			System.out.println("File Saved");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadHighScore (){
		try{
		fileScanner = new Scanner(new File("C://Users//Sonny//workspace//PipeyBird//res.txt"));
			highScore = Integer.valueOf(fileScanner.next());
			//System.out.println("highScore");
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void scoreBoard() {

		this.getGraphics().fillRect(0, 0, 100, 100);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		kh.keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		kh.keyReleased(arg0);

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		kh.keyTyped(arg0);
	}

	public int getScore() {
		return score;
	}

	public void addCamSpeed() {
		this.camSpeed += 2;

	}

	public void pause() {
		isPause = true;
	}

	public void unpause() {
		isPause = false;
	}

	public void setUI() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.err.println("UI ERROR");
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		mouseHandler.mouseClicked(arg0);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseHandler.mouseEntered(arg0);

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseHandler.mouseExited(arg0);

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouseHandler.mousePressed(arg0);

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseHandler.mouseReleased(arg0);

	}

	public void setIsPause(boolean isPause) {
		this.isPause = isPause;
	}

	public boolean getIsPause() {
		return isPause;
	}

}
