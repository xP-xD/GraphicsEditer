package shapes;

import java.awt.Point;
import java.awt.geom.Line2D;

	public class GLine extends GShape {
		
		private int px, py;

		public GLine() {
		}

		@Override
		public void setShape(int x1, int y1, int x2, int y2) {
			this.shape = new Line2D.Double(x1, y1, x2, y2);
		}
		
		@Override
		public void resizePoint(int x2, int y2) {
			Line2D line2D = (Line2D)shape;
			line2D.setLine(line2D.getX1(), line2D.getY1(), x2, y2);
		}


		@Override
		public void setPoint(int x, int y) {
			this.px = x;
			this.py = y;
		}

		@Override
		public void movePoint(int x, int y) {
			Line2D line2D = (Line2D)shape;
			line2D.setLine(line2D.getX1()+x-px, line2D.getY1()+y-py, line2D.getX2()+x-px, line2D.getY2()+y-py);
			this.px = x;
			this.py = y;
		}
		
		public boolean onShape(Point p) {
			return shape.getBounds().contains(p);
		}

	}
