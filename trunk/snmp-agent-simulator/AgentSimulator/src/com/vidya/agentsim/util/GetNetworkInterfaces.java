package com.mindtree.agentsim.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

/**
 * Lists all network interfaces on a system
 * 
 * @author Bela Ban Dec 18
 * @author 2003
 * @version $Id: GetNetworkInterfaces.java,v 1.1 2005/06/23 13:31:09 belaban Exp
 *          $
 */
public class GetNetworkInterfaces {
	public static void main(String[] args) throws SocketException {
		// Enumeration en = NetworkInterface.getNetworkInterfaces();
		// while (en.hasMoreElements())
		// {
		// NetworkInterface i = (NetworkInterface) en.nextElement();
		// System.out.println(i.getName() + ':');
		// System.out.println("  \t" + i.getDisplayName());
		// for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();)
		// {
		// InetAddress addr = (InetAddress) en2.nextElement();
		// System.out.println("  \t" + addr + " (" + addr.getHostName()+ ')');
		// }
		// System.out.println("---------------------");
		// }
		try 
		{
			String line;
			Process process;
			int i = 2;
			int j = 10;
			for(int cnt = 0 ; cnt < 20 ; cnt++)
			{
				String cmd = "/sbin/ifconfig eth0:"+i+" 10.4.14."+j+" netmask 255.255.255.224 down";
				System.out.println(cmd);
				StringBuffer resultBuffer = new StringBuffer();
				Runtime runtime = Runtime.getRuntime();
				process = runtime.exec(cmd);
	
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
	
				while ((line = br.readLine()) != null) 
				{
					resultBuffer.append(line);
				}
				i++;
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
