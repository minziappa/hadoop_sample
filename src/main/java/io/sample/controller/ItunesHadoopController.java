package io.sample.controller;

import io.paging.Paging;
import io.paging.bean.PagingBean;
import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HbaseModel;
import io.sample.bean.para.AppDataDetailsPara;
import io.sample.bean.para.DeleteAppAdminPara;
import io.sample.bean.para.RankingAppPara;
import io.sample.bean.para.RegisterAppAdminPara;
import io.sample.bean.para.SampleAppPara;
import io.sample.common.Common;
import io.sample.service.HbaseService;
import io.sample.service.MapReduceService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;



import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/itunes")
public class ItunesHadoopController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(ItunesHadoopController.class);

    @Autowired
    private Validator validator;
	@Autowired
	private MessageSource message;
	@Autowired
    private Configuration configuration;
	@Autowired
    private HbaseService hbaseService;
	@Autowired
    private MapReduceService mapReduceService;

	@RequestMapping(value = {"rankingApp.sg"})
	public String rankingApp(@Valid RankingAppPara rankingAppPara, ModelMap model) throws Exception {

		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("itunes");

		PagingBean paging = new PagingBean();

		// Send All users a messag
		hbaseModel.setSelectFree(
				hbaseService.getItuensRanking(configuration.getString("itunes.game.table"),
				configuration.getString("itunes.game.allFree.columnfamily") ,
				configuration.getString("itunes.game.allFree.qualifier"), 
				rankingAppPara.getNowPage()));

		hbaseModel.setSelectGrossing(
				hbaseService.getItuensRanking(configuration.getString("itunes.game.table"),
				configuration.getString("itunes.game.allGrossing.columnfamily"), 
				configuration.getString("itunes.game.allGrossing.qualifier"), 
				rankingAppPara.getNowPage()));

		// Set Paging list
		paging.setAllCount(400);
		Paging.linkPaging(paging, rankingAppPara.getNowPage());
		hbaseModel.setPaging(paging);

		// Set a message for error
		hbaseModel.setErrorMessage(rankingAppPara.getError());
		model.addAttribute("model", hbaseModel);

		return "hbase/rankingApp";
	}

	@RequestMapping(value = {"sampleApp.sg"})
	public String sampleApp(@Valid SampleAppPara sampleAppPara, BindingResult bindingResult, ModelMap model, HttpSession session) throws Exception {
		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("itunes");

		new ItunesHadoopValidator().validate(sampleAppPara, bindingResult);		
		if (bindingResult.hasErrors()) {
			logger.warn("sampleApp.sg >>>> it has occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return "hbase/sampleApp";
		}

		HadoopAdminModel hadoopAdmin = (HadoopAdminModel) session.getAttribute("admin");
		if(hadoopAdmin == null) {
			return "redirect:sampleApp.sg?error=There is no admin";
		}
		hbaseModel.setSampleAppList(hbaseService.getAmebaApp(hadoopAdmin.getAdminId(), sampleAppPara.getFamily(), 
				sampleAppPara.getStartDate(), sampleAppPara.getEndDate()));
		hbaseModel.setAppAdminList(hbaseService.selectAppAdminList(hadoopAdmin.getAdminId(), sampleAppPara.getFamily()));
		hbaseModel.setSampleAppPara(sampleAppPara);

		model.addAttribute("model", hbaseModel);

		return this.handleIphone(session) ? "sp/sampleApp" : "hbase/sampleApp";
	}

	@RequestMapping(value = {"getAppAdmin.sg"})
	public String getAppAdmin(@Valid SampleAppPara sampleAppPara, BindingResult bindingResult, 
			ModelMap model, HttpSession session) throws Exception {
		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("itunes");

		if (bindingResult.hasErrors()) {
			logger.warn("getAppAdmin.sg >>>> it has occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return this.handleIphone(session) ? "sp/getAppAdmin" : "hbase/getAppAdmin";
		}

		HadoopAdminModel hadoopAdmin = (HadoopAdminModel) session.getAttribute("admin");
		if(hadoopAdmin == null) {
			return "redirect:getAppAdmin.sg?error=There is no admin";
		}

		hbaseModel.setAppAdminList(hbaseService.selectAppAdminList(hadoopAdmin.getAdminId(), sampleAppPara.getFamily()));
		hbaseModel.setSampleAppPara(sampleAppPara);

		model.addAttribute("model", hbaseModel);

		return "hbase/getAppAdmin";
	}

	@RequestMapping(value = {"deleteAppAdmin.sg"})
	public String deleteAppAdmin(@Valid DeleteAppAdminPara deleteAppAdminPara, BindingResult bindingResult, 
			ModelMap model, HttpSession session) throws Exception {
		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("itunes");

		if (bindingResult.hasErrors()) {
			logger.warn("getAppAdmin.sg >>>> it has occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return "hbase/getAppAdmin";
		}

		HadoopAdminModel hadoopAdmin = (HadoopAdminModel) session.getAttribute("admin");
		if(hadoopAdmin == null) {
			return "redirect:sampleApp.sg?error=There is no admin";
		}

		// Delete a App in Admin
		if(!hbaseService.deleteAppAdmin(hadoopAdmin.getAdminId(), deleteAppAdminPara.getAppId(), deleteAppAdminPara.getAppAdminGenre())) {
			return "redirect:sampleApp.sg?error=There is a system error";
		}

		model.addAttribute("model", hbaseModel);

		return "redirect:sampleApp.sg?family="+deleteAppAdminPara.getAppAdminGenre();
	}

	@RequestMapping(value = {"appDataDetails.sg"})
	public String appDataDetails(@Valid AppDataDetailsPara appDataDetailsPara, 
			BindingResult bindingResult, ModelMap model, HttpSession session) throws Exception {
		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("itunes");

		if (bindingResult.hasErrors()) {
			logger.warn("appDataDetails.sg >>>> it has occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return "hbase/appDataDetails";
		}

		hbaseModel.setAppData(hbaseService.getAppRanking("itunesApp", appDataDetailsPara.getId(), appDataDetailsPara.getFamily()));
		hbaseModel.setAppDataDetails(hbaseService.getAppDataDetails(null, null, 
				Common.LOOKUP_JP_URL_S + "&id=" + appDataDetailsPara.getId()));
		hbaseModel.setAppDataDetailsPara(appDataDetailsPara);

		model.addAttribute("model", hbaseModel);

		return this.handleIphone(session) ? "sp/appDataDetails" : "hbase/appDataDetails";
	}

	@RequestMapping(value = {"registerAppAdmin.sg"}, method = RequestMethod.POST)
	public String registerAppAdmin(@Valid RegisterAppAdminPara registerAppAdminPara, BindingResult bindingResult, 
			ModelMap model, HttpSession session) throws Exception {

		if (bindingResult.hasErrors()) {
			logger.error("registerAppAdmin.sg >>>> It has occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			return "redirect:rankingApp.sg?error=It has occured a parameter error.";
		}

		HadoopAdminModel hadoopAdmin = (HadoopAdminModel) session.getAttribute("admin");

		if(hadoopAdmin == null) {
			return "redirect:rankingApp.sg?error=There is no admin";
		}

		if(!hbaseService.insertAppAdmin(hadoopAdmin.getAdminId(), registerAppAdminPara.getAppId(), registerAppAdminPara.getAppAdminGenre())) {
			model.addAttribute("errorMessage", message.getMessage("hadoop.system.error.message", null, null));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
	        return "redirect:rankingApp.sg?error=A system error has occured";
		}

		return "redirect:rankingApp.sg?error=These are saved successfully";
	}

}