package io.sample.service;

import io.sample.bean.AppDataBean;
import io.sample.bean.HbaseResultBean;
import io.sample.bean.SampleAppBean;
import io.sample.bean.game.EntryBean;
import io.sample.bean.model.HadoopAppAdminModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HbaseService {

	public boolean insertAppAdmin(String adminId, String[] appId, String appAdminGenre);

	public List<HadoopAppAdminModel> selectAppAdminList(String adminId, String appAdminGenre);
	public boolean deleteAppAdmin(String adminId, String[] appId, String appAdminGenre);

	public List<HbaseResultBean> selectRow(String tableName, String rowkey) throws Exception;
	public List<HbaseResultBean> selectScan(String tableName) throws Exception;
	public List<HbaseResultBean> selectScanColumnFamily(String tableName, String rowKey, String family) throws Exception;

	public boolean deleteRow(String tableName, String rowkey) throws Exception;
	public boolean deleteTable(String tableName) throws Exception;

	public boolean createTableColumn(String tableName, String[] cfs) throws Exception;
	public void addKeyValue(String tableName, String rowKey, String[] cfs, String[] qfs, String[] vals) throws Exception;

	public boolean saveRssJsonItunes(String tableName, String columnFamily, String country, String url) throws Exception;
	public Map<String, Object> getAppDataDetails(String tableName, String columnFamily, String url) throws Exception;
	public List<List<EntryBean>> getItuensRanking(String tableName, String family, String qualifier, int nowPage) throws Exception;
	public List<AppDataBean> getAppRanking(String tableName, String rowKey, String family) throws Exception;
	public List<SampleAppBean> getAmebaApp(String adminId, String family, Date startDate, Date endDate) throws Exception;

}