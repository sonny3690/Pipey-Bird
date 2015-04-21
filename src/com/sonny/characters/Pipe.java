package com.sonny.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.sonny.framework.Pane;

public class Pipe {

	private int camSpeed;
	private int x, y;
	private int pipeSize = 70;
	private int velY = 0;

	private Bird bird;
	private Pane pane;

	private boolean isCollided = false;

	private boolean passed = false;
	private int inc = 0;

	public Pipe(int x, int y, int camSpeed, Pane pane, Bird bird) {
		this.camSpeed = camSpeed;
		this.x = x;
		this.y = y;
		this.bird = bird;
		this.pane = pane;
		System.out.println(x);
	}

	public void tick() {
		x -= camSpeed;

		if (getBottomRectangle().y - 256 < 30) {
			if (velY < 0)
				velY = 0;
		}

		if (getBottomRectangle().y >= 738) {
			if (velY > 0)
				velY = 0;
		}

		if (x < bird.getX() && !passed) {
			pane.addScore();
			passed = true;
		}
		y += velY;

		if (inc % 1000 == 0) {
			camSpeed += 1;
		}

		inc++;
		checkCollision();
	}

	public void drawPipe(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y + 256, pipeSize, 768);
		g.fillRect(x, 0, pipeSize, y);
	}

	public void changeY(int z) {
		velY = z;
	}

	public void checkCollision() {
		if (bird.getBounds().intersects(getTopRectangle())
				|| bird.getBounds().intersects(getBottomRectangle())
				&& !isCollided) {
			isCollided = true;
			pane.endGame();

		}
	}

	public Rectangle getBottomRectangle() {
		return new Rectangle(x, y + 256, pipeSize, 768);
	}

	public Rectangle getTopRectangle() {
		return new Rectangle(x, 0, pipeSize, y);
	}

}
