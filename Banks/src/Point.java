import java.awt.geom.Point2D;

public class Point extends Point2D.Double{
	private static final long serialVersionUID = 1L;

	public Point(double x, double y) {
		super(x, y);
	}
	
	public double norm() {
		return Math.hypot(x, y);
	}
	
	public Point scale(double s) {
		return new Point(x*s, y*s);
	}
	
	public Point add(Point o) {
		return new Point(x + o.x, y + o.y);
	}
	
	public Point subtract(Point o) {
		return this.add(o.opposite());
	}
	
	public Point midPoint(Point o) {
		return new Point((x + o.x)/2, (y + o.y)/2);
	}
	
	public Point opposite() {
		return new Point(-x, -y);
	}
	
	public double dot(Point o) {
		return x*o.x + y*o.y;
	}
	public double angle(Point o) {
		return Math.acos(this.dot(o)/(norm()*o.norm()));
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean same = false;
		Point p = (Point) o;
		double eps = .000001;
		if(o != null && o instanceof Point) {
			same = Math.abs(x - p.x) < eps && Math.abs(y - p.y) < eps;
		}
		
		return same;
	}
}
