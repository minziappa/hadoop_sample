package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchFlumePara {

    @NotNull(message = "searchWord")
    @Size(min = 1, max = 100 ,message = "searchWord")
	private String path;

    @NotNull(message = "searchWord")
    @Size(min = 1, max = 100 ,message = "searchWord")
	private String searchWord;

	private String searchFlag;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

}