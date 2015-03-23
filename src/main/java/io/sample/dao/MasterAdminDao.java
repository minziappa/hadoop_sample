package io.sample.dao;

import java.sql.SQLException;
import java.util.Map;

public interface MasterAdminDao {

	public int insertGame(Map<String, Object> map) throws SQLException;
	public int updateGame(Map<String, Object> map) throws SQLException;
	public int insertAdmin(Map<String, Object> map) throws SQLException;
	public int updateAdmin(Map<String, Object> map) throws SQLException;
	public int insertApp(Map<String, Object> map) throws SQLException;
	public int insertAppAdmin(Map<String, Object> map) throws SQLException;
	public int updateAppAdmin(Map<String, Object> map) throws SQLException;
	public int updateSequece(Map<String, Object> map) throws SQLException;

}