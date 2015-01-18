import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;


public class Controller extends KeyAdapter implements KeyListener {
	private final static double THETA = 1e-2;
	private final static double ANGLE_INC = Math.PI/16;
	
	private Snake snake;
	private boolean[] directions;
	
	
	public Controller(final Snake snake){
		this.snake = snake;
		directions = new boolean[4];
		new Timer(50, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				snake.turn(
							directions[0] ? computeAngle(Math.PI) : 
								directions[1] ? computeAngle(-Math.PI/2) :
									directions[2] ? computeAngle(0) :
										directions[3] ? computeAngle(Math.PI/2) : 0
						);
			}
		}).start();
	}
	
	@Override
	public void keyPressed(final KeyEvent key) {
		process(key.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(final KeyEvent key) {
		process(key.getKeyCode(), false);
	}
	
	private void process(final int keyCode, final boolean pressed){
		switch(keyCode){
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_DOWN:
				directions[0] = false;
				directions[1] = false;
				directions[2] = false;
				directions[3] = false;
				directions[keyCode - 37] = pressed;
		}
	}
	
	private double computeAngle(double target){
		double delta = target - snake.getHeadOrientation();
		if(Math.abs(delta) > Math.abs(invert(delta)))
			delta = invert(delta);

		if(Math.abs(delta) <= THETA) 
			return 0;
		
		return Math.signum(delta) * ANGLE_INC;
	}
	
	private double invert(double angle){
		if(angle < 0)
			angle += 2*Math.PI;
		else
			angle -= 2*Math.PI;
		
		return angle;
	}
}
