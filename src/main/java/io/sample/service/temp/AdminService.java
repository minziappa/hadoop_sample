//package io.sample.service.temp;
//
//import java.util.Date;
//import java.util.List;
//
//public interface AdminService {
//
//	public boolean insertGame(RegisterGamePara registerGamePara) throws Exception;
//	public boolean insertAdmin(RegisterAdminPara registerAdminPara) throws Exception;
//
//	public boolean updateGame(RegisterGamePara registerGamePara) throws Exception;
//	public boolean updateAdmin(RegisterAdminPara registerAdminPara) throws Exception;
//
//	public List<ApnsAdminModel> getAdminList() throws Exception;
//
//	public boolean sendMessagePer(SendMessagePerPara sendMessagePerPara, ApnsAdminModel apnsAdmin) throws Exception;
//	public boolean sendMessageInBatch(SendMessageAllPara sendMessageAllPara, ApnsAdminModel apnsAdmin) throws Exception;
//	public boolean reSendMessageInFile(String historyId, int beforeDay) throws Exception;
//
//	public ApnsGameModel selectGame(String gameId) throws Exception;
//	public ApnsAdminModel selectAdmin(String adminId, String gameId) throws Exception;
//
//	public List<ApnsUuModel> selectUser(SearchUserPara searchUserPara) throws Exception;
//	public List<ApnsUuModel> selectUserList(String gameId, int nowPage) throws Exception;
//	public int selectUserSum(String gameId) throws Exception;
//
//	public List<ApnsHistoryAdminModel> showHistoryList(String gameId, int nowPage) throws Exception;
//	public int selectHistorySum(String gameId) throws Exception;
//	public int getCntErrorLog(String historyId, int beforeDay) throws Exception;
//
//	public boolean insertBook(BookMessageAllPara bookMessageAllPara, ApnsAdminModel apnsAdmin) throws Exception;
//	public List<ApnsBookModel> selectBookList(String gameId, int nowPage) throws Exception;
//	public List<ApnsBookModel> selectBookList(Date pastDate) throws Exception;
//	public int selectBookSum(String gameId) throws Exception;
//	public boolean updateBookStatus(int bookId, String status) throws Exception;
//
//}