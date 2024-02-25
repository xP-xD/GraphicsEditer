package shapes;

import java.awt.Point;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

public class GQuadCurve extends GShape{
	
	private int px, py;
	
	public GQuadCurve() {
		this.xPoint = new ArrayList<Integer>();
		this.yPoint = new ArrayList<Integer>();
	}

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		clearPoint();
		this.shape = new QuadCurve2D.Double(x1, y1, x1, y1, x2, y2);
		this.xPoint.add(x1);
		this.yPoint.add(y1);
	}

	public void addPoint(int x2, int y2) {
		this.xPoint.add(x2);
		this.yPoint.add(y2);
	}
	
	@Override
	public void resizePoint(int x, int y) {
		QuadCurve2D quadCurve2D = (QuadCurve2D)shape;
		
		if(this.xPoint.size() > 1) {
			quadCurve2D.setCurve(this.xPoint.get(0) , this.yPoint.get(0), x, y, this.xPoint.get(1), this.yPoint.get(1));
		}else {
			quadCurve2D.setCurve(this.xPoint.get(0) , this.yPoint.get(0) , x , y, x , y);
		}
		
	}


	@Override
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void movePoint(int x, int y) {
		QuadCurve2D quadCurve2D = (QuadCurve2D)shape;
		quadCurve2D.setCurve(quadCurve2D.getX1()+x-px,quadCurve2D.getY1()+x-px,quadCurve2D.getCtrlX(),quadCurve2D.getCtrlY(), quadCurve2D.getX2()+x-px, quadCurve2D.getY2()+y-py);
		this.px = x;
		this.py = y;
		
	}
	
	public boolean onShape(Point p) {
		return shape.getBounds().contains(p);
	}
	
}
