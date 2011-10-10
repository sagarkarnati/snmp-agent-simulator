package com.mindtree.agentsim.mina.snmp;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.asn1.BER;
import org.snmp4j.asn1.BERInputStream;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

import com.mindtree.agentsim.common.SNMPTo;

public class SNMP4JDecoderCodec extends ProtocolDecoderAdapter {

	private static Logger logger = Logger.getLogger(SNMP4JDecoderCodec.class);

	public void decode(IoSession ioSession, IoBuffer ioBuffer,ProtocolDecoderOutput protocolDecoderOutput) throws Exception 
	{
		ByteBuffer pduBuffer = ioBuffer.buf();

		BERInputStream berStream = new BERInputStream(pduBuffer);
		BER.MutableByte mutableByte = new BER.MutableByte();
		BER.decodeHeader(berStream, mutableByte);

		//decode version
		Integer32 version = new Integer32();
		version.decodeBER(berStream);
		logger.debug("PDU Version :::"+version);

		// decode community string
		OctetString securityName = new OctetString();
		securityName.decodeBER(berStream);

		logger.debug("Community :::"+securityName);
		SNMPTo trap;
		if(version.getValue() == SnmpConstants.version1)
		{
			logger.debug("Received Packet is of type V1");
			PDUv1 pdu = new PDUv1();

			// decode the remaining PDU
			pdu.decodeBER(berStream);

			trap = new SNMPTo(ioSession.getRemoteAddress().toString(),securityName.toString(),version.getValue(),mutableByte.getValue(),pdu);
			protocolDecoderOutput.write(trap);

		}else if(version.getValue() == SnmpConstants.version2c)
		{
			logger.debug("Received Packet is of type V2C");
			PDU pdu = new PDU();

			// decode the remaining PDU
			pdu.decodeBER(berStream);
			trap = new SNMPTo(ioSession.getRemoteAddress().toString(),securityName.toString(),version.getValue(),mutableByte.getValue(),pdu);
			protocolDecoderOutput.write(trap);


		}else if(version.getValue() == SnmpConstants.version3)
		{
			logger.debug("Received Packet is of type V3");
			ScopedPDU pdu = new ScopedPDU();

			// decode the remaining PDU
			pdu.decodeBER(berStream);
			trap = new SNMPTo(ioSession.getRemoteAddress().toString(),securityName.toString(),version.getValue(),mutableByte.getValue(),pdu);
			protocolDecoderOutput.write(trap);
		}
	}
}
