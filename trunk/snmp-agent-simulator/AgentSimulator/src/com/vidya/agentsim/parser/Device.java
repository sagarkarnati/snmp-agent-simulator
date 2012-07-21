package com.mindtree.agentsim.parser;

import java.util.List;

import com.mindtree.agentsim.mibble.MibEntry;

public class Device {
	
	private Long id;
	private String deviceIPAddress;
	private int port;
	private String model;
	private String snmpVersion;
	private String readCommunity;
	private String writeCommunity;
	private String intf;
	private String netMask;
	private String mibFileName;
	private String ipCreationStr;
	private List<MibEntry> mibEntries;
	
	public Device() {
		
		//default constructor
	}
	
	public Device(Long id, String deviceIPAddress, int port, String model,
			String snmpVersion, String readCommunity, String writeCommunity,
			String intf, String netMask, String mibFileName,
			List<MibEntry> mibEntries) {
		super();
		this.id = id;
		this.deviceIPAddress = deviceIPAddress;
		this.port = port;
		this.model = model;
		this.snmpVersion = snmpVersion;
		this.readCommunity = readCommunity;
		this.writeCommunity = writeCommunity;
		this.intf = intf;
		this.netMask = netMask;
		this.mibFileName = mibFileName;
		this.mibEntries = mibEntries;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
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
	 * @return the mibEntries
	 */
	public List<MibEntry> getMibEntries() {
		return mibEntries;
	}

	/**
	 * @param mibEntries the mibEntries to set
	 */
	public void setMibEntries(List<MibEntry> mibEntries) {
		this.mibEntries = mibEntries;
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
		final int maxLen = 10;
		return "Device [id="
				+ id
				+ ", deviceIPAddress="
				+ deviceIPAddress
				+ ", port="
				+ port
				+ ", model="
				+ model
				+ ", snmpVersion="
				+ snmpVersion
				+ ", readCommunity="
				+ readCommunity
				+ ", writeCommunity="
				+ writeCommunity
				+ ", intf="
				+ intf
				+ ", netMask="
				+ netMask
				+ ", mibFileName="
				+ mibFileName
				+ ", mibEntries="
				+ (mibEntries != null ? mibEntries.subList(0,
						Math.min(mibEntries.size(), maxLen)) : null) + "]";
	}
}
