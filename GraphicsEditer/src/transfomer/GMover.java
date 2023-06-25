package transfomer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GMover extends GTransformer {

	private AffineTransform affineTransform;
	private int px,py,tx,ty;
	
	public GMover(GShape shape) {
		super(shape);
		this.affineTransform = new AffineTransform();
	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		px=x;
		py=y;
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		
		for (GShape shape : shapes) {
			if(shape.getSelected()) {
				this.affineTransform.setToTranslation(x - px, y - py);
				Shape transformedShape = this.affineTransform.createTransformedShape(shape.getShape());
				shape.setShape(transformedShape);
				shape.draw(graphics2D);
			}
		}
		
		px = x;
		py = y;
	}


	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
		this.shape.setSelected(true);
		this.shape.draw(graphics2d);
	}

}
