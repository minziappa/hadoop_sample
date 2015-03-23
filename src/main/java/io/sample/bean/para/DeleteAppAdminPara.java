package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeleteAppAdminPara {

	@NotNull(message = "appId")
	private String [] appId;

    @NotNull(message = "appAdminGenre")
    @Size(min = 1, max = 45 ,message = "appAdminGenre")
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