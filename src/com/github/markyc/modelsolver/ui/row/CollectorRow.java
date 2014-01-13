package com.github.markyc.modelsolver.ui.row;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CollectorRow extends JPanel {
	
	private static final long serialVersionUID = -8004012359010254615L;
	
	private final JCheckBox checkBox;
	
	/**
	 * @return Whether the checkbox is selected or not
	 */
	public boolean isSelected() {
		return checkBox.getModel().isSelected();
	}
	
	private final JLabel name;

	/**
	 * @return the name of the item in the current row
	 */
	public String getName() {
		return name.getText();
	}

	public CollectorRow(File f) {
		super();
		this.setLayout(new BorderLayout());
		
		this.checkBox = new JCheckBox();
		
		this.name = new JLabel(f.getName());
		name.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				ButtonModel m = checkBox.getModel();
				m.setSelected(!m.isSelected());
			}

			@Override public void mouseEntered(MouseEvent arg0) { }
			@Override public void mouseExited(MouseEvent arg0) { }
			@Override public void mousePressed(MouseEvent arg0) { }
			@Override public void mouseReleased(MouseEvent arg0) { }
			
		});
		
		this.add(checkBox, BorderLayout.WEST);
		this.add(name, BorderLayout.CENTER);
	}
}
