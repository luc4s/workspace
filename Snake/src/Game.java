import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class Game extends JPanel {
	private final static long DELAY = 10;
	private final static int START_LENGTH = 20;
	private Controller controller;
	private Snake snake;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Thread loop;
	
	public Game(){
		this.setOpaque(false);
		snake = new Snake();
		controller = new Controller(snake);
		snake.grow(START_LENGTH);
		loop = new Thread(){
			private long tstamp;
			@Override
			public void run(){
				while(true){
					if(System.currentTimeMillis() - tstamp > DELAY){
						//do some game tick
						snake.move();
						repaint();
						tstamp = System.currentTimeMillis();
					}
				}
			}
		};
		loop.start();
	}
	
	public void restart(){
		snake = new Snake();
		snake.grow(START_LENGTH);
		controller = new Controller(snake);
	}
	
	public Controller getController(){
		return controller;
	}
	
	public void stop(){
		loop.interrupt();
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(screenSize);
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.GREEN);
		((Graphics2D)g).translate(screenSize.width/2, screenSize.height/2);
		
		for(SnakePart s : snake){
			g.setColor(Color.GREEN);
			g.fillOval((int)s.x() - 10, (int)s.y() - 10, 20, 20);
			g.setColor(Color.RED);
			g.fillOval((int)(s.x() - 5 + 5*(Math.cos(s.getOrientation()))), (int)(s.y()- 5 + 5*(Math.sin(s.getOrientation()))), 10, 10);
		}
	}
}
