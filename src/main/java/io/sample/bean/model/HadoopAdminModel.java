package io.sample.bean.model;

import java.io.Serializable;
import java.util.Date;

public class HadoopAdminModel implements Serializable {

	private static final long serialVersionUID = -6384892220625835403L;

	private String adminId;
	private String adminPwd;
	private String gameId;
	private String adminName;
	private String adminMail;
	private String adminStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminPwd() {
		return adminPwd;
	}
	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminMail() {
		return adminMail;
	}
	public void setAdminMail(String adminMail) {
		this.adminMail = adminMail;
	}
	public String getAdminStatusFlag() {
		return adminStatusFlag;
	}
	public void setAdminStatusFlag(String adminStatusFlag) {
		this.adminStatusFlag = adminStatusFlag;
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