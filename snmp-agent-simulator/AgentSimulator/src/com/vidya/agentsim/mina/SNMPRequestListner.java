package com.mindtree.agentsim.mina;
import org.apache.log4j.Logger;

import com.mindtree.agentsim.common.SimModuleIntf;
import com.mindtree.agentsim.main.AgentSimMain;


public class SNMPRequestListner implements SimModuleIntf{
	
	private boolean isRunning = false;	
	private static Logger logger = Logger.getLogger(SNMPRequestListner.class);

	public String getModuleName() throws Exception {

		return "SNMP Request Listner";
	}

	public boolean isRunning() throws Exception {
		
		return isRunning;
	}

	public boolean postInit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean postShutDown() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean preInit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean preShutdown() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean shutDown() throws Exception {
		
		logger.debug("Shutdown triggered for the module ::"+getModuleName());
		isRunning = false;
		
		return true;
	}

	public boolean startUp() throws Exception {
		try
		{
			logger.debug("Start up triggered for the module ::"+getModuleName());
			AgentSimMain.getAppContext().getBean("trapHandler");
			isRunning = true;
			
			logger.debug(getModuleName()+" started successfully.");
			return true;
		
		}catch(Exception e)
		{
			throw new Exception("Error while starting "+getModuleName(),e);
		}
	}
}
