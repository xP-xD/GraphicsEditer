package frames;

import javax.swing.JMenuBar;

import contents.Contesnts.EMenuBar;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	//뉴는 안에서 하는데 정의는 밖에서할 것
	
	public GMenuBar() {
		
		for(EMenuBar eMenuBar :EMenuBar.values()){
			GFileMenu eMenuBarItem = new GFileMenu(eMenuBar.getName());
			this.add(eMenuBarItem);
			
		}
	}
}
