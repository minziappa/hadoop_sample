package io.sample.controller;

import io.sample.bean.model.HadoopModel;
import io.sample.service.AdminService;
import io.sample.service.HadoopService;
import io.sample.service.HbaseService;
import io.sample.service.MapReduceService;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/real")
public class RealHadoopController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(RealHadoopController.class);

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

	@RequestMapping(value = {"webSocket.sg"})
	public String webSocket(ModelMap model) throws Exception {
		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("hadoop");

		model.addAttribute("model", hadoopModel);

		return "real/webSocket";
	}

	@RequestMapping(value = {"sockJsSocket.sg"})
	public String sockJsSocket(ModelMap model) throws Exception {
		HadoopModel hadoopModel = new HadoopModel();
		hadoopModel.setNavi("hadoop");
		hadoopModel.setCharge(adminService.getChargeSum());

		model.addAttribute("model", hadoopModel);

		return "real/sockJsSocket";
	}

	@RequestMapping(value = {"test.sg"})
	public void test(@RequestBody String body, HttpServletResponse response) throws Exception {

		logger.info("Get the json : " + body);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.write("{\"aaa\":\"ddd\"}");
		pw.flush();
		pw.close();

		logger.info("Ajax - end");

	}

}