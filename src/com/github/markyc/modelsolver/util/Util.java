package com.github.markyc.modelsolver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.github.markyc.modelsolver.model.UserProcess;
import com.github.markyc.stat.service.StatService;

public class Util {

	/**
	 * Singleton BundleContext
	 */
	private static BundleContext bundleContext;

	public static List<UserProcess> getProcesses() {
		
		List<UserProcess> processes = new ArrayList<UserProcess>();
		
		try {
			String line;
			Process p = getTaskListProcess();
			BufferedReader input =
					new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				processes.add(parseProcess(line));
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		return processes;
	}
	
	private static Process getTaskListProcess() throws IOException {
		if (isWindows()) {
			// Windows
			return Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
		} else {
			// *Nix
			return Runtime.getRuntime().exec("ps -e");
		}
	}
	
	private static UserProcess parseProcess(String line) {
		if (isWindows()) {
			
			// "Image Name","PID","Session Name","Session#","Mem Usage"
			String[] fields = removeQuotes(line.split(","));
			
			
			String name = fields[0];
			
			int pid = 0;
			try {
				pid = Integer.parseInt(fields[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			return new UserProcess(name, pid);
			
		} else {
			throw new UnsupportedOperationException("Linux process support not yet implemented");
		}
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}
	
	
	private static String[] removeQuotes(String[] str) {
		String[] result = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			result[i] = str[i].replaceAll("\"", "");
		}
		return result;
	}
	
	public static BundleContext getBundleContext() {
		return bundleContext;
	}
	
	public static void setBundleContext(BundleContext c) {
		bundleContext = c;
	}
	
	public static StatService getStatService() {
		
		BundleContext context = Util.getBundleContext();
		
	    ServiceReference<?> statServiceReference = context.getServiceReference(StatService.class.getName());
	    return (StatService) context.getService(statServiceReference);
	}
}
