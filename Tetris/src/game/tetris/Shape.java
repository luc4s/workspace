package game.tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Shape implements Iterable<Block>{

	private final static Vector2D DOWNWARD = new Vector2D(0, 1);
	private final static List<Shape> SHAPES = Arrays.asList(
			new Shape(
				Color.WHITE, 
				  "0000"
				+ "0110"
				+ "0110"
				+ "0000"
				)
			,
			new Shape(
				Color.RED,
				new String[]{
						"0000" + "1111" + "0000" + "0000",
						"0100" + "0100" + "0100" + "0100"
					}
			),
			new Shape(
				Color.BLUE,
				new String[]{
						"1100"+
						"0110"+
						"0000"+
						"0000",
						
						"0010"+
						"0110"+
						"0100"+
						"0000"
				}
			),
			new Shape(
					Color.GREEN,
					new String[]{
							"0011"+
							"0110"+
							"0000"+
							"0000",
							"0100"+
							"0110"+
							"0010"+
							"0000"
					}),
			new Shape(
					Color.MAGENTA,
					new String[]{
							"0100"+
							"1110"+
							"0000"+
							"0000",
							
							"1000"+
							"1100"+
							"1000"+
							"0000",
							
							"1110"+
							"0100"+
							"0000"+
							"0000",
							
							"0100"+
							"1100"+
							"0100"+
							"0000"
					}),
			new Shape(Color.CYAN,
					
					new String[]{
						"1000"+
						"1000"+
						"1100"+
						"0000",
						
						"1110"+
						"1000"+
						"0000"+
						"0000",
						
						"1100"+
						"0100"+
						"0100"+
						"0000",
						
						"0010"+
						"1110"+
						"0000"+
						"0000"
			}),
			new Shape(
					Color.YELLOW,
					new String[]{
						"0100"+
						"0100"+
						"1100"+
						"0000",
						
						"1000"+
						"1110"+
						"0000"+
						"0000",
						
						"1100"+
						"1000"+
						"1000"+
						"0000",
						
						"1110"+
						"0010"+
						"0000"+
						"0000"
			}));
	private static final int DEBUG = -1;
	
	private final ArrayList<Block> blocks;
	private final Vector2D direction;
	private Vector2D anchor;
	private final Color color;
	private String[] shapePatterns;
	private int width;
	private int currentPattern = 0;

	/*
	private Shape(Color c, Block b, Block ... blocks){
		this.blocks = new ArrayList<>();
		this.blocks.add(b);
		for(Block bl : blocks)
			this.blocks.add(bl);
		
		int x1=this.blocks.size(), x2=0;
		for(Block block : this.blocks){
			if(block.getPosition().x() < x1) x1 = block.getPosition().x();
			if(block.getPosition().x() > x2) x2 = block.getPosition().x();
		}
		width = x2 - x1 + 1;
		direction = DOWNWARD;
		color = c;
	}
	//*/
	//*
	private Shape(Color clr, String shapePattern){
		anchor = new Vector2D(0, 0);
		color = clr;
		direction = DOWNWARD;
		blocks = new ArrayList<>();
		shapePatterns = new String[]{shapePattern};
		
		currentPattern = 0;
		width = setBlocks(shapePatterns[currentPattern], true);
	}
	
	private Shape(Color clr, String[] patterns){
		this(clr, patterns[0]);
		shapePatterns = patterns;
	}
	
	private int setBlocks(String pattern, boolean build){
		int minY = 4, maxY = 0, j = 0;
		Vector2D v;
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if(c == '1'){
				v = new Vector2D(i % 4, (int)(i/4d));
				if(build)
					blocks.add(new Block(v));
				else{
					v = v.add(anchor);
					blocks.get(j).setPosition(v);
				}
				
				if(i%4 < minY) minY = i%4;
				if(i%4 > maxY) maxY = i%4;
				j++;
			}
		}
		return maxY - minY + 1;
	}
	//*/
	public Shape(Shape shape) {
		direction = DOWNWARD;
		color = shape.color;
		this.blocks = new ArrayList<>();
		for(Block b : shape)
			blocks.add(new Block(b));
		shapePatterns = shape.shapePatterns;
		width = shape.width;
		anchor = shape.anchor;
	}

	public void add(Block block){
		blocks.add(block);
	}
	
	public void remove(Block b){
		blocks.remove(b);
	}
	
	public Vector2D getDirection(){
		return direction;
	}
	
	@Override
	public Iterator<Block> iterator() {
		return new ShapeIterator();
	}

	public boolean contains(Block b) {
		return blocks.contains(b);
	}

	public static Shape generateNewRandomShape() {
		Random rnd = new Random();
		
		if(DEBUG >= 0)
			return new Shape(SHAPES.get(DEBUG));
		else
		return new Shape(SHAPES.get(rnd.nextInt(SHAPES.size())));
	}
	
	@Override
	public String toString(){
		return blocks.toString();
	}
	

	public Color getColor() {
		return color;
	}
	
	public void rotate(boolean clockWise){
		width = setBlocks(shapePatterns[(currentPattern += (clockWise ? 1 : 3)) % shapePatterns.length], false);
//		int r = (clockWise ? 1 : -1);
//		state += r;
//		state = (state < 0 ? state + )
	}

	public class ShapeIterator implements Iterator<Block> {
		private PriorityQueue<Block> queue;
		
		private ShapeIterator(){
			queue = new PriorityQueue<Block>(
					Grid.WIDTH * Grid.HEIGHT, new Comparator<Block>(){
						@Override
						public int compare(Block arg0, Block arg1) {
							return arg0.getPosition().y() - arg1.getPosition().y();
						}
					}
				);
			
			for(Block b : blocks)
				queue.add(b);
		}
		
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public Block next() {
			return queue.remove();
		}

		@Override
		public void remove() {}

	}

	public int getX() {
		int x = blocks.get(0).getPosition().x();
		for(Block b : this){
			if(b.getPosition().x() < x) x = b.getPosition().x();
		}
		return x;
	}

	public int getWidth() {
		return width;
	}

	public void moveAnchor(Vector2D direction2) {
		anchor = anchor.add(direction2);
	}
}
