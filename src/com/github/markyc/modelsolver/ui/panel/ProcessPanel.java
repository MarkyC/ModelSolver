package com.github.markyc.modelsolver.ui.panel;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.markyc.modelsolver.model.UserProcess;
import com.github.markyc.modelsolver.util.Util;

public class ProcessPanel {

	public static final String TITLE = "Processes";
	public static final String ADD = "Specify Process";
	public static final String SELECT = "Select a process from the list.";
	
	public static final String CPU = "CPU";
	public static final String MEMORY = "Memory";
	public static final String DISK = "Disk IO";
	
	
	private JPanel panel;
	private JPanel processListPanel;
	private JPanel monitorPanel;
	
	JList<UserProcess> processes;
	
	private ProcessPanel() {
		
		UserProcess[] userProcesses = Util.getProcesses().toArray(new UserProcess[0]);
		
		// Sort array by process name
		Arrays.sort(userProcesses);
		
		this.processes = createProcessList(userProcesses);
		
		/*JButton addButon = new JButton(ADD);
		addButon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(panel,
					    "Not Implemented Yet.",
					    "Not Implemented Yet",
					    JOptionPane.ERROR_MESSAGE);
			}
		});*/
		
		this.processListPanel = new JPanel();
		processListPanel.setLayout(new BoxLayout(processListPanel, BoxLayout.Y_AXIS));
		processListPanel.add(new JScrollPane(processes));
		
		this.monitorPanel = createMonitorPanel(userProcesses);
		
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.add(processListPanel);
		//left.add(addButon);
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(monitorPanel);
		
		// Main container
		this.panel = new JPanel(); 		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TITLE));
		panel.add(left);
		panel.add(right);
	}

	private JList<UserProcess> createProcessList(UserProcess[] p) {
		JList<UserProcess> result = new JList<UserProcess>(p);
		
		result.setPreferredSize(new Dimension(result.getPreferredSize().width, 300));
		
		result.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 9135637040065370696L;

			public Component getListCellRendererComponent(
		    		JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    	
		        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        if (value instanceof UserProcess) {
		            UserProcess p = (UserProcess) value;
		            setText(p.getName() + " (" + p.getPid() +")");
		            setToolTipText(p.getName());
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
				
				/*monitorPanel = createMonitorPanel(p);
				panel.revalidate();
				panel.repaint();*/
			}
			
		});
		
		return result;
	}

	private JPanel createMonitorPanel(UserProcess[] userProcesses) {
		
		JPanel result = new JPanel(new CardLayout());
		
		result.add(createEmptyMonitorPanel());
		
		for (UserProcess p : userProcesses) {
			result.add(createMonitorPanelForProcess(p), p.toString()); // TODO: don't user toString
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
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
		
		final JCheckBox cpu = new JCheckBox(CPU, p.isCpuMonitored());
		cpu.addItemListener(new ItemListener() {
			
			@Override
		    public void itemStateChanged(ItemEvent e) {
				// Updates whether we are monitoring the UserProcess' CPU 
				// based on the whether the checkbox is selected
				p.setCpuMonitored(cpu.isSelected());
			}
			
		});
		
		final JCheckBox mem = new JCheckBox(MEMORY, p.isMemoryMonitored());
		mem.addItemListener(new ItemListener() {
			
			@Override
		    public void itemStateChanged(ItemEvent e) {
				p.setMemoryMonitored(mem.isSelected());
			}
			
		});
		
		final JCheckBox disk = new JCheckBox(DISK, p.isDiskMonitored());
		disk.addItemListener(new ItemListener() {
			
			@Override
		    public void itemStateChanged(ItemEvent e) {
				p.setDiskMonitored(disk.isSelected());
			}
			
		});
		
		result.add(cpu);
		result.add(mem);
		result.add(disk);
		
		return result;
	}

	public static ProcessPanel newInstance() {
		return new ProcessPanel();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}

}
