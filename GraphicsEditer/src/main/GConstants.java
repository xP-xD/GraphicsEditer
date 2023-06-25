package main;

import java.awt.Cursor;

import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelect;
import shapes.GShape;
import transfomer.GMover;
import transfomer.GResizer;
import transfomer.GRotator;
import transfomer.GTransformer;

public class GConstants {
	public class GMainFrame {
		// JSON, XML파일로 읽어오는게 가능
		static final int x = 200;
		static final int y = 100;
		static final int w = 600;
		static final int h = 400;
	}

	public enum EUserAction {
		e2Point, eNPoint;
	}
	
	public enum EAnchors{
		NW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		NN(new Cursor(Cursor.N_RESIZE_CURSOR )), 
		NE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		EE(new Cursor(Cursor.E_RESIZE_CURSOR)), 
		SE(new Cursor(Cursor.SE_RESIZE_CURSOR)), 
		SS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		SW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		WW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		RR(new Cursor(Cursor.HAND_CURSOR)),
		MM(new Cursor(Cursor.MOVE_CURSOR));
		
		Cursor cursor;
		
		private EAnchors(Cursor cursor) {
			this.cursor = cursor;
		}
		
		public Cursor getCursor() {
			return this.cursor;
		};

		public GTransformer getTransformer(GShape shape, EAnchors eSelectedAnchor) {
			// TODO Auto-generated method stub
			
			switch (eSelectedAnchor) {
			case MM:
				return new GMover(shape);
			case RR:
				return new GRotator(shape);
			default:
				return new GResizer(shape);
			}
		}
		
	}

	public enum EShape {

		eSelect("Select", new GSelect(), EUserAction.e2Point),
		eRectangle("Rectangle", new GRectangle(), EUserAction.e2Point), eOval("Oval", new GOval(), EUserAction.e2Point),
		eLine("Line", new GLine(), EUserAction.e2Point), ePolygon("Polygon", new GPolygon(), EUserAction.eNPoint);

		private String name;
		private GShape gShape;
		private EUserAction eUserAction;

		private EShape(String name, GShape gShape, EUserAction eUserAction) {
			this.name = name;
			this.gShape = gShape;
			this.eUserAction = eUserAction;
		}

		public String getName() {
			return this.name;
		}

		public GShape getGShape() {
			return this.gShape;
		}

		public EUserAction getEUserAction() {
			return this.eUserAction;
		}

	}

}
