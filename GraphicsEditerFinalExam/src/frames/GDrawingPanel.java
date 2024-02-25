package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextField;

import main.GConstants.EAnchors;
import main.GConstants.EButton;
import main.GConstants.EShape;
import main.GConstants.EUserAction;
import shapes.GRectangle;
import shapes.GShape;
import transfomer.GDrawer;
import transfomer.GSelecter;
import transfomer.GTransformer;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EDrawingEvent {
		eStart, eMoving, eCont, eEnd;
	}

	private enum EDrawingState {
		eIdle, eTransforming;
	}

	GDrawingPanel gDrawingPanel = this;

	private EDrawingState eDrawingState;
	private Vector<GShape> shapes;
	private GShape currentShape;

	private Image doublebufferImage;
	private Graphics doublebufferGraphics;

	private GToolbar toolbar;
	private GTransformer transformer;

	public void setToolbar(GToolbar toolbar) {
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		super();
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<GShape>();
		this.currentShape = null;
		this.setBackground(Color.WHITE);
		this.setDoubleBuffered(true);

		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		if (doublebufferImage == null) {
			doublebufferImage = createImage(getWidth(), getHeight());
			doublebufferGraphics = doublebufferImage.getGraphics();
		}
		paintComponent(doublebufferGraphics);
		graphics.drawImage(doublebufferImage, 0, 0, null);
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		for (GShape shape : this.shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}

	public void initTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		if (this.toolbar.getESelectedShape() == EShape.eSelect) {// select, group
			this.currentShape = this.toolbar.getESelectedShape().getGShape();
			System.out.println("Select State");
			EAnchors eSelectedAnchor = this.onShape(x, y);

			if (eSelectedAnchor == null) {
				System.out.println("Selected null");
				System.out.println(this.currentShape);
				this.clearSelection();
				this.transformer = new GSelecter(this.currentShape);
				this.transformer.initTransform(x, y, graphics2D, this.shapes);

			} else {// move, resize, rotate
				for (EAnchors eAnchor : EAnchors.values()) {
					if (eAnchor.equals(eSelectedAnchor)) {
						this.transformer = eAnchor.getTransformer(this.currentShape, eSelectedAnchor);
					}
				}
				this.transformer.initTransform(x, y, graphics2D, shapes);
			}

		} else {// draw
			this.currentShape = (GShape) this.toolbar.getESelectedShape().getGShape().clone();
			this.transformer = new GDrawer(this.currentShape);
			this.transformer.initTransform(x, y, graphics2D, this.shapes);
		}
	}

	public void keepTransforming(int x, int y) {

		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		paint(graphics2D);
		this.transformer.keepTransform(x, y, graphics2D, this.shapes);

	}

	public void continueTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		paint(graphics2D);
		this.transformer.continueTransform(x, y, graphics2D);
	}

	public void finalizeTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		this.transformer.finalizeTransform(x, y, graphics2D, this.shapes);
		this.toolbar.resetESelectedShape();
		this.currentShape = null;
		paint(graphics2D);

	}

	public GShape onShape(Point point) {
		for (GShape shape : shapes) {
			if (shape.onShape(point)) {
				return shape;
			}
		}
		return null;
	}

	private EAnchors onShape(int x, int y) {
		for (GShape gShape : this.shapes) {
			EAnchors eAnchor = gShape.onShape(x, y);
			if (eAnchor != null) {
				this.currentShape = gShape;
				return eAnchor;
			}
		}
		return null;
	}

	private void clearSelection() {
		for (GShape gShape : this.shapes) {
			if (gShape.getSelected()) {
				gShape.setSelected(false);
			}
		}

	}

	private void changeCursors(int x, int y) {
		EAnchors hoveredAnchor = this.onShape(x, y);
		Cursor cursor = null;
		for (EAnchors eAnchor : EAnchors.values()) {
			if (eAnchor.equals(hoveredAnchor)) {
				cursor = eAnchor.getCursor();
			}
		}
		this.setCursor(cursor);
	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {

		GRectangle gRectangle = new GRectangle();

		int x, y;

		@Override
		public void mouseClicked(MouseEvent e) {
//			System.out.println("mouseClicked" + e.getX() + " " + e.getY());
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}

		}

		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
			} else if (eDrawingState == EDrawingState.eTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursors(e.getX(), e.getY());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

			// button
			if (toolbar.getESlectedButton() != null) {
				if (toolbar.getESlectedButton() == EButton.eTextBox) {
					gRectangle.setShape(e.getX(), e.getY(), e.getX(), e.getY());
					this.x = e.getX();
					this.y = e.getY();
				} else if (toolbar.getESlectedButton() == EButton.eFill) {
					if (onShape(e.getPoint()) != null) {
						for (int i = 0; i < shapes.size(); i++) {
							if (onShape(e.getPoint()) == shapes.get(i)) {
								Graphics graphics = getGraphics();
								shapes.get(i).fill(graphics);
								update(graphics);
							}
						}
					}
				}

			} else {

				if (eDrawingState == EDrawingState.eIdle) {
					initTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eTransforming;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			// button
			if (toolbar.getESlectedButton() != null) {
				if (toolbar.getESlectedButton() == EButton.eEraser) {
					// 버튼 누르고 드래그하면 지워짐
					if (onShape(e.getPoint()) != null) {
						for (int i = 0; i < shapes.size(); i++) {
							if (onShape(e.getPoint()) == shapes.get(i)) {
								shapes.remove(i);
								Graphics graphics = getGraphics();
								update(graphics);
								paint(graphics);
							}
						}
					}
				}
				else if (toolbar.getESlectedButton() == EButton.eTextBox) {
					Graphics graphics = getGraphics();
					graphics.setXORMode(getBackground());
					gRectangle.draw((Graphics2D) graphics);
					gRectangle.resizePoint(e.getX(), e.getY());
					gRectangle.draw((Graphics2D) graphics);
				}

			} else {
				if (eDrawingState == EDrawingState.eTransforming && transformer != null) {
					keepTransforming(e.getX(), e.getY());
				} else {
					eDrawingState = EDrawingState.eIdle;
					Graphics2D graphics2D = (Graphics2D) getGraphics();
					update(graphics2D);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			if (toolbar.getESlectedButton() != null) {
				if (toolbar.getESlectedButton() == EButton.eTextBox) {
					JTextField field = new JTextField();
					field.setBounds(gRectangle.getBounds());
					field.setColumns(field.getPreferredSize().width);
					field.setColumns(field.getPreferredSize().height);
//					field.getPreferredScrollableViewportSize();
//					field.createToolTip();
					add(field);
				}
			} else {
				if (eDrawingState == EDrawingState.eTransforming
						&& toolbar.getESelectedShape().getEUserAction().equals(EUserAction.e2Point)) {
					finalizeTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// button
			// ordinal : eraser -> textBox
			if (toolbar.getESlectedButton() != null) {
				setCursor(toolbar.getESlectedButton().transformCursor());
			} else {
				// shape
				setCursor(null);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// button
			if (toolbar.getESlectedButton() != null) {
				setCursor(null);
			} else {
				// shape
				
			}

			// 그림 밖으로 못나가게
			transformer = null;
			currentShape = null;
			clearSelection();
		}
	}

}
