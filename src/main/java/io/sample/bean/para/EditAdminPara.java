package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditAdminPara {

    @NotNull(message = "adminId")
    @Size(min = 1, max = 45 ,message = "adminId")
	private String adminId;

    @NotNull(message = "gameId")
    @Size(min = 1, max = 45 ,message = "gameId")
	private String gameId;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

}