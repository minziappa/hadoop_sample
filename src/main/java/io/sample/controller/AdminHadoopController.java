package io.sample.controller;

import io.sample.bean.CommonBean;
import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HadoopModel;
import io.sample.bean.para.EditAdminPara;
import io.sample.bean.para.EditGamePara;
import io.sample.bean.para.RegisterAdminPara;
import io.sample.bean.para.RegisterGamePara;
import io.sample.service.AdminService;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
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

/***
 * The <code>AdminHadoopController</code> class represents action controller.
 * 
 * @since   JDK1.7
 * 
 */
@Controller
@RequestMapping("/admin")
public class AdminHadoopController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(AdminHadoopController.class);
	Locale LOCALE = Locale.JAPAN;

    @Autowired
    private Validator validator;
	@Autowired
	private MessageSource message;
	@Autowired
    private Configuration configuration;
	@Autowired
    private AdminService adminService;
	@Autowired
	private CommonBean commonbean;

	@RequestMapping(value = {"inputGame.sg"})
	public String inputGame(ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel daulModel = new HadoopModel();
		daulModel.setNavi("admin");

		daulModel.setHadoopGameList(adminService.selectGameList());
		model.addAttribute("model", daulModel);

		return "admin/inputGame";
	}

	@RequestMapping(value = {"registerGame.sg"}, method = RequestMethod.POST)
	public String registerGame(@Valid RegisterGamePara registerGamePara, BindingResult bindingResult, 
			ModelMap model, HttpServletResponse response) throws Exception {

		if (bindingResult.hasErrors()) {
			logger.error("registerGame.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			return "admin/inputGame";
		}

		String strName = registerGamePara.getUpfile().getOriginalFilename();

		if (strName.length() < 1) {
			logger.error("file is null in the UpdateGame, gameId={}", registerGamePara.getGameId());
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {"File"}, LOCALE));
			return "admin/inputGame";
		}

		if(!adminService.insertGame(registerGamePara)) {
			model.addAttribute("errorMessage", message.getMessage("hadoop.system.error.message", null, null));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
	        return "admin/inputGame";
		}

		return "redirect:inputGame.sg";
	}

	@RequestMapping(value = {"editGame.sg"})
	public String editGame(@Valid EditGamePara editGamePara, BindingResult bindingResult, 
			ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();

		hadoopModel.setHadoopGame(adminService.selectGame(editGamePara.getGameId()));
		hadoopModel.setNavi("admin");

		model.addAttribute("model", hadoopModel);

		return "admin/editGame";
	}

	@RequestMapping(value = {"editRegisterGame.sg"}, method = RequestMethod.POST)
	public String editRegisterGame(@Valid RegisterGamePara registerGamePara, BindingResult bindingResult, 
			ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();

		hadoopModel.setNavi("admin");

		if (bindingResult.hasErrors()) {
			logger.error("registerClientId.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));

			hadoopModel.setHadoopGame(adminService.selectGame(registerGamePara.getGameId()));
			model.addAttribute("model", hadoopModel);

			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			return "admin/editGame";
		}

		String strCertificateName = registerGamePara.getUpfile().getOriginalFilename();
		if (strCertificateName.length() < 1) {
			logger.error("file is null in the UpdateGame, gameId={}", registerGamePara.getGameId());
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {"File"}, LOCALE));

			hadoopModel.setHadoopGame(adminService.selectGame(registerGamePara.getGameId()));
			model.addAttribute("model", hadoopModel);

			return "admin/editGame";
		}

		if(!adminService.updateGame(registerGamePara)) {
			model.addAttribute("errorMessage", message.getMessage("hadoop.system.error.message", null, null));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
	        return "admin/editGame";
		}

		return "redirect:getGameList.sg";
	}

	@RequestMapping(value = {"getGameList.sg"})
	public String getGameList(ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setHadoopGameList(adminService.selectGameList());
		hadoopModel.setNavi("admin");

		model.addAttribute("model", hadoopModel);

		return "admin/getGameList";
	}

	@RequestMapping(value = {"inputAdmin.sg"})
	public String inputAdmin(ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();

		hadoopModel.setHadoopGameList(adminService.selectGameList());
		hadoopModel.setNavi("admin");

		model.addAttribute("model", hadoopModel);

		return "admin/inputAdmin";
	}

	@RequestMapping(value = {"registerAdmin.sg"}, method = RequestMethod.POST)
	public String registerAdmin(@Valid RegisterAdminPara registerAdminPara, BindingResult bindingResult, 
			ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();

		if (bindingResult.hasErrors()) {
			logger.error("registerAdmin.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			model.addAttribute("model", hadoopModel);

			// Write the Log file a error
            this.handleValidator(bindingResult.getAllErrors());
    		return "admin/inputAdmin";
		}

		if(!adminService.insertAdmin(registerAdminPara)) {
			model.addAttribute("errorMessage", message.getMessage("hadoop.system.error.message", null, LOCALE));
			model.addAttribute("model", hadoopModel);

			// Write the Log file a error
            this.handleValidator(bindingResult.getAllErrors());
            return "admin/inputAdmin";
		}

		return "redirect:getAdminList.sg";
	}

	@RequestMapping(value = {"editAdmin.sg"})
	public String editAdmin(@Valid EditAdminPara editAdminPara, BindingResult bindingResult, 
			ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();

		if (bindingResult.hasErrors()) {
			logger.error("editAdmin.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));

			model.addAttribute("model", hadoopModel);

			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			return "admin/getAdminList";
		}
		hadoopModel.setHadoopAdmin(adminService.selectAdmin(editAdminPara.getAdminId(), editAdminPara.getGameId()));
		hadoopModel.setNavi("admin");

		model.addAttribute("model", hadoopModel);

		return "admin/editAdmin";
	}

	@RequestMapping(value = {"editRegisterAdmin.sg"}, method = RequestMethod.POST)
	public String editRegisterAdmin(@Valid RegisterAdminPara registerAdminPara, BindingResult bindingResult, 
			ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("admin");
		HadoopAdminModel hadoopAdmin = new HadoopAdminModel();
		hadoopAdmin.setAdminId(registerAdminPara.getAdminId());
		hadoopAdmin.setAdminPwd(registerAdminPara.getAdminPwd());
		hadoopAdmin.setGameId(registerAdminPara.getGameId());
		hadoopAdmin.setAdminName(registerAdminPara.getAdminName());
		hadoopAdmin.setAdminMail(registerAdminPara.getAdminMail());
		hadoopAdmin.setAdminStatusFlag(registerAdminPara.getAdminStatusFlag());
		hadoopModel.setHadoopAdmin(hadoopAdmin);

		if (bindingResult.hasErrors()) {
			logger.error("editRegisterAdmin.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();

			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			model.addAttribute("model", hadoopModel);

			// Write the Log file a error
            this.handleValidator(bindingResult.getAllErrors());
    		return "admin/editAdmin";
		}

		if(!adminService.updateAdmin(registerAdminPara)) {
			logger.error("It is a error in the system.");
			model.addAttribute("errorMessage", message.getMessage("hadoop.system.error.message", null, LOCALE));
			model.addAttribute("model", hadoopModel);

			// Write the Log file a error
            this.handleValidator(bindingResult.getAllErrors());
            return "admin/editAdmin";
		}

		return "redirect:getAdminList.sg";
	}

	@RequestMapping(value = {"getAdminList.sg"})
	public String getAdminList(ModelMap model, HttpServletResponse response) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("admin");

		hadoopModel.setHadoopAdminList(adminService.selectAdminList());

		model.addAttribute("model", hadoopModel);
	
		return "admin/getAdminList";
	}

}