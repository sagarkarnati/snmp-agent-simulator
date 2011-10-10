package com.mindtree.agentsim.mibble;


public class MibEntry {
	
	private Long id;
	private String entryName;
	private String oid;
	private String type;
	private String value;
	private String access;
	private String valueGenType;
	private String constraints;
	
	public MibEntry() {
		
	}
	
	public MibEntry(String entryName,String oid,String type,String value,String access,String valueGenType,String constraints) {
		
		this.entryName = entryName; 
		this.oid = oid;
		this.type = type;
		this.value = value;
		this.access = access;
		this.valueGenType = valueGenType;
		this.constraints = constraints;
	}
	
	public MibEntry(String entryName,String oid,String type,String constraints) {
		
		this.entryName = entryName; 
		this.oid = oid;
		this.type = type;
		this.value = "";
		this.valueGenType = "";
		this.access = "";
		this.constraints = constraints;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getValueGenType() {
		return valueGenType;
	}

	public void setValueGenType(String valueGenType) {
		this.valueGenType = valueGenType;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}
	
	@Override
	public String toString() {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(" Entry Name ::"+this.entryName);
		strBuilder.append(" OID ::"+this.oid);
		strBuilder.append(" Type ::"+this.type);
		strBuilder.append(" Value ::"+this.value);
		strBuilder.append(" Access ::"+this.access);
		strBuilder.append(" Value Generation Type ::"+this.valueGenType);
		strBuilder.append(" Constraints ::"+this.constraints);
		
		return strBuilder.toString();
	}
		
}
