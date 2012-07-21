package com.mindtree.agentsim.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;

public class IPUtils {
	
	private static Logger logger = Logger.getLogger(IPUtils.class);
	public static List<String> IPRangeCalc( String startIP , String endIP ) {

		List<String> list = new ArrayList<String>();
		for( long i = ipToInt(startIP) ; i <= ipToInt(endIP) ; i++ ) {
			String val = intToIp(i);
			list.add(val);
		}
		return list;
	}

	private static String intToIp( long i ) {

		return ((i >> 24) & 0xFF) + "." +((i >> 16) & 0xFF) + "." +((i >> 8) & 0xFF) + "." +(i & 0xFF);
	}

	private static Long ipToInt( String addr ) {

		String[] addrArray = addr.split("\\.");
		long num = 0;
		for( int i = 0 ; i < addrArray.length ; i++ ) {

			int power = 3 - i;
			num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
		}
		return num;
	}

	public static List<String> getAllConfiguredIPAddress() throws Exception
	{
		List<String> ipList = new ArrayList<String>();
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
		{
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
			{
				ipList.add(enumIpAddr.nextElement().getHostAddress());
			}
		}
		logger.debug(ipList.toString());
		return ipList;
	}
}