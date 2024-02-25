package shapes;

import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;

public class GCubicCurve extends GShape {

	private int px, py;

	public GCubicCurve() {
		this.xPoint = new ArrayList<Integer>();
		this.yPoint = new ArrayList<Integer>();
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		// (double x1, double y1, double ctrlx1, double ctrly1, double ctrlx2, double
		// ctrly2, double x2, double y2)
		clearPoint();
		this.shape = new CubicCurve2D.Double(x1, y1, x1, y1, x2, y2, x2, y2);
		this.xPoint.add(x1);
		this.yPoint.add(y1);
	}

	public void addPoint(int x2, int y2) {
		this.xPoint.add(x2);
		this.yPoint.add(y2);
	}

	@Override
	public void resizePoint(int x, int y) {
		CubicCurve2D cubicCurve2D = (CubicCurve2D) shape;

		if (this.xPoint.size() > 2) {
			cubicCurve2D.setCurve(this.xPoint.get(0), this.yPoint.get(0), x, y, this.xPoint.get(2), this.yPoint.get(2),
					this.xPoint.get(1), this.yPoint.get(1));
		} else if (this.xPoint.size() > 1) {
			cubicCurve2D.setCurve(this.xPoint.get(0), this.yPoint.get(0), x, y, x, y, this.xPoint.get(1),
					this.yPoint.get(1));
		} else {
			cubicCurve2D.setCurve(this.xPoint.get(0), this.yPoint.get(0), x, y, x, y, x, y);
		}
	}

	@Override
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void movePoint(int x, int y) {
		CubicCurve2D cubicCurve2D = (CubicCurve2D) shape;
		cubicCurve2D.setCurve(cubicCurve2D.getX1() + x - px, cubicCurve2D.getY1() + x - px, cubicCurve2D.getCtrlX1(),
				cubicCurve2D.getCtrlY1(), cubicCurve2D.getCtrlX2(), cubicCurve2D.getCtrlY2(),
				cubicCurve2D.getX2() + x - px, cubicCurve2D.getY2() + y - py);
		this.px = x;
		this.py = y;
	}

}