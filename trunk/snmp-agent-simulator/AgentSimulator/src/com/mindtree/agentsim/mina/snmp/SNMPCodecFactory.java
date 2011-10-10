package com.mindtree.agentsim.mina.snmp;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SNMPCodecFactory implements ProtocolCodecFactory {
	
	private ProtocolDecoder decoder;
	private ProtocolEncoder encoder;
	
	public SNMPCodecFactory(ProtocolEncoder encoder,ProtocolDecoder decoder) {
		
		this.decoder = decoder;
		this.encoder = encoder;
	}
	
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		
		return encoder;
	}
	
	
}
