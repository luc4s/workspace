package game.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;


public class Grid extends JComponent implements Iterable<Shape>{
	public static final int BLOCK_BORDER_THICKNESS = 3;
	public final static int WIDTH = 12, HEIGHT = 18;
	private LinkedList<Shape> shapes;
	private long lastMoveDelay;
	
	public Grid(){
		lastMoveDelay = System.currentTimeMillis();
		shapes = new LinkedList<Shape>();
		setBackground(new Color(0, 0, 0, 0));
	}
	
	public synchronized Block get(Vector2D p){
		for(Shape s : shapes){
			for(Block b : s)
				if(b.getPosition().equals(p))
					return b;
		}
		
		return null;
	}
	
	public synchronized boolean addShape(Shape s){
		for(Block b : s){
			if(!isEmpty(b.getPosition()) || !contains(b.getPosition()))
				return false;
		}

		shapes.add(s);
		return true;
	}
	
	@Override
	public synchronized void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		Color c1 = new Color(0, 0, 0, 0),
				c2 = new Color(100, 150, 200, 255);
		
		int w = (int) Math.round(g2d.getClipBounds().width / (double)WIDTH),
			h = (int) Math.round(g2d.getClipBounds().height / (double)HEIGHT),
			s = (w <= h) ? w : h,
			xOffset = (g2d.getClipBounds().width - s*WIDTH)/2,
			yOffset = (g2d.getClipBounds().height - s*HEIGHT)/2;
//		g2d.fillRect(0, 0, g2d.getClipBounds().width, g2d.getClipBounds().height);
//		g2d.setColor(Color.WHITE);
//		g2d.setPaint(new GradientPaint(xOffset-32, 0, c1, xOffset, 0, c2));
//		g2d.fillRect(xOffset, yOffset, s*width, s*height);
		g2d.setColor(new Color(0,0,0,100));
		g2d.fillRect(0, 0, xOffset, getHeight());
//		g2d.setPaint(new GradientPaint(xOffset + (width * s), 0, c2, xOffset + (width * s) +32, 0, c1));
		g2d.fillRect(xOffset + (WIDTH * s), 0, getWidth(), getHeight());
		int thickness = BLOCK_BORDER_THICKNESS * (getWidth() / WIDTH) / 100;
		for(Shape sh : shapes){
			for(Block b : sh){
				Vector2D p = b.getPosition();
				g2d.setColor(sh.getColor().darker());
				g2d.fillRect(xOffset+p.x()*s, yOffset+p.y()*s, s, s); 
				g2d.setColor(sh.getColor());
				   g2d.fillRect(xOffset+p.x()*s+thickness, 
							yOffset+p.y()*s+thickness, 
							s-2*thickness, 
							s-2*thickness);
			}
		}
	}

	public Iterator<Shape> iterator() {
		return shapes.iterator();
	}

	public boolean contains(Vector2D newPos) {
		return newPos.x() >= 0 && newPos.x() < WIDTH && newPos.y() < HEIGHT;
	}

	public synchronized int clean() {
		int c = 0;
		int removed = 0;
		ArrayList<Shape> line = new ArrayList<>();
		for(int i = 0;i < HEIGHT;i++){
			line.clear();
			c = 0;
			for(Shape s : shapes){
				for(Block b : s){
					if(b.getPosition().y() == i){
						line.add(s);
						c++;
					}
				}
			}
			if(c == WIDTH){
				for(Shape s : line){
					for(Block b : s){
						if(b.getPosition().y() == i){
							removed++;
							s.remove(b);
						}
					}
				}
			}
		}
		return removed;
	}

	public boolean isEmpty(Vector2D newPos) {
		return get(newPos) == null;
	}

	public synchronized boolean move(Shape s, Vector2D direction) {
		Vector2D newPos;
		for(Block b : s){
			newPos = b.getPosition().add(direction);
			if(!(contains(newPos) && (isEmpty(newPos) || s.contains(get(newPos)))))
				return false;
		}
		
		for(Block b : s)
			b.setPosition(b.getPosition().add(direction));

		s.moveAnchor(direction);
		lastMoveDelay = System.currentTimeMillis();
		return true;
	}
	
	public boolean move(Shape s){
		return move(s, s.getDirection());
	}
/*
	public boolean move(Shape s){
		Vector2D newPos;
		for(Block b : s){
			newPos = b.getPosition().add(s.getDirection());
			if(!contains(newPos) && (!isEmpty(newPos) && !s.contains(get(newPos))))
				return false;
		}
		
		for(Block b : s)
			b.setPosition(b.getPosition().add(s.getDirection()));

		repaint();
		return true;
	}
*/
	public synchronized void rotate(Shape s, boolean clockwise){
		s.rotate(clockwise);
		for(Block b : s){
			if(!isEmpty(b.getPosition()) && get(b.getPosition()) != b || !contains(b.getPosition())){		
				s.rotate(!clockwise);
				return;
			}
		}
	}

	public boolean isEmpty() {
		return shapes.isEmpty();
	}

	public int cellsWidth() {
		return WIDTH;
	}

	public long lastMove() {
		return lastMoveDelay;
	}
}
