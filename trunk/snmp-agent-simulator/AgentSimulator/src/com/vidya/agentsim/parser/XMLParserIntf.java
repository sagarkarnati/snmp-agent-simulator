/**
 * 
 */
package com.mindtree.agentsim.parser;

import java.util.List;

import com.mindtree.agentsim.mibble.MibEntry;

/**
 * @author VidyaSagar
 *
 */
public interface XMLParserIntf 
{
	public List<String> getAllConfiguredModels() throws Exception;
	
	public List<DeviceDetails> getDevicesList() throws Exception;

	public void addDevicesToDevicesList(List<Object> devices) throws Exception;

	public void addMibEntriesToModel(List<MibEntry> mibEntries,String modelName) throws Exception;

	public List<MibEntry> getAllMibEntries(String modelName) throws Exception;
	
	public MibEntry getModelMibEntry(String modelName,String oid) throws Exception;
	
	public MibEntry getMibEntry(String IPAddress, String oid) throws Exception;

	public String getModelXMLDirLocation();
	
}