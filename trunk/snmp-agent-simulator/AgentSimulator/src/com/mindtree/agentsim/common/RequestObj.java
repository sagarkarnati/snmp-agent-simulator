package com.mindtree.agentsim.common;

import org.apache.mina.core.session.IoSession;

public class RequestObj {
	
	private IoSession session;
	private Object snmpObj;
	
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	public Object getSnmpObj() {
		return snmpObj;
	}
	public void setSnmpObj(Object snmpObj) {
		this.snmpObj = snmpObj;
	}
}
