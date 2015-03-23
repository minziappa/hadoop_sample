package io.sample.controller;

import io.sample.bean.DirectoryBean;
import io.sample.bean.RootDirectoryBean;
import io.sample.bean.model.HadoopModel;
import io.sample.bean.para.DirFlumePara;
import io.sample.bean.para.SearchFlumePara;
import io.sample.service.AdminService;
import io.sample.service.HadoopService;
import io.sample.service.HbaseService;
import io.sample.service.MapReduceService;

import java.util.List;
import java.util.Locale;

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

@Controller
@RequestMapping("/log")
public class LogHadoopController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(LogHadoopController.class);

	Locale LOCALE = Locale.JAPAN;
    @Autowired
    private Validator validator;
	@Autowired
	private MessageSource message;
	@Autowired
    private Configuration configuration;
	@Autowired
    private HbaseService hbaseService;
	@Autowired
    private AdminService adminService;
	@Autowired
    private HadoopService hadoopService;
	@Autowired
    private MapReduceService mapReduceService;

	@RequestMapping(value = {"searchLogs.sg"})
	public String searchLogs(@Valid SearchFlumePara searchFlumePara, 
			BindingResult bindingResult, ModelMap model, HttpSession session) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("log");
		String resultSearch = null;

		if (bindingResult.hasErrors()) {
			logger.warn("searchUser.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));

			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());

			model.addAttribute("model", hadoopModel);

			return "flume/inputSearchFlume";
		}

		if(session.getAttribute("game").equals("uh")) {
			resultSearch = hadoopService.readLogsThreePlural(searchFlumePara.getPath(), searchFlumePara.getSearchWord().trim(), 
					searchFlumePara.getSearchFlag());
		} else {
			resultSearch = hadoopService.readLogsThreePlural(searchFlumePara.getPath(), searchFlumePara.getSearchWord().trim(), 
					searchFlumePara.getSearchFlag());
			searchFlumePara.setSearchFlag("");
			if(resultSearch == null) {
				searchFlumePara.setSearchFlag("false");
			}
		}

		hadoopModel.setSearchFlumePara(searchFlumePara);
		hadoopModel.setResultSearch(resultSearch);
		List<DirectoryBean> directoryList = hadoopService.directoryList(searchFlumePara.getPath());
		List<RootDirectoryBean> rootDirectoryList = hadoopService.rootDirectory(directoryList.get(0).getPath());

		hadoopModel.setPath(rootDirectoryList.get(rootDirectoryList.size()-1).getPath());
		hadoopModel.setRootdirectoryList(rootDirectoryList);
		hadoopModel.setDirectoryList(directoryList);

		model.addAttribute("model", hadoopModel);

		return "flume/inputSearchFlume";
	}

	@RequestMapping(value = {"directoryList.sg"})
	public String directoryList(@Valid DirFlumePara dirFlumePara, BindingResult bindingResult, 
			ModelMap model) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("log");

		if (bindingResult.hasErrors()) {
			logger.warn("listDirectory.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));

			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hadoopModel);

			return "flume/inputSearchFlume";
		}

		List<DirectoryBean> directoryList = hadoopService.directoryList(dirFlumePara.getPath());
		if (directoryList != null && directoryList.size() > 0) {
			List<RootDirectoryBean> rootDirectoryList = hadoopService.rootDirectory(directoryList.get(0).getPath());
			hadoopModel.setPath(rootDirectoryList.get(rootDirectoryList.size()-1).getPath());
			hadoopModel.setRootdirectoryList(rootDirectoryList);
		}

		hadoopModel.setDirectoryList(directoryList);

		model.addAttribute("model", hadoopModel);

		return "flume/inputSearchFlume";
	}

}