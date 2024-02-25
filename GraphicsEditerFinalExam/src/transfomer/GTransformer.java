package transfomer;

import java.awt.Graphics2D;
import java.util.Vector;

import shapes.GShape;

public abstract class GTransformer {

	protected GShape shape;
	
	
	public GTransformer(GShape shape) {
		this.shape = shape;
	}
	public abstract void initTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes);
	public abstract void keepTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) ;
	public abstract void finalizeTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes);
	
	public void continueTransform(int x, int y, Graphics2D graphics2D) {}
	
}
