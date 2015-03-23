package io.sample.dao;

import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HadoopAppAdminModel;
import io.sample.bean.model.HadoopAppModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SlaveAdminDao  {

	public List<HadoopAdminModel> selectAdminList(Map<String, Object> map) throws SQLException;
	public HadoopAppModel selectApp(Map<String, Object> map) throws SQLException;
	public List<HadoopAppAdminModel> selectAppAdminList(Map<String, Object> map) throws SQLException;

}