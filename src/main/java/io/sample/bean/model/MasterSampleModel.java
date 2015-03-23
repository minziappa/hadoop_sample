package io.sample.bean.model;

import java.io.Serializable;
import java.util.Date;

public class MasterSampleModel implements Serializable {

	private static final long serialVersionUID = -6384892220625835403L;

	private String masterId;
	private String masterDomain;
	private String masterTitle;
	private String masterExplain;
	private byte[] masterFile;
	private String masterStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getMasterDomain() {
		return masterDomain;
	}
	public void setMasterDomain(String masterDomain) {
		this.masterDomain = masterDomain;
	}
	public String getMasterTitle() {
		return masterTitle;
	}
	public void setMasterTitle(String masterTitle) {
		this.masterTitle = masterTitle;
	}
	public String getMasterExplain() {
		return masterExplain;
	}
	public void setMasterExplain(String masterExplain) {
		this.masterExplain = masterExplain;
	}
	public byte[] getMasterFile() {
		return masterFile;
	}
	public void setMasterFile(byte[] masterFile) {
		this.masterFile = masterFile;
	}
	public String getMasterStatusFlag() {
		return masterStatusFlag;
	}
	public void setMasterStatusFlag(String masterStatusFlag) {
		this.masterStatusFlag = masterStatusFlag;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
}