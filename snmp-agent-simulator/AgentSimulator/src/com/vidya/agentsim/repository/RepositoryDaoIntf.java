package com.mindtree.agentsim.repository;

import java.util.List;

import com.mindtree.agentsim.mibble.MibEntry;
import com.mindtree.agentsim.parser.Device;

/**
 * 
 * @author VidyaSagar
 *
 */
public interface RepositoryDaoIntf {
	
	public MibEntry getMibEntry(String oid) throws Exception;
	
	public void saveOrUpdate(MibEntry mibEntry) throws Exception;
	
	public void saveOrUpdate(List<MibEntry> mibEntry) throws Exception;
	
	public MibEntry find(Long id) throws Exception;

	public void delete(MibEntry mibentry) throws Exception;

	public List getAllMibEntries() throws Exception; 
	
	public MibEntry getNextMibEntry(String deviceIp,String oid) throws Exception;

	public void saveOrUpdate(Device device);
}
