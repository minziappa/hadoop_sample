//package io.sample.service.temp;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.LineNumberReader;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.commons.configuration.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.encoding.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//	final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//    private MasterAdminDao masterAdminDao;
//	@Autowired
//    private SlaveAdminDao slaveAdminDao;
//	@Autowired
//    private SlaveDao slaveDao;
//	@Autowired
//    private Configuration configuration;
//	@Autowired
//	private AppService appService;
//	@Autowired
//	private  AppClientService appClientService;
//
//	private Long selectHistoryId() throws Exception {
//
//		Long lngMax = slaveAdminDao.selectHistoryMax();
//
//		if(lngMax == null || lngMax.intValue() < 1) {
//			logger.error("lngMax is error");
//			lngMax = new Long(0);
//		}
//
//		lngMax = lngMax + 1;
//
//		return lngMax;
//	}
//
//	private FileReader getLogFile(int beforeDay) throws Exception {
//
//		String filePath = null;
//		File logFile = null;
//		FileReader fsReader = null;
//
//		if(beforeDay > 0) {
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String beforeDayFormat = format.format(DateUtility.getDayInMillisBefore(beforeDay));
//			filePath = configuration.getString("logfile.error") + "." + beforeDayFormat;
//		} else {
//			filePath = configuration.getString("logfile.error");
//		}
//
//		logFile = new File(filePath);
//
//		if(!logFile.exists()) {
//			logger.warn("There is no file. beforeDay=" + beforeDay);
//			return null;
//		} else {
//			fsReader = new FileReader(filePath);
//		}
//
//		return fsReader;
//	}
//
//	private boolean insertHistoryList(Long lngMax, String gameId, String message, String uuIds, 
//			AppAdminModel appAdmin, String kindFlag, String sandBoxFlag, String statusFlag) throws Exception {
//
//		int intResult = 0;
//		String strSandBoxFlag = null;
//
//		if(sandBoxFlag.equals("false")) {
//			strSandBoxFlag = "0";
//		} else {
//			strSandBoxFlag = "1";
//		}
//
//		Map<String, Object> mapHistory = new HashMap<String, Object>();
//		mapHistory.put("historyId", lngMax);
//		mapHistory.put("gameId", gameId);
//		mapHistory.put("adminId", appAdmin.getAdminId());
//		mapHistory.put("adminName", appAdmin.getAdminName());
//		mapHistory.put("historyMessage", message);
//		mapHistory.put("historyUuId", uuIds);
//		mapHistory.put("historyKindFlag", kindFlag);
//		mapHistory.put("historySandBoxFlag", strSandBoxFlag);
//		mapHistory.put("historyStatusFlag", statusFlag);
//
//		try {
//			intResult = masterAdminDao.insertHistory(mapHistory);			
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//
//		if(intResult < 1) {
//			logger.error("insertHistoryList error, userId={}, gameId={}", appAdmin.getAdminId(), gameId);
//			return false;
//		}
//
//		return true;
//	}
//
//	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
//	@Override
//	public boolean insertGame(RegisterGamePara registerGamePara) throws Exception {
//
//		int intResult = 0;
//
//		Map<String, Object> mapCreateGame = new HashMap<String, Object>();
//		mapCreateGame.put("gameDb", registerGamePara.getGameId());
//
//		try {
//			masterAdminDao.createTableGame(mapCreateGame);
//			masterAdminDao.createIndexUu(mapCreateGame);
//			masterAdminDao.createIndexDeny(mapCreateGame);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		// While a table is created, 
//		Thread.sleep(500);
//
//		MultipartFile file = registerGamePara.getUpfile();
//		String strCertificateName = file.getOriginalFilename();
//		byte[] byteCertificateName = file.getBytes();
//
//		Map<String, Object> mapRegisterGame = new HashMap<String, Object>();
//		mapRegisterGame.put("gameId", registerGamePara.getGameId());
//		mapRegisterGame.put("gameDomain", registerGamePara.getGameDomain());
//		mapRegisterGame.put("gameTitle", registerGamePara.getGameTitle());
//		mapRegisterGame.put("gameExplain", registerGamePara.getGameExplain());
//		mapRegisterGame.put("gameTokenPassword", registerGamePara.getGameTokenPassword());
//		mapRegisterGame.put("gameCertificateName", strCertificateName);
//		mapRegisterGame.put("gameCertificateFile", byteCertificateName);
//		mapRegisterGame.put("gameStatusFlag", "0");
//
//		try {
//			intResult = masterAdminDao.insertGame(mapRegisterGame);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//		if(intResult < 1) {
//			logger.error("InsertGame error, gameId={}", registerGamePara.getGameId());
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public boolean updateGame(RegisterGamePara registerGamePara) throws Exception {
//
//		int intResult = 0;
//
//		MultipartFile file = registerGamePara.getUpfile();
//		String strCertificateName = file.getOriginalFilename();
//		byte[] byteCertificateName = file.getBytes();
//
//		Map<String, Object> mapRegisterGame = new HashMap<String, Object>();
//		mapRegisterGame.put("gameId", registerGamePara.getGameId());
//		mapRegisterGame.put("gameDomain", registerGamePara.getGameDomain());
//		mapRegisterGame.put("gameTitle", registerGamePara.getGameTitle());
//		mapRegisterGame.put("gameExplain", registerGamePara.getGameExplain());
//		mapRegisterGame.put("gameTokenPassword", registerGamePara.getGameTokenPassword());
//		mapRegisterGame.put("gameCertificateName", strCertificateName);
//		mapRegisterGame.put("gameCertificateFile", byteCertificateName);
//		mapRegisterGame.put("gameStatusFlag", "0");
//
//		try {
//			intResult = masterAdminDao.updateGame(mapRegisterGame);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//		if(intResult < 1) {
//			logger.error("InsertGame error, gameId={}", registerGamePara.getGameId());
//			return false;
//		}
//
//		return true;
//	}
//
//	public AppGameModel selectGame(String gameId) throws Exception {
//	
//		AppGameModel appGame = null;
//	
//		Map<String, Object> mapSelectGame = new HashMap<String, Object>();
//		mapSelectGame.put("gameId", gameId.trim());
//	
//		try {
//			appGame = slaveDao.selectGame(mapSelectGame);
//	
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//	
//		return appGame;
//	}
//
//	@Override
//	public boolean insertAdmin(RegisterAdminPara registerAdminPara) throws Exception {
//
//		int intResult = 0;
//
//		if(!appService.checkGameId(registerAdminPara.getGameId())) {
//			return false;
//		}
//
//		Map<String, Object> mapRegisterAdminId = new HashMap<String, Object>();
//		mapRegisterAdminId.put("adminId", registerAdminPara.getAdminId());
//		mapRegisterAdminId.put("adminPwd", passwordEncoder.encodePassword(registerAdminPara.getAdminPwd(), null));
//		mapRegisterAdminId.put("gameId", registerAdminPara.getGameId());
//		mapRegisterAdminId.put("adminName", registerAdminPara.getAdminName());
//		mapRegisterAdminId.put("adminMail", registerAdminPara.getAdminMail());
//		mapRegisterAdminId.put("adminStatusFlag", registerAdminPara.getAdminStatusFlag());
//
//		try {
//			intResult = masterAdminDao.insertAdmin(mapRegisterAdminId);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		if(intResult < 1) {
//			logger.error("InsertAdmin error, userId={}, gameId={}", registerAdminPara.getAdminId(), registerAdminPara.getGameId());
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public AppAdminModel selectAdmin(String adminId, String gameId) throws Exception {
//
//		AppAdminModel appAdmin = null;
//
//		Map<String, Object> mapSelectAdmin = new HashMap<String, Object>();
//		mapSelectAdmin.put("adminId", adminId.trim());
//		mapSelectAdmin.put("gameId", gameId.trim());
//
//		try {
//			appAdmin = slaveAdminDao.selectAdmin(mapSelectAdmin);
//
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appAdmin;
//	}
//
//	@Override
//	public boolean updateAdmin(RegisterAdminPara registerAdminPara) throws Exception {
//
//		int intResult = 0;
//
//		Map<String, Object> mapRegisterAdmin = new HashMap<String, Object>();
//		mapRegisterAdmin.put("adminId", registerAdminPara.getAdminId());
//		mapRegisterAdmin.put("gameId", registerAdminPara.getGameId());
//		mapRegisterAdmin.put("adminPwd", passwordEncoder.encodePassword(registerAdminPara.getAdminPwd(), null));
//		mapRegisterAdmin.put("adminName", registerAdminPara.getAdminName());
//		mapRegisterAdmin.put("adminMail", registerAdminPara.getAdminMail());
//		mapRegisterAdmin.put("adminStatusFlag", registerAdminPara.getAdminStatusFlag());
//
//		try {
//			intResult = masterAdminDao.updateAdmin(mapRegisterAdmin);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//		if(intResult < 1) {
//			logger.error("UpdateAdmin error, adminId={}, gameId={}", registerAdminPara.getAdminId(), registerAdminPara.getGameId());
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public boolean sendMessagePer(SendMessagePerPara sendMessagePerPara, AppAdminModel appAdmin) throws Exception {
//		String result = null;
//
//		try {
//			appClientService.setURL(configuration.getString("app.api.url"));
//
//			Map<String, Object> mapOption = new HashMap<String, Object>();
//			mapOption.put("badge", sendMessagePerPara.getBadge());
//			mapOption.put("sound", sendMessagePerPara.getSound());
//
//			result = appClientService.sendMessage(sendMessagePerPara.getSendUuId(), sendMessagePerPara.getGameId(), sendMessagePerPara.getMessage(),
//					mapOption,sendMessagePerPara.getSandBox());
//
//		} catch (Exception e) {
//			logger.error("Exception resutl=" + result, e);
//			return false;
//		}
//
//		if(result == null) {
//			logger.error("result is null, uuId=" + sendMessagePerPara.getSendUuId() + ", gameId=" + sendMessagePerPara.getGameId() + 
//					", message=" + sendMessagePerPara.getMessage());
//			return false;
//		}
//
//		logger.info("result of sending >>> " + result);
//
//		StringBuffer uuIds = new StringBuffer();
//		for(int i=0; i < sendMessagePerPara.getSendUuId().length; i++) {
//			String uuId = sendMessagePerPara.getSendUuId()[i];
//			uuIds.append(uuId).append(":");
//		}
//
//		Long lngMax = this.selectHistoryId();
//
//		// Complete to send a message in a per.
//		this.insertHistoryList(lngMax, sendMessagePerPara.getGameId(), sendMessagePerPara.getMessage(), 
//				uuIds.toString(), appAdmin, "0", sendMessagePerPara.getSandBox(), "1");
//
//		return true;
//	}
//
//	@Override
//	public boolean sendMessageInBatch(SendMessageAllPara sendMessageAllPara, AppAdminModel appAdmin) throws Exception {
//
//		logger.info("Theard start");
//
//		// Get a gameId from Master in the common cache.
//		AppGameModel appGame = appService.getAppGame(sendMessageAllPara.getGameId());
//		// if there is none, return to false.
//		if(appGame == null) {
//			logger.error("there has no a GameId. a GameId is {}", sendMessageAllPara.getGameId());
//			return false;
//		}
//
//		Long lngMax = this.selectHistoryId();
//
//		SendMessageThread smt = new SendMessageThread(configuration, sendMessageAllPara, lngMax);
//		ExecutorService es = Executors.newFixedThreadPool(1);
//
//		es.submit(smt);
//		es.shutdown();
//
//		this.insertHistoryList(lngMax, sendMessageAllPara.getGameId(), sendMessageAllPara.getMessage(), "", appAdmin, "1", sendMessageAllPara.getSandBox(), "0");
//
//		return true;
//	}
//
//	@Override
//	public boolean reSendMessageInFile(String historyId, int beforeDay) throws Exception {
//
//		AppHistoryAdminModel appHistory = null;
//		FileReader fsReader = null;
//		LineNumberReader lnReader = null;
//		String strReadResult = null;
//		String result = null;
//		String[] to = null;
//		String strSandBoxFlag = "false";
//		int i = 0;
//
//		try {
//			
//			appHistory = this.selectHistory(historyId);
//			if(appHistory == null) {
//				logger.error("this is no history. historyId=" + historyId);
//				return false;
//			}
//
//			fsReader = this.getLogFile(beforeDay);
//		    lnReader = new LineNumberReader(fsReader);
//		    to = new String[appHistory.getHistoryErrorCnt()];
//
//			this.updateHistoryStatus(historyId, "2");
//		    
//			while((strReadResult = lnReader.readLine()) != null) {
//
//				String [] historyIdInLog = strReadResult.split("_");
//				if(historyIdInLog.length > 1) {
//					if(historyIdInLog[2].equals(historyId)) {
//						to[i] = historyIdInLog[3];
//						i++;
//					}
//				}
//			}
//
//			if (appHistory.getHistorySandBoxFlag().equals("0")) {
//				strSandBoxFlag = "false";
//			} else {
//				strSandBoxFlag = "true";
//			}
//
//			appClientService.setURL(configuration.getString("app.api.url"));
//			result = appClientService.sendMessage(to, appHistory.getGameId(), appHistory.getHistoryMessage(), strSandBoxFlag);
//
//			if(result == null) {
//				logger.error("result is null, uuId=" + to + ", gameId=" + appHistory.getGameId() + ", message=" + appHistory.getHistoryMessage());
//				return false;
//			}
//
//			this.updateHistoryStatus(historyId, "3");
//
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return true;
//	}
//
//	public List<AppUuModel> selectUser(SearchUserPara searchUserPara) throws Exception {
//
//		List<AppUuModel> appUserList = null;
//
//		Map<String, Object> mapSelectUser = new HashMap<String, Object>();
//		mapSelectUser.put("gameDb", searchUserPara.getGameId().trim());
//		mapSelectUser.put("uuId", searchUserPara.getUuId().trim());
//
//		try {
//			AppUuModel appUuModel = slaveDao.selectUser(mapSelectUser);
//			if(appUuModel != null) {
//				appUserList = new ArrayList<AppUuModel>();
//				appUserList.add(appUuModel);
//			}
//
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appUserList;
//	}
//
//	public List<AppUuModel> selectUserList(String gameId, int nowPage) throws Exception {
//
//		List<AppUuModel> appUserList = null;
//
//    	// Check a nowPage
//		if(nowPage <= 0){
//			nowPage = 1;
//		}
//
//		Map<String, Object> mapSelectUserList = new HashMap<String, Object>();
//		mapSelectUserList.put("gameDb", gameId);
//		mapSelectUserList.put("nowPage", (nowPage - 1) * 10);
//
//		try {
//			appUserList = slaveAdminDao.selectUserList(mapSelectUserList);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appUserList;
//	}
//
//	@Override
//	public int selectUserSum(String gameId) throws Exception {
//
//		Map<String, Object> mapSelectUserSum = new HashMap<String, Object>();
//		mapSelectUserSum.put("gameDb", gameId);
//
//		return slaveAdminDao.selectUserSum(mapSelectUserSum);
//	}
//
//	@Override
//	public int selectHistorySum(String gameId) throws Exception {
//
//		Map<String, Object> mapSelectHistorySum = new HashMap<String, Object>();
//		mapSelectHistorySum.put("gameId", gameId);
//
//		return slaveAdminDao.selectHistorySum(mapSelectHistorySum);
//	}
//
//	@Override
//	public List<AppAdminModel> getAdminList() throws Exception {
//
//		List<AppAdminModel> appUserList = null;
//
//		Map<String, Object> mapSelectAdminIdList = new HashMap<String, Object>();
//		mapSelectAdminIdList.put("gameDb", "bt");
//
//		try {
//			appUserList = slaveAdminDao.selectAdminList(mapSelectAdminIdList);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appUserList;
//	}
//
//	@Override
//	public List<AppHistoryAdminModel> showHistoryList(String gameId, int nowPage) throws Exception {
//
//		List<AppHistoryAdminModel> appHistoryList = null;
//
//		// Check a nowPage
//		if(nowPage <= 0){
//			nowPage = 1;
//		}
//
//		Map<String, Object> mapSelectHistoryList = new HashMap<String, Object>();
//		mapSelectHistoryList.put("gameId", gameId);
//		mapSelectHistoryList.put("nowPage", (nowPage - 1) * 10);
//
//		try {
//			appHistoryList = slaveAdminDao.selectHistoryList(mapSelectHistoryList);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appHistoryList;
//	}
//
//	public AppHistoryAdminModel selectHistory(String historyId) throws Exception {
//
//		AppHistoryAdminModel appHistory = null;
//
//		Map<String, Object> mapSelectHistory = new HashMap<String, Object>();
//		mapSelectHistory.put("historyId", new Long(historyId));
//
//		try {
//			appHistory = slaveAdminDao.selectHistory(mapSelectHistory);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appHistory;
//	}
//
//	public boolean updateHistoryErrorCnt(String historyId, int errorCnt, String errorFlag) throws Exception {
//
//		int intResult = 0;
//
//		Map<String, Object> mapHistoryErrorFlug = new HashMap<String, Object>();
//		mapHistoryErrorFlug.put("historyId", new Long(historyId));
//		mapHistoryErrorFlug.put("historyErrorCnt", new Integer(errorCnt));
//		mapHistoryErrorFlug.put("historyErrorFlag", errorFlag);
//
//		try {
//			intResult = masterAdminDao.updateHistoryErrorCnt(mapHistoryErrorFlug);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//
//		if(intResult < 1) {
//			logger.error("updateHistoryErrorFlug error, lngMax={}", historyId);
//			return false;
//		}
//
//		return true;
//	}
//
//	public boolean updateHistoryStatus(String historyId, String status) throws Exception {
//
//		int intResult = 0;
//
//		Map<String, Object> mapHistoryErrorFlug = new HashMap<String, Object>();
//		mapHistoryErrorFlug.put("historyId", new Long(historyId));
//		mapHistoryErrorFlug.put("historyStatusFlag", status);
//
//		try {
//			intResult = masterAdminDao.updateHistoryStatus(mapHistoryErrorFlug);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//
//		if(intResult < 1) {
//			logger.error("updateHistoryStatus error, historyId={}", historyId);
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	public int getCntErrorLog(String historyId, int beforeDay) throws Exception {
//
//		int intSum = 0;
//		String strReadResult = null;
//		AppHistoryAdminModel appHistory = null;
//
//		FileReader fsReader = null;
//		LineNumberReader lnReader = null;
//
//		try {
//
//			appHistory = this.selectHistory(historyId);
//			if(appHistory == null) {
//				logger.error("this is no history. historyId=" + historyId);
//				return 1;
//			}
//
//			// If there is no file, return 0;
//			fsReader = this.getLogFile(beforeDay);
//			if(fsReader == null) {
//				return 1;
//			}
//
//		    lnReader = new LineNumberReader(fsReader);
//
//			while((strReadResult = lnReader.readLine()) != null) {
//
//				String [] historyIdInLog = strReadResult.split("_");
//				if(historyIdInLog.length > 1) {
//					if(historyIdInLog[2].equals(historyId)) {
//						intSum = intSum + 1;
//					}
//				}
//
//			}
//
//			if(intSum < 1) {
//				logger.error("this is no error. historyId=" + historyId + ", errorCont=" + intSum);
//			}
//
//			if(!this.updateHistoryErrorCnt(historyId, intSum, "1")) {
//				logger.error("this is a error in The UpdateHistoryErrorFlug. historyId=" + historyId + ", errorCont=" + intSum);
//				return 1;
//			}
//
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		} finally {
//			if(lnReader != null) {
//			    lnReader.close();
//			}
//			if(fsReader != null) {
//				fsReader.close();
//			}
//		}
//
//		return intSum;
//	}
//
//	public boolean insertBook(BookMessageAllPara bookMessageAllPara, AppAdminModel appAdmin) throws Exception {
//
//		int intResult = 0;
//		String strSendbox = "0";
//
//		logger.info(bookMessageAllPara.getBookDate().toString());
//
//		if(!appService.checkGameId(appAdmin.getGameId())) {			
//			logger.error("There is no game id." + appAdmin.getGameId());
//			return false;
//		}
//
//		if(bookMessageAllPara.getSandbox().equals("true")) {
//			strSendbox="1";
//		}
//
//		Map<String, Object> mapInsertBook = new HashMap<String, Object>();
//		mapInsertBook.put("gameId", bookMessageAllPara.getGameId());
//		mapInsertBook.put("adminId", appAdmin.getAdminId());
//		mapInsertBook.put("bookName", appAdmin.getAdminName());
//		mapInsertBook.put("bookTime", bookMessageAllPara.getBookDate());
//		mapInsertBook.put("bookThread", bookMessageAllPara.getThreads());
//		mapInsertBook.put("bookSandbox", strSendbox);
//		mapInsertBook.put("bookBadge", bookMessageAllPara.getBadge());
//		mapInsertBook.put("bookSound", bookMessageAllPara.getSound());
//		mapInsertBook.put("bookMessage", bookMessageAllPara.getMessage());
//		mapInsertBook.put("bookStatusFlag", "0");
//
//		try {
//			intResult = masterAdminDao.insertBook(mapInsertBook);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		if(intResult < 1) {
//			logger.error("InsertAdmin error, userId={}, gameId={}", appAdmin.getAdminId(), appAdmin.getGameId());
//			return false;
//		}
//
//		return true;
//	}
//
//	public List<AppBookModel> selectBookList(String gameId, int nowPage) throws Exception {
//
//		List<AppBookModel> appBookList = null;
//
//		// Check a nowPage
//		if(nowPage <= 0){
//			nowPage = 1;
//		}
//
//		Map<String, Object> mapSelectBookList = new HashMap<String, Object>();
//		mapSelectBookList.put("gameId", gameId);
//		mapSelectBookList.put("nowPage", (nowPage - 1) * 10);
//
//		try {
//			appBookList = slaveAdminDao.selectBookList(mapSelectBookList);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appBookList;
//	}
//
//	public List<AppBookModel> selectBookList(Date pastDate) throws Exception {
//
//		List<AppBookModel> appBookList = null;
//
//		Map<String, Object> mapSelectBookList = new HashMap<String, Object>();
//		mapSelectBookList.put("pastData", pastDate);
//
//		try {
//			appBookList = slaveAdminDao.selectBookListAll(mapSelectBookList);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//		}
//
//		return appBookList;
//	}
//
//	@Override
//	public int selectBookSum(String gameId) throws Exception {
//
//		Map<String, Object> mapSelectBookSum = new HashMap<String, Object>();
//		mapSelectBookSum.put("gameId", gameId);
//
//		return slaveAdminDao.selectBookSum(mapSelectBookSum);
//	}
//
//	@Override
//	public boolean updateBookStatus(int bookId, String status) throws Exception {
//
//		int intResult = 0;
//
//		Map<String, Object> mapBookStatusFlag = new HashMap<String, Object>();
//		mapBookStatusFlag.put("bookId", new Long(bookId));
//		mapBookStatusFlag.put("bookStatusFlag", status);
//
//		try {
//			intResult = masterAdminDao.updateBookStatus(mapBookStatusFlag);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//
//		if(intResult < 1) {
//			logger.error("updateBookStatus error, bookId={}", bookId);
//			return false;
//		}
//
//		return true;
//	}
//
//}