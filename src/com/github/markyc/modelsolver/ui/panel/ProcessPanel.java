package com.github.markyc.modelsolver.ui.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.markyc.modelsolver.model.UserProcess;
import com.github.markyc.modelsolver.util.HelpModal;
import com.github.markyc.modelsolver.util.Util;
import com.github.markyc.stat.service.StatCollector;
import com.github.markyc.stat.service.StatService;

public class ProcessPanel {

	public static final String TITLE = "Processes";
	public static final String ADD = "Specify Process";
	public static final String SELECT = "Select a process from the list.";
	
	public static final String CPU 				= "CPU";
	public static final String MEMORY 			= "Memory";
	public static final String DISK				= "Disk IO";
	public static final String RESOLUTION 		= " Resolution: ";
	public static final String CREATE_COLLECTION= "Create Collector";
	public static final String START_COLLECTION	= "Start Collectors";
	public static final String STOP_COLLECTION	= "Stop Collectors";
	
	private static final int MAX_COMPONENT_HEIGHT = 25;
	private static final Dimension LIST_SIZE = new Dimension(300, 200);
	private static final Color ACTIVE_CELL = new Color(0, 200, 0);
	
	private JPanel panel;
	private JPanel monitorPanel;
	
	JList<UserProcess> processes;
	

	public static ProcessPanel newInstance() {
		return new ProcessPanel();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	private ProcessPanel() {
		
		final UserProcess[] userProcesses = Util.getProcesses().toArray(new UserProcess[0]);
		
		// Sort array by process name
		Arrays.sort(userProcesses);
		
		this.processes = createProcessList(userProcesses);
		
		/*JButton btn = new JButton(ADD);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});*/
		
		JScrollPane scrollableProcessList = new JScrollPane();
		scrollableProcessList.setViewportView(processes);
		scrollableProcessList.setPreferredSize(LIST_SIZE);

		
		this.monitorPanel = createMonitorPanel(userProcesses);
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.add(scrollableProcessList);
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(monitorPanel);
		//right.add(btn);
		
		// Main container
		this.panel = new JPanel(); 		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TITLE));
		panel.add(left);
		panel.add(right);
	}

	private JList<UserProcess> createProcessList(UserProcess[] p) {
		JList<UserProcess> result = new JList<UserProcess>(p);
				
		result.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 9135637040065370696L;

			public Component getListCellRendererComponent(
		    		JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    	
		        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        if (value instanceof UserProcess) {
		            UserProcess p = (UserProcess) value;
		            setText(p.getName() + " (" + p.getPid() +")");
		            setToolTipText(p.getName());
		            
		            // Change bg color if the process is being monitored
		            // Done so user can distinguish between monitored and unmonitored processes
		            if (p.isMonitored()) {
		            	this.setBackground(ACTIVE_CELL);
		            	invalidate();
		            }
		        }
		        return this;
		    }
		});
		
		// Only one item can be selected at a time
		result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		result.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				UserProcess p = processes.getSelectedValue();
				
				// Show the card belonging to this UserProcess
				((CardLayout) monitorPanel.getLayout()).show(monitorPanel, p.toString()); // TODO: don't use toString
			}
			
		});
		
		return result;
	}

	private JPanel createMonitorPanel(UserProcess[] userProcesses) {
		
		JPanel result = new JPanel(new CardLayout());
		
		result.add(createEmptyMonitorPanel());
		
		for (UserProcess p : userProcesses) {
			result.add(createMonitorPanelForProcess(p), p.toString()); // TODO: don't use toString
		}
		return result;
		

	}
	
	private static JPanel createEmptyMonitorPanel() {
		JPanel empty = new JPanel();
		empty.setLayout(new BoxLayout(empty, BoxLayout.X_AXIS));
		empty.add(Box.createHorizontalStrut(15));
		empty.add(new JLabel(SELECT));
		empty.add(Box.createHorizontalStrut(15));
		return empty;

	}
	
	private JPanel createMonitorPanelForProcess(final UserProcess p) {
		// CPU
		final JCheckBox cpu = new JCheckBox(CPU, p.isCpuMonitored());
		cpu.addItemListener(createCPUItemListener(cpu, p));
		
		// Memory
		final JCheckBox mem = new JCheckBox(MEMORY, p.isMemoryMonitored());
		mem.addItemListener(createMemoryItemListener(mem, p));
		
		// Disk
		final JCheckBox disk = new JCheckBox(DISK, p.isDiskMonitored());
		disk.addItemListener(createDiskItemListener(disk, p));
		
		// Resolution
		JPanel resolution = createResolutionPanel(p);	
		resolution.setMaximumSize(new Dimension(resolution.getMaximumSize().width, MAX_COMPONENT_HEIGHT));
		
		// Create Collector
		JButton create = new JButton(CREATE_COLLECTION);
		create.addActionListener(createCollectorActionListener(p));
		
		// Start Collector
		JButton start = new JButton(START_COLLECTION);
		start.addActionListener(createStartActionListener(p));		

		// Stop Collector
		JButton stop = new JButton(STOP_COLLECTION);
		stop.addActionListener(createStopActionListener(p));
		
		JPanel buttons = new JPanel(new GridLayout(3,1));
		buttons.add(create);
		buttons.add(start);
		buttons.add(stop);
			
		// Align all components
		cpu.setAlignmentX(Component.LEFT_ALIGNMENT);
		mem.setAlignmentX(Component.LEFT_ALIGNMENT);
		disk.setAlignmentX(Component.LEFT_ALIGNMENT);
		resolution.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		// Add components to JPanel
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		result.add(cpu);
		result.add(mem);
		result.add(disk);
		result.add(resolution);
		result.add(buttons);
		
		return result;
	}
	
	/**
	 * Updates whether we are monitoring the UserProcess' CPU based on the whether the checkbox is selected
	 * @param check
	 * @param p
	 * @return
	 */
	private ItemListener createCPUItemListener(final JCheckBox check, final UserProcess p) {
		return new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				p.setCpuMonitored(check.isSelected());
			}
		};
	}
	
	/**
	 * Updates whether we are monitoring the UserProcess' Memory based on the whether the checkbox is selected
	 * @param check
	 * @param p
	 * @return
	 */
	private ItemListener createMemoryItemListener(final JCheckBox check, final UserProcess p) {
		return new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				p.setMemoryMonitored(check.isSelected());
			}
		};
	}
	
	/**
	 * Updates whether we are monitoring the UserProcess' Disk based on the whether the checkbox is selected
	 * @param check
	 * @param p
	 * @return
	 */
	private ItemListener createDiskItemListener(final JCheckBox check, final UserProcess p) {
		return new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent e) {
				p.setDiskMonitored(check.isSelected());
			}
		};
	}

	private static JPanel createResolutionPanel(final UserProcess p) {
		final JComboBox<String> resolution = new JComboBox<String>(new String[] {
				"5"//, 10, 30, 60, 90
		});
		resolution.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				int newResolution = 0;
				try {
					newResolution = Integer.parseInt((String) resolution.getSelectedItem());
					p.setResolution(newResolution);
					addItem(newResolution);
				} catch (Exception ex) { /* Do nothing on fail for now */ }
			}
			
			private void addItem(int newItem) throws NumberFormatException {
				if (0 == newItem) return;
				
				// Check if item is already in the JComboBox
				boolean found = false;
				for (int i = 0; i < resolution.getModel().getSize(); i++) {
					if (Integer.parseInt(resolution.getItemAt(i)) == newItem) {
						found = true;
					}
				}
				
				// Add if not already in the JComboBox
				if (!found) resolution.addItem("" + newItem);
			}
			
		});
		resolution.setEditable(true);
		
		JComboBox<String> resolutionType = new JComboBox<String>(new String[] { "seconds" });
		resolutionType.setEditable(false);
		resolutionType.setEnabled(false); // Seconds only for now
		
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
		result.add(new JLabel(RESOLUTION));
		result.add(resolution);
		result.add(resolutionType);
		return result;
	}
	
	
	
	private ActionListener createCollectorActionListener(final UserProcess p) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			    StatService statService = Util.getStatService();
		        try {
		        	// Show help window
					HelpModal.show(panel, HelpModal.RUN_AS_ADMIN);
					
					StatCollector collector = statService.createCollector(
							p.getName(), 
							p.getPid(), 
							p.isCpuMonitored(), 
							p.isMemoryMonitored(), 
							p.isDiskMonitored(), 
							p.getResolution());
					
					p.addCollector(collector);
					/*
					collector.stop();
					WatchService fileWatcher = FileSystems.getDefault().newWatchService();
					fileWatcher. */
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
	private ActionListener createStartActionListener(final UserProcess p) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Show help window
				HelpModal.show(panel, HelpModal.RUN_AS_ADMIN);
				
				for (StatCollector collector : p.getCollectors()) {
					try {
						collector.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
	}
	
	private ActionListener createStopActionListener(final UserProcess p) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Show help window
				HelpModal.show(panel, HelpModal.RUN_AS_ADMIN);
				
				for (StatCollector collector : p.getCollectors()) {
					try {
						collector.stop();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
	}

}
