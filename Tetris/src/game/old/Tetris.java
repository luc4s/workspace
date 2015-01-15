package game.old;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Tetris {
	private Thread gameLoop;
	private int delay;
	private Grid grid;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				(new Tetris()).start();
			}
		});
	}
	
	private Tetris(){
		delay = 500;
		grid = new Grid(10, 20);
	}

	private void start() {
		JFrame jf = new JFrame();
		jf.setPreferredSize(new Dimension(250, 500));
		jf.add(grid);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		Block b1 = new Block(new Vector2D(5, 7), new Vector2D(0, 1), Color.RED),
				b2 = new Block(new Vector2D(4, 7), new Vector2D(0, 1), Color.BLUE),
				b3 = new Block(new Vector2D(5, 7), new Vector2D(0, 1), Color.GREEN);
		grid.addBlock(b1);
		grid.addBlock(b2);
		grid.addBlock(b3);
		gameLoop = new Thread(){
			@Override
			public void run(){
				while(!stuck()){
					tick();
					try{
						sleep(delay);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		gameLoop.start();
	}

	private void tick() {
		for(Block b : grid)
			if(canMove(b)) {
				move(b);
			}
		grid.repaint();
	}
	
	private void move(Block b) {
		b.setPosition(b.getPosition().add(b.getDirection()));
	}

	private boolean canMove(Block b){
		Vector2D newPos = b.getPosition().add(b.getDirection());
		return (grid.get(newPos) == null && grid.contains(newPos));
	}
	
	private boolean stuck() {
		return false;
	}
}
