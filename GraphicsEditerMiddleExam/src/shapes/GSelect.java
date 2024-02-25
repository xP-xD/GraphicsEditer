package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GSelect extends GRectangle{

	public GSelect() {
		
	}
	
	
	
	public void draw(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(Color.LIGHT_GRAY);
		graphics2D.draw(shape);
	}




}
