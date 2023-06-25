package transfomer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GRotator extends GTransformer {

	private double angle; // 회전 각도
	
	private int px, py;
	private AffineTransform affineTransform;

    public GRotator(GShape shape) {
        super(shape);
        this.angle = 0.0;
    }

    @Override
    public void initTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
    	px = x;
    	py = y;
    }

    @Override
    public void keepTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
    	angle = (x - px)/10;
    	
    	for (GShape shape : shapes) {
    		if (shape.getSelected()) {
    		// 회전 중심점 설정
            int centerX = shape.getShape().getBounds().x + shape.getShape().getBounds().width / 2;
            int centerY = shape.getShape().getBounds().y + shape.getShape().getBounds().height / 2;
            
            // 회전을 위한 어파인트렌스포머 생성
            affineTransform = new AffineTransform();
            affineTransform.setToRotation(Math.toRadians(angle), centerX, centerY);
            Shape transformedShape = affineTransform.createTransformedShape(shape.getShape());
            shape.setShape(transformedShape);
            // 도형을 회전시킵니다.
    		}
    	}
    	
    }

    @Override
    public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
    }

}
