package main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import frames.GDrawingPanel;
import frames.GMenuBar;
import frames.GToolbar;

public class GMainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private	GMenuBar menuBar;
	private GToolbar toolBar;
	private GDrawingPanel drawingPanel;
	 
	public GMainFrame() {
	
		//attributes
		//내특성을 먼저 정의하고나서 밑에 컴포넌트로 붙이는 것
		this.setSize(GConstants.GMainFrame.w, GConstants.GMainFrame.h);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//components 컴포넌트가 자식인가보네
		///자식 만들고 등록하는 것을 반
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);
		
		BorderLayout Layout = new BorderLayout();
		this.setLayout(Layout);
		
		this.toolBar = new GToolbar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
		//association
		this.drawingPanel.setToolbar(toolBar);
		this.menuBar.setDrawingPanel(drawingPanel);
	}
	

}
