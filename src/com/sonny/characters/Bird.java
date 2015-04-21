package com.sonny.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sonny.framework.Pane;

public class Bird {

	private int x, y, velY;
	private Random rand;
	private int inc = 0;
	private int maxSpeed = 5;

	private BufferedImage birdImage;
	private BufferedImage[][] birdImages = new BufferedImage[3][3];
	private int birdIndex;
	private Pane pane;
	private int spriteSpeed = 3;
	private int birdColor;

	// smaller is faster

	public Bird(int x, int y, Pane pane) {
		this.x = x;
		this.y = y;
		this.pane = pane;
		rand = new Random();
		birdColor = rand.nextInt(3);

		try {
			birdImage = ImageIO
					.read(new File(
							"E://Programming//Java Workspace//Pipey Bird//Sprites.png"));

			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					birdImages[i][j] = birdImage.getSubimage(40 * i,
							0 + 40 * j, 40, 40);
				}

		} catch (Exception e) {
			System.err.println("Image processing error");
		}

	}

	public void tick() {
		y += velY;

		if (inc % 7 == 0) {
			makeDecision();
		}

		if (inc % spriteSpeed == 0) {
			if (birdIndex <= 1) {
				birdIndex++;
			} else {
				birdIndex = 0;
			}
		}

		if (inc % 200 == 0) {
			maxSpeed += 2;

		}

		if (y <= 32) {
			y += maxSpeed;
		} else if (y >= 768-maxSpeed-40) {
			y -= maxSpeed;
		}

		inc++;
	}

	public void render(Graphics g) {
		// g.setColor(Color.RED);
		// g.fillRect(x, y, 32, 32);

		// System.out.println(birdColor);

		try {
			g.drawImage(birdImages[birdColor][birdIndex], x, y, null);
			// g.fillRect(x, y, 40, 40);
		} catch (Exception e) {
			System.out.println("drawing error");
		}

		// g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}

	public void makeDecision() {
		int increment = rand.nextInt(maxSpeed) - (maxSpeed - 1) / 2;
		velY = increment;
	}

	public int getX() {
		return x;
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 3, y + 8, 34, 23);
	}

	public void setBirdColor(int x) {
		birdColor = x;
	}

}
