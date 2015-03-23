package io.sample.bean.model;

import io.paging.bean.PagingBean;
import io.sample.bean.AppDataBean;
import io.sample.bean.HbaseResultBean;
import io.sample.bean.SampleAppBean;
import io.sample.bean.game.EntryBean;
import io.sample.bean.para.AppDataDetailsPara;
import io.sample.bean.para.DeleteAppAdminPara;
import io.sample.bean.para.HbaseSearchPara;
import io.sample.bean.para.SampleAppPara;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HbaseModel implements Serializable {

	private static final long serialVersionUID = -9196037947175787440L;

	private List<HbaseResultBean> hbaseResultList;
	private List<List<EntryBean>> selectFree;
	private List<List<EntryBean>> selectGrossing;
	private List<SampleAppBean> sampleAppList;
	private List<AppDataBean> appData;
	private List<HadoopAppAdminModel> appAdminList;

	/** Paging **/
    private PagingBean paging;
	/** Navi in pages **/
    private String navi;

    /** Parametor **/
    private HbaseSearchPara hbaseSearchPara;
    private SampleAppPara sampleAppPara;
    private DeleteAppAdminPara deleteAppAdminPara;
    private AppDataDetailsPara appDataDetailsPara;
    private Map<String, Object> appDataDetails;

    private boolean bln;
    private String strJson;

    /** Show the message to page.**/
    private String errorMessage;

    
	public List<HbaseResultBean> getHbaseResultList() {
		return hbaseResultList;
	}

	public void setHbaseResultList(List<HbaseResultBean> hbaseResultList) {
		this.hbaseResultList = hbaseResultList;
	}

	public List<List<EntryBean>> getSelectFree() {
		return selectFree;
	}

	public void setSelectFree(List<List<EntryBean>> selectFree) {
		this.selectFree = selectFree;
	}

	public List<List<EntryBean>> getSelectGrossing() {
		return selectGrossing;
	}

	public void setSelectGrossing(List<List<EntryBean>> selectGrossing) {
		this.selectGrossing = selectGrossing;
	}

	
	
	public PagingBean getPaging() {
		return paging;
	}

	public void setPaging(PagingBean paging) {
		this.paging = paging;
	}

	public String getNavi() {
		return navi;
	}

	public void setNavi(String navi) {
		this.navi = navi;
	}

	public boolean isBln() {
		return bln;
	}

	public void setBln(boolean bln) {
		this.bln = bln;
	}

	public HbaseSearchPara getHbaseSearchPara() {
		return hbaseSearchPara;
	}

	public void setHbaseSearchPara(HbaseSearchPara hbaseSearchPara) {
		this.hbaseSearchPara = hbaseSearchPara;
	}

	public String getStrJson() {
		return strJson;
	}

	public void setStrJson(String strJson) {
		this.strJson = strJson;
	}

	public List<SampleAppBean> getSampleAppList() {
		return sampleAppList;
	}

	public void setSampleAppList(List<SampleAppBean> sampleAppList) {
		this.sampleAppList = sampleAppList;
	}

	public SampleAppPara getSampleAppPara() {
		return sampleAppPara;
	}

	public void setSampleAppPara(SampleAppPara sampleAppPara) {
		this.sampleAppPara = sampleAppPara;
	}

	public DeleteAppAdminPara getDeleteAppAdminPara() {
		return deleteAppAdminPara;
	}

	public void setDeleteAppAdminPara(DeleteAppAdminPara deleteAppAdminPara) {
		this.deleteAppAdminPara = deleteAppAdminPara;
	}

	public AppDataDetailsPara getAppDataDetailsPara() {
		return appDataDetailsPara;
	}

	public void setAppDataDetailsPara(AppDataDetailsPara appDataDetailsPara) {
		this.appDataDetailsPara = appDataDetailsPara;
	}

	public Map<String, Object> getAppDataDetails() {
		return appDataDetails;
	}

	public void setAppDataDetails(Map<String, Object> appDataDetails) {
		this.appDataDetails = appDataDetails;
	}

	public List<AppDataBean> getAppData() {
		return appData;
	}

	public void setAppData(List<AppDataBean> appData) {
		this.appData = appData;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<HadoopAppAdminModel> getAppAdminList() {
		return appAdminList;
	}

	public void setAppAdminList(List<HadoopAppAdminModel> appAdminList) {
		this.appAdminList = appAdminList;
	}

}