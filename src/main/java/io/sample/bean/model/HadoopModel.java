package io.sample.bean.model;

import io.paging.bean.PagingBean;
import io.sample.bean.DirectoryBean;
import io.sample.bean.RootDirectoryBean;
import io.sample.bean.para.DirFlumePara;
import io.sample.bean.para.SearchFlumePara;

import java.io.Serializable;
import java.util.List;

public class HadoopModel implements Serializable {

	private static final long serialVersionUID = -2758511412673492544L;

	private String sytemEnvironment;

	private ChargeModel charge;
	/** A game information **/
	private HadoopGameModel hadoopGame;
	/** A game List **/
	private List<HadoopGameModel> hadoopGameList;
	/** A admin information **/
	private HadoopAdminModel hadoopAdmin;
	/** A admin List **/
	private List<HadoopAdminModel> hadoopAdminList;
	/** Directory List **/
	private List<DirectoryBean> directoryList;
	/** Directory List **/
	private List<RootDirectoryBean> rootdirectoryList;

	/** Paging **/
    private PagingBean paging;
	/** Navi in pages **/
    private String navi;
	/** Past **/
    private String strPast;

	/** Past **/
    private String path;

	/** resultSearch **/
    private String resultSearch;
    
    /** Set the parameter **/
    private SearchFlumePara searchFlumePara;
    private DirFlumePara dirFlumePara;

	public ChargeModel getCharge() {
		return charge;
	}

	public void setCharge(ChargeModel charge) {
		this.charge = charge;
	}

	public HadoopGameModel getHadoopGame() {
		return hadoopGame;
	}

	public void setHadoopGame(HadoopGameModel hadoopGame) {
		this.hadoopGame = hadoopGame;
	}

	public List<HadoopGameModel> getHadoopGameList() {
		return hadoopGameList;
	}

	public void setHadoopGameList(List<HadoopGameModel> hadoopGameList) {
		this.hadoopGameList = hadoopGameList;
	}

	public String getSytemEnvironment() {
		return sytemEnvironment;
	}

	public void setSytemEnvironment(String sytemEnvironment) {
		this.sytemEnvironment = sytemEnvironment;
	}

	public HadoopAdminModel getHadoopAdmin() {
		return hadoopAdmin;
	}

	public void setHadoopAdmin(HadoopAdminModel hadoopAdmin) {
		this.hadoopAdmin = hadoopAdmin;
	}

	public List<HadoopAdminModel> getHadoopAdminList() {
		return hadoopAdminList;
	}

	public void setHadoopAdminList(List<HadoopAdminModel> hadoopAdminList) {
		this.hadoopAdminList = hadoopAdminList;
	}

	public List<DirectoryBean> getDirectoryList() {
		return directoryList;
	}

	public void setDirectoryList(List<DirectoryBean> directoryList) {
		this.directoryList = directoryList;
	}

	public List<RootDirectoryBean> getRootdirectoryList() {
		return rootdirectoryList;
	}

	public void setRootdirectoryList(List<RootDirectoryBean> rootdirectoryList) {
		this.rootdirectoryList = rootdirectoryList;
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

	public String getStrPast() {
		return strPast;
	}

	public void setStrPast(String strPast) {
		this.strPast = strPast;
	}

	public String getResultSearch() {
		return resultSearch;
	}

	public void setResultSearch(String resultSearch) {
		this.resultSearch = resultSearch;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SearchFlumePara getSearchFlumePara() {
		return searchFlumePara;
	}

	public void setSearchFlumePara(SearchFlumePara searchFlumePara) {
		this.searchFlumePara = searchFlumePara;
	}

	public DirFlumePara getDirFlumePara() {
		return dirFlumePara;
	}

	public void setDirFlumePara(DirFlumePara dirFlumePara) {
		this.dirFlumePara = dirFlumePara;
	}

}