import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Voronoi merch;
	Voronoi loc;
	int[] color;
	int cells;

	public DrawPanel(int width, int height) {
		super();
		setBackground(Color.BLACK);
		merch = new Voronoi("va_merc_loc");
		loc = new Voronoi("va_loc");
		merch.voronoi();
		cells = merch.points.size();
		color = new int[cells];
		Random rand = new Random();
		for (int i = 0; i < cells; i++)
			color[i] = rand.nextInt(16777215);
	}

	public void drawPoint(Graphics2D g, Color color, Point p, double sX, double sY, double mX, double mY) {
		int w = getWidth(), h = getHeight();
		g.setColor(color);
		double x = (p.x - mX) * sX;
		double y = (p.y - mY) * sY;
		Ellipse2D.Double point = new Ellipse2D.Double(x + w / 2 - 4, h / 2 - y - 4, 8, 8);
		g.fill(point);
	}

	public void drawText(Graphics2D g, Color color, String str, Point p, double sX, double sY, double mX, double mY) {
		int w = getWidth(), h = getHeight();
		g.setColor(color);
		g.setFont(new Font("SansSerif", Font.BOLD, 18));
		double x = (p.x - mX) * sX;
		double y = (p.y - mY) * sY;
		g.drawString(str, (int) (x + w / 2 - 2), (int) (h / 2 - y + 4));
	}

	public void drawEdge(Graphics2D g, Color color, Edge e, double sX, double sY, double mX, double mY) {
		int w = getWidth(), h = getHeight();
		g.setColor(color);
		g.setStroke(new BasicStroke(2));
		double x1 = (e.a.x - mX) * sX;
		double x2 = (e.b.x - mX) * sX;
		double y1 = (e.a.y - mY) * sY;
		double y2 = (e.b.y - mY) * sY;
		Line2D.Double l = new Line2D.Double(w / 2 + x1, h / 2 - y1, w / 2 + x2, h / 2 - y2);
		g.draw(l);
	}

	public double[] scale(Voronoi v) {
		int w = getWidth(), h = getHeight();

		double scaleX = .6 * w / Math.max(v.maxX - v.mX, v.mX - v.minX);
		double scaleY = .9 * h / Math.max(v.maxY - v.mY, v.mY - v.minY);
		return new double[] { scaleX, scaleY };
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		super.paintComponent(g);
		double[] scale = scale(loc);
		// g2.setColor(Color.BLUE);
		// drawPoints(g2, loc.points, scale[0], scale[1], loc.mX, loc.mY);

		scale = scale(loc);

		for (Edge e : merch.vorE)
			drawEdge(g2, Color.WHITE, e, scale[0], scale[1], loc.mX, loc.mY);
		for (Point p : loc.points)
			drawPoint(g2, new Color(color[merch.queryPoint(p)]), p, scale[0], scale[1], loc.mX, loc.mY);
		for (int i = 0; i < cells; i++)
			drawText(g2, new Color(color[i]).brighter(), "$", merch.points.get(i), scale[0], scale[1], loc.mX, loc.mY);
	}

	public static void main(String[] args) {
		int width = 500, height = 500;
		DrawPanel panel = new DrawPanel(width, height);
		JFrame app = new JFrame("Voronoi Diagram");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.add(panel);
		app.setSize(width, height);
		app.setVisible(true);
	}

}
