
public class SnakePart {
	private double x, y, or;
	private boolean head;
	
	public SnakePart(final double xx, final double yy, final double orientation, final boolean isHead){
		x = xx;
		y = yy;
		or = orientation;
		head = isHead;
	}
	
	public double x(){ return x; }
	public double y(){ return y; }
	public double getOrientation(){ return or; }
	public boolean isHead(){ return head; }

	public void setPosition(double xx, double yy) {
		x = xx;
		y = yy;
	}
	
	public void setOrientation(double angle){
		or = angle;
	}
}
