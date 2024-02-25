package frames;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import contents.Contesnts.EFileMenu;

public class GFileMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;

	public GFileMenu(String title) {
		super(title);
		
		for(EFileMenu eGManuBar :EFileMenu.values()){
			JMenuItem eGManuBarItem = new JMenuItem(eGManuBar.getName());
			this.add(eGManuBarItem);
			
		}
	}
	
}
