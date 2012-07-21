package com.mindtree.agentsim.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import com.mindtree.agentsim.parser.DeviceDetails;
import com.mindtree.agentsim.parser.IPRange;

public class DeviceXMLCreator {
	
	private Logger logger = Logger.getLogger(DeviceXMLCreator.class.getClass());
	public void createDeviceXMLFile(){

		List<Object> devicesList = new ArrayList<Object>();
		DeviceDetails deviceDetails = new DeviceDetails("172.25.200.250","162","GSM1413","0","public","public","eth0","255.255.255.0","RFC1213");
		IPRange ipRange = new IPRange("172.25.200.251","172.25.200.254","162","GSM1567","1","public","public","eth0","255.255.255.0","RFC1213");

		devicesList.add(deviceDetails);
		devicesList.add(ipRange);

		try {
			FileWriter writer = new FileWriter(new File("/home/mindtree/helios_workspace/AgentSimulator/config/Devices.xml"));
			Marshaller.marshal(devicesList, writer);
//			FileReader reader = new FileReader("/home/mindtree/helios_workspace/AgentSimulator/config/Devices.xml");
//			ArrayList< Object> listOfDevices = (ArrayList<Object>) Unmarshaller.unmarshal(ArrayList.class, reader);
//			
//			for(Object obj : listOfDevices) {
//				
//				if(obj instanceof DeviceDetails){
//					DeviceDetails deviceDetails = (DeviceDetails) obj;
//					System.out.println(deviceDetails.toString());
//				} 
//				else if(obj instanceof IPRange) {
//					IPRange ipRange = (IPRange) obj;
//					System.out.println(ipRange.toString());
//				}
//			} 

		}catch (MarshalException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (ValidationException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		new DeviceXMLCreator().createDeviceXMLFile();
	}

}
