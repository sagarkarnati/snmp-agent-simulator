package com.mindtree.tools;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.snmp4j.asn1.BER;
import org.snmp4j.asn1.BERInputStream;
import org.snmp4j.asn1.BERSerializable;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

import com.mindtree.agentsim.mina.server.ServerHandler;
 /**
  * The <code>HeaderData</code> represents the message header information
  * of SNMPv3 message.
  * @author Frank Fock
  * @version 1.0
  */
public class HeaderData implements  BERSerializable {

	 private static Logger logger = Logger.getLogger(ServerHandler.class);
	 public static final byte FLAG_AUTH = 0x01;
	 public static final byte FLAG_PRIV = 0x02;

	 Integer32 msgID = new Integer32(0);
	 public Integer32 msgMaxSize = new Integer32(Integer.MAX_VALUE);
	 OctetString msgFlags = new OctetString(new byte[1]);
	 Integer32 securityModel = new Integer32(
			 SecurityModel.SECURITY_MODEL_ANY);

	 public void setMsgID(int msgID) {
		 this .msgID.setValue(msgID);
	 }

	 public int getMsgID() {
		 return msgID.getValue();
	 }

	 public void setMsgMaxSize(int msgMaxSize) {
		 this .msgMaxSize.setValue(msgMaxSize);
	 }

	 public int getMsgMaxSize() {
		 return msgMaxSize.getValue();
	 }

	 public void setMsgFlags(int flags) {
		 this .msgFlags.getValue()[0] = (byte) flags;
	 }

	 public int getMsgFlags() {
		 return msgFlags.getValue()[0] & 0xFF;
	 }

	 public void setSecurityModel(int model) {
		 securityModel.setValue(model);
	 }

	 public int getSecurityModel() {
		 return securityModel.getValue();
	 }

	 public int getBERPayloadLength() {
		 int length = msgID.getBERLength();
		 length += msgMaxSize.getBERLength();
		 length += msgFlags.getBERLength();
		 length += securityModel.getBERLength();
		 return length;
	 }

	 public int getBERLength() {
		 int length = getBERPayloadLength();
		 length += BER.getBERLengthOfLength(length) + 1;
		 return length;
	 }

	 public void decodeBER(BERInputStream message)
	 throws IOException {
		 BER.MutableByte type = new BER.MutableByte();
		 int length = BER.decodeHeader(message, type);
		 if (type.getValue() != BER.SEQUENCE) {
			 throw new IOException(
					 "Unexpected sequence header type: "
					 + type.getValue());
		 }
		 msgID.decodeBER(message);
		 msgMaxSize.decodeBER(message);
		 if (msgMaxSize.getValue() < 484) {
			 throw new IOException("Invalid msgMaxSize: "
					 + msgMaxSize);
		 }
		 msgFlags.decodeBER(message);
		 if (msgFlags.length() != 1) {
			 throw new IOException("Message flags length != 1: "
					 + msgFlags.length());
		 }
		 securityModel.decodeBER(message);
		 if (logger.isDebugEnabled()) {
			 logger.debug("SNMPv3 header decoded: msgId=" + msgID
					 + ", msgMaxSize=" + msgMaxSize + ", msgFlags="
					 + msgFlags.toHexString() + ", secModel="
					 + securityModel);
		 }
		 BER.checkSequenceLength(length, this );
	 }

	 public void encodeBER(OutputStream outputStream)
	 throws IOException {
		 BER.encodeHeader(outputStream, BER.SEQUENCE,
				 getBERPayloadLength());
		 msgID.encodeBER(outputStream);
		 msgMaxSize.encodeBER(outputStream);
		 msgFlags.encodeBER(outputStream);
		 securityModel.encodeBER(outputStream);
	 }
 }
