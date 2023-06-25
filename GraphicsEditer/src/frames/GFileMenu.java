package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import menuItems.GPrinting;

public class GFileMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;

	JMenuItem itemNew;
	GDrawingPanel gDrawingPanel;
	
	public GFileMenu(String title, GDrawingPanel gDrawingPanel) {
		super(title);
		this.gDrawingPanel = gDrawingPanel;
		ActionHandler actionHandler = new ActionHandler();
		itemNew = new JMenuItem("print");
		itemNew.setActionCommand(itemNew.getName());
		itemNew.addActionListener(actionHandler);
		this.add(itemNew);
	}
	
	
	
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			GPrinting printer = new GPrinting(gDrawingPanel);
			printer.printDrawing();
		}
		
	}
	
}
