package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditGamePara {

    @NotNull(message = "gameId")
    @Size(min = 1, max = 45 ,message = "gameId")
	private String gameId;

	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

}