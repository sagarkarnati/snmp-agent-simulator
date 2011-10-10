package com.mindtree.agentsim.parser;

import java.util.List;

/**
 * @author VidyaSagar
 * 
 */
public class DeviceDataParser implements DeviceDataParserIntf {
	
	private XMLParserIntf xmlParserUtil;
	
	public DeviceDataParser(XMLParserIntf xmlParserUtil) {
		
		this.xmlParserUtil = xmlParserUtil;
	}
	
	public void createAllVIP(List<DeviceDetails> deviceDetailsList)	throws Exception 
	{
		// TODO Auto-generated method stub

	}

	public void createVIP(DeviceDetails deviceDetails) throws Exception {
		// TODO Auto-generated method stub

	}

	public void delete(DeviceDetails deviceDetail) throws Exception {
		 

	}

	public List<String> getAllConfiguredIP() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DeviceDetails> getAllDeviceDetails() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public DeviceDetails getDeviceDetails(String ipAddress) throws Exception {
		
		return null;
	}

	public void saveOrUpdate(DeviceDetails deviceDetail) throws Exception {
		// TODO Auto-generated method stub

	}

	public void saveOrUpdate(List<DeviceDetails> deviceDetails)	throws Exception 
	{
		// TODO Auto-generated method stub

	}

	public String getModelName(String ipAddr) throws Exception 
	{
		for(Object obj : xmlParserUtil.getDevicesList())
		{
			DeviceDetails details = (DeviceDetails)obj;
			
			return details.getModel();
		}
		return null;
	}

	public XMLParserIntf getXmlParserUtilIntf() {
		return xmlParserUtil;
	}

	public void setXmlParserUtil(XMLParserIntf xmlParserUtilIntf) {
		this.xmlParserUtil = xmlParserUtil;
	}
}
