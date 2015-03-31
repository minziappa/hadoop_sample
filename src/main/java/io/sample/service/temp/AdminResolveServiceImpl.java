//package io.sample.service.temp;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.configuration.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.encoding.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdminResolveServiceImpl implements AdminResolveService {
//
//	final Logger logger = LoggerFactory.getLogger(AdminResolveServiceImpl.class);
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//    private MasterAdminDao masterAdminDao;
//	@Autowired
//    private SlaveAdminDao slaveAdminDao;
//	@Autowired
//    private Configuration configuration;
//	@Autowired
//	private ApnsService apnsService;
//	@Autowired
//	private  ApnsClientService apnsClientService;
//
//	@Override
//	public boolean updateUser() throws Exception {
//
//		int intResult = 0;
//
//		Map<String, Object> mapRegisterGame = new HashMap<String, Object>();
//		mapRegisterGame.put("gameStatusFlag", "0");
//
//		try {
//			intResult = masterAdminDao.updateGame(mapRegisterGame);
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//
//		return true;
//	}
//
//	public boolean selectUser() throws Exception {
//
//		List<ApnsResolveModel> apnsUuResolveList = null;
//		
//		List<ApnsUuModel> apnsUuList = null;
//
//		try {
//			apnsUuResolveList = slaveAdminDao.selectUserDuplicationList();
//
//			List<String> uuIdList = new ArrayList<String>();
//
//			for(ApnsResolveModel apnsUuResolve: apnsUuResolveList) {
//
//				logger.info("UuDeviceToken >> " + apnsUuResolve.getUuDeviceToken());
//				logger.info("Cnt >> " + apnsUuResolve.getCnt());
//				logger.info("=============================");
//
//				Map<String, Object> mapSelectUserResolveList = new HashMap<String, Object>();
//				mapSelectUserResolveList.put("gameDb", "bt");
//				mapSelectUserResolveList.put("uuDeviceToken", apnsUuResolve.getUuDeviceToken());
//
//				apnsUuList = slaveAdminDao.selectUserResolveList(mapSelectUserResolveList);
//
//				int i = 0;
//				for(ApnsUuModel apnsUs: apnsUuList) {
//
//					logger.info("i >>> " + i);
//
//					if(i == 0) {
//						logger.info("00000");
//					} else {
//						uuIdList.add(apnsUs.getUuId());
//						logger.info("userId >>>> " + apnsUs.getUuId());
//					}
//					i++;
//				}
//
//			}
//
//			Map<String, Object> mapUpdateUserResolveList = new HashMap<String, Object>();
//			mapUpdateUserResolveList.put("uuIdList", uuIdList);
//			mapUpdateUserResolveList.put("gameDb", "bt");
//			mapUpdateUserResolveList.put("uuDeny", "1");
//
//			masterAdminDao.updateUserResolveList(mapUpdateUserResolveList);
//
//		} catch (Exception e) {
//			logger.error("Exception error", e);
//			return false;
//		}
//
//		return true;
//	}
//
//}