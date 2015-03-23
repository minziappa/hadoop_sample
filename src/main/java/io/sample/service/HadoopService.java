package io.sample.service;

import io.sample.bean.DirectoryBean;
import io.sample.bean.RootDirectoryBean;
import io.sample.bean.model.MasterSampleModel;

import java.util.List;

public interface HadoopService {

	public void init() throws Exception;

	public MasterSampleModel getMasterSample(String key) throws Exception;

	public String readLogsOne(String searchWord) throws Exception;
	public String readLogsTwoPlural(String path, String searchWord) throws Exception;
	public String readLogsThreePlural(String path, String searchWord, String searchFlag) throws Exception;

	public List<DirectoryBean> directoryList(String path) throws Exception;
	public List<RootDirectoryBean> rootDirectory(String path) throws Exception;

}