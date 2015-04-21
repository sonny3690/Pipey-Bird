package com.sonny.framework;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class MouseHandler {

	private int x, y;

	public MouseHandler() {

	}

	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
	
	public Rectangle getRectangle (){
		return new Rectangle (x,y,1,1);
	}
	

}
