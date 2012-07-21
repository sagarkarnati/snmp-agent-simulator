package com.mindtree.agentsim.main;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mindtree.agentsim.common.AgentSimConstants;
import com.mindtree.agentsim.mibble.MibEntry;
import com.mindtree.agentsim.mibble.MibParserIntf;
import com.mindtree.agentsim.parser.Device;
import com.mindtree.agentsim.parser.DeviceDetails;
import com.mindtree.agentsim.parser.XMLParserIntf;
import com.mindtree.agentsim.repository.RepositoryDaoIntf;
import com.mindtree.agentsim.startup.VIPUtilities;

/**
 * @author VidyaSagar
 *
 */
public class AgentSimMain {

	private static Logger logger = Logger.getLogger(AgentSimMain.class);
	private static ApplicationContext appContext;
	private NioDatagramAcceptor acceptor;
	private List<SocketAddress> bindSocketAdd;
	private XMLParserIntf xmlParser;
	private VIPUtilities vipCreator;
	private List<DeviceDetails> deviceList;
	private Map<String,DeviceDetails> deviceMap;
	
	public static ApplicationContext getAppContext()
	{
		if(null == appContext)
		{
			String filePath = System.getProperty("user.dir")+File.separator+"config"+File.separator+"SimulatorContext.xml";
			appContext = new FileSystemXmlApplicationContext("file:"+filePath);
		}
		return appContext;
	}

	public AgentSimMain() {

		try
		{	
			deviceMap = new HashMap<String, DeviceDetails>();
			
			//load model xml files to db and cache
			xmlParser = (XMLParserIntf)getAppContext().getBean(AgentSimConstants.XML_PARSER);
			deviceList = xmlParser.getDevicesList();

			acceptor = (NioDatagramAcceptor)getAppContext().getBean("ioAcceptor");
			bindSocketAdd = new ArrayList<SocketAddress>();

			vipCreator = (VIPUtilities)getAppContext().getBean(AgentSimConstants.VIP_CREATER);
			try
			{
				vipCreator.createIp(deviceList);
			}catch(Exception e)
			{
				System.err.println("Failed to create the IPAddress");
				System.exit(0);
			}
			RepositoryDaoIntf repoDao = (RepositoryDaoIntf)getAppContext().getBean(AgentSimConstants.MIB_REPOSITORY);

			for(DeviceDetails devicedetail : deviceList)
			{
				deviceMap.put(devicedetail.getDeviceIPAddress(), devicedetail);
				
				Device device = getDevice(devicedetail);

				checkXMLFile(devicedetail);

				List<MibEntry> entryList = xmlParser.getAllMibEntries(devicedetail.getModel());
				device.setMibEntries(entryList);

				repoDao.saveOrUpdate(device);

				//creating socketAddress Object and binding the data
				bindSocketAdd.add(new InetSocketAddress(device.getDeviceIPAddress(), device.getPort()));
			}

			acceptor.bind(bindSocketAdd);

			logger.debug("adding shutdown hook");
			Runtime rt = Runtime.getRuntime();
			rt.addShutdownHook(new shutDownThread());
			logger.info("adding shutdown hook");
			logger.info("Launching the command mode");
			startCmdMode();
			logger.info("Application properly initialized");
		}catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	private class shutDownThread extends Thread
	{
		public void run() 
		{
			try{
				logger.debug("Unbinding all the binded ipaddress");
				acceptor.unbind(bindSocketAdd);

				//TODO:To Remove all the Virtual IPAdress created
				vipCreator.removeIp(deviceList);
				logger.info("Shutdown completed");
			}catch(Exception e)
			{
				logger.error("Exception while shutdowning the Simulator",e);
				e.printStackTrace();
				System.exit(0);
			}
		}	
	}


	private void startCmdMode()
	{
		Scanner scanner = new Scanner(System.in);
		String command = "";
		System.out.println("Application initialized");
		while(true)
		{
			System.out.print("$");
			command = scanner.nextLine();
			if(command.equalsIgnoreCase("exit"))
			{
				System.out.println("Exiting Application...");
				System.exit(0);
			}else if(command.equalsIgnoreCase("deviceList"))
			{
				for(DeviceDetails deviceDetails : deviceList)
				{
					System.out.println(deviceDetails.getDeviceIPAddress());
				}
				
			}else if(command.equalsIgnoreCase("help"))
			{
				StringBuilder strBuilder = new StringBuilder();
				strBuilder.append("exit --- to shutdown the application\n");
				strBuilder.append("deviceList --- to list down all the device list\n");
				strBuilder.append("delete <IpAddress> --- to delete the device.Syntax::delete<space><ipaddress of the device>\n");
				System.out.print(strBuilder.toString());
				
			}else if(command.startsWith("delete "))
			{
				if(command.contains(" "))
				{	
					try
					{
						String ipAddress = command.substring(command.indexOf(" "),command.length()).trim();
						DeviceDetails details = deviceMap.get(ipAddress);
						vipCreator.removeIp(details);
						
						deviceList.remove(details);
						deviceMap.remove(ipAddress);
						
						System.out.println("Device "+ipAddress+"removed successfully.");
					}catch(Exception e)
					{
						logger.error(e);
						System.err.println("Failed to remove Device.");
					}
				}else
				{
					System.err.println("Invalid delete command format.please enter the valid format");
				}
			}/*else if(command.startsWith("adddevice "))
			{
				//-d172.25.200.250 -mintel.mib -p161 -v0 -wcpublic -rcpublic -MINTEL -ieth0 -n255.255.255.0
				StringTokenizer token = new StringTokenizer(command, " ");
				token.
				if()
				{
					
				}
			}else if(command.startsWith("addrange "))
			{
				//- 172.25.200.250 -mintel.mib -p161 -v0 -wcpublic -rcpublic -MINTEL -ieth0 -n255.255.255.0
			}else
			{
				System.err.println("Invalid command.check help for valid commands");
			}*/
		}
	}

	private void checkXMLFile(DeviceDetails devicedetail) throws Exception
	{
		logger.debug("Checking for the model xml file");
		String modelXMLLocation = xmlParser.getModelXMLDirLocation()+File.separatorChar + devicedetail.getModel() + ".xml";
		logger.debug("Model xml file location ::"+modelXMLLocation);
		File modelFile = new File(modelXMLLocation);
		if(!modelFile.exists())
		{
			logger.debug("Model xml file ::"+modelXMLLocation+" dose not exists.so creating the new");

			MibParserIntf mibParser = (MibParserIntf)getAppContext().getBean("mibParser");
			mibParser.createModelFileFromMib(devicedetail.getMibFileName(), devicedetail.getModel(), xmlParser.getModelXMLDirLocation());
			logger.debug("Model xml file ::"+modelXMLLocation+" created successfully");
		}else
		{
			logger.debug("Model xml file ::"+modelXMLLocation+" exists");
		}

	}

	public Device getDevice(DeviceDetails deviceDetails)
	{

		Device device = new Device();

		device.setDeviceIPAddress(deviceDetails.getDeviceIPAddress());
		device.setPort(Integer.parseInt(deviceDetails.getPort()));
		device.setModel(deviceDetails.getModel());
		device.setSnmpVersion(deviceDetails.getSnmpVersion());
		device.setReadCommunity(deviceDetails.getReadCommunity());
		device.setWriteCommunity(deviceDetails.getWriteCommunity());
		device.setIntf(deviceDetails.getIntf());
		device.setNetMask(deviceDetails.getNetMask());
		device.setMibFileName(deviceDetails.getMibFileName());

		return device;  
	}

	public static void main(String[] args) {

		new AgentSimMain();
	}
}
