package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public abstract class GShape implements Cloneable {

	protected Shape shape;

	// nPoint
	protected ArrayList<Integer> xPoint;
	protected ArrayList<Integer> yPoint;

	// color
	protected Color selectedColor;
	protected boolean filled;

	GShape() {
		selectedColor = Color.DARK_GRAY;
		filled = false;
	}

	public boolean onShape(Point p) {
		return shape.contains(p);
	}

	public void setDrawColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void draw(Graphics graphics) {
//		graphics2D.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,0));
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(selectedColor);
		if (!filled) {
			graphics2D.draw(shape);
		} else {
			graphics2D.fill(shape);
		}
	}

	// shape 안을 채움
	public void fill(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(selectedColor);
		graphics2D.fill(shape);
		this.filled = true;
	}

	public Rectangle getBounds() {
		return shape.getBounds();
	}

	public abstract void setShape(int x1, int y1, int x2, int y2);

	public abstract void setPoint(int x, int y);

	public abstract void resizePoint(int x, int y);

	public abstract void movePoint(int x, int y);

	public void addPoint(int x, int y) {
	}

	public void clearPoint() {
		if (nPointShape()) {
			this.xPoint.clear();
			this.yPoint.clear();
		}
	};

	public boolean nPointShape() {
		if (xPoint != null) {
			return true;
		}
		return false;
	}

	// clone을 오버라이딩함으로서 객체의 깊은 복사
	@Override
	public Object clone() {
		Object GShapeClone = null;
		try {
			GShapeClone = super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GShapeClone;
	}

}
