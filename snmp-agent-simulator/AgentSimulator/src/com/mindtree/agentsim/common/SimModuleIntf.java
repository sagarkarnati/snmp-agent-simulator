package com.mindtree.agentsim.common;

public interface SimModuleIntf {
	
	public boolean preInit() throws Exception;
	public boolean startUp() throws Exception;
	public boolean postInit() throws Exception;
	
	public boolean preShutdown() throws Exception;
	public boolean shutDown() throws Exception;
	public boolean postShutDown() throws Exception;
	
	public String getModuleName() throws Exception;
	public boolean isRunning() throws Exception;
}
