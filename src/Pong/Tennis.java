package Pong;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tennis extends Applet implements Runnable, KeyListener {
	final int WIDTH = 800;
	final int HEIGHT = 500;
	Thread thread;
	ManPaddle p1;
	AiPaddle p2;
	Ball b1;
	boolean gameStarted;
	Graphics gr;
	Image img;
	
	
	
	public void init() {
		this.resize(WIDTH, HEIGHT);
		gameStarted = false;
		this.addKeyListener(this);
		p1 = new ManPaddle(1);
		b1 = new Ball();
		p2 = new AiPaddle(2, b1);
		img = createImage(WIDTH, HEIGHT);
		gr = img.getGraphics();
		thread = new Thread(this);
		thread.start();
	}
	public void paint(Graphics g) {
		gr.setColor(Color.black);
		gr.fillRect(0, 0, WIDTH, HEIGHT);
		if(b1.getX() < -10 || b1.getX() > 810) {
			gr.setColor(Color.white);
			gr.drawString("Game Over", 350, 250);
		}
		else {
			p1.draw(gr);
			b1.draw(gr);
			p2.draw(gr);
		}
		if(!gameStarted)
			gr.drawString("Press Enter", 350, 200);
		g.drawImage(img, 0, 0, this);
	}
	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		for(;;) {
			if(gameStarted) {
			p1.move();
			p2.move();
			b1.move();
			b1.checkPaddle(p1, p2);
			repaint();
			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			p1.setUpAccel(true);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			p1.setDownAccel(true);
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			gameStarted = true;
		}
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			p1.setUpAccel(false);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			p1.setDownAccel(false);
		}
	}
	public void keyTyped(KeyEvent arg0) {
		
	}
}
