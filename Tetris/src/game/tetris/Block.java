package game.tetris;

public class Block {
	private Vector2D position;
	
	public Block(Vector2D p){
		position = p;
	}
	
	public Block(int x, int y){
		position = new Vector2D(x, y);
	}
	
	public Block(Block copy){
		position = copy.position;
	}

	public void setPosition(Vector2D v){
		position = v;
	}
	
	public Vector2D getPosition(){
		return position;
	}
	
	@Override
	public String toString(){
		return "["+position+"]";
	}
	
	@Override
	public int hashCode(){
		return position.x() << 8 + position.y();
	}
}
