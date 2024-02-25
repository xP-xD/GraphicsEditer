package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import main.GConstants.EAnchors;

public class GAnchors {
	
	static final int w = 10;
	static final int h = 10;
	private Ellipse2D[] anchors;
	
	public GAnchors() {
		anchors = new Ellipse2D[EAnchors.values().length-1];
		for(int i = 0; i<anchors.length;i++){
			anchors[i] = new Ellipse2D.Float(0,0,w,h);
		}
	}
	
	public void setPostion(Rectangle rectangle) {
		int x = rectangle.x -w/2;
		int y =  rectangle.y- h/2;
		anchors[EAnchors.NW.ordinal()].setFrame(x,					y,					w,h);
		anchors[EAnchors.NN.ordinal()].setFrame(x+rectangle.width/2,y,					w,h);
		anchors[EAnchors.NE.ordinal()].setFrame(x+rectangle.width,	y,					w,h);
		anchors[EAnchors.EE.ordinal()].setFrame(x+rectangle.width,	y+rectangle.height/2,w,h);
		anchors[EAnchors.SE.ordinal()].setFrame(x+rectangle.width,	y+rectangle.height,	w,h);
		anchors[EAnchors.SS.ordinal()].setFrame(x+rectangle.width/2,y+rectangle.height,	w,h);
		anchors[EAnchors.SW.ordinal()].setFrame(x,					y+rectangle.height,	w,h);
		anchors[EAnchors.WW.ordinal()].setFrame(x,					y+rectangle.height/2,w,h);
		anchors[EAnchors.RR.ordinal()].setFrame(x+rectangle.width/2,y-30,				w,h);
		
	}
	
	public void draw(Graphics2D graphics2D,Rectangle rectangle) {
		//set Position
		setPostion(rectangle);
		for(Ellipse2D anchor : anchors) {
			graphics2D.draw(anchor);
			graphics2D.fill(anchor);
		}
	}

	public EAnchors onShape(int x, int y) {
		for(int i =0; i<anchors.length; i++) {
			if(anchors[i].contains(x,y)) {
				return EAnchors.values()[i];
			}
		}
		return null;
	}
	
}
