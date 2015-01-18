import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class SnakeGame {
	public static void main(String[] args) {
		final Game game = new Game();
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				final JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setUndecorated(true);
					frame.add(game);
					frame.setBackground(new Color(0, 0, 0, 0));
					frame.addKeyListener(new KeyAdapter(){
						@Override
						public void keyPressed(KeyEvent ke){
							if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) 
								System.exit(0);
							else if(ke.getKeyCode() == KeyEvent.VK_R){
								frame.removeKeyListener(game.getController());
								game.restart();
								frame.addKeyListener(game.getController());
								
							}
						}
					});
					frame.addKeyListener(game.getController());
					frame.pack();
					frame.setVisible(true);
			}
		});
	}
}
