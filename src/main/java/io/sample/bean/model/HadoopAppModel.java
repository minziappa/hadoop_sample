package io.sample.bean.model;

import java.io.Serializable;
import java.util.Date;

public class HadoopAppModel implements Serializable {

	private static final long serialVersionUID = 481325297246205545L;

	private String id;
	private String appId;
	private String appName;
	private String appImg;
	private String appGenre;
	private String appUpdate;
	private String appCountry;
	private String appRanking;
	private String appStatusFlag;
	private Date insertTime;
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppImg() {
		return appImg;
	}
	public void setAppImg(String appImg) {
		this.appImg = appImg;
	}
	public String getAppGenre() {
		return appGenre;
	}
	public void setAppGenre(String appGenre) {
		this.appGenre = appGenre;
	}
	public String getAppUpdate() {
		return appUpdate;
	}
	public void setAppUpdate(String appUpdate) {
		this.appUpdate = appUpdate;
	}
	public String getAppCountry() {
		return appCountry;
	}
	public void setAppCountry(String appCountry) {
		this.appCountry = appCountry;
	}
	public String getAppRanking() {
		return appRanking;
	}
	public void setAppRanking(String appRanking) {
		this.appRanking = appRanking;
	}
	public String getAppStatusFlag() {
		return appStatusFlag;
	}
	public void setAppStatusFlag(String appStatusFlag) {
		this.appStatusFlag = appStatusFlag;
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