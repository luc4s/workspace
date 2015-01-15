package game.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Controller extends KeyAdapter{
	private Shape currentShape;
	private final Grid grid;
	
	public Controller(final Grid gr){
		this.grid = gr;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(currentShape != null)
			process(arg0.getKeyCode());
	}
	
	private void process(int keyCode){
		Vector2D move = new Vector2D(0, 0);
		switch(keyCode){
			case KeyEvent.VK_RIGHT:
				move = new Vector2D(1, 0);
				break;
			case KeyEvent.VK_DOWN:
				move = new Vector2D(0, 1);
				break;
			case KeyEvent.VK_LEFT:
				move = new Vector2D(-1, 0);
				break;
			case KeyEvent.VK_SPACE:
				rotate();
				break;
		}
		grid.move(currentShape, move);
	}

	private void rotate() {
		grid.rotate(currentShape, true);
	}

	public void setCurrentShape(Shape newShape) {
		currentShape = newShape;
	}
}
