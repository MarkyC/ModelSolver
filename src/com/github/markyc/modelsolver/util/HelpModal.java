package com.github.markyc.modelsolver.util;

import java.awt.Component;

import javax.swing.JOptionPane;

public class HelpModal {
	
	public static String DEFAULT_TITLE = "Help";
	public static String RUN_AS_ADMIN = 
			"Please run the highlighted batch file as an Administrator "
			+ "to create the counter (Right click > Run as Administrator)";

	public static void show(Component c, String msg) {
		show(c, DEFAULT_TITLE, msg);
	}
	
	public static void show(Component c, String title, String msg) {
		JOptionPane.showMessageDialog(c, msg, title, JOptionPane.QUESTION_MESSAGE);
	}
}
