package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import shapes.GCubicCurve;
import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GQuadCurve;
import shapes.GRectangle;
import shapes.GRoundRectangle;
import shapes.GSelect;
import shapes.GShape;

public class GToolbar extends JToolBar {

	private static final long serialVersionUID = 1L;

	private JComboBox<ImageIcon> shapeBox;
	private Color currentColor;

	public enum EShape {

		eSelect("Select", new GSelect(), null),

		eRectangle("Rectangle", new GRectangle(), new ImageIcon("img/Rectangle.png")),
		eRoundRectangle("RoundRectangle", new GRoundRectangle(), new ImageIcon("img/RoundRectangle.png")),
		eOval("Oval", new GOval(), new ImageIcon("img/Oval.png")),
		eLine("Line", new GLine(), new ImageIcon("img/Line.png")),
		eQuadCurve("QuadCurve", new GQuadCurve(), new ImageIcon("img/QuadCurve.png")),
		eCubicCurve("CubicCurve", new GCubicCurve(), new ImageIcon("img/CubicCurve.png")),
		ePolygon("Polygon", new GPolygon(), new ImageIcon("img/Polygon.png"));

		private String name;
		private GShape gShape;
		private ImageIcon img;

		private int iconSize = 20;

		private EShape(String name, GShape gShape, ImageIcon img) {
			this.name = name;
			this.gShape = gShape;
			this.img = resizeImg(img);
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
		
		private void setColor(Color color) {
			this.gShape.setDrawColor(color);
		}

	}

	private EShape eSlectedShape;

	public EShape getESelectedShape() {
		return this.eSlectedShape;
	}

	public void resetESelectedShape() {
		this.shapeBox.setSelectedItem(null);
		this.eSlectedShape = EShape.eSelect;
	}

	public enum EButton {
		eEraser("Eraser", new ImageIcon("img/Eraser.png")), eTextBox("TextBox", new ImageIcon("img/TextBox.png")),
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

		private JButton getButton() {
			// TODO Auto-generated method stub
			return btn;
		}

		public Color getColor() {
			return color;
		}

		private void setColor() {
			Color selectedColor = JColorChooser.showDialog(null, getName(), Color.white);
			this.color = selectedColor;
		}

	}

	private EButton eSlectedButton;

	public EButton getESlectedButton() {
		return this.eSlectedButton;
	}

	public void resetESlectedButton() {
		eSlectedButton = null;
	}

	public GToolbar() {
		// attribute
		super();
		this.setFloatable(false);
		this.setLayout(new FlowLayout(LEFT));

		// common Component
		ActionHandler actionHandler = new ActionHandler();

		// toolbarButtons
		for (EButton eButton : EButton.values()) {
			eButton.getButton().setToolTipText(eButton.getName());
			eButton.getButton().addActionListener(actionHandler);
			this.add(eButton.getButton());
		}

		// shapeForDropBox
		this.shapeBox = new JComboBox<ImageIcon>();
		for (EShape eButtonShape : EShape.values()) {
			if (eButtonShape != EShape.eSelect) {
				this.shapeBox.addItem(eButtonShape.getImg());
			}
		}
		this.shapeBox.addActionListener(actionHandler);
		this.add(shapeBox);
		resetESelectedShape();
		resetESlectedButton();
	};

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!e.getSource().equals(shapeBox)) {
				// button event
				for (EButton eButton : EButton.values()) {
					if (e.getSource().equals(eButton.getButton())) {
						System.out.println(eButton.getName() + "Mode");
						if (eSlectedButton == null) {
							if (eButton.getName() == "ColorCollection") {
								eButton.setColor();
								currentColor = eButton.getColor();
							}else {
								eSlectedButton = eButton;
								eButton.getButton().setSelected(true);
							}
						} else {
							resetESlectedButton();
							eButton.getButton().setSelected(false);
						}

					}

				}
			} else {
				// shape event
				int eSelectedShape = shapeBox.getSelectedIndex() + 1;
				for (EShape eShape : EShape.values()) {
					if (eShape.ordinal() == eSelectedShape) {
						shapeBox.setToolTipText(eShape.getName());
						eShape.setColor(currentColor);
						eSlectedShape = eShape;
					}
				}
			}

		}
	}
}
