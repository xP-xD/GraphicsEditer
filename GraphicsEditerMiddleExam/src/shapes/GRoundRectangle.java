package shapes;

import java.awt.geom.RoundRectangle2D;

public class GRoundRectangle extends GShape {
	
	private int px, py;

	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		this.shape = new RoundRectangle2D.Double(x1, y1, x2-x1,y2-y1,10,10);
		
	}

	@Override
	public void resizePoint(int x2, int y2) {
		RoundRectangle2D roundRectangle = (RoundRectangle2D)shape;
		roundRectangle.setFrame(roundRectangle.getX(), roundRectangle.getY(), x2-roundRectangle.getX(), y2-roundRectangle.getY());
		
	}

	@Override
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;
	}
	
	@Override
	public void movePoint(int x, int y) {
		RoundRectangle2D roundRectangle = (RoundRectangle2D)shape;
		roundRectangle.setFrame(roundRectangle.getX()+x-px, roundRectangle.getY()+y-py,roundRectangle.getWidth(),roundRectangle.getHeight());
		this.px = x;
		this.py = y;
	}

}