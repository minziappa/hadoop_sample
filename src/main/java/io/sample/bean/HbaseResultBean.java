package io.sample.bean;

import java.io.Serializable;

public class HbaseResultBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String row;
	private String family;
	private String qualifier;
	private String value;
	private Long timestamp;

	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}