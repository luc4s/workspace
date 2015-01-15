package game.old;
import java.awt.Color;


public class Block {
	private Vector2D direction,
					position;
	private Color color;
	
	public Block(Vector2D p, Vector2D d, Color c){
		direction = d;
		position = p;
		color = c;
		color = c;
	}

	public void setPosition(Vector2D v){
		position = v;
	}
	
	public Vector2D getPosition(){
		return position;
	}
	public Color getColor() {
		return color;
	}

	public Vector2D getDirection() {
		return direction;
	}
	
	@Override
	public String toString(){
		return color+" "+position+" "+direction;
	}
}
