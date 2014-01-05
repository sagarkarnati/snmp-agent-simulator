package com.mindtree.tools;

import org.snmp4j.smi.Variable;


/**
 * @author Vidya Sagar
 *
 * Date Jan 5, 2014 12:26:10 PM
 *
 */
public interface VariableGenIntf
{

	public Variable getGeneVariable(String oid) throws Exception;
}
