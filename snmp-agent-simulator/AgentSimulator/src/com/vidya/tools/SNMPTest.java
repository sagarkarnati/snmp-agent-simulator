package com.mindtree.tools;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * @author Vidya Sagar
 * 
 * Date Jan 5, 2014 12:24:54 PM
 * 
 */
public class SNMPTest
{

	public static void main(String[] args)
	{

		try
		{
			Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161");
			TransportMapping transport = new DefaultUdpTransportMapping();
			Snmp snmp = new Snmp(transport);

			transport.listen();
			// setting up target
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString("public"));
			target.setAddress(targetAddress);
			target.setRetries(0);
			target.setTimeout(1500);
			target.setVersion(SnmpConstants.version1);
			// creating PDU
			PDUv1 pdu = new PDUv1();
			pdu.add(new VariableBinding(new OID(new int[]
			{ 1, 3, 6, 1, 2, 1, 1, 1 })));
			pdu.add(new VariableBinding(new OID(new int[]
			{ 1, 3, 6, 1, 2, 1, 1, 2 })));
			pdu.setType(PDU.GET);
			ResponseEvent event = snmp.send(pdu, target);
			System.out.println("Request ::" + ToStringBuilder.reflectionToString(event.getRequest(), ToStringStyle.MULTI_LINE_STYLE));
			System.out.println("Response ::" + ToStringBuilder.reflectionToString(event.getResponse(), ToStringStyle.MULTI_LINE_STYLE));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
