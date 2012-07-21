package com.mindtree.agentsim.startup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.mindtree.agentsim.parser.DeviceDetails;
import com.mindtree.agentsim.util.IPUtils;

public class VIPUtilities {

	private static Logger logger = Logger.getLogger(VIPUtilities.class);

	public void createIp(List<DeviceDetails> ipList) throws Exception
	{
		String os = System.getProperty("os.name");
		logger.debug("Operating System is :::"+os);
		if(os.toUpperCase().contains("WINDOWS"))
		{
			logger.debug("Operating System resolved as windows");
			createWindowsIp(ipList);
		}else if(os.toUpperCase().contains("LINUX"))
		{
			logger.debug("Operating System resolved as Linux");
			createLinuxIp(ipList);
		}
	}

	public void createWindowsIp(List<DeviceDetails> deviceList) throws Exception
	{
		logger.debug("Creating "+deviceList.toString()+" Virtual IpAddress on windows");
		for(DeviceDetails device : deviceList)
		{
			createWindowsIp(device);
		}
	}

	//netsh in ip add address "Local Area Connection" 10.0.0.2 255.0.0.0
	private void createWindowsIp(DeviceDetails deviceDetails) throws Exception
	{
		if(!isIPCreated(deviceDetails.getDeviceIPAddress()))
		{
			String [] strArr = {};
			String cmd = "netsh in ip add address \""+deviceDetails.getIntf()+"\" "+deviceDetails.getDeviceIPAddress()+" "+deviceDetails.getNetMask();
			logger.debug("Command ::"+cmd);
			String result = executeCMD(cmd,strArr);
			Thread.sleep(5000);
			if(isIPCreated(deviceDetails.getDeviceIPAddress()))
			{
				logger.debug(deviceDetails.getDeviceIPAddress()+" IpAddess and subnetmask "+deviceDetails.getNetMask()+"created on windows successfully");
				deviceDetails.setIpCreationStr(cmd);
			}else
			{
				logger.error("Error while Creating IpAddess on windows"+result);
				throw new Exception("Error while Creating IpAddess on windows");
			}
		}
	}

	public void removeWindowsIp(List<DeviceDetails> deviceList) throws Exception
	{
		logger.debug("Removing "+deviceList.toString()+" Virtual IpAddress on windows");
		for(DeviceDetails device : deviceList)
		{
			removeWindowsIp(device);
		}
	}

	//netsh in ip delete address "Local Area Connection" 10.0.0.2 255.0.0.0
	private void removeWindowsIp(DeviceDetails deviceDetails) throws Exception
	{
		if(isIPCreated(deviceDetails.getDeviceIPAddress()))
		{
			String [] strArr = {};
			String cmd = "netsh in ip delete address \""+deviceDetails.getIntf()+"\" "+deviceDetails.getDeviceIPAddress()+" "+deviceDetails.getNetMask();
			logger.debug("Command ::"+cmd);
			String result = executeCMD(cmd,strArr);
			Thread.sleep(5000);
			if(!isIPCreated(deviceDetails.getDeviceIPAddress()))
			{
				logger.debug(deviceDetails.getDeviceIPAddress()+" IpAddess and subnetmask "+deviceDetails.getNetMask()+"deleted on windows successfully");
				deviceDetails.setIpCreationStr("");
			}else
			{
				logger.error("Error while deleting IpAddess on windows"+result);
				throw new Exception("Error while deleting IpAddess on windows");
			}
		}
	}

	
	///	/sbin/ifconfig eth0:1 172.19.1.2 netmask 255.255.255.0  up
	private void createLinuxIp(DeviceDetails deviceDetails,int cnt) throws Exception
	{
		if(!isIPCreated(deviceDetails.getDeviceIPAddress()))
		{
			String [] strArr = {};
			String cmd = "/sbin/ifconfig "+deviceDetails.getIntf()+":"+cnt+" "+deviceDetails.getDeviceIPAddress()+" netmask "+deviceDetails.getNetMask()+" up";
			logger.info("Creation Command ::"+cmd);
			String result = executeCMD(cmd,strArr);

			if(isIPCreated(deviceDetails.getDeviceIPAddress()))
			{
				logger.debug(deviceDetails.getDeviceIPAddress()+" IpAddess created on Linux successfully");
				deviceDetails.setIpCreationStr(cmd);
			}else
			{
				logger.error("Error while Creating IpAddess on Linux"+result);
				throw new Exception("Error while Creating IpAddess on Linux");
			}
		}
	}


	private String executeCMD(String cmd,String[] args) throws Exception
	{
		String line;
		Process process;

		StringBuffer resultBuffer = new StringBuffer();
		Runtime runtime = Runtime.getRuntime();
		if(args.length > 0)
			process = runtime.exec(cmd,args);
		else
			process = runtime.exec(cmd);

		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		while ((line = br.readLine()) != null) 
		{
			resultBuffer.append(line);
		}

		return resultBuffer.toString();
	}

	public void createLinuxIp(List<DeviceDetails> ipList) throws Exception
	{
		logger.debug("Creating "+ipList.toString()+" Virtual IpAddress on Linux");
		int cnt = 1;
		for(DeviceDetails ip :ipList)
		{
			createLinuxIp(ip,cnt++);
		}
	}

	public void removeLinuxIP(List<DeviceDetails> ipList) throws Exception
	{
		logger.debug("Removing "+ipList.toString()+" Virtual IpAddress on Linux");
		for(DeviceDetails ip :ipList)
		{
			removeLinuxIP(ip);
		}
	}

	public void removeLinuxIP(DeviceDetails device) throws Exception
	{
		if(isIPCreated(device.getDeviceIPAddress()))
		{
			String [] strArr = {};
			String str = device.getIpCreationStr();
			str = str.replace("up", "down");
			logger.debug("Query ::"+str);
			String result = executeCMD(str,strArr);
			
			if(!isIPCreated(device.getDeviceIPAddress()))
			{
				logger.debug(device.getDeviceIPAddress()+" IpAddess and subnetmask "+device.getNetMask()+"deleted on linux successfully");
				device.setIpCreationStr("");
			}else
			{
				logger.error("Error while deleting IpAddess on Linux"+result);
				throw new Exception("Error while deleting IpAddess on Linux");
			}
		}
	}
	
	public void removeIp(List<DeviceDetails> ipList) throws Exception
	{
		String os = System.getProperty("os.name");
		logger.debug("Operating System is :::"+os);
		if(os.toUpperCase().contains("WINDOWS"))
		{
			logger.debug("Operating System resolved as windows");
			removeWindowsIp(ipList);
		}else if(os.toUpperCase().contains("LINUX"))
		{
			logger.debug("Operating System resolved as Linux");
			removeLinuxIP(ipList);
		}
	}
	
	public void removeIp(DeviceDetails device) throws Exception
	{
		String os = System.getProperty("os.name");
		logger.debug("Operating System is :::"+os);
		if(os.toUpperCase().contains("WINDOWS"))
		{
			logger.debug("Operating System resolved as windows");
			removeWindowsIp(device);
		}else if(os.toUpperCase().contains("LINUX"))
		{
			logger.debug("Operating System resolved as Linux");
			removeLinuxIP(device);
		}
	}
	
	private boolean isIPCreated(String ipAddress) throws Exception
	{
		return IPUtils.getAllConfiguredIPAddress().contains(ipAddress);
	}
}
