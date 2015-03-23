package io.sample.bean.model;

import java.io.Serializable;
import java.util.Date;

public class HadoopAppAdminModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String adminId;
	private String appId;
	private String appAdminName;
	private String appAdminImg;
	private String appAdminGenre;
	private String appAdminCountry;
	private String appAdminStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppAdminName() {
		return appAdminName;
	}
	public void setAppAdminName(String appAdminName) {
		this.appAdminName = appAdminName;
	}
	public String getAppAdminImg() {
		return appAdminImg;
	}
	public void setAppAdminImg(String appAdminImg) {
		this.appAdminImg = appAdminImg;
	}
	public String getAppAdminGenre() {
		return appAdminGenre;
	}
	public void setAppAdminGenre(String appAdminGenre) {
		this.appAdminGenre = appAdminGenre;
	}
	public String getAppAdminCountry() {
		return appAdminCountry;
	}
	public void setAppAdminCountry(String appAdminCountry) {
		this.appAdminCountry = appAdminCountry;
	}
	public String getAppAdminStatusFlag() {
		return appAdminStatusFlag;
	}
	public void setAppAdminStatusFlag(String appAdminStatusFlag) {
		this.appAdminStatusFlag = appAdminStatusFlag;
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