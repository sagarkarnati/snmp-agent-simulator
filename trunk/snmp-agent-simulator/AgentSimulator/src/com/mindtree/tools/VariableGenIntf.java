package com.mindtree.tools;

import org.snmp4j.smi.Variable;

/**
 * @author VidyaSagar
 *
 */
public interface VariableGenIntf {
	
	public Variable getGeneVariable(String oid) throws Exception;
}
