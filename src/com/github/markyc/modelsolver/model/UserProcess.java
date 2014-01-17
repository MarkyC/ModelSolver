package com.github.markyc.modelsolver.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.markyc.stat.service.StatCollector;

/**
 * @author Marco
 *
 */
public class UserProcess implements Comparable<UserProcess>, Serializable {
	
	private static final long serialVersionUID = -3081338408878150273L;
	
	String name;
	int pid;
	boolean cpuMonitored;
	boolean memoryMonitored;
	boolean diskMonitored;
	int resolution;		// in seconds, for now

	private List<StatCollector> collectors;
	
	private static final short DEFAULT_RESOLUTION = 5;
	
	public UserProcess(String name, int pid) {
		this(name, pid, false, false, false, DEFAULT_RESOLUTION);
	}

	public UserProcess(String name, int pid, boolean cpuMonitored,
			boolean memoryMonitored, boolean diskMonitored) {
		this(name, pid, cpuMonitored, memoryMonitored, diskMonitored, DEFAULT_RESOLUTION);
	}

	/**
	 * @param name
	 * @param pid
	 * @param cpuMonitored
	 * @param memoryMonitored
	 * @param diskMonitored
	 * @param resolution (in seconds)
	 */
	public UserProcess(String name, int pid, boolean cpuMonitored,
			boolean memoryMonitored, boolean diskMonitored, int resolution) {
		super();
		this.name = name;
		this.pid = pid;
		this.cpuMonitored = cpuMonitored;
		this.memoryMonitored = memoryMonitored;
		this.diskMonitored = diskMonitored;
		this.resolution = resolution;
		this.collectors = new ArrayList<StatCollector>();
	}
	
	public void addCollector(StatCollector collector) {
		this.collectors.add(collector);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public boolean isMonitored() {
		return isCpuMonitored() || isMemoryMonitored() || isDiskMonitored();
	}

	/**
	 * @return the cpuMonitored
	 */
	public boolean isCpuMonitored() {
		return cpuMonitored;
	}

	/**
	 * @param cpuMonitored the cpuMonitored to set
	 */
	public void setCpuMonitored(boolean cpuMonitored) {
		this.cpuMonitored = cpuMonitored;
	}

	/**
	 * @return the memoryMonitored
	 */
	public boolean isMemoryMonitored() {
		return memoryMonitored;
	}

	/**
	 * @param memoryMonitored the memoryMonitored to set
	 */
	public void setMemoryMonitored(boolean memoryMonitored) {
		this.memoryMonitored = memoryMonitored;
	}

	/**
	 * @return the diskMonitored
	 */
	public boolean isDiskMonitored() {
		return diskMonitored;
	}

	/**
	 * @param diskMonitored the diskMonitored to set
	 */
	public void setDiskMonitored(boolean diskMonitored) {
		this.diskMonitored = diskMonitored;
	}

	/**
	 * @return the resolution
	 */
	public int getResolution() {
		return resolution;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	@Override
	public int compareTo(UserProcess p) {
        return getName().compareTo(p.getName());
	}
	
	public void serialize(File file) throws IOException{
		
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);

		out.close();
    	fileOut.close();
		
	}
	
	public UserProcess deserialize(File file) throws IOException, ClassNotFoundException {
		

		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		 
		UserProcess p = (UserProcess) in.readObject();
		 
		in.close();
		fileIn.close();
		return p;
		
	}

}
