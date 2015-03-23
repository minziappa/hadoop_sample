package io.sample.dao;

import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HadoopGameModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SlaveDao {

	public HadoopAdminModel selectAdmin(Map<String, Object> map) throws SQLException;
	public HadoopGameModel selectGame(Map<String, Object> map) throws SQLException;
	public List<HadoopGameModel> selectGameList(Map<String, Object> map) throws SQLException;
	public List<HadoopAdminModel> selectAdminList(Map<String, Object> map) throws SQLException;
	public long selectSequence(Map<String, Object> map) throws SQLException;

}