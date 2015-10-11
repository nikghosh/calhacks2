
public class Triangle {
	Point[] points; //ccw
	Edge[] edges;
	public Triangle(Point _a, Point _b, Point _c) {
		points = sort(new Point[]{_a, _b, _c});
		edges = new Edge[3];
		for(int i = 0; i < 3; i++)
			edges[i] = new Edge(points[i], points[(i+1)%3]);
	}
	
	public boolean inside(Point p) {
		Point p0 = points[0], p1 = points[1], p2 = points[2];
		double s = p0.y*p2.x - p0.x*p2.y + (p2.y - p0.y)*p.x + (p0.x - p2.x)*p.y;
		double t = p0.x*p1.y - p0.y*p1.x + (p0.y - p1.y)*p.x + (p1.x - p0.x)*p.y;
		
		return s > 0 && t > 0 && 1 - s - t > 0;
	}
	
	public double det(double[][] A) {
		return A[0][0]*(A[1][1]*A[2][2]-A[1][2]*A[2][1])
			  -A[0][1]*(A[1][0]*A[2][2]-A[1][2]*A[2][0])
			  +A[0][2]*(A[1][0]*A[2][1]-A[1][1]*A[2][0]);
	}
	
	public double[] row(Point p) {
		return new double[]{p.x, p.y, 1};
	}
	
	public Point[] sort(Point[] p) {
		double[][] A = new double[3][3];
		for(int i = 0; i < 3; i++)
			A[i] = row(p[i]);
		if(det(A) > 0)
			return p;
		else 
			return new Point[]{p[0], p[2], p[1]};
	}
	
	public Point circumcenter() {
		Point A = points[0];
		Point B = points[1];
		Point C = points[2];
		
		double x = (A.x*A.x + A.y*A.y)*(B.y- C.y) + (B.x*B.x + B.y*B.y)*(C.y - A.y) + (C.x*C.x + C.y*C.y)*(A.y - B.y);
		double y = (A.x*A.x + A.y*A.y)*(C.x- B.x) + (B.x*B.x + B.y*B.y)*(A.x - C.x) + (C.x*C.x + C.y*C.y)*(B.x - A.x);
		double D = 2*(A.x*(B.y - C.y) + B.x*(C.y - A.y) + C.x*(A.y - B.y));
		x /= D;
		y /= D;
		return new Point(x, y);
	}
	
	public String toString() {
		return points[0] + " " + points[1] + " " + points[2];			
	}
}
