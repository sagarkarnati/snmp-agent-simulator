package com.mindtree.agentsim.mina.server;

import java.net.InetSocketAddress;
import java.util.Vector;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.mindtree.agentsim.common.AgentSimConstants;
import com.mindtree.agentsim.common.RequestObj;
import com.mindtree.agentsim.common.SNMPTo;
import com.mindtree.agentsim.main.AgentSimMain;
import com.mindtree.agentsim.repository.RepositoryDao;
import com.mindtree.agentsim.repository.RepositoryDaoIntf;
import com.mindtree.agentsim.util.OIDExtracter;
import com.mindtree.tools.VariableGenIntf;

public class RequestProcessWorker implements Runnable{

	private static Logger logger = Logger.getLogger(RequestProcessWorker.class);

	private Object obj;
	private VariableGenIntf varGen;
	private RepositoryDaoIntf repository;
	
	public RequestProcessWorker(Object obj) 
	{
		logger.debug("RequestProcessWorker is created");

		this.obj = obj;
		this.varGen = (VariableGenIntf)AgentSimMain.getAppContext().getBean(AgentSimConstants.VARIABLE_GENERATOR);
		this.repository = (RepositoryDao)AgentSimMain.getAppContext().getBean(AgentSimConstants.MIB_REPOSITORY);
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try
		{
			logger.debug("RequestProcessWorker is started");
			if(null != this.obj)
			{
				RequestObj obj = (RequestObj)this.obj;
				SNMPTo snmpTo = (SNMPTo)obj.getSnmpObj();
				PDU pdu = snmpTo.getPdu();
				InetSocketAddress remoteAdd = (InetSocketAddress) ((IoSession)obj.getSession()).getLocalAddress();
				String remoteIp = remoteAdd.getAddress().getHostAddress();
				logger.debug("Binded Session IP ::"+remoteIp);
				
				if((PDU.GET == pdu.getType()) || (PDU.GETBULK == pdu.getType()))
				{
					Vector<VariableBinding> varBindVector = (Vector<VariableBinding>)pdu.getVariableBindings();
					for(VariableBinding varBind : varBindVector)
					{
						varBind.setVariable(varGen.getGeneVariable(varBind.getOid().toString()));
					}
				}else if(pdu.getType() == PDU.GETNEXT)
				{
					logger.debug("GetNext Message Received");
					Vector<VariableBinding> varBindVector = (Vector<VariableBinding>)pdu.getVariableBindings();
					for(VariableBinding varBind : varBindVector)
					{
						String oldOID = varBind.getOid().toString();
						logger.debug("Fectching next OID for ::"+oldOID);
						String nextOID = repository.getNextMibEntry(remoteIp,oldOID).getOid();
						logger.debug(nextOID+" is the next OID for OID "+oldOID);
						if(null != nextOID)
						{
							Variable genVar = varGen.getGeneVariable(nextOID);
							varBind.setVariable(genVar);
							varBind.setOid(new OID(nextOID));
							
							logger.debug("Value for the OID ::"+nextOID+" is ::"+genVar);
						}
					}
				}
				obj.getSession().write(pdu);
				logger.debug("PDU Sent Properly");
			}
		}catch(Exception e)
		{
			logger.error(e);
		}
	}
}