package frames;

import javax.swing.JMenuBar;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	//뉴는 안에서 하는데 정의는 밖에서할 것
	private GFileMenu fileMenu;
	private GDrawingPanel gDrawingPanel;
	
	public GMenuBar() {
		
		fileMenu = new GFileMenu("File",gDrawingPanel);
		this.add(fileMenu);
	}

	public void setDrawingPanel(GDrawingPanel gDrawingPanel) {
		// TODO Auto-generated method stub
		this.gDrawingPanel = gDrawingPanel;
		
	}
	
}
