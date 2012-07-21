package com.mindtree.agentsim.startup;

import java.util.List;

import org.apache.log4j.Logger;

import com.mindtree.agentsim.common.SimModuleIntf;

public class StartUpManager {
	
	private static Logger logger = Logger.getLogger(StartUpManager.class);
	
	public StartUpManager(List<SimModuleIntf> modules) {
		
		try{
			for(SimModuleIntf module : modules)
			{
				logger.debug("doing preInit for the module ::"+module.getModuleName());
				module.preInit();
				logger.debug("doing startup for the module ::"+module.getModuleName());
				module.startUp();
				logger.debug("doing postInit for the module ::"+module.getModuleName());
				module.postInit();
			}
		}catch(Exception e)
		{
			logger.error(e.getMessage(),e);
		}
	}
}
