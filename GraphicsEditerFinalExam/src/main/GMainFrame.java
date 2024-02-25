package main;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import frames.GDrawingPanel;
import frames.GMenuBar;
import frames.GToolbar;
import main.GConstants.EMainFrame;

public class GMainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private	GMenuBar menuBar;
	private GToolbar toolBar;
	
	private JTabbedPane drawingTabs;
	
	private GDrawingPanel drawingPanel;
	
	public GMainFrame() {
		//attributes
		//내특성을 먼저 정의하고나서 밑에 컴포넌트로 붙이는 것
		this.setSize(EMainFrame.Width.getNum(), EMainFrame.Height.getNum());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//eventListeners 
		MouseHandler mouseHandler = new MouseHandler();
		
		FocusHandler focusHandler = new FocusHandler();
		this.addFocusListener(focusHandler);
		setMainFrameFocus();
		
		KeyHandler keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		
		//components 위에부터 순서대로
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);

		this.toolBar = new GToolbar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
			//센터에 드로윙 패널 그릴 수 있는 탭 
				//나중에 엑스버튼 누르면 삭제되는 것도 구현
		this.drawingTabs = new JTabbedPane(JTabbedPane.TOP);
		this.drawingTabs.addMouseListener(mouseHandler);
		this.drawingPanel = new GDrawingPanel();
		this.drawingTabs.addTab("drawingPanel",new ImageIcon("img/drawingIcon.png"),drawingPanel);
		this.drawingTabs.add("+", null);
		this.add(this.drawingTabs, BorderLayout.CENTER);
		
		//association
		this.drawingPanel.setToolbar(toolBar);
		this.menuBar.setDrawingPanel(drawingPanel);
	}
	
	public void setMainFrameFocus() {
		this.setFocusable(true);
		this.requestFocus();
	}
	
	
	private class MouseHandler implements MouseListener{

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			//나중에 여기부터 만들면 될듯
			if(drawingTabs==e.getSource()) {
				setMainFrameFocus();
			}
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			if(drawingTabs.getSelectedComponent() == null) {
				drawingPanel = new GDrawingPanel();
				drawingTabs.addTab("drawingPanel" + drawingTabs.getSelectedIndex(),new ImageIcon("img/drawingIcon.png"), drawingPanel);
				drawingTabs.remove(drawingTabs.getSelectedIndex());
				drawingPanel.setToolbar(toolBar);
				drawingTabs.add("+", null);
				
			}
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class FocusHandler implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
		}
		
	}
	
	private class KeyHandler implements KeyListener{
		
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
				}
		
				@Override
				public void keyPressed(KeyEvent e) {
				}
		
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						int dialogOption = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?","프로그램 종료",JOptionPane.YES_NO_OPTION);
						if(dialogOption == JOptionPane.YES_OPTION) {
							System.exit(0);
						}
					}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						System.out.println("rr");
					}
				}
				
	}

}
