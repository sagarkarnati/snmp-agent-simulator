package com.mindtree.agentsim.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.mindtree.agentsim.mibble.MibEntry;
import com.mindtree.agentsim.util.IPUtils;

/**
 * @author VidyaSagar
 *
 */
public class XMLParser implements XMLParserIntf{

	private static Logger logger = Logger.getLogger(XMLParser.class);
	
	private String deviceXMLLocation;
	private String modelXMLDirLocation;

	public XMLParser(String deviceXMLLocation,String modelXMLDirLocation)
	{
		this.deviceXMLLocation = deviceXMLLocation;
		this.modelXMLDirLocation = modelXMLDirLocation;
	}

	public List<DeviceDetails> getDevicesList() throws FileNotFoundException, MarshalException, ValidationException{

		List<DeviceDetails> finalDeviceList = new ArrayList<DeviceDetails>();
		FileReader reader = new FileReader(getDeviceXMLLocation());
		List<Object> deviceList = (ArrayList<Object>) Unmarshaller.unmarshal(ArrayList.class, reader);
		for(Object deviceObj : deviceList)
		{
			if(deviceObj instanceof DeviceDetails)
			{
				finalDeviceList.add((DeviceDetails)deviceObj);

			}else if(deviceObj instanceof IPRange)
			{
				finalDeviceList.addAll(getIPRangeDeviceList((IPRange)deviceObj));
			}
		}
		return finalDeviceList;
	}

	public void addDevicesToDevicesList(List<Object> devices) throws MarshalException, ValidationException, IOException {

		FileReader reader = new FileReader(getDeviceXMLLocation());
		ArrayList<Object> devicesList= (ArrayList<Object>) Unmarshaller.unmarshal(ArrayList.class, reader);

		devicesList.addAll(devices);

		FileWriter writer = new FileWriter(new File(getDeviceXMLLocation()));
		Marshaller.marshal(devicesList, writer);

	}

	public void addMibEntriesToModel(List<MibEntry> mibEntries,String modelName) throws MarshalException, ValidationException, IOException{

		String modelXMLLocation = getModelXMLDirLocation() + modelName + ".xml";
		if(!new File(modelXMLLocation).exists())
		{
			FileWriter modelXMLWriter = new FileWriter(new File(modelXMLLocation));
			Marshaller.marshal(mibEntries, modelXMLWriter);

		}else
		{
			FileReader modelFileReader = new FileReader(modelXMLLocation);
			ArrayList<Object> mibEntriesList= (ArrayList<Object>) Unmarshaller.unmarshal(ArrayList.class, modelFileReader);

			mibEntriesList.addAll(mibEntries);

			FileWriter writer = new FileWriter(new File(modelXMLLocation));
			Marshaller.marshal(mibEntriesList, writer);
		}
	}

	public List<MibEntry> getAllMibEntries(String modelName) throws FileNotFoundException, MarshalException, ValidationException{

		String modelXMLLocation = getModelXMLDirLocation()+File.separatorChar + modelName + ".xml";
		FileReader reader = new FileReader(modelXMLLocation);

		return (List<MibEntry>) Unmarshaller.unmarshal(ArrayList.class, reader);
	}

	public MibEntry getModelMibEntry(String modelName,String oid) throws FileNotFoundException, MarshalException, ValidationException{

		List<MibEntry> mibEntries = getAllMibEntries(modelName);

		for(MibEntry mibEntry : mibEntries ) {

			if(mibEntry.getOid().equals(oid)) {
				return mibEntry;	
			}
		}
		return null;

	}

	public MibEntry getMibEntry(String IPAddress, String oid) throws MarshalException, ValidationException, FileNotFoundException {

		List<DeviceDetails> devicesList = getDevicesList();

		for(DeviceDetails obj : devicesList) 
		{
			if(IPAddress.equals(obj.getDeviceIPAddress()))
			{
				return getModelMibEntry(obj.getModel(), oid);
			}
		}
		return null;
	}

	public String getDeviceXMLLocation() {
		return deviceXMLLocation;
	}

	public void setDeviceXMLLocation(String deviceXMLLocation) {
		this.deviceXMLLocation = deviceXMLLocation;
	}

	public String getModelXMLDirLocation() {
		return modelXMLDirLocation;
	}

	public void setModelXMLDirLocation(String modelXMLDirLocation) {
		this.modelXMLDirLocation = modelXMLDirLocation;
	}

	public List<String> getAllConfiguredModels() throws Exception 
	{	
		List<String> configModels = new ArrayList<String>();
		
		for(DeviceDetails device : getDevicesList())
		{
			configModels.add(device.getModel());
		}
		return configModels;
	}
	
	public List<DeviceDetails> getIPRangeDeviceList(IPRange ipRangeObj)
	{
		List<DeviceDetails> deviceDetailsList = new ArrayList<DeviceDetails>();
		for(String ipAdd : IPUtils.IPRangeCalc(ipRangeObj.getFromIP(), ipRangeObj.getToIP()))
		{
			deviceDetailsList.add(getConstructedDeviceDetails(ipRangeObj,ipAdd));
		}
		return deviceDetailsList;
	}
	
	private DeviceDetails getConstructedDeviceDetails(IPRange ipRangeObj,String ip)
	{
		DeviceDetails deviceDetails = new DeviceDetails();
		
		deviceDetails.setDeviceIPAddress(ip);
		deviceDetails.setPort(ipRangeObj.getPort());
		deviceDetails.setIntf(ipRangeObj.getIntf());
		deviceDetails.setMibFileName(ipRangeObj.getMibFileName());
		deviceDetails.setModel(ipRangeObj.getModel());
		deviceDetails.setNetMask(ipRangeObj.getNetMask());
		deviceDetails.setReadCommunity(ipRangeObj.getReadCommunity());
		deviceDetails.setSnmpVersion(ipRangeObj.getSnmpVersion());
		deviceDetails.setWriteCommunity(ipRangeObj.getWriteCommunity());
		
		return deviceDetails;
	}
}
