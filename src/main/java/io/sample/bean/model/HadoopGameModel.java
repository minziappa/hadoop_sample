package io.sample.bean.model;

import java.io.Serializable;
import java.util.Date;

public class HadoopGameModel implements Serializable {

	private static final long serialVersionUID = -6384892220625835403L;

	private String gameId;
	private String gameDomain;
	private String gameTitle;
	private String gameExplain;
	private byte[] gameFile;
	private String gameStatusFlag;
	private Date insertTime;
	private Date updateTime;

	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameDomain() {
		return gameDomain;
	}
	public void setGameDomain(String gameDomain) {
		this.gameDomain = gameDomain;
	}
	public String getGameTitle() {
		return gameTitle;
	}
	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	public String getGameExplain() {
		return gameExplain;
	}
	public void setGameExplain(String gameExplain) {
		this.gameExplain = gameExplain;
	}
	public byte[] getGameFile() {
		return gameFile;
	}
	public void setGameFile(byte[] gameFile) {
		this.gameFile = gameFile;
	}
	public String getGameStatusFlag() {
		return gameStatusFlag;
	}
	public void setGameStatusFlag(String gameStatusFlag) {
		this.gameStatusFlag = gameStatusFlag;
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