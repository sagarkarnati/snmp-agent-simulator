package com.mindtree.agentsim.mina.snmp;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.snmp4j.PDU;
import org.snmp4j.asn1.BER;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

public class SNMP4JEncoderCodec extends ProtocolEncoderAdapter{

	private static Logger logger = Logger.getLogger(SNMP4JEncoderCodec.class);

	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)	throws Exception 
	{
		logger.debug("Into SNMP Encoder");
		PDU pdu = (PDU)arg1;
		pdu.setType(PDU.RESPONSE);

		ByteArrayOutputStream outPutStream = new ByteArrayOutputStream(); 
		BER.encodeHeader(outPutStream,48,outPutStream.toByteArray().length);

		//encode version
		Integer32 version = new Integer32(SnmpConstants.version1);
		version.encodeBER(outPutStream);

		// encode community string
		OctetString securityName = new OctetString("public".getBytes());
		securityName.encodeBER(outPutStream);
		
		pdu.encodeBER(outPutStream);
		
		IoBuffer ioBuf = IoBuffer.wrap(outPutStream.toByteArray());
		arg2.write(ioBuf);
		arg2.flush();
	}
}
