package com.github.markyc.modelsolver;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.github.markyc.modelsolver.ui.MainWindow;
import com.github.markyc.stat.service.StatService;

public class Activator implements BundleActivator {
	
    ServiceReference<?> statServiceReference;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
		
		statServiceReference= context.getServiceReference(StatService.class.getName());
        StatService statService = (StatService) context.getService(statServiceReference);
        System.out.println(statService.getStats());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}
	
	private static void createAndShowGUI() {
		
		// Create and show new MainWindow
		JFrame window = MainWindow.newInstance();
		
        //Display the window.
        window.pack();
        window.setVisible(true);
        
    }
	
	/** http://stackoverflow.com/questions/15426158/loading-of-osgi-bundle-dynamically-from-a-file-system **/
	void install( BundleContext context, File file) throws Exception {
		System.out.println("installing"); 
	    Bundle b = context.installBundle( file.toURI().toString() );
	    b.start();
		System.out.println("finished"); 
	}

}
