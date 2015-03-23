package io.sample.controller;

import io.sample.bean.model.HadoopModel;
import io.sample.service.AdminService;

import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/login")
public class LoginHadoopController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(LoginHadoopController.class);

    @Autowired
    private Validator validator;
	@Autowired
	private MessageSource message;
	@Autowired
    private Configuration configuration;
	@Autowired
    private AdminService adminService;

	@RequestMapping(value = {"index.sg"})
	public String index(ModelMap model, HttpSession session) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("admin");

		this.handleLogin(session, adminService);

		model.addAttribute("model", hadoopModel);

		return this.handleIphone(session) ? "sp/index" : "index";
	}

	@RequestMapping(value = {"login.sg"})
	public String login(@RequestParam(value="error", required=false) boolean error, ModelMap model, HttpSession session) throws Exception {

		logger.info("login - start");
		if (error == true) {
		   // Assign an error message
			model.put("error", "You have entered an invalid username or password!");
		} else {
			model.put("error", "");
		}

		logger.info("login - end");

		return this.handleIphone(session) ? "sp/login" : "login/login";
	}

	@RequestMapping(value = {"logout.sg"})
	public String logout(ModelMap model, SessionStatus sessionStatus, HttpSession session) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("login");

		logger.info("logout");

		// Clear data in the session.
		sessionStatus.isComplete();

		model.addAttribute("model", hadoopModel);

		return this.handleIphone(session) ? "sp/index" : "index";
	}

	@RequestMapping(value = {"denied.sg"})
	public String denied(ModelMap model, HttpSession session) throws Exception {

		logger.info("denied - start");

		logger.info("denied - end");

		return "login/denied";
	}

	@RequestMapping(value = {"help.sg"})
	public String help(ModelMap model, HttpSession session) throws Exception {
		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("admin");

		model.addAttribute("model", hadoopModel);

		return this.handleIphone(session) ? "sp/help" : "help";
	}

}