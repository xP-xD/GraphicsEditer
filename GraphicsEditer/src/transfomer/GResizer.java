package transfomer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GResizer extends GTransformer {
	
	private AffineTransform affineTransform;
	private int px, py;
	
	public GResizer(GShape shape) {
		super(shape);
		this.affineTransform = new AffineTransform();
	}

	public void initTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		px = x;
		py = y;
		
	}

	public void keepTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		
		for (GShape shape : shapes) {
			if (shape.getSelected()) {
				
				double scaleX = (double) (x - px) / 200 + 1;  // X축 스케일링 비율
	            double scaleY = (double) (y - py) / 200 + 1;  // Y축 스케일링 비율

	            // 원래 점을 기준으로 스케일링 변환
	            AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	            Shape transformedShape = scaleTransform.createTransformedShape(shape.getShape());
	            
				// 이동 변환을 적용하지 않고 크기만 조정
	            double dx = shape.getShape().getBounds2D().getX() * (1 - scaleX);
	            double dy = shape.getShape().getBounds2D().getY() * (1 - scaleY);
	            AffineTransform translationTransform = AffineTransform.getTranslateInstance(dx, dy);
	            transformedShape = translationTransform.createTransformedShape(transformedShape);
				
				shape.setShape(transformedShape);
				shape.draw(graphics2D);
			}
		}
		
		px = x;
		py = y;
	}

	
	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
	}

}
