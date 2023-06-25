package transfomer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import shapes.GShape;

public class GSelecter extends GTransformer {

	private int px, py;
	private int ox, oy;

	public GSelecter(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		this.shape.setShape(x, y, x, y);
		ox = x;
		oy = y;
		px = x;
		py = y;
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		this.shape.resizePoint(x, y);
		this.shape.draw(graphics2D);
		px = x;
		py = y;

	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		 for (GShape shape : shapes) {
		        if (isWithinArea(x, y, shape)) {
		            shape.setSelected(true);
		            shape.anchorDraw(graphics2D);
		        }
		}
	}

	public boolean isWithinArea(int x2, int y2, GShape shape) {
        Rectangle bounds = shape.getShape().getBounds();
        int shapeX1 = bounds.x;
        int shapeY1 = bounds.y;
        int shapeX2 = bounds.width + bounds.x;
        int shapeY2 = bounds.height+ bounds.y;

        boolean leftTopX = ox < shapeX1;
        boolean leftTopY = oy < shapeY1;
        boolean rigtBottomX = x2 > shapeX2;
        boolean rigtBottomY = y2 > shapeY2;
        
        return leftTopX && leftTopY && rigtBottomX && rigtBottomY;
        
    }
	
}
