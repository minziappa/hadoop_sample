package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class RegisterGamePara {

    @NotNull(message = "gameId")
    @Size(min = 1, max = 45 ,message = "gameId")
	private String gameId;

    @Size(min = 1, max = 45 ,message = "gameDomain")
	private String gameDomain;

    @NotNull(message = "gameTitle")
    @Size(min = 1, max = 45 ,message = "gameTitle")
	private String gameTitle;

	@Size(min = 1, max = 200 ,message = "gameExplain")
	private String gameExplain;

	@NotNull(message = "upfile must not be blank.")
	private MultipartFile upfile;

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
	public MultipartFile getUpfile() {
		return upfile;
	}
	public void setUpfile(MultipartFile upfile) {
		this.upfile = upfile;
	}

}