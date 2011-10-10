package com.mindtree.agentsim.common;

import org.snmp4j.PDU;

public class SNMPTo {

	private String ipAddress;
	private String community;
	private int version;
	private int mutableByte;
	private PDU pdu;

	public SNMPTo(String ipAdd, String community, int version,int mutableByte, PDU pdu) {

		this.ipAddress = ipAdd;
		this.community = community;
		this.version = version;
		this.mutableByte = mutableByte;
		this.pdu = pdu;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getMutableByte() {
		return mutableByte;
	}

	public void setMutableByte(int mutableByte) {
		this.mutableByte = mutableByte;
	}

	public PDU getPdu() {
		return pdu;
	}

	public void setPdu(PDU pdu) {
		this.pdu = pdu;
	}

}
