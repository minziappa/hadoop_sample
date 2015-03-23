package io.sample.service.impl;

import io.sample.bean.model.ChargeModel;
import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HadoopGameModel;
import io.sample.bean.para.RegisterAdminPara;
import io.sample.bean.para.RegisterGamePara;
import io.sample.dao.MasterAdminDao;
import io.sample.dao.SlaveDao;
import io.sample.http.ApiHttpClient;
import io.sample.service.AdminService;
import io.sample.utility.DateUtility;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.configuration.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

@Service
public class AdminServiceImpl implements AdminService {

	final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private Md5PasswordEncoder passwordEncoder;
	@Autowired
    private Configuration configuration;
	@Autowired
	private SqlSession masterAdminDao;
	@Autowired
	private SqlSession slaveAdminDao;

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean insertGame(RegisterGamePara registerGamePara) throws Exception {

		int intResult = 0;

		MultipartFile file = registerGamePara.getUpfile();
		String strName = file.getOriginalFilename();
		byte[] byteName = file.getBytes();

		logger.info("gameId >> " + registerGamePara.getGameId());
		logger.info("gameDomain >> " + registerGamePara.getGameDomain());
		logger.info("gameTitle >> " + registerGamePara.getGameTitle());
		logger.info("gameExplain >> " + registerGamePara.getGameExplain());

		Map<String, Object> mapRegisterGame = new HashMap<String, Object>();
		mapRegisterGame.put("gameId", registerGamePara.getGameId());
		mapRegisterGame.put("gameDomain", registerGamePara.getGameDomain());
		mapRegisterGame.put("gameTitle", registerGamePara.getGameTitle());
		mapRegisterGame.put("gameExplain", registerGamePara.getGameExplain());
		mapRegisterGame.put("gameFile", byteName);
		mapRegisterGame.put("gameStatusFlag", "0");

		try {
			intResult = masterAdminDao.getMapper(MasterAdminDao.class).insertGame(mapRegisterGame);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}
		if(intResult < 1) {
			logger.error("InsertGame error, gameId={}", registerGamePara.getGameId());
			return false;
		}

		return true;
	}

	public HadoopGameModel selectGame(String gameId) throws Exception {
		
		HadoopGameModel hadoopGame = null;

		Map<String, Object> mapSelectGame = new HashMap<String, Object>();
		mapSelectGame.put("gameId", gameId.trim());

		try {
			hadoopGame = slaveAdminDao.getMapper(SlaveDao.class).selectGame(mapSelectGame);

		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return hadoopGame;
	}

	@Override
	public List<HadoopGameModel> selectGameList() throws Exception {

		List<HadoopGameModel> hadoopGameList = null;

		Map<String, Object> mapSelectGameList = new HashMap<String, Object>();

		try {
			hadoopGameList = slaveAdminDao.getMapper(SlaveDao.class).selectGameList(mapSelectGameList);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return hadoopGameList;
	}

	@Override
	public boolean updateGame(RegisterGamePara registerGamePara) throws Exception {

		int intResult = 0;

		MultipartFile file = registerGamePara.getUpfile();
		String strName = file.getOriginalFilename();
		byte[] byteName = file.getBytes();

		Map<String, Object> mapRegisterGame = new HashMap<String, Object>();
		mapRegisterGame.put("gameId", registerGamePara.getGameId());
		mapRegisterGame.put("gameDomain", registerGamePara.getGameDomain());
		mapRegisterGame.put("gameTitle", registerGamePara.getGameTitle());
		mapRegisterGame.put("gameExplain", registerGamePara.getGameExplain());
		mapRegisterGame.put("gameFile", byteName);
		mapRegisterGame.put("gameStatusFlag", "0");

		try {
			intResult = masterAdminDao.getMapper(MasterAdminDao.class).updateGame(mapRegisterGame);
		} catch (Exception e) {
			logger.error("Exception error", e);
			return false;
		}
		if(intResult < 1) {
			logger.error("InsertGame error, gameId={}", registerGamePara.getGameId());
			return false;
		}

		return true;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean insertAdmin(RegisterAdminPara registerAdminPara) throws Exception {

		int intResult = 0;

		Map<String, Object> mapRegisterAdminId = new HashMap<String, Object>();
		mapRegisterAdminId.put("adminId", registerAdminPara.getAdminId());
		mapRegisterAdminId.put("adminPwd", "sample");
		mapRegisterAdminId.put("gameId", registerAdminPara.getGameId());
		mapRegisterAdminId.put("adminName", registerAdminPara.getAdminName());
		mapRegisterAdminId.put("adminMail", registerAdminPara.getAdminMail());
		mapRegisterAdminId.put("adminStatusFlag", registerAdminPara.getAdminStatusFlag());

		try {
			intResult = masterAdminDao.getMapper(MasterAdminDao.class).insertAdmin(mapRegisterAdminId);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		if(intResult < 1) {
			logger.error("InsertAdmin error, sampleId={}, gameId={}", registerAdminPara.getAdminId(), registerAdminPara.getGameId());
			return false;
		}

		return true;
	}

	@Override
	public HadoopAdminModel selectAdmin(String adminId, String gameId) throws Exception {

		HadoopAdminModel hadoopAdminModel = null;

		Map<String, Object> mapSelectAdmin = new HashMap<String, Object>();
		mapSelectAdmin.put("adminId", adminId.trim());
		mapSelectAdmin.put("gameId", gameId.trim());

		try {
			hadoopAdminModel = slaveAdminDao.getMapper(SlaveDao.class).selectAdmin(mapSelectAdmin);

		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return hadoopAdminModel;
	}

	@Override
	public List<HadoopAdminModel> selectAdminList() throws Exception {

		List<HadoopAdminModel> apnsUserList = null;

		Map<String, Object> mapSelectAdminIdList = new HashMap<String, Object>();
		mapSelectAdminIdList.put("gameDb", "bt");

		try {
			apnsUserList = slaveAdminDao.getMapper(SlaveDao.class).selectAdminList(mapSelectAdminIdList);
		} catch (Exception e) {
			logger.error("Exception error", e);
		}

		return apnsUserList;
	}

	@Override
	public boolean updateAdmin(RegisterAdminPara registerAdminPara) throws Exception {

		int intResult = 0;

		Map<String, Object> mapRegisterAdmin = new HashMap<String, Object>();
		mapRegisterAdmin.put("adminId", registerAdminPara.getAdminId());
		mapRegisterAdmin.put("gameId", registerAdminPara.getGameId());
		mapRegisterAdmin.put("adminPwd", passwordEncoder.encodePassword(registerAdminPara.getAdminPwd(), null));
		mapRegisterAdmin.put("adminName", registerAdminPara.getAdminName());
		mapRegisterAdmin.put("adminMail", registerAdminPara.getAdminMail());
		mapRegisterAdmin.put("adminStatusFlag", registerAdminPara.getAdminStatusFlag());

		try {
			intResult = masterAdminDao.getMapper(MasterAdminDao.class).updateAdmin(mapRegisterAdmin);
		} catch (Exception e) {
			logger.error("Exception error", e);
			return false;
		}
		if(intResult < 1) {
			logger.error("UpdateAdmin error, adminId={}, gameId={}", registerAdminPara.getAdminId(), registerAdminPara.getGameId());
			return false;
		}

		return true;
	}

	public int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
	    int cnt = 0;
	    String lineRead = "";
	    while ((lineRead = reader.readLine()) != null) {
	    	
	    }

	    cnt = reader.getLineNumber(); 
	    reader.close();
	    return cnt;
	}

	@Override
	public ChargeModel getChargeSum() throws Exception {

		ChargeModel charge = new ChargeModel();

		List<Object> clientIdList = (List<Object>)configuration.getList("realTime.clientId");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String startData = sdf.format(DateUtility.getStartToday());
		String nowData = sdf.format(DateUtility.getNowTime());

		Map<String, Long> mapChargeSum = new TreeMap<String, Long>();
		// BiMap<String, Long> mapChargeSum = HashBiMap.create();

		Long longChargeTotalSum = new Long(0);
		for (Object clientId : clientIdList) {

			StringBuffer sb = new StringBuffer();
			sb.append("https://api-sample/sample/").append(clientId)
			.append("/charge/hourly/").append(startData).append(":").append(nowData)
			.append("?access_token=11111111111111111111111111111");

			logger.info("url=" + sb);

			String json = ApiHttpClient.reqGetMethod(null, sb.toString());

			if(json == null) {
				continue;
			}

			JSONParser parser=new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			JSONObject objData = (JSONObject)obj.get("result");
			JSONArray objArray = (JSONArray)objData.get("data");
			for(int i = 0; i < objArray.size(); i++) {
				JSONObject jsonObj = (JSONObject)objArray.get(i);

				Long longCharge = (Long) mapChargeSum.get(String.valueOf(i));
				if(longCharge == null) {
					longCharge = new Long(0);
				}

				Long longChargeSum = longCharge + (Long)jsonObj.get("charge");
				mapChargeSum.put(String.valueOf(i), longChargeSum);

				logger.info("plase1 key=" + String.valueOf(i) + ", value=" + longChargeSum);

			}
			charge.setNowChargeSum(mapChargeSum.get(String.valueOf(objArray.size() - 1)));
		}

		charge.setGameDomain(mapChargeSum);

		logger.info("--------------------------------------------------");

		Set<Entry<String, Long>> set = mapChargeSum.entrySet();
		Iterator<Entry<String, Long>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Long> e = (Map.Entry<String, Long>)it.next();
			longChargeTotalSum = longChargeTotalSum + e.getValue();
			// logger.info("plase2 key=" + e.getKey() + ", value=" + e.getValue());
		}
		charge.setChargeTotalSum(longChargeTotalSum);

		logger.info("sum=" + mapChargeSum.toString());

		return charge;
	}
	
}