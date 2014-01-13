package com.github.markyc.modelsolver.ui.panel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ResultPanel {
	
	private JPanel panel;
	
	public static final String TITLE = "Results";
	
	private ResultPanel() {
		this.panel = new JPanel(); 
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TITLE));
		
		JComboBox<String> selector = new JComboBox<String>(new String[] {
				"txt", "graph", "csv", "tsv"
		});
		
		panel.add(selector);
		
	}

	public static ResultPanel newInstance() {
		
		return new ResultPanel();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}

}
