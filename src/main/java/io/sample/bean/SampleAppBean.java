package io.sample.bean;

import java.io.Serializable;
import java.util.List;

public class SampleAppBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String img;
	private List<AppDataBean> appDataList;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public List<AppDataBean> getAppDataList() {
		return appDataList;
	}
	public void setAppDataList(List<AppDataBean> appDataList) {
		this.appDataList = appDataList;
	}

}