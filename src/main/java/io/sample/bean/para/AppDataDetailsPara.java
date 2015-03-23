package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AppDataDetailsPara {

    @NotNull(message = "id")
    @Size(min = 1, max = 30 ,message = "id")
	private String id;

    @NotNull(message = "family")
    @Size(min = 1, max = 30 ,message = "family")
	private String family;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

}