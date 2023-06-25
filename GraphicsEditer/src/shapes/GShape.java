package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import main.GConstants.EAnchors;

	public abstract class GShape implements Cloneable, Serializable {
		
		private static final long serialVersionUID = 1L;
		
		protected Shape shape;
		private boolean bSelected;
		private GAnchors gAnchors;
		
		GShape() {
			this.bSelected = false;
			this.gAnchors = new GAnchors();
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
		public Shape getShape() {return this.shape;}
		public void setShape(Shape shape) {this.shape = shape;}

		public boolean onShape(Point p) {
			return shape.contains(p);
		}
		
		public void draw(Graphics2D graphics2D) {
			graphics2D.draw(shape);
			if(this.bSelected) {
				this.gAnchors.draw(graphics2D, this.shape.getBounds());
			}
		}
		
		public void setSelected(boolean bSelected) {
			this.bSelected = bSelected;
		}
		
		public boolean getSelected() {
			return this.bSelected;
		}
		
		
		
		public EAnchors onShape(int x, int y) {
			if(this.bSelected) {
				EAnchors eAnchor =	this.gAnchors.onShape(x,y);
				if(eAnchor != null) {
					return eAnchor;
				}
			}
			if(this.shape.getBounds().contains(x,y)){
				return EAnchors.MM;
			}
			return null;
		}
		
		public abstract void setShape(int x1, int y1, int x2, int y2);
		public abstract void setPoint(int x, int y);
		public abstract void resizePoint(int x, int y);
		public abstract void movePoint(int x, int y);
		public void addPoint(int x, int y) {}
		public void anchorDraw(Graphics2D graphics2D) {
			this.gAnchors.draw(graphics2D, this.shape.getBounds());
		}
		public void scale(double scaleX, double scaleY) {
		    // 현재 Shape를 AffineTransform으로 변환
		    AffineTransform transform = new AffineTransform();
		    transform.scale(scaleX, scaleY);
		    this.shape = transform.createTransformedShape(this.shape);
		}

	}
