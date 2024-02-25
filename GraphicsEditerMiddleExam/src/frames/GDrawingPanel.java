package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frames.GToolbar.EButton;
import frames.GToolbar.EShape;
import shapes.GRectangle;
import shapes.GShape;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private enum EDrawingState {
		eIdle, eDrawing, eMoving, eSelecting, eResizing;

	}

	private enum EButtonSelectedState {
		eIdle, eErasing, eTextBox;
	}

	private EDrawingState eDrawingState;
	private EButtonSelectedState eButtonSelectedState;

	private Vector<GShape> shapes;
	private GShape currentShape;

	private GToolbar toolbar;

	public void setToolbar(GToolbar toolbar) {
		this.toolbar = toolbar;
	}

	public GDrawingPanel() {
		super();
		this.eDrawingState = EDrawingState.eIdle;
		this.eButtonSelectedState = EButtonSelectedState.eIdle;
		this.shapes = new Vector<GShape>();
		this.currentShape = null;

		this.setBackground(Color.WHITE);
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (GShape shape : this.shapes) {
			shape.draw(graphics);
		}
	}

	public GShape onShape(Point point) {
		for (GShape shape : shapes) {
			if (shape.onShape(point)) {
				return shape;
			}
		}
		return null;
	}

	public void prepareTransforming(int x, int y) {
		if (eDrawingState == EDrawingState.eDrawing) {
			currentShape = toolbar.getESelectedShape().getGShape();
			currentShape.setShape(x, y, x, y);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape = toolbar.getESelectedShape().getGShape();
			currentShape.setShape(x, y, x, y);
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape.setPoint(x, y);

		}
	}

	public void keepTransforming(int x, int y) {
		Graphics graphics = getGraphics();
		graphics.setXORMode(getBackground());
		if (eDrawingState == EDrawingState.eDrawing) {
			currentShape.draw(graphics);
			currentShape.resizePoint(x, y);
			currentShape.draw(graphics);
		} else if (eDrawingState == EDrawingState.eSelecting) {
			currentShape.draw(graphics);
			currentShape.resizePoint(x, y);
			currentShape.draw(graphics);
		} else if (eDrawingState == EDrawingState.eMoving) {
			currentShape.draw(graphics);
			currentShape.movePoint(x, y);
			currentShape.draw(graphics);
		}
	}

	public void continueTransforming(int x, int y) {
		currentShape.addPoint(x, y);
	}

	public void finalizeTransforming(int x, int y) {

		if (eDrawingState == EDrawingState.eDrawing) {
			shapes.add((GShape) currentShape.clone());
		} else if (eDrawingState == EDrawingState.eSelecting) {
			Graphics graphics = getGraphics();
			graphics.setXORMode(getBackground());
			currentShape.draw(graphics);
		}
		if (eDrawingState == EDrawingState.eMoving) {

		}

		currentShape = null;
		toolbar.resetESelectedShape();
	}

	private class MouseEventHandler implements MouseListener, MouseMotionListener {
		GRectangle gRectangle = new GRectangle();
		
		int x, y;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}

		private void mouse2Clicked(MouseEvent e) {
			// button
			if (toolbar.getESlectedButton() != null) {
				
			} else {
				// shape
				if (eDrawingState == EDrawingState.eDrawing) {
					finalizeTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}
		}

		private void mouse1Clicked(MouseEvent e) {

			// button
			if (toolbar.getESlectedButton() != null) {

			} else {
				// shape
				if (eDrawingState == EDrawingState.eIdle) {
					if (toolbar.getESelectedShape().getGShape().nPointShape()) {
						eDrawingState = EDrawingState.eDrawing;
						prepareTransforming(e.getX(), e.getY());
					}
				} else if (eDrawingState == EDrawingState.eDrawing) {
					if (toolbar.getESelectedShape().getGShape().nPointShape()) {
						continueTransforming(e.getX(), e.getY());
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

			// button
			if (toolbar.getESlectedButton() != null) {
				if(toolbar.getESlectedButton()==EButton.eTextBox) {
					gRectangle.setShape(e.getX(), e.getY(), e.getX(), e.getY());
					this.x = e.getX();
					this.y = e.getY();
				}else if(toolbar.getESlectedButton()==EButton.eFill) {
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
				// shape
				if (eDrawingState == EDrawingState.eIdle) {
					if (toolbar.getESelectedShape() == EShape.eSelect) {
						currentShape = onShape(e.getPoint());
						if (currentShape == null) {
							eDrawingState = EDrawingState.eSelecting;
							prepareTransforming(e.getX(), e.getY());
						} else {
							// resize, rotate, move
							eDrawingState = EDrawingState.eMoving;
							prepareTransforming(e.getX(), e.getY());
						}
					} else {
						if (!(toolbar.getESelectedShape().getGShape().nPointShape())) {
							eDrawingState = EDrawingState.eDrawing;
							prepareTransforming(e.getX(), e.getY());
						}
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// button
			if (toolbar.getESlectedButton() != null) {
				if (toolbar.getESlectedButton() == EButton.eEraser) {
					//버튼 누르고 드래그하면 지워짐
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
				} else if(toolbar.getESlectedButton() == EButton.eTextBox) {
						Graphics graphics = getGraphics();
						graphics.setXORMode(getBackground());
						gRectangle.draw(graphics);
						gRectangle.resizePoint(e.getX(), e.getY());
						gRectangle.draw(graphics);;
				}

			} else {
				// shape
				if (eDrawingState == EDrawingState.eDrawing) {
					if (!(toolbar.getESelectedShape().getGShape().nPointShape())) {
						keepTransforming(e.getX(), e.getY());
					}

				} else if (eDrawingState == EDrawingState.eSelecting) {
					keepTransforming(e.getX(), e.getY());
				} else if (eDrawingState == EDrawingState.eMoving) {
					keepTransforming(e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// button
			if (toolbar.getESlectedButton() != null) {
				
			} else {
				// shape
				if (eDrawingState == EDrawingState.eDrawing) {
					if (toolbar.getESelectedShape().getGShape().nPointShape()) {
						keepTransforming(e.getX(), e.getY());
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// button
			if (toolbar.getESlectedButton() != null) {
				if(toolbar.getESlectedButton()==EButton.eTextBox) {
					JTextField field = new JTextField();
					field.setBounds(gRectangle.getBounds());
					field.setColumns(field.getPreferredSize().width);
					field.setColumns(field.getPreferredSize().height);
//					field.getPreferredScrollableViewportSize();
//					field.createToolTip();
					add(field);
				}
			} else {
				// shape
				if (eDrawingState == EDrawingState.eDrawing) {
					if (!(toolbar.getESelectedShape().getGShape().nPointShape())) {
						finalizeTransforming(e.getX(), e.getY());
						eDrawingState = EDrawingState.eIdle;
					}

				} else if (eDrawingState == EDrawingState.eSelecting) {
					finalizeTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				} else if (eDrawingState == EDrawingState.eMoving) {
					finalizeTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eIdle;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// button
				//ordinal : eraser -> textBox
			if (toolbar.getESlectedButton() != null) {
				setCursor(toolbar.getESlectedButton().transformCursor());
			}else {
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
		}

	}

}
