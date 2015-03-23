package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterAppAdminPara {

	@NotNull(message = "appId")
	private String [] appId;

    @NotNull(message = "appGenre")
    @Size(min = 1, max = 45 ,message = "appGenre")
	private String appAdminGenre;

	public String[] getAppId() {
		return appId;
	}

	public void setAppId(String[] appId) {
		this.appId = appId;
	}

	public String getAppAdminGenre() {
		return appAdminGenre;
	}

	public void setAppAdminGenre(String appAdminGenre) {
		this.appAdminGenre = appAdminGenre;
	}

}