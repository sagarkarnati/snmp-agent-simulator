package com.mindtree.agentsim.parser;

public class IPRange {
	
	private String fromIP;
	private String toIP;
	private String port;
	private String model;
	private String snmpVersion;
	private String readCommunity;
	private String writeCommunity;
	
	//incase of windows give this is the LAN Name and Linux give this as interface Name
	private String intf;
	private String netMask;
	private String mibFileName;
	
	public IPRange() {
		//default
	}
	
	public IPRange(String fromIP, String toIP, String port,String model,
			String snmpVersion, String readCommunity, String writeCommunity,
			String intf, String netMask, String mibFileName) {
		super();
		this.fromIP = fromIP;
		this.toIP = toIP;
		this.port = port;
		this.model = model;
		this.snmpVersion = snmpVersion;
		this.readCommunity = readCommunity;
		this.writeCommunity = writeCommunity;
		this.intf = intf;
		this.netMask = netMask;
		this.mibFileName = mibFileName;
	}

	public String getFromIP() {
		return fromIP;
	}

	public void setFromIP(String fromIP) {
		this.fromIP = fromIP;
	}

	public String getToIP() {
		return toIP;
	}

	public void setToIP(String toIP) {
		this.toIP = toIP;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public String getReadCommunity() {
		return readCommunity;
	}

	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	public String getWriteCommunity() {
		return writeCommunity;
	}

	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}

	public String getIntf() {
		return intf;
	}

	public void setIntf(String intf) {
		this.intf = intf;
	}

	public String getNetMask() {
		return netMask;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public String getMibFileName() {
		return mibFileName;
	}

	public void setMibFileName(String mibFileName) {
		this.mibFileName = mibFileName;
	}

	@Override
	public String toString() {
		return "IPRange [fromIP=" + fromIP + ", toIP=" + toIP + ", port="
				+ port + ", model=" + model + ", snmpVersion=" + snmpVersion
				+ ", readCommunity=" + readCommunity + ", writeCommunity="
				+ writeCommunity + ", intf=" + intf + ", netMask=" + netMask
				+ ", mibFileName=" + mibFileName + "]";
	}
}
