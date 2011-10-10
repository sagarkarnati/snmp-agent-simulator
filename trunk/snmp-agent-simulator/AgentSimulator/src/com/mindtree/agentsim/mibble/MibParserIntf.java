package com.mindtree.agentsim.mibble;

import java.util.List;

/**
 * @author VidyaSagar
 *
 */
public interface MibParserIntf {
	
	public List<MibEntry> parseMib(String fileName) throws Exception;
	
	public void createModelFileFromMib(String mibFileName,String modelName,String modelPath) throws Exception;
}
