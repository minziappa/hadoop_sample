package io.sample.controller;

import io.sample.bean.model.HadoopAdminModel;
import io.sample.bean.model.HadoopModel;
import io.sample.bean.model.HbaseModel;
import io.sample.bean.para.SampleAppPara;
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

@Controller
@RequestMapping("/sp")
public class SpItunesController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(SpItunesController.class);

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

			return "sp/sampleApp";
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

		return "sp/sampleApp";
	}

	@RequestMapping(value = {"realTime.sg"})
	public String webSocket(ModelMap model) throws Exception {

		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("itunes");

		model.addAttribute("model", hadoopModel);

		return "sp/realTime";
	}

}