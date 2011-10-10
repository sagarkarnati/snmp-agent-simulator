/**
 * 
 */
package com.mindtree.agentsim.parser;

import java.util.List;

/**
 * @author VidyaSagar
 *
 */
public interface DeviceDataParserIntf {
	
	public DeviceDetails getDeviceDetails(String ipAddress) throws Exception;
	
	public void createVIP(DeviceDetails deviceDetails) throws Exception;
	
	public void createAllVIP(List<DeviceDetails> deviceDetailsList) throws Exception;
	
	public List<String> getAllConfiguredIP() throws Exception;
	
	public void saveOrUpdate(DeviceDetails deviceDetail) throws Exception;
	
	public void saveOrUpdate(List<DeviceDetails> deviceDetails) throws Exception;
	
	public void delete(DeviceDetails deviceDetail) throws Exception;
	
	public List<DeviceDetails> getAllDeviceDetails() throws Exception;
	
	public String getModelName(String ipAddr) throws Exception;
}
