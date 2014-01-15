package com.github.markyc.modelsolver.ui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.markyc.modelsolver.ui.row.CollectorRow;

public class CollectorPanel {
	
	private JPanel panel;
	private JPanel collectors;
	
	public static final String PATH = "C:\\Users\\Marco\\Documents\\GitHub\\ModelSolver\\collector"; 
	
	public static final String TITLE = "Collectors";
	public static final String ADD = "Add Collector";
	
	private List<CollectorRow> collectorRows;
	
	private CollectorPanel() {
		this.panel = new JPanel(); 
		this.collectors = new JPanel();
		this.collectorRows = new ArrayList<CollectorRow>();
		
		collectors.setLayout(new BoxLayout(collectors, BoxLayout.Y_AXIS));
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TITLE));
		
		for (File f : getCollectors()) {
			addCollectorRow(f);
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
			        addCollectorRow(chooser.getSelectedFile());
			    }
			}
		});
		
		panel.add(Box.createHorizontalGlue());
		panel.add(collectors);
		panel.add(addButon);
		panel.add(Box.createHorizontalGlue());
	}
	
	public void addCollectorRow(final File f) {
		CollectorRow row = createCollectorRow(f);
		collectorRows.add(row);
		collectors.add(row);
		
		panel.revalidate();
	}

	/**
	 * Creates the panel that display's the stat collectors
	 * @return the panel that display's the stat collectors
	 */
	private CollectorRow createCollectorRow(File f) {
		return new CollectorRow(f);
	}

	public File[] getCollectors() {
		
		File folder = new File(PATH);
		return folder.listFiles(); 
	}

	public static CollectorPanel newInstance() {
		return new CollectorPanel();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}

}
