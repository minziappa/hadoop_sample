package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class RegisterAdminPara {

    @NotNull(message = "adminId")
    @Size(min = 1, max = 45 ,message = "adminId")
	private String adminId;

    @Size(min = 1, max = 45 ,message = "adminPwd")
	private String adminPwd;

    @NotNull(message = "gameId")
    @Size(min = 1, max = 45 ,message = "gameId")
	private String gameId;

	@Size(min = 1, max = 100 ,message = "adminName")
	private String adminName;

	@NotNull(message = "adminMail must not be blank.")
	@Size(min = 1, max = 100 ,message = "adminMail")
	@Email
	private String adminMail;

	@NotNull(message = "adminStatusFlag must not be blank.")
	private String adminStatusFlag;

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

	
}