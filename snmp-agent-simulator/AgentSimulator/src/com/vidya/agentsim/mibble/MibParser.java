package com.mindtree.agentsim.mibble;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderLog;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.snmp.SnmpAccess;
import net.percederberg.mibble.snmp.SnmpAgentCapabilities;
import net.percederberg.mibble.snmp.SnmpModuleCompliance;
import net.percederberg.mibble.snmp.SnmpModuleIdentity;
import net.percederberg.mibble.snmp.SnmpNotificationGroup;
import net.percederberg.mibble.snmp.SnmpNotificationType;
import net.percederberg.mibble.snmp.SnmpObjectGroup;
import net.percederberg.mibble.snmp.SnmpObjectIdentity;
import net.percederberg.mibble.snmp.SnmpObjectType;
import net.percederberg.mibble.snmp.SnmpTextualConvention;
import net.percederberg.mibble.snmp.SnmpTrapType;
import net.percederberg.mibble.type.BitSetType;
import net.percederberg.mibble.type.BooleanType;
import net.percederberg.mibble.type.ChoiceType;
import net.percederberg.mibble.type.ElementType;
import net.percederberg.mibble.type.IntegerType;
import net.percederberg.mibble.type.NullType;
import net.percederberg.mibble.type.ObjectIdentifierType;
import net.percederberg.mibble.type.RealType;
import net.percederberg.mibble.type.SequenceOfType;
import net.percederberg.mibble.type.SequenceType;
import net.percederberg.mibble.type.StringType;
import net.percederberg.mibble.type.TypeReference;
import net.percederberg.mibble.value.BitSetValue;
import net.percederberg.mibble.value.BooleanValue;
import net.percederberg.mibble.value.NullValue;
import net.percederberg.mibble.value.NumberValue;
import net.percederberg.mibble.value.ObjectIdentifierValue;
import net.percederberg.mibble.value.StringValue;
import net.percederberg.mibble.value.ValueReference;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.mindtree.agentsim.common.AgentSimConstants;

public class MibParser implements MibParserIntf{

	private static Logger logger = Logger.getLogger(MibParser.class);
	private String mibDir;
	private List<String> tmpList;
	
	public MibParser(String dirPath) {

		this.mibDir = dirPath;
	}

//	public static void main(String[] args) {
//
//		try{
//			MibParser parser = new MibParser("/home/mindtree/helios_workspace/AgentSimulator/mibs");
//			List<MibEntry> mibentryList = parser.parseMib("RFC1213-MIB");
//			addMibEntriesToModel(mibentryList,"GSM1213","/home/mindtree/helios_workspace/AgentSimulator/models");
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	public void createModelFileFromMib(String mibFileName,String modelName,String modelPath) throws Exception
	{
		List<MibEntry> mibentryList = parseMib(mibFileName);
		addMibEntriesToModel(mibentryList,modelName,modelPath);
	}
	
	public List<MibEntry> parseMib(String fileName) throws Exception 
	{
		Mib mib = loadMib(mibDir+File.separatorChar+fileName);
		MibLoaderLog log = mib.getLog();
		if(log.errorCount() > 0)
		{
			logger.error(log.toString());
		}
		else
		{
			logger.debug(log.toString());
		}
		List<MibEntry> mibEntryList = getAllOidInfo(mib);
		logger.debug("Total mib elements count ::"+mibEntryList.size());

		return mibEntryList;
	}

	private Mib loadMib(String filePath) throws Exception
	{
		logger.debug("Loading mibfile ::"+filePath);
		MibLoader  loader = new MibLoader();

		File mibFile = new File(filePath);
		loader.addDir(new File(mibDir));

		return loader.load(mibFile);
	}

	private List<MibEntry> getAllOidInfo(Mib mib)
	{
		Collection<MibSymbol> allSymbols = mib.getAllSymbols();
		List<MibEntry> mibEntryList = new ArrayList<MibEntry>();
		tmpList = new ArrayList<String>();
		
		logger.debug("Total Elements Count in the mib File is :: "+allSymbols.size());
		for(MibSymbol symbol : allSymbols)
		{
			if(symbol instanceof MibValueSymbol)
			{
				extractOid(symbol,mibEntryList);
			}
		}
		return mibEntryList;
	}

	private void extractOid(MibSymbol symbol,List<MibEntry> mibEntryList) 
	{
		MibValue  value;
		MibValueSymbol valueSymbol;
		MibEntry entry = new MibEntry();
		entry.setType("");
		entry.setAccess("");
		
		entry.setValueGenType(AgentSimConstants.NONE);
		valueSymbol = (MibValueSymbol)symbol;
		value = valueSymbol.getValue();
		
		MibType type = valueSymbol.getType();
		if (value instanceof ObjectIdentifierValue) 
		{
			ObjectIdentifierValue oidval =  (ObjectIdentifierValue) value;
			entry.setEntryName(oidval.getName());
			entry.setOid(oidval.toString());
			
		}else if (value instanceof BitSetValue) 
		{
			BitSetValue oidval =  (BitSetValue) value;
			entry.setEntryName(oidval.getName());
			entry.setOid(oidval.toString());

		}else if (value instanceof BooleanValue) 
		{
			BooleanValue oidval =  (BooleanValue) value;
			entry.setEntryName(oidval.getName());
			entry.setOid(oidval.toString());

		}else if (value instanceof StringValue) 
		{
			StringValue oidval =  (StringValue) value;
			entry.setEntryName(oidval.getName());
			entry.setOid(oidval.toString());

		}else if (value instanceof NumberValue) 
		{
			NumberValue oidval =  (NumberValue) value;
			entry.setEntryName(oidval.getName());
			entry.setOid(oidval.toString());

		}else if (value instanceof NullValue) 
		{
			NullValue oidval =  (NullValue) value;
			entry.setEntryName(oidval.getName());
			entry.setOid(oidval.toString());

		}else if (value instanceof ValueReference) 
		{
			logger.info("Value Reference");
		}
		fillMibType(type,entry);
		if((null == entry.getValue()) || (entry.getValue().trim().length() == 0))
			entry.setValue("");
		
		if(valueSymbol.isScalar())
		{
			String oldOID = entry.getOid();
			entry.setOid(oldOID+".0");
		}

		if(!tmpList.contains(entry.getOid()))
		{
			mibEntryList.add(entry);
			tmpList.add(entry.getOid());
		}
		for(MibValueSymbol valSymbol : valueSymbol.getChildren())
		{
			extractOid(valSymbol,mibEntryList);
		}
	}

	private void fillMibType(MibType type,MibEntry entry)
	{
		entry.setConstraints("");
		entry.setValueGenType(AgentSimConstants.NONE);
		if (type instanceof BitSetType) 
		{
			BitSetType oidType = (BitSetType) type;
			entry.setType(oidType.getName());
			if(null != oidType.getConstraint())
				entry.setConstraints(oidType.getConstraint().toString());

		}else if (type instanceof BooleanType) 
		{
			BooleanType oidType = (BooleanType) type;
			entry.setType(oidType.getName());

		}else if (type instanceof ChoiceType) 
		{
			ChoiceType oidType = (ChoiceType) type;
			entry.setType(oidType.getName());

		}else if (type instanceof ElementType) 
		{
			ElementType oidType = (ElementType) type;
			entry.setType(oidType.getName());

		}else if (type instanceof IntegerType) 
		{
			IntegerType oidType = (IntegerType) type;
			entry.setType(oidType.getName());
			entry.setValueGenType(AgentSimConstants.RANDOM);
			entry.setValue("0");
			if(null != oidType.getConstraint())
				entry.setConstraints(oidType.getConstraint().toString());


		}else if (type instanceof NullType) 
		{
			NullType oidType = (NullType) type;
			entry.setType(oidType.getName());

		}else if (type instanceof ObjectIdentifierType) 
		{
			ObjectIdentifierType oidType = (ObjectIdentifierType) type;
			entry.setType(oidType.getName());

		}else if (type instanceof RealType) 
		{
			RealType oidType = (RealType) type;
			entry.setType(oidType.getName());

		}else if (type instanceof SequenceOfType) 
		{
			SequenceOfType oidType = (SequenceOfType) type;
			entry.setType(oidType.getName());
			entry.setValueGenType(AgentSimConstants.INCREMENT);
			entry.setValue("0");
			if(null != oidType.getConstraint())
				entry.setConstraints(oidType.getConstraint().toString());
			
		}else if (type instanceof SequenceType) 
		{
			SequenceType oidType = (SequenceType) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpAgentCapabilities) 
		{
			SnmpAgentCapabilities oidType = (SnmpAgentCapabilities) type;
			entry.setType(oidType.getName());

		}else if (type instanceof SnmpModuleCompliance) 
		{
			SnmpModuleCompliance oidType = (SnmpModuleCompliance) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpModuleIdentity) 
		{
			SnmpModuleIdentity oidType = (SnmpModuleIdentity) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpNotificationGroup) 
		{
			SnmpNotificationGroup oidType = (SnmpNotificationGroup) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpNotificationType) 
		{
			SnmpNotificationType oidType = (SnmpNotificationType) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpObjectGroup) 
		{
			SnmpObjectGroup oidType = (SnmpObjectGroup) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpObjectIdentity) 
		{
			SnmpObjectIdentity oidType = (SnmpObjectIdentity) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpObjectType) 
		{
			SnmpObjectType oidType = (SnmpObjectType) type;
			entry.setType(oidType.getName());
			SnmpAccess access = oidType.getAccess();
			entry.setAccess(access.toString());
			
			fillMibType(oidType.getSyntax(),entry);
			
		}else if (type instanceof SnmpTextualConvention) 
		{
			SnmpTextualConvention oidType = (SnmpTextualConvention) type;
			entry.setType(oidType.getName());
		}else if (type instanceof SnmpTrapType) 
		{
			SnmpTrapType oidType = (SnmpTrapType) type;
			entry.setType(oidType.getName());
		}else if (type instanceof StringType) 
		{
			StringType oidType = (StringType) type;
			entry.setType(oidType.getName());
			entry.setValue("Agent Simulator");
			
		}else if (type instanceof TypeReference) 
		{
			TypeReference oidType = (TypeReference) type;
			entry.setType(oidType.getName());
		}
	}
	
	public static void addMibEntriesToModel(List<MibEntry> mibEntries,String modelName,String dirLoc) throws MarshalException, ValidationException, IOException{

		String modelXMLLocation =  dirLoc +File.separatorChar+ modelName + ".xml";
		if(!new File(modelXMLLocation).exists())
		{
			FileWriter modelXMLWriter = new FileWriter(new File(modelXMLLocation));
			Marshaller.marshal(mibEntries, modelXMLWriter);

		}else
		{
			FileReader modelFileReader = new FileReader(modelXMLLocation);
			ArrayList<Object> mibEntriesList= (ArrayList<Object>) Unmarshaller.unmarshal(ArrayList.class, modelFileReader);

			mibEntriesList.addAll(mibEntries);

			FileWriter writer = new FileWriter(new File(modelXMLLocation));
			Marshaller.marshal(mibEntriesList, writer);
		}
	}

}
