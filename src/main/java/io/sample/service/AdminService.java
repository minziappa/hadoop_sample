package io.sample.service;

import io.sample.bean.model.ChargeModel;
import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HadoopGameModel;
import io.sample.bean.para.RegisterAdminPara;
import io.sample.bean.para.RegisterGamePara;

import java.util.List;

public interface AdminService {

	public boolean insertGame(RegisterGamePara registerGamePara) throws Exception;
	public HadoopGameModel selectGame(String gameId) throws Exception;
	public List<HadoopGameModel> selectGameList() throws Exception;
	public boolean updateGame(RegisterGamePara registerGamePara) throws Exception;

	public boolean insertAdmin(RegisterAdminPara registerAdminPara) throws Exception;
	public HadoopAdminModel selectAdmin(String adminId, String gameId) throws Exception;
	public List<HadoopAdminModel> selectAdminList() throws Exception;
	public boolean updateAdmin(RegisterAdminPara registerAdminPara) throws Exception;

	public ChargeModel getChargeSum() throws Exception;

}