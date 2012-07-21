package com.mindtree.agentsim.parser;

public class DeviceDetails {
	

	private String deviceIPAddress;
	private String port;
	private String model;
	private String snmpVersion;
	private String readCommunity;
	private String writeCommunity;

	//incase of windows give this is the LAN Name and Linux give this as interface Name
	private String intf;
	private String netMask;
	private String mibFileName;
	private String ipCreationStr;
	
	public DeviceDetails(String deviceIPAddress, String port, String model,
			String snmpVersion, String readCommunity, String writeCommunity,
			String intf, String netMask, String mibFileName) {
		super();
		this.deviceIPAddress = deviceIPAddress;
		this.port = port;
		this.model = model;
		this.snmpVersion = snmpVersion;
		this.readCommunity = readCommunity;
		this.writeCommunity = writeCommunity;
		this.intf = intf;
		this.netMask = netMask;
		this.mibFileName = mibFileName;
	}

	public DeviceDetails() {
		
		//default
	}

	/**
	 * @return the deviceIPAddress
	 */
	public String getDeviceIPAddress() {
		return deviceIPAddress;
	}

	/**
	 * @param deviceIPAddress the deviceIPAddress to set
	 */
	public void setDeviceIPAddress(String deviceIPAddress) {
		this.deviceIPAddress = deviceIPAddress;
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

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the snmpVersion
	 */
	public String getSnmpVersion() {
		return snmpVersion;
	}

	/**
	 * @param snmpVersion the snmpVersion to set
	 */
	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	/**
	 * @return the readCommunity
	 */
	public String getReadCommunity() {
		return readCommunity;
	}

	/**
	 * @param readCommunity the readCommunity to set
	 */
	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	/**
	 * @return the writeCommunity
	 */
	public String getWriteCommunity() {
		return writeCommunity;
	}

	/**
	 * @param writeCommunity the writeCommunity to set
	 */
	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}

	/**
	 * @return the intf
	 */
	public String getIntf() {
		return intf;
	}

	/**
	 * @param intf the intf to set
	 */
	public void setIntf(String intf) {
		this.intf = intf;
	}

	/**
	 * @return the netMask
	 */
	public String getNetMask() {
		return netMask;
	}

	/**
	 * @param netMask the netMask to set
	 */
	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	/**
	 * @return the mibFileName
	 */
	public String getMibFileName() {
		return mibFileName;
	}

	/**
	 * @param mibFileName the mibFileName to set
	 */
	public void setMibFileName(String mibFileName) {
		this.mibFileName = mibFileName;
	}

	/**
	 * @return the ipCreationStr
	 */
	public String getIpCreationStr() {
		return ipCreationStr;
	}

	/**
	 * @param ipCreationStr the ipCreationStr to set
	 */
	public void setIpCreationStr(String ipCreationStr) {
		this.ipCreationStr = ipCreationStr;
	}

	@Override
	public String toString() {
		return "DeviceDetails [deviceIPAddress=" + deviceIPAddress + ", port="
				+ port + ", model=" + model + ", snmpVersion=" + snmpVersion
				+ ", readCommunity=" + readCommunity + ", writeCommunity="
				+ writeCommunity + ", intf=" + intf + ", netMask=" + netMask
				+ ", mibFileName=" + mibFileName + "]";
	}
	
}
