import java.util.Iterator;
import java.util.LinkedList;


public class Snake implements Iterable<SnakePart>{
	public final static double START_SPEED = 2;
	public final static double PART_LENGTH = 20;
	
	private LinkedList<SnakePart> body;
	
	public Snake(){
		body = new LinkedList<>();
		body.add(new SnakePart(0, 0, Math.PI/2, true));
	}

	@Override
	public Iterator<SnakePart> iterator() {
		return body.iterator();
	}
	
	public double getHeadOrientation(){
		return body.getFirst().getOrientation();
	}
	
	public void grow(){
		SnakePart last = body.getLast();
		SnakePart s = new SnakePart(last.x() - (Math.cos(last.getOrientation()) * PART_LENGTH), last.y() - (Math.sin(last.getOrientation()) * PART_LENGTH), last.getOrientation(), false);
		body.add(s);
	}
	
	public void grow(int n){
		for (int i = 0; i < n; i++) {
			grow();
		}
	}
	
	private final double RATIO = 0.1;
	public void move(){
		SnakePart n = body.getFirst();
		n.setPosition(n.x() + (START_SPEED * (Math.cos(n.getOrientation()))), n.y() + START_SPEED * Math.sin(n.getOrientation()));
		for(SnakePart s : body.subList(1, body.size())){
			s.setPosition(s.x() + (START_SPEED * (Math.cos(s.getOrientation()))), s.y() + START_SPEED * Math.sin(s.getOrientation()));
			s.setOrientation(Math.atan2((n.y() - s.y()), (n.x() - s.x())));
			n = s;
		}
		
	}
	
	public void turn(double angle){
		SnakePart head = body.getFirst();
		head.setOrientation(head.getOrientation() + angle);
	}
}
