package game.tetris;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Tetris {
	private JLabel d_score, d_level;
	private Thread gameLoop;
	private int level;
	private int score;
	private long timeOut,
				fastTimeOut,
				gameTick;
	private boolean stuck;
	private Grid grid;
	private Controller ctrl;
	private Font font;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				(new Tetris()).start();
			}
		});
	}
	
	private Tetris(){
		stuck = false;
		grid = new Grid();
		ctrl = new Controller(grid);
		level = 1;
		
		timeOut = 5000;
		gameTick = 300;
		fastTimeOut = 600;
		
		try {
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
					Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/tetrominoes.ttf"))
					);
			font = new Font("Tetrominoes", Font.BOLD, 64);
		} catch (FontFormatException | IOException e) {
			font = Font.getFont("Arial");
			System.err.println(e);
		}
		Font f = new Font("Arial", Font.PLAIN, 64);
		d_score = new JLabel("0");//SCORE 0");
			d_score.setForeground(Color.WHITE);
			d_score.setFont(f);
		d_level = new JLabel("1");
			d_level.setForeground(Color.WHITE);
			d_level.setFont(f);
	}

	private void start(){
		final JFrame jf = new JFrame();
			jf.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			jf.setUndecorated(true);
			jf.setBackground(new Color(0, 0, 0, 0));
			jf.addKeyListener(ctrl);
			jf.addKeyListener(new KeyAdapter(){
				@Override
				public void keyPressed(KeyEvent ke){
					if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
				}
			});
			
		final JLayeredPane jlp = new JLayeredPane();
			jlp.add(grid);
			grid.setSize(jf.getPreferredSize());
			grid.setLocation(0, 0);
			
		final JPanel scorePanel = new JPanel();
			scorePanel.setLayout(new FlowLayout());
			JLabel scoreLabel = new JLabel("SCORE");
				scoreLabel.setFont(font);
			scorePanel.add(scoreLabel);
			scorePanel.add(d_score);
			scorePanel.add(d_level);
			scorePanel.setOpaque(false);
//			scorePanel.setBackground(Color.GREEN);
			scorePanel.setSize(jf.getPreferredSize());
			jlp.add(scorePanel);
			jlp.moveToFront(scorePanel);

			
		jf.add(jlp);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameLoop = new Thread(){
			@Override
			public void run(){
				long delay = System.currentTimeMillis();
				while(!stuck){
					if(System.currentTimeMillis() - delay >= gameTick){
						tick();
						delay = System.currentTimeMillis();
					}
					grid.repaint();
				}

				
				final JPanel jp = new JPanel(){
					private static final long serialVersionUID = 1L;
					private boolean draw = true;
					private Font fnt = font;
					private String gameOver = "GAME OVER", 
									pressKey = "PRESS ANY KEY";
					@Override
					public void paint(Graphics g){
						g.setColor(new Color(0, 0, 0, 200));
						g.fillRect(0, 0, getWidth(), getHeight());
						g.setColor(Color.RED.darker());
						g.setFont(fnt);
						FontMetrics fm = g.getFontMetrics(fnt);
						g.drawString(gameOver, (getWidth() - fm.stringWidth(gameOver)) / 2, getHeight() * 1/3);
						
						if(draw) 
							g.drawString(pressKey, (getWidth() - fm.stringWidth(pressKey)) / 2, getHeight() * 1/3 + fm.getHeight() + 32);
						
						draw = !draw;
					}
				};
				jlp.add(jp);
					jp.setSize(grid.getSize());
					jp.setLocation(0, 0);
					jlp.moveToBack(grid);
				
				jf.addKeyListener(new KeyAdapter(){
					@Override
					public void keyPressed(KeyEvent ke){
						System.exit(0);
					}
				});
				
				jf.setAutoRequestFocus(true);
				jf.setVisible(true);
			}
		};
		jf.setVisible(true);
		gameLoop.start();
		popNewRandomShape();
	}

	private long timeSnapshot = System.currentTimeMillis();
	private void tick() {
		boolean moved = false,
				atLeastOneMoved = false,
				pop = true;
		int cleaned = 0;
		
		for(Shape s : grid){
			moved = grid.move(s);
			atLeastOneMoved = atLeastOneMoved || moved;
		}
		if(!moved) { //check if the last piece move => the one that is being controlled
			if(System.currentTimeMillis() - timeSnapshot > timeOut
					|| System.currentTimeMillis() - grid.lastMove() > fastTimeOut){
				cleaned = grid.clean();
				if(cleaned > 0) {
					updateScore(cleaned);
					updateDifficulty();
				}
				pop = popNewRandomShape();				
			}
		}
		else
			timeSnapshot = System.currentTimeMillis();
		
		
		stuck = !((atLeastOneMoved || grid.isEmpty() || pop) && pop);
		grid.repaint();
	}
	
	private void updateDifficulty(){
		gameTick = 100 + 300/level;
		timeOut = 300 + 900/level;
	}
	private void updateScore(int cleaned) {
		int baseScore = 50;
		switch(cleaned){
			case 2:
				baseScore = 110;
				break;
			case 3:
				baseScore = 330;
				break;
			case 4:
				baseScore = 500;
				break;
		}
		score += baseScore * (level + 1);
		level = (int)Math.ceil(
				Math.pow(Math.ceil(score/100d), 1.2));
		
		d_score.setText("SCORE: "+String.valueOf(score));
		d_level.setText("LEVEL "+String.valueOf(level));
	}

	private boolean popNewRandomShape() {
		Shape newShape = Shape.generateNewRandomShape();
		ctrl.setCurrentShape(newShape);
		int offset = grid.cellsWidth()/2 - Math.round(newShape.getWidth()/2);
		if(!grid.move(newShape, new Vector2D(offset, -1))) 	return false;
		return grid.addShape(newShape);
	}
	
}
