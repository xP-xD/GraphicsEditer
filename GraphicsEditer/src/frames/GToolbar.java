package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import main.GConstants.EShape;
import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelect;
import shapes.GShape;

public class GToolbar extends JToolBar {
	
	
	private static final long serialVersionUID = 1L;
	
	ButtonGroup buttonGroup;

	
	private EShape eSlectedShape;
	
	EShape getESelectedShape() {
		return this.eSlectedShape;
	}
	
	public void resetESelectedShape() {
//		JRadioButton selectedButton = (JRadioButton) this.getComponent(EShape.eSelect.ordinal());
//		selectedButton.doClick();
		this.buttonGroup.clearSelection();
		this.eSlectedShape = EShape.eSelect;
	}
	
	public GToolbar() {
		super();
		
		this.setFocusable(false);
		
		ActionHandler actionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();
		
		//add
		for(EShape eButtonShape : EShape.values()) {
			if(eButtonShape != EShape.eSelect) {
				JRadioButton btnShape = new JRadioButton(eButtonShape.getName());
				this.add(btnShape);
				btnShape.setActionCommand(eButtonShape.toString());
				btnShape.addActionListener(actionHandler);
				buttonGroup.add(btnShape);
			}
		}
		
		resetESelectedShape();
	};
	
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			eSlectedShape = EShape.valueOf(e.getActionCommand());
			
			
		}
		
	}
	
}
