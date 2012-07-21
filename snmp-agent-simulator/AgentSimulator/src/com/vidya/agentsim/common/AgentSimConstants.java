package com.mindtree.agentsim.common;


public class AgentSimConstants {
	
	//Generation Types
	public static final String RANDOM = "RANDOM";
	public static final String INCREMENT = "INCREMENT";
	public static final String DECREMENT = "DECREMENT";
	public static final String NONE = "NONE";
	
	//Data Types
	public static final String INTEGER = "INTEGER";
	public static final String STRING = "OCTET STRING";
	public static final String OID = "OBJECT IDENTIFIER";
	public static final String TIME_TICKS = "TIME_TICKS";
	public static final String SEQUENCE = "SEQUENCE";
	
	//BeanNames
	public static final String MIB_REPOSITORY = "mibrepositoty";
	public static final String VARIABLE_GENERATOR = "variableGen";
	public static final String MIB_PARSER = "mibParser";
	public static final String DEVICE_PARSER = "deviceParser";
	public static final String XML_PARSER = "xmlParser";
	public static final String VIP_CREATER = "vipCreater";
	
	//Access types
	public static final String NOT_IMPLEMENTED = "not-implemented";
	public static final String NOT_ACCESSIBLE = "not-accessible";
	public static final String ACCESSIBLE_FOR_NOTIFY = "accessible-for-notify";
	public static final String READ_ONLY = "read-only";
	public static final String READ_WRITE = "read-write";
	public static final String READ_CREATE = "read-create";
	public static final String WRITE_ONLY = "write-only";
}
