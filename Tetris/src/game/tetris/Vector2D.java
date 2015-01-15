package game.tetris;

public class Vector2D {
	public static final Vector2D NULL = new Vector2D(0, 0);
	public static final Vector2D DOWNWARD = new Vector2D(0, -1);
	
	private int x, y;
	public Vector2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int x(){
		return x;
	}
	public int y(){
		return y;
	}
	public Vector2D add(Vector2D v){
		return new Vector2D(x+v.x, y+v.y);
	}
	public Vector2D opposite(){
		return new Vector2D(-x, -y);
	}
	@Override
	public boolean equals(Object o){
		if(!o.getClass().equals(this.getClass()))
			return false;
		
		Vector2D v = (Vector2D)o;
		return v.x == x && v.y == y;
	}
	@Override
	public String toString(){
		return x+";"+y;
	}
}
