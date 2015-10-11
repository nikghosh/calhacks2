
public class Edge {
	Point a, b;
	public Edge(Point _a, Point _b) {
		if(_a.x < _b.x) {
			a = _a;
			b = _b;
		}
		else {
			a = _b;
			b = _a;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		boolean same = false;
		if(o != null && o instanceof Edge) {
			Edge e = (Edge) o;
			same = a.equals(e.a) && b.equals(e.b);
		}
		
		return same;
	}
	
	public String toString() {
		return a + " " + b;
	}
}
