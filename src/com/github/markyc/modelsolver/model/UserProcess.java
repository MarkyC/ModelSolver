/**
 * 
 */
package com.github.markyc.modelsolver.model;

/**
 * @author Marco
 *
 */
public class UserProcess implements Comparable<UserProcess> {
	

	String name;
	int pid;
	boolean cpuMonitored;
	boolean memoryMonitored;
	boolean diskMonitored;
	short resolution;		// in seconds, for now
	
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
			boolean memoryMonitored, boolean diskMonitored, short resolution) {
		super();
		this.name = name;
		this.pid = pid;
		this.cpuMonitored = cpuMonitored;
		this.memoryMonitored = memoryMonitored;
		this.diskMonitored = diskMonitored;
		this.resolution = resolution;
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
	public short getResolution() {
		return resolution;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(short resolution) {
		this.resolution = resolution;
	}

	@Override
	public int compareTo(UserProcess p) {
        return getName().compareTo(p.getName());
	}

}
