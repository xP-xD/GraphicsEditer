package frames;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import main.GConstants.EButton;
import main.GConstants.EShape;

public class GToolbar extends JToolBar {
	
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox<ImageIcon> shapeBox;
	private Color currentColor;

	private EShape eSlectedShape;
	
	EShape getESelectedShape() {
		return this.eSlectedShape;
	}
	
	public void resetESelectedShape() {
		this.shapeBox.setSelectedItem(null);
		this.eSlectedShape = EShape.eSelect;
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
