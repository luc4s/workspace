package game.old;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;


public class Grid extends Component implements Iterable<Block>{
	private Block[][] cells;
	private LinkedList<Block> blocks;
	private int width, height;
	
	public Grid(int width, int height){
		cells = new Block[height][];
		for (int i = 0; i < height; i++) {
			cells[i] = new Block[width];
		}
		this.width = width;
		this.height = height;
		blocks = new LinkedList<>();
	}
	
	public Block get(Vector2D p){
		for(Block b : blocks)
			if(b.getPosition().equals(p))
				return b;
		
		return null;
	}
	
	public void addBlock(Block b){
		blocks.add(b);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(cells[0].length*32, cells.length*32);
	}
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.DARK_GRAY);
		int w = g2d.getClipBounds().width / cells[0].length,
			h = g2d.getClipBounds().height / cells.length,
			s = (w < h) ? w : h;

		g2d.fillRect(0, 0, s*width, s*height);
		
		for(Block b : blocks){
			g2d.setColor(b.getColor());
			Vector2D p = b.getPosition();
			g2d.fillRect(p.x()*s, p.y()*s, w, h);
		}
	}

	@Override
	public Iterator<Block> iterator() {
		return blocks.iterator();
	}

	public boolean contains(Vector2D newPos) {
		return newPos.x() >= 0 && newPos.x() < width && newPos.y() < height && newPos.y() >= 0;
	}
}
