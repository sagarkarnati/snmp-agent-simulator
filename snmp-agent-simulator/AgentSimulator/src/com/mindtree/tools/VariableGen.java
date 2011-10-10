package com.mindtree.tools;

import java.util.StringTokenizer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.Variable;

import com.mindtree.agentsim.common.AgentSimConstants;
import com.mindtree.agentsim.main.AgentSimMain;
import com.mindtree.agentsim.mibble.MibEntry;
import com.mindtree.agentsim.mina.server.RequestProcessWorker;
import com.mindtree.agentsim.parser.DeviceDataParserIntf;
import com.mindtree.agentsim.repository.RepositoryDaoIntf;
import com.mindtree.agentsim.util.OIDExtracter;

public class VariableGen implements VariableGenIntf
{
	private RepositoryDaoIntf mibRepo;
	private DeviceDataParserIntf deviceParser;
	private static Logger logger = Logger.getLogger(VariableGen.class);
	
	public VariableGen(RepositoryDaoIntf mibRepo,DeviceDataParserIntf deviceParser)
	{
		this.mibRepo = mibRepo;
		this.deviceParser = deviceParser;
		
		logger.debug("Initialized VaraiableGenerator");
	}

	public Variable getGeneVariable(String oid) throws Exception
	{
		//String modelName = getDeviceParser().getModelName(deviceIp);
		logger.debug("need to generate value for OID ::"+oid);
		MibEntry mibEntry = mibRepo.getMibEntry(oid);
		logger.debug("MIBEntry for OID ::"+oid+" is ::"+ToStringBuilder.reflectionToString(mibEntry,ToStringStyle.MULTI_LINE_STYLE));
		if(null != mibEntry)
		{
			if(AgentSimConstants.INTEGER.equals(mibEntry.getType()))
			{	
				Long [] constrArr = getConstraints(mibEntry.getConstraints());
				Long val = null;
				if((null != constrArr) && (constrArr.length > 0))
				{
					if(AgentSimConstants.INCREMENT.equals(mibEntry.getValueGenType()))
					{
						val = incrementVal(constrArr[0], constrArr[constrArr.length - 1], Integer.parseInt(mibEntry.getValue()));
					}else if(AgentSimConstants.DECREMENT.equals(mibEntry.getValueGenType()))
					{
						val = decrementVal(constrArr[0], constrArr[constrArr.length - 1], Integer.parseInt(mibEntry.getValue()));
					}else if(AgentSimConstants.RANDOM.equals(mibEntry.getValueGenType()))
					{
						val = getRandomInt(constrArr[0], constrArr[constrArr.length - 1]);
					}
				}else
				{
					String value = mibEntry.getValue();
					if((null != value) && (value.trim().length() > 0))
						val = Long.parseLong(mibEntry.getValue());
				}
				return new Integer32(Math.abs(val.intValue()));
			}else if(AgentSimConstants.STRING.equals(mibEntry.getType()))
			{
				return new OctetString(mibEntry.getValue());
			}else if(AgentSimConstants.OID.equals(mibEntry.getType()))
			{
				return new OID(mibEntry.getValue());
			}else if(AgentSimConstants.TIME_TICKS.equals(mibEntry.getType()))
			{
				return new TimeTicks(System.currentTimeMillis());
			}else if(AgentSimConstants.SEQUENCE.equals(mibEntry.getType()))
			{	
				Long [] constrArr = getConstraints(mibEntry.getConstraints());
				long val = incrementVal(constrArr[0], constrArr[constrArr.length], Integer.parseInt(mibEntry.getValue()));

				return new Integer32((int)val);
			}
		}
		return new OctetString("Error while construction");
	}

	private Long[] getConstraints(String rawStr)
	{
		if(rawStr.contains("."))
			return tokenizeStr(rawStr,".");
		else if(rawStr.contains("|"))
			return tokenizeStr(rawStr,"|");

		return null;
	}

	private Long[] tokenizeStr(String rawStr,String seperator)
	{
		StringTokenizer tokenizer = new StringTokenizer(rawStr,seperator);
		Long[] valArr = new Long[tokenizer.countTokens()];
		int counter = 0;
		while (tokenizer.hasMoreElements())
		{
			String token = tokenizer.nextToken();
			if(token.trim().length() > 0)
				valArr[counter++] = Long.parseLong(token.trim()); 
		}

		return valArr;
	}

	private long getRandomInt(long startVal,long endVal) throws Exception
	{
		long randomNum = (long) (Math.random() * (endVal - startVal) ) + startVal;

		return randomNum;
	}

	private long incrementVal(long minVal,long MaxVal,long currVal) throws Exception
	{
		if(currVal == MaxVal)
		{
			return MaxVal;
		}else
		{
			return (++currVal);
		}
	}

	private long decrementVal(long minVal,long MaxVal,long currVal) throws Exception
	{
		if(currVal == minVal)
		{
			return currVal;
		}else
		{
			return (--currVal);
		}
	}

	public RepositoryDaoIntf getMibRepo() {
		return mibRepo;
	}

	public void setMibRepo(RepositoryDaoIntf mibRepo) {
		this.mibRepo = mibRepo;
	}

	public DeviceDataParserIntf getDeviceParser() {
		return deviceParser;
	}

	public void setDeviceParser(DeviceDataParserIntf deviceParser) {
		this.deviceParser = deviceParser;
	}

}
