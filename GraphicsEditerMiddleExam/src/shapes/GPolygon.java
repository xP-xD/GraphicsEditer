package shapes;

import java.awt.Polygon;
import java.util.ArrayList;

	public class GPolygon extends GShape {
		
		private int px, py;
		
		public GPolygon() {
			this.xPoint = new ArrayList<Integer>();
			this.yPoint = new ArrayList<Integer>();
		}

	
	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		clearPoint();
		this.shape = new Polygon();
		Polygon polygon = (Polygon)shape;
		polygon.addPoint(x1, y1);
		polygon.addPoint(x2, y2);
		
	}
	
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon)shape;
		polygon.addPoint(x, y);
	}
	
	public void resizePoint(int x, int y) {
		Polygon polygon = (Polygon)shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
		
	}

	@Override
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;

	}

	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon) shape;
		Polygon newPolygon = new Polygon();
		for (int i = 0; i < polygon.npoints; i++) {
			newPolygon.addPoint(polygon.xpoints[i] + x - px, polygon.ypoints[i] + y - py);
		}
		this.shape = newPolygon;
		this.px = x;
		this.py = y;
	}
	
}
