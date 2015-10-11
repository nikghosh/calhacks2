import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Voronoi {
	ArrayList<Point> points;
	ArrayList<Triangle> tr;
	ArrayList<Edge> vorE;
	ArrayList<Point> vorN;

	double minX, maxX, minY, maxY;
	double mX, mY;

	double X0 = -250, X1 = 250, Y0 = -250, Y1 = 250;

	public Voronoi(String file) {
		readData(file);
	}

	public void readData(String file) {
		Scanner sc = null;
		points = new ArrayList<Point>();
		maxX = maxY = Double.NEGATIVE_INFINITY;
		minX = minY = Double.POSITIVE_INFINITY;
		mX = mY = 0;

		try {
			sc = new Scanner(new FileReader(file));
			while (sc.hasNext()) {
				double x = sc.nextDouble(), y = sc.nextDouble();
				maxX = Math.max(x, maxX);
				minX = Math.min(x, minX);
				maxY = Math.max(y, maxY);
				minY = Math.min(y, minY);
				mX += x;
				mY += y;
				points.add(new Point(x, y));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		mX /= points.size();
		mY /= points.size();
	}

	public double det(double[][] A) {
		return A[0][0] * (A[1][1] * A[2][2] - A[1][2] * A[2][1]) - A[0][1] * (A[1][0] * A[2][2] - A[1][2] * A[2][0])
				+ A[0][2] * (A[1][0] * A[2][1] - A[1][1] * A[2][0]);
	}

	public double[] row(Point a, Point b) {
		return new double[] { a.x - b.x, a.y - b.y, a.x * a.x - b.x * b.x + a.y * a.y - b.y * b.y };
	}

	public boolean inside(Point p, Triangle t) {
		double[][] M = new double[3][3];
		for (int i = 0; i < 3; i++)
			M[i] = row(t.points[i], p);
		return det(M) > 0;
	}

	public int queryPoint(Point x) {
		double min = Double.POSITIVE_INFINITY;
		int ind = 0;
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			double d = p.subtract(x).norm();
			if (d < min) {
				min = d;
				ind = i;
			}
		}
		return ind;
	}

	public void watson() {
		tr = new ArrayList<Triangle>();

		Point A = new Point((X0 + X1) / 2, 10 * Y1);
		Point B = new Point(X0, Y0);
		Point C = new Point(X1, Y0);
		Triangle st = new Triangle(A, B, C);
		tr.add(st);

		for (Point p : points) {
			ArrayList<Triangle> bt = new ArrayList<Triangle>();
			for (Triangle t : tr) {
				if (inside(p, t)) {
					bt.add(t);
				}
			}

			ArrayList<Edge> polygon = new ArrayList<Edge>();
			ArrayList<Edge> remove = new ArrayList<Edge>();

			for (Triangle t : bt) {
				for (Edge e : t.edges) {
					if (!polygon.contains(e))
						polygon.add(e);
					else
						remove.add(e);
				}
			}

			polygon.removeAll(remove);

			for (Triangle t : bt) {
				tr.remove(t);
			}

			for (Edge e : polygon) {
				tr.add(new Triangle(e.a, e.b, p));
			}
		}

		ArrayList<Triangle> remove = new ArrayList<Triangle>();
		for (Triangle t : tr) {
			for (Point p : t.points) {
				for (Point sp : st.points) {
					if (p.equals(sp))
						remove.add(t);
				}
			}
		}

		tr.removeAll(remove);
	}

	public void voronoi() {
		watson();

		vorE = new ArrayList<Edge>();
		vorN = new ArrayList<Point>();
		for (Triangle t : tr) {
			vorN.add(t.circumcenter());
		}

		int K = tr.size();

		for (int i = 0; i < K; i++) {
			Triangle t = tr.get(i);
			for (int j = 0; j < 3; j++) {
				boolean shared = false;
				Edge e = t.edges[j];
				for (int k = 0; k < K; k++) {
					if (i != k) {
						Triangle tn = tr.get(k);
						if (Arrays.asList(tn.edges).contains(e)) {
							shared = true;
							Edge vE = new Edge(vorN.get(i), vorN.get(k));
							if (!vorE.contains(vE))
								vorE.add(new Edge(vorN.get(i), vorN.get(k)));
							break;
						}
					}
				}

				if (!shared) {
					Point a = vorN.get(i);
					Point r = e.a.subtract(e.b);
					Point r1 = new Point(-r.y, r.x);

					Point c = null;
					for (int ii = 0; ii < 3; ii++) {
						if (!t.points[ii].equals(e.a) && !t.points[ii].equals(e.b))
							c = t.points[ii];
					}
					Point s = e.a.subtract(c);

					if (s.dot(r1) < 0)
						r1 = r1.opposite();

					vorE.add(new Edge(a, a.add(r1.scale(10000))));
				}
			}
		}
	}
}
