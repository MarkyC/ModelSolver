package com.github.markyc.modelsolver.ui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.markyc.modelsolver.ui.row.ModelRow;

public class ModelPanel {
	
	private JPanel panel;
	private JPanel models;
	
	public static final String PATH = "C:\\Users\\Marco\\Documents\\GitHub\\ModelSolver\\model"; 
	
	public static final String TITLE = "Models";
	public static final String ADD = "Add Model";
	
	private List<ModelRow> modelRows;
	
	private ModelPanel() {
		this.panel = new JPanel(); 
		this.models = new JPanel();
		this.modelRows = new ArrayList<ModelRow>();
		
		models.setLayout(new BoxLayout(models, BoxLayout.Y_AXIS));
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TITLE));
		
		for (File f : getModels()) {
			addModelRow(f);
		}
		
		JButton addButon = new JButton(ADD);
		addButon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("JAR Plugins", "jar");
			    chooser.setFileFilter(filter);

		    	// Our composite JPanel "panel" is the parent component
			    if( chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION ) {
			        addModelRow(chooser.getSelectedFile());
			    }
			}
		});
		
		panel.add(models);
		panel.add(addButon);
	}
	
	public void addModelRow(final File f) {
		ModelRow row = createModelRow(f);
		modelRows.add(row);
		models.add(row);
		
		panel.revalidate();
	}

	/**
	 * Creates the panel that display's the stat models
	 * @return the panel that display's the stat models
	 */
	private ModelRow createModelRow(File f) {
		return new ModelRow(f);
	}

	public File[] getModels() {
		
		File folder = new File(PATH);
		return folder.listFiles(); 
	}

	public static ModelPanel newInstance() {
		
		return new ModelPanel();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}

}
