package io.sample.bean;

import io.sample.bean.model.HadoopGameModel;
import io.sample.bean.model.MasterSampleModel;

import java.io.Serializable;
import java.util.List;

public class CommonBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<MasterSampleModel> masterSampleList;

	public List<MasterSampleModel> getMasterSampleList() {
		return masterSampleList;
	}

	public void setMasterSampleList(List<MasterSampleModel> masterSampleList) {
		this.masterSampleList = masterSampleList;
	}

	private List<HadoopGameModel> hadoopGameList;

	public List<HadoopGameModel> getHadoopGameList() {
		return hadoopGameList;
	}

	public void setHadoopGameList(List<HadoopGameModel> hadoopGameList) {
		this.hadoopGameList = hadoopGameList;
	}

}