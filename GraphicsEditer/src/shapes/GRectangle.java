package shapes;

import java.awt.Rectangle;

	public class GRectangle extends GShape {
		
		private int px, py;
		
		public GRectangle() {
		}
		
		@Override
		public void setShape(int x1, int y1, int x2, int y2) {
			this.shape = new Rectangle(x1, y1, x2-x1, y2-y1);
		};
		
		public void resizePoint(int x2, int y2) {
			Rectangle rectangle = (Rectangle)shape;
			rectangle.setFrame(rectangle.getX(), rectangle.getY(), x2-rectangle.getX(), y2-rectangle.getY());
		}

		@Override
		public void setPoint(int x, int y) {
			this.px = x;
			this.py = y;
		}
		
		@Override
		public void movePoint(int x, int y) {
			Rectangle rectangle = (Rectangle)shape;
			rectangle.setLocation(rectangle.x+x-px, rectangle.y+y-py);
			this.px = x;
			this.py = y;
		}

	}
