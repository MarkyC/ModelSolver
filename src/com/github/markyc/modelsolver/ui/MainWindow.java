package com.github.markyc.modelsolver.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.github.markyc.modelsolver.ui.panel.ProcessPanel;
import com.github.markyc.modelsolver.ui.panel.ResultPanel;

public class MainWindow {
	
	public static final String TITLE = "Molder Solver";
	public static final String MENU_FILE = "File";
	public static final String MENU_EXIT = "Exit";
	
	// This JFrame is our main window
	private JFrame window;
	
	// This JPanel handles collection tools
	//private CollectorPanel collectorPanel;
	//private ModelPanel modelPanel;
	private ResultPanel resultPanel;
	private ProcessPanel processPanel;
	
	/**
	 * @return the window
	 */
	public JFrame getWindow() {
		return window;
	}


	/**
	 * @param window the window to set
	 */
	public void setWindow(JFrame window) {
		this.window = window;
	}


	private MainWindow() {

        try {
        	// Set native Look and Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { } 
     
		this.window = createWindow();
	}
	
	
	/**
	 * Construct a new MainWindow object. 
	 * Creation as a factory method allows for greater modularity.
	 * @return a JFrame representing our main program window
	 */
	public static JFrame newInstance() {
		
		MainWindow app = new MainWindow();		
        return app.getWindow();
        
	}
	
	/**
	 * Responsible for creating and laying out components for our main window
	 * @return
	 */
	private JFrame createWindow() {
		 //Create and set up the window.
        final JFrame window = new JFrame(TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setJMenuBar(createMenu());
        window.add(createBody(), BorderLayout.CENTER);
        
        return window;
	}
	
	/**
	 * Creates a Menu for the given window
	 * @param window the JFrame to give a Menu to
	 * @return A JMenuBar you can add to the given window
	 */
	private JMenuBar createMenu() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu(MENU_FILE);
		menuBar.add(file);
		
		JMenuItem exit = new JMenuItem(MENU_EXIT);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				System.exit(0); // TODO: OSGi should close another way
			}
			
		});
		file.add(exit);
				
		return menuBar;
	}
	
	/**
	 * Creates the main window body (middle part)
	 * @param window the JFrame to give a body to
	 * @return a JPanel consisting of body  
	 */
	private JPanel createBody() {
		JPanel body = new JPanel();
		
		body.setLayout(new BoxLayout(body, BoxLayout.X_AXIS));
		
		processPanel	= ProcessPanel.newInstance();
		//collectorPanel 	= CollectorPanel.newInstance();
		//modelPanel 		= ModelPanel.newInstance();
		resultPanel 	= ResultPanel.newInstance();
		
		body.add(processPanel.getPanel());
		//body.add(collectorPanel.getPanel());
		//body.add(modelPanel.getPanel());
		body.add(resultPanel.getPanel());
		
		return body;
	}

}
