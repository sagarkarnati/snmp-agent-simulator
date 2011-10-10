package com.mindtree.tools;

import java.util.Date;
import java.util.Scanner;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class TrapSender_withV3 {
	public static final String community = "public";

	// Sending Trap for sysLocation of RFC1213
	// public static final String trapOid = "1.3.6.1.4.1.9.9.171.2.0.8";
	public static final String trapOid = "1.3.6.1.4.1.9.9.171.2";

	public static final String ipAddress = "172.25.200.154";

	public static final String deviceIp = "172.25.200.154";

	public static final int port = 165;

	public static void main(String[] args) {
		TrapSender_withV3 snmp4JTrap = new TrapSender_withV3();
		Scanner scanner = new Scanner(System.in);
		String trapVersion = "";
		String trapCnt = "";
		int trpIntCnt = 0;
		while (true) {
			System.out.print("Please enter the trap version.\n"
					+ "Enter 0 --- to send v1 Trap\n"
					+ "Enter 1 --- to send V2cTrap\n"
					+ "Enter exit -to exit the application.\nInput ::");
			trapVersion = scanner.nextLine();
			System.out.print("Please enter the trap count ::");
			trapCnt = scanner.nextLine();
			if (trapCnt == "" || trapCnt.trim().length() == 0)
				trpIntCnt = 1;
			else
				trpIntCnt = Integer.parseInt(trapCnt.trim());

			if (trapVersion.equalsIgnoreCase("exit")) {
				System.out.println("Exiting Application...");
				return;
			} else if (Integer.parseInt(trapVersion) == 0) {

				System.out.println("Sending v1Trap");
				snmp4JTrap.sendSnmpV1Trap(trpIntCnt);
				System.out.println("V1Trap sent");
			} else if (Integer.parseInt(trapVersion) == 1) {

				System.out.println("Sending v2Trap");
				snmp4JTrap.sendSnmpV2Trap(trpIntCnt);
				System.out.println("V2Trap sent");
			} else if (Integer.parseInt(trapVersion) == 2) {

				System.out.println("Sending v3Trap");
				snmp4JTrap.sendSnmpV3Trap(trpIntCnt);
				System.out.println("V3Trap sent");
			}
		}
	}

	/**
	 * This methods sends the V1 trap to the Localhost in port 163
	 */
	public void sendSnmpV1Trap(int cnt) {
		try {
			// Create Transport Mapping
			TransportMapping transport = new DefaultUdpTransportMapping();
			transport.listen();

			// Create Target
			CommunityTarget comtarget = new CommunityTarget();
			comtarget.setCommunity(new OctetString(community));
			comtarget.setVersion(SnmpConstants.version1);
			comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
			comtarget.setRetries(2);
			comtarget.setTimeout(5000);

			// Trap=cikeTunnelStop, cikePeerLocalAddr=01:04:64:ad,
			// cikePeerRemoteAddr=01:04:00:00, cikeTunActiveTime=180000,
			// 1.3.6.1.4.1.9.9.171.1.4.2.1.1.2.8460=2

			// Create PDU for V1
			PDUv1 pdu = new PDUv1();
			pdu.setType(PDU.V1TRAP);
			pdu.setEnterprise(new OID(trapOid));
			pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
			pdu.setSpecificTrap(7);
			pdu.setAgentAddress(new IpAddress(deviceIp));

			pdu.add(new VariableBinding(SnmpConstants.sysUpTime,
					new OctetString(new Date().toString())));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(
					trapOid)));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress,
					new IpAddress(deviceIp)));

			// Send the PDU
			Snmp snmp = new Snmp(transport);
			for (int i = 0; i < cnt; i++) {
				System.out.println("Sending V1 Trap to " + ipAddress
						+ " on Port " + port);
				snmp.send(pdu, comtarget);
			}
			snmp.close();
		} catch (Exception e) {
			System.err.println("Error in Sending V1 Trap to " + ipAddress
					+ " on Port " + port);
			System.err.println("Exception Message = " + e.getMessage());
		}
	}

	/**
	 * This methods sends the V2 trap to the Localhost in port 163
	 */
	public void sendSnmpV2Trap(int cnt) {
		try {
			// Create Transport Mapping
			TransportMapping transport = new DefaultUdpTransportMapping();
			transport.listen();

			// Create Target
			CommunityTarget comtarget = new CommunityTarget();
			comtarget.setCommunity(new OctetString(community));
			comtarget.setVersion(SnmpConstants.version2c);
			comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
			comtarget.setRetries(2);
			comtarget.setTimeout(5000);

			// Create PDU for V2
			PDU pdu = new PDU();

			// need to specify the system up time
			pdu.add(new VariableBinding(SnmpConstants.sysUpTime,
					new OctetString(new Date().toString())));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(
					trapOid)));
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapAddress,
					new IpAddress(deviceIp)));

			// variable binding for Enterprise Specific objects, Severity
			// (should be defined in MIB file)
			pdu.add(new VariableBinding(new OID(trapOid)));
			pdu.setType(PDU.NOTIFICATION);

			// Send the PDU
			Snmp snmp = new Snmp(transport);
			for (int i = 0; i < cnt; i++) {
				System.out.println("Sending V2 Trap to " + ipAddress
						+ " on Port " + port);
				snmp.send(pdu, comtarget);
			}
			snmp.close();
		} catch (Exception e) {
			System.err.println("Error in Sending V2 Trap to " + ipAddress
					+ " on Port " + port);
			System.err.println("Exception Message = " + e.getMessage());
		}
	}

	public void sendSnmpV3Trap(int cnt)
	{
		try
		{
			Address targetAddress = GenericAddress.parse("udp:172.25.200.154/165");
			TransportMapping transport = new DefaultUdpTransportMapping();
			transport.listen();
			
			UserTarget target = new UserTarget();
			target.setAddress(targetAddress);
			target.setRetries(1);
			target.setTimeout(5000);
			target.setVersion(SnmpConstants.version3);
			target.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
			target.setSecurityName(new OctetString("MD5DES"));
			
			// create the PDU
			PDU pdu = new ScopedPDU();
			pdu.add(new VariableBinding(new OID("1.3.6")));
			pdu.setType(PDU.NOTIFICATION);

			USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);

			Snmp snmp = new Snmp(transport);
			snmp.getUSM().addUser(new OctetString("MD5DES"),new UsmUser(new OctetString("MD5DES"),AuthMD5.ID,new OctetString("MD5DESUserAuthPassword"),
							PrivDES.ID,	new OctetString("MD5DESUserPrivPassword")));
			
			for (int i = 0; i < cnt; i++) 
			{
				System.out.println("Sending V3 Trap to " + ipAddress+ " on Port " + port);
				snmp.send(pdu, target);
			}
			snmp.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
/*	
	public static void main(String[] args) {
		
		new TrapSender_withV3().sendSnmpV3Trap(1);
	}*/
}