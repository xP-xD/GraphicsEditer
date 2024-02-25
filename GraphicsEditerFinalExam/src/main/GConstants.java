package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import main.GConstants.EUserAction;
import shapes.GCubicCurve;
import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GQuadCurve;
import shapes.GRectangle;
import shapes.GRoundRectangle;
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
	
	public enum EMainFrame {

		Width(1000),
		Height(700);

		private int num;

		private EMainFrame(int num) {
			this.num = num;
		}

		public int getNum() {
			return this.num;
		}

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

		eSelect("Select", new GSelect(), null, EUserAction.e2Point),

		eRectangle("Rectangle", new GRectangle(), new ImageIcon("img/Rectangle.png"), EUserAction.e2Point),
		eRoundRectangle("RoundRectangle", new GRoundRectangle(), new ImageIcon("img/RoundRectangle.png"), EUserAction.e2Point),
		eOval("Oval", new GOval(), new ImageIcon("img/Oval.png"), EUserAction.e2Point),
		eLine("Line", new GLine(), new ImageIcon("img/Line.png"), EUserAction.e2Point),
		eQuadCurve("QuadCurve", new GQuadCurve(), new ImageIcon("img/QuadCurve.png"), EUserAction.eNPoint),
		eCubicCurve("CubicCurve", new GCubicCurve(), new ImageIcon("img/CubicCurve.png"), EUserAction.eNPoint),
		ePolygon("Polygon", new GPolygon(), new ImageIcon("img/Polygon.png"), EUserAction.eNPoint);

		private String name;
		private GShape gShape;
		private ImageIcon img;
		private EUserAction eUserAction;


		private int iconSize = 20;

		private EShape(String name, GShape gShape, ImageIcon img, EUserAction eUserAction) {
			this.name = name;
			this.gShape = gShape;
			this.img = resizeImg(img);
			this.eUserAction = eUserAction;
		}

		public String getName() {
			return this.name;
		}

		public GShape getGShape() {
			return this.gShape;
		}

		public ImageIcon getImg() {
			return img;
		}

		private ImageIcon resizeImg(ImageIcon img) {
			if (img != null) {
				Image tempImg = img.getImage();
				Image updateImg = tempImg.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
				return new ImageIcon(updateImg);
			}
			return null;
		}
		
		public void setColor(Color color) {
			this.gShape.setDrawColor(color);
		}
		
		public EUserAction getEUserAction() {
			return this.eUserAction;
		}

	}
	
	public enum EButton {
		eEraser("Eraser", new ImageIcon("img/Eraser.png")),
		eTextBox("TextBox", new ImageIcon("img/TextBox.png")),
		eColorCollection("ColorCollection", new ImageIcon("img/ColorCollection.png")),
		eFill("Fill", new ImageIcon("img/Fill.png"));

		private String name;
		private ImageIcon img;
		private JButton btn;
		private Color color;

		private int iconSize = 20;

		private EButton(String name, ImageIcon img) {
			this.name = name;
			btn = new JButton(this.img = resizeImg(img));
		}

		public String getName() {
			return this.name;
		}

		public ImageIcon getImg() {
			return img;
		}

		private ImageIcon resizeImg(ImageIcon img) {
			if (img != null) {
				Image tempImg = img.getImage();
				Image updateImg = tempImg.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
				return new ImageIcon(updateImg);
			}
			return null;
		}

		public Cursor transformCursor() {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image tempCursor = toolkit.getImage("img/" + this.name + ".png");
			Image transFormCursor = tempCursor.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
			Point point = new Point(5, 5);
			Cursor eraserCusor = toolkit.createCustomCursor(transFormCursor, point, getName());
			return eraserCusor;
		}

		public JButton getButton() {
			// TODO Auto-generated method stub
			return btn;
		}

		public Color getColor() {
			return color;
		}

		public void setColor() {
			Color selectedColor = JColorChooser.showDialog(null, getName(), Color.white);
			this.color = selectedColor;
		}

	}

}
