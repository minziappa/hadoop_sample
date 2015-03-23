package io.sample.service.impl;

import io.sample.bean.AppDataBean;
import io.sample.bean.HbaseResultBean;
import io.sample.bean.SampleAppBean;
import io.sample.bean.game.Category;
import io.sample.bean.game.EntryBean;
import io.sample.bean.game.Id;
import io.sample.bean.game.ImArtist;
import io.sample.bean.game.ImContentType;
import io.sample.bean.game.ImImage;
import io.sample.bean.game.ImName;
import io.sample.bean.game.ImPrice;
import io.sample.bean.game.ImReleaseDate;
import io.sample.bean.game.Link;
import io.sample.bean.game.Rights;
import io.sample.bean.game.Summary;
import io.sample.bean.game.Title;
import io.sample.bean.model.HadoopAppAdminModel;
import io.sample.bean.model.HadoopAppModel;
import io.sample.dao.HBaseDAO;
import io.sample.dao.MasterAdminDao;
import io.sample.dao.SlaveAdminDao;
import io.sample.http.ApiHttpClient;
import io.sample.service.HbaseService;
import io.sample.utility.DateUtility;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HbaseServiceImpl implements HbaseService {

	final Logger logger = LoggerFactory.getLogger(HbaseServiceImpl.class);
	final private String dataKey = "new";

	@Autowired
	private HBaseDAO hBaseDAO;
	@Autowired
	private SqlSession masterAdminDao;
	@Autowired
	private SqlSession slaveAdminDao;
	@Autowired
    private Configuration configuration;

	private List<HbaseResultBean> getHbaseResult(ResultScanner rs) {

		List<HbaseResultBean> hbaseResultList = null;
		try {

			if(rs == null) {
				return null;
			}

			hbaseResultList = new ArrayList<HbaseResultBean>();
	
	        for (Result r : rs) {
	        	for (KeyValue kv : r.raw()) {
	        		String value = new String(kv.getValue());
	        		if(new String(kv.getValue()).length() > 20) {
	        			value =  new String(kv.getValue()).substring(0, 20);
	        		}

	        		HbaseResultBean hbaseResult = new HbaseResultBean();
	        		hbaseResult.setRow(new String(kv.getRow()));
	        		hbaseResult.setFamily(new String(kv.getFamily()));
	        		hbaseResult.setQualifier(new String(kv.getQualifier()));
	        		hbaseResult.setValue(value);
	        		hbaseResult.setTimestamp(new Long(kv.getTimestamp()));
	
	        		hbaseResultList.add(hbaseResult);
	            }
	        }
	
		} catch (Exception e) {
			logger.error("Exception >>> ", e);
		} finally {
			if(rs !=null ) {
				rs.close();			
			}
		}
		
		return hbaseResultList;
	
	}

	private List<HbaseResultBean> updateData(Result r) {

		List<HbaseResultBean> hbaseResultList = new ArrayList<HbaseResultBean>();
	
		for (KeyValue kv : r.raw()) {
	
			String value = null;
	
		    Object obj=JSONValue.parse(new String(kv.getValue()));
		    JSONObject obj2=(JSONObject)obj;
		    JSONObject obj3=(JSONObject)obj2.get("feed");
		    JSONObject obj4=(JSONObject)obj3.get("updated");
	
		    value = obj4.get("label").toString();
	
			HbaseResultBean hbaseResult = new HbaseResultBean();
			hbaseResult.setRow(new String(kv.getRow()));
			hbaseResult.setFamily(new String(kv.getFamily()));
			hbaseResult.setQualifier(new String(kv.getQualifier()));
			hbaseResult.setValue(value);
			hbaseResult.setTimestamp(new Long(kv.getTimestamp()));

			hbaseResultList.add(hbaseResult);
	    }
	
	    return hbaseResultList;
	}

	private List<EntryBean> parsingForJson(String json, int nowPage) throws Exception {

		List<EntryBean> entryList = new ArrayList<EntryBean>();

	    Object obj=JSONValue.parse(json);

	    JSONObject obj2=(JSONObject)obj;
	    JSONObject obj3=(JSONObject)obj2.get("feed");
	    JSONArray obj4=(JSONArray)obj3.get("entry");

		// Check a nowPage
		if(nowPage <= 0){
			nowPage = 1;
		}
	    int intStart = (nowPage - 1) * 10;
	    int intEnd = intStart + 10;

	    logger.info("nowPage >>> " + nowPage);
	    int i = 0;
	    @SuppressWarnings("unchecked")
		Iterator<JSONObject> itr = obj4.iterator();
	    while(itr.hasNext()) {
	    	i++;

	    	EntryBean entry = new EntryBean();
	    	JSONObject ele = itr.next();

	    	if(i <= intStart) {
	    		logger.info("[continue] number >>> " + i);
	    		continue;
	    	}

	    	JSONObject object1 = (JSONObject)ele.get("im:name");
	    	object1.get("label").toString();
	    	ImName imName = new ImName();
	    	imName.setLabel(object1.get("label").toString());
	    	entry.setImName(imName);
	
	    	JSONArray objectArray1 = (JSONArray)ele.get("im:image");
	    	@SuppressWarnings("unchecked")
			Iterator<JSONObject> itr2 = objectArray1.iterator();
	    	List<ImImage> imImageList = new ArrayList<ImImage>();
		    while(itr2.hasNext()) {
		    	JSONObject ele2 = itr2.next();
		    	// ele2.get("label").toString();
		    	JSONObject object2 = (JSONObject)ele2.get("attributes");
		    	// object2.get("height").toString();
		    	ImImage imImage = new ImImage();
		    	imImage.setLabel(ele2.get("label").toString());
		    	imImage.setHeight(object2.get("height").toString());
		    	imImageList.add(imImage);
		    }
		    entry.setImImageList(imImageList);

		    object1 = (JSONObject)ele.get("summary");
		    // object1.get("label").toString();
		    Summary summary = new Summary();
		    summary.setLabel(object1.get("label").toString());
		    entry.setSummary(summary);
	
	    	object1 = (JSONObject)ele.get("im:price");
	    	// object1.get("label").toString();
	    	JSONObject object2 = (JSONObject)object1.get("attributes");
	    	// object2.get("amount").toString();
	    	// object2.get("currency").toString();
	    	ImPrice imPrice = new ImPrice();
	    	imPrice.setLabel(object1.get("label").toString());
	    	imPrice.setAmount(object2.get("amount").toString());
	    	imPrice.setCurrency(object2.get("currency").toString());
	    	entry.setImPrice(imPrice);
	
	    	object1 = (JSONObject)ele.get("im:contentType");
	    	object2 = (JSONObject)object1.get("attributes");
	    	// object2.get("term").toString();
	    	// object2.get("label").toString();
	    	ImContentType imContentType = new ImContentType();
	    	imContentType.setTerm(object2.get("term").toString());
	    	imContentType.setLabel(object2.get("label").toString());
	    	entry.setImContentType(imContentType);
	
	    	object1 = (JSONObject)ele.get("rights");
	    	// object1.get("label").toString();
	    	Rights rights = new Rights();
	    	rights.setLabel(object1.get("label").toString());
	    	entry.setRights(rights);
	    	
	    	object1 = (JSONObject)ele.get("title");
	    	// object1.get("label").toString();
	    	Title title = new Title();
	    	title.setLabel(object1.get("label").toString());
	    	entry.setTitle(title);
	
	    	if(ele.get("link") instanceof JSONObject) {
		    	object1 = (JSONObject)ele.get("link");
		    	object2 = (JSONObject)object1.get("attributes");
		    	// object2.get("rel").toString();
		    	// object2.get("type").toString();
		    	// object2.get("href").toString();
		    	Link link = new Link();
		    	link.setRel(object2.get("rel").toString());
		    	link.setType(object2.get("type").toString());
		    	link.setHref(object2.get("href").toString());
		    	entry.setLink(link);
	    	} else {
	    		// logger.warn("this is array");
	    	}
	
	    	object1 = (JSONObject)ele.get("id");
	    	// object1.get("label").toString();
	    	object2 = (JSONObject)object1.get("attributes");
	    	// object2.get("im:id").toString();
	    	// object2.get("im:bundleId").toString();
	    	Id id = new Id();
	    	id.setLabel(object1.get("label").toString());
	    	id.setImId(object2.get("im:id").toString());
	    	id.setImBundleId(object2.get("im:bundleId").toString());
	    	entry.setId(id);
	
	    	object1 = (JSONObject)ele.get("im:artist");
	    	// object1.get("label").toString();
	    	object2 = (JSONObject)object1.get("attributes");
	    	// object2.get("href").toString();
	    	ImArtist imArtist = new ImArtist();
	    	imArtist.setLabel(object1.get("label").toString());
	    	if(object2 != null) {
		    	imArtist.setHref(object2.get("href").toString());	    		
	    	}
	    	entry.setImArtist(imArtist);

	    	object1 = (JSONObject)ele.get("category");
	    	object2 = (JSONObject)object1.get("attributes");
	    	// object2.get("im:id").toString();
	    	// object2.get("term").toString();
	    	// object2.get("scheme").toString();
	    	// object2.get("label").toString();
	    	Category category = new Category();
	    	category.setImId(object2.get("im:id").toString());
	    	category.setTerm(object2.get("term").toString());
	    	category.setScheme(object2.get("scheme").toString());
	    	category.setLabel(object2.get("label").toString());
	    	entry.setCategory(category);

	    	object1 = (JSONObject)ele.get("im:releaseDate");
	    	// object1.get("label").toString();
	    	object2 = (JSONObject)object1.get("attributes");
	    	// object2.get("label").toString();
	    	ImReleaseDate imReleaseDate = new ImReleaseDate();
	    	imReleaseDate.setLabel(object1.get("label").toString());
	    	imReleaseDate.setAlabel(object2.get("label").toString());
	    	entry.setImReleaseDate(imReleaseDate);

	    	entryList.add(entry);
	    	logger.info("[[entry] ImName] >>> " + entry.getImName().getLabel());

	    	if(i >= intEnd) {
	    		logger.info("[break] number >>> " + i);
	    		break;
	    	}
	    }

	    return entryList;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private boolean saveAppInMysql(String appId, String appName, String appImg, String appGenre, 
			String appUpdate, String appCountry, String appRanking) {

		long id = this.getSequence("app");
		logger.info("app >>>> ID >>>> " + id);

		Map<String, Object> mapSelectApp = new HashMap<String, Object>();
		mapSelectApp.put("seqName", "app");
		mapSelectApp.put("appId", appId);
		mapSelectApp.put("appName", appName);
		mapSelectApp.put("appImg", appImg);
		mapSelectApp.put("appGenre", appGenre);
		mapSelectApp.put("appUpdate", appUpdate);
		mapSelectApp.put("appCountry", appCountry);
		mapSelectApp.put("appRanking", appRanking);
		mapSelectApp.put("appStatusFlag", "0");

		try {
			id = masterAdminDao.getMapper(MasterAdminDao.class).insertApp(mapSelectApp);
			if(id < 1) {
				logger.error("insertApp is error. appId=" + appId);
				return false;
			}
		} catch (DuplicateKeyException e) {
			logger.error("DuplicateKeyException error", e);
			return false;
		} catch (Exception e) {
			logger.error("Exception error", e);
			// Force it to throw
			new Throwable(e);
		}

		return true;
	}

	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private long getSequence(String seqName){

		long id = 0;
		Map<String, Object> mapSelectSequence = new HashMap<String, Object>();
		mapSelectSequence.put("seqName", seqName);
		try {
			id = masterAdminDao.getMapper(MasterAdminDao.class).updateSequece(mapSelectSequence);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return id;
	}

	public HbaseServiceImpl() {

	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean insertAppAdmin(String adminId, String[] appId, String appAdminGenre) {

		long id = this.getSequence("appAdmin");
		int intResult = 0;
		HadoopAppModel app = null;

		for(int i=0; appId.length > i; i++) {
			this.getSequence("appAdmin");

			try {
				Map<String, Object> mapSelectApp = new HashMap<String, Object>();
				mapSelectApp.put("appId", appId[i]);
				mapSelectApp.put("appGenre", appAdminGenre);
				app = slaveAdminDao.getMapper(SlaveAdminDao.class).selectApp(mapSelectApp);

				if(app == null) {
					logger.info("app is null >>>> " + appId[i]);
					logger.info("There is a appName >>>> " + appAdminGenre);
					continue;
				} else {
					logger.info("There is a app >>>> " + appId[i]);
					logger.info("There is a appName >>>> " + appAdminGenre);					
				}

			} catch (Exception e) {
				logger.error("Exception error. appId=" + appId[i], e);
			}

			Map<String, Object> mapInsertAppAdmin = new HashMap<String, Object>();
			mapInsertAppAdmin.put("seqName", "appAdmin");
			mapInsertAppAdmin.put("adminId", adminId);
			mapInsertAppAdmin.put("appId", app.getAppId());
			mapInsertAppAdmin.put("appAdminName", app.getAppName());
			mapInsertAppAdmin.put("appAdminImg", app.getAppImg());
			mapInsertAppAdmin.put("appAdminGenre", appAdminGenre);
			mapInsertAppAdmin.put("appAdminStatusFlag", "0");

			try {
				intResult = masterAdminDao.getMapper(MasterAdminDao.class).insertAppAdmin(mapInsertAppAdmin);
				if(intResult < 1) {
					logger.warn("appId[" + i + "]=" + appId[i]);
					return false;
				}
			} catch (DuplicateKeyException e) {
				logger.error("DuplicateKeyException error", e);
				return false;
			} catch (Exception e) {
				// Force it to throw
				logger.error("Exception error", e);
				try {
					throw new Exception("There is a exception");
				} catch (Exception te) {
					logger.error("throw new Exception", te);
				}
			}

		}
	
		return true;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean deleteAppAdmin(String adminId, String[] appId, String appAdminGenre) {

		int intResult = 0;

		for(int i=0; appId.length > i; i++) {

			Map<String, Object> mapDeleteAppAdmin = new HashMap<String, Object>();
			mapDeleteAppAdmin.put("adminId", adminId);
			mapDeleteAppAdmin.put("appId", appId[i]);
			mapDeleteAppAdmin.put("appAdminGenre", appAdminGenre);
			mapDeleteAppAdmin.put("appAdminStatusFlag", "1");

			try {
				intResult = masterAdminDao.getMapper(MasterAdminDao.class).updateAppAdmin(mapDeleteAppAdmin);
				if(intResult < 1) {
					logger.warn("appId[" + i + "]=" + appId[i]);
					return false;
				}
			} catch (Exception e) {
				// Force it to throw
				logger.error("Exception error", e);
				try {
					throw new Exception("There is a exception");
				} catch (Exception te) {
					logger.error("throw new Exception", te);
				}
			}

		}

		return true;
	}

	public List<HadoopAppAdminModel> selectAppAdminList(String adminId, String appAdminGenre) {

		List<HadoopAppAdminModel> appAdminList = null;

		Map<String, Object> mapSelectAppAdmin = new HashMap<String, Object>();
		mapSelectAppAdmin.put("adminId", adminId);
		mapSelectAppAdmin.put("appAdminGenre", appAdminGenre);
		mapSelectAppAdmin.put("appAdminStatusFlag", "0");

		try {
			appAdminList = slaveAdminDao.getMapper(SlaveAdminDao.class).selectAppAdminList(mapSelectAppAdmin);
		} catch (Exception e) {
			logger.error("Exception error, adminId=" + adminId + ", appAdminGenre=" + appAdminGenre, e);
		}

    	return appAdminList;
	}

	public List<HbaseResultBean> selectRow(String tableName, String rowkey) throws Exception {

		Result r = hBaseDAO.result(tableName, rowkey);
		List<HbaseResultBean> hbaseResultList = new ArrayList<HbaseResultBean>();

    	for (KeyValue kv : r.raw()) {

    		HbaseResultBean hbaseResult = new HbaseResultBean();
    		hbaseResult.setRow(new String(kv.getRow()));
    		hbaseResult.setFamily(new String(kv.getFamily()));
    		hbaseResult.setQualifier(new String(kv.getQualifier()));
    		hbaseResult.setValue(new String(kv.getValue()));
    		hbaseResult.setTimestamp(new Long(kv.getTimestamp()));

    		hbaseResultList.add(hbaseResult);
        }

    	return hbaseResultList;
	}

	public List<HbaseResultBean> selectRowAndFamily(String tableName, String rowkey) throws Exception {

		Result r = hBaseDAO.result(tableName, rowkey);
		List<HbaseResultBean> hbaseResultList = new ArrayList<HbaseResultBean>();

    	for (KeyValue kv : r.raw()) {

    		HbaseResultBean hbaseResult = new HbaseResultBean();
    		hbaseResult.setRow(new String(kv.getRow()));
    		hbaseResult.setFamily(new String(kv.getFamily()));
    		hbaseResult.setQualifier(new String(kv.getQualifier()));
    		hbaseResult.setValue(new String(kv.getValue()));
    		hbaseResult.setTimestamp(new Long(kv.getTimestamp()));

    		hbaseResultList.add(hbaseResult);
        }

    	return hbaseResultList;
	}

	public List<HbaseResultBean> selectScan(String tableName) throws Exception {

		ResultScanner rs = null;

		try {
			rs = hBaseDAO.resultScanner(tableName, 50);
		} catch (Exception e) {
			logger.error("error >>>> ", e);
		}

		return getHbaseResult(rs);
	}

	public List<HbaseResultBean> selectScanColumnFamily(String tableName, String rowKey, String family) throws Exception {

		ResultScanner rs = null;

		try {
			rs = hBaseDAO.resultScannerRowAndFamily(tableName, rowKey, family);
		} catch (Exception e) {
			logger.error("Exception >>>> rowKey=" + rowKey + ", family=" + family, e);
		}

		return getHbaseResult(rs);
	}

	public boolean createTableColumn(String tableName, String[] cfs) throws Exception {

        hBaseDAO.createTableColumn(tableName, cfs);

        return true;
	}

	public void addKeyValue(String tableName, String rowKey, String[] cfs, String[] qfs, String[] vals) throws Exception {

        hBaseDAO.addKeyValue(tableName, rowKey, cfs, qfs, vals);

	}

	@Override
	public boolean deleteRow(String tableName, String rowkey) throws Exception {

		boolean resultDelete = hBaseDAO.deleteRow(tableName, rowkey);
		
		if(resultDelete) {
			logger.info("delete a row is ture");
		} else {
			logger.info("delete a row is false");
		}

		return resultDelete;
	}

	@Override
	public boolean deleteTable(String tableName) throws Exception {

		boolean resultDelete = hBaseDAO.deleteTable(tableName);

		if(resultDelete) {
			logger.info("delete a table is ture");
		} else {
			logger.info("delete a table is false");
		}

		return false;
	}

	public boolean saveRssJsonItunes(String tableName, String columnFamily, 
			String country, String url) throws Exception {

		StringBuffer sbValue = new StringBuffer();

		try {
			String vals = ApiHttpClient.reqGetMethodAsStream(null, url);

			JSONObject obj=(JSONObject)JSONValue.parse(new StringReader(vals));
    	    JSONObject objFeed=(JSONObject)obj.get("feed");
    	    JSONObject objUpdated=(JSONObject)objFeed.get("updated");

			String update = objUpdated.get("label").toString();
			logger.info("update >> " + update);

			Result r = hBaseDAO.resultRowAndFamilyAndQualifier(tableName, this.dataKey, columnFamily, country);

			if(r != null) {

				List<HbaseResultBean> resultList = this.updateData(r);

				for(HbaseResultBean result: resultList) {
					logger.info("new update = " + update);
					logger.info("old update = " + result.getRow());

					if (result.getRow().equals(update)) {
						logger.info("There is data");
						return false;
					} else {
						logger.info("There is no data");
					}
				}
			}

			hBaseDAO.addKeyValue(tableName, "new", columnFamily, country, vals);

			sbValue.append("update=").append(update).append("\t")
					.append("country=").append(country);

			int i = 0;
			JSONArray arrayEntry = (JSONArray)objFeed.get("entry");
			String [] jsonApp = new String[arrayEntry.size()];
			String [] rowKeyArray = new String[arrayEntry.size()];
			String [] valueRanking = new String[arrayEntry.size()];

    	    @SuppressWarnings("unchecked")
			Iterator<JSONObject> itr = arrayEntry.iterator();
    	    while(itr.hasNext()) {
    	    	JSONObject ele = itr.next();
    	    	jsonApp[i] = ele.toJSONString();

    	    	JSONObject objId = (JSONObject)ele.get("id");
    	    	JSONObject objAttributes = (JSONObject)objId.get("attributes");
    	    	rowKeyArray[i] = objAttributes.get("im:id").toString();

    	    	JSONObject objName = (JSONObject)ele.get("im:name");
    	    	String appName = objName.get("label").toString();

    	    	JSONArray arrayImg = (JSONArray)ele.get("im:image");
    	    	JSONObject objImg = (JSONObject)arrayImg.get(0);
    	    	String strImg = (String)objImg.get("label");

    	    	logger.info("appName>>>>>" + i+ ">>>>" + appName);
    	    	valueRanking[i] = String.valueOf(i+1);

    	    	// It is not necessary to same to the DB.
        	    this.saveAppInMysql(rowKeyArray[i], appName, strImg, columnFamily, 
        	    		update, country, valueRanking[i]);					

    	    	i++;
    	    }

			hBaseDAO.addKeyValue(configuration.getString("itunes.app.table"), 
					rowKeyArray, columnFamily, sbValue.toString(), valueRanking);

		} catch (Exception e) {
			logger.error("Exception error >> ", e);
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAppDataDetails(String tableName, String columnFamily, String url) throws Exception {

		Map<String, Object> mapAppData = null;

		try {

			String vals = ApiHttpClient.reqGetMethodAsStream(null, url);

			JSONObject obj=(JSONObject)JSONValue.parse(new StringReader(vals));
			JSONArray objResults=(JSONArray)obj.get("results");

			String jsonText = objResults.get(0).toString();
			ContainerFactory containerFactory = new ContainerFactory(){
				public List<String> creatArrayContainer() {
					return new LinkedList<String>();
				}
				public Map<String, Object> createObjectContainer() {
					return new LinkedHashMap<String, Object>();
				}
			};

    		JSONParser parser = new JSONParser();
    	    mapAppData = (Map<String, Object>)parser.parse(jsonText, containerFactory);

		} catch(Exception e) {
			logger.error("Exception >> ", e);
		}

		return mapAppData;
	}

	public List<AppDataBean> getAppRanking(String tableName, String rowKey, String family) throws Exception {

		ResultScanner rs = null;

		try {
			long lngStart = DateUtility.getStartBeforeDay(5).getTime();
			long lngEnd = DateUtility.getNowTime().getTime();
			
			Date currentDate = new Date(lngStart);
			DateFormat df = new SimpleDateFormat("yy-MM-dd:HH-mm-ss");

			System.out.println("start date = " + df.format(currentDate));

			rs = hBaseDAO.resultScannerRowFamilyAndTimestemp(tableName, rowKey, family, lngStart, lngEnd);
		} catch (Exception e) {
			logger.error("Exception >>>> rowKey=" + rowKey + ", family=" + family, e);
		}

		List<HbaseResultBean> HbaseResultList = this.getHbaseResult(rs);
		List<AppDataBean> sampleAppDataList = new ArrayList<AppDataBean>();

		for(HbaseResultBean hbaseResult: HbaseResultList) {
			AppDataBean sampleAppData = new AppDataBean();

			String strTime = hbaseResult.getQualifier();
			logger.info("strTime >> " + strTime);
			String[] date = strTime.split("T");
			String splitDate = date[0];
			String[] splitedDate = splitDate.split("-");
			sampleAppData.setYear(splitedDate[0]);

			logger.info("splitedDate[1] >> " + splitedDate[1]);

			int intMonth = Integer.parseInt(splitedDate[1]) - 1;
			sampleAppData.setMonth(String.valueOf(intMonth));
			sampleAppData.setDay(splitedDate[2]);

			String splitTime = date[1];
			String[] splitedTime = splitTime.split(":");
			sampleAppData.setHour(splitedTime[0]);
			sampleAppData.setValue(hbaseResult.getValue());
			sampleAppDataList.add(sampleAppData);
		}

		return sampleAppDataList;
	}

	public List<List<EntryBean>> getItuensRanking(String tableName, String family, String qualifier, int nowPage) throws Exception {

		Result r = null;
		List<List<EntryBean>> rankingList = null;
		try {

			r = hBaseDAO.resultRowAndFamilyAndQualifier(tableName, this.dataKey, family, qualifier);
			if(r == null) {
				logger.info("The result is null.");
				return null;
			}

			String value = null;
        	for (KeyValue kv : r.raw()) {
        		value = new String(kv.getValue());
            }
        	
        	if(value == null) {
        		logger.info("the value is null");
        		return null;
        	}

			rankingList = new ArrayList<List<EntryBean>>();
			rankingList.add(this.parsingForJson(value, nowPage));

		} catch (Exception e) {
			logger.error("Exception >>> ", e);
		}

		return rankingList;
	}

	public List<SampleAppBean> getAmebaApp(String adminId, String family, Date startDate, Date endDate) throws Exception {

		// Get App list from user information.
		List<HadoopAppAdminModel> appAdminList = this.selectAppAdminList(adminId, family);
		List<SampleAppBean> sampleAppList = new ArrayList<SampleAppBean>();
		long lngStart = 0;
		long lngEnd = 0;
		for(HadoopAppAdminModel appAdmin : appAdminList) {

			if(startDate == null) {
				lngStart = DateUtility.getStartBeforeDay(configuration.getInt("itunes.app.period")).getTime();
				lngEnd = DateUtility.getNowTime().getTime();
			} else {
				lngStart = startDate.getTime();
				lngEnd = endDate.getTime();
			}

			Result r = hBaseDAO.resultRowAndFamilyAndTimestemp(configuration.getString("itunes.app.table"), appAdmin.getAppId(), family, lngStart, lngEnd);

			List<AppDataBean> sampleAppDataList = new ArrayList<AppDataBean>();
			SampleAppBean sampleApp = new SampleAppBean();
	    	for (KeyValue kv : r.raw()) {

	    		AppDataBean sampleAppData = new AppDataBean();

				String strQualifiers = new String(kv.getQualifier());
				String [] qualifier = strQualifiers.split("\t");

				String update = null;
				for(int k = 0; qualifier.length > k; k++) {
					String [] keyValue= qualifier[k].split("=");
					if(keyValue[0].equals("update")) {
						update = keyValue[1];
					}
				}

				// Set time
				String[] date = update.split("T");
				String splitDate = date[0];

				String[] splitedDate = splitDate.split("-");
				sampleAppData.setYear(splitedDate[0]);

				int intMonth = Integer.parseInt(splitedDate[1]) - 1;
				sampleAppData.setMonth(String.valueOf(intMonth));
				sampleAppData.setDay(splitedDate[2]);

				String splitTime = date[1];
				String[] splitedTime = splitTime.split(":");
				sampleAppData.setHour(splitedTime[0]);
				sampleAppData.setValue(new String(kv.getValue()));
				sampleAppDataList.add(sampleAppData);
	        }

			sampleApp.setName(appAdmin.getAppAdminName());
			sampleApp.setImg(appAdmin.getAppAdminImg());
			sampleApp.setAppDataList(sampleAppDataList);
			logger.info("appId >>>>>" + appAdmin.getAppId());

			sampleAppList.add(sampleApp);
		}

		return sampleAppList;
	}

}