package com.sonny.framework;

import java.awt.event.KeyEvent;

import com.sonny.characters.Pipe;

public class KeyHandler {

	private Pane pane;
	private int upSpeed = 10;

	public KeyHandler(Pane pane) {
		this.pane = pane;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getExtendedKeyCode();

		if (key == e.VK_UP) {
			for (int i = 0; i < pane.pipeList.size(); i++)
				pane.pipeList.get(i).changeY(-upSpeed);
		} else if (key == e.VK_DOWN) {
			for (int i = 0; i < pane.pipeList.size(); i++)
				pane.pipeList.get(i).changeY(+upSpeed);
		}else if (key ==e.VK_ESCAPE && !pane.getIsPause()){
			pane.pause();
		}else if (key==e.VK_ESCAPE && pane.getIsPause()){
			pane.unpause();
		}
	}

	public void keyReleased(KeyEvent e) {

		for (int i = 0; i < pane.pipeList.size(); i++) {
			pane.pipeList.get(i).changeY(0);
		}

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
