package io.sample.controller;

import io.sample.bean.model.HbaseModel;
import io.sample.bean.para.HbaseCreatePara;
import io.sample.bean.para.HbaseDeletePara;
import io.sample.bean.para.HbaseSearchPara;
import io.sample.service.AdminService;
import io.sample.service.HadoopService;
import io.sample.service.HbaseService;
import io.sample.service.MapReduceService;

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
@RequestMapping("/hadoop")
public class HadoopController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(HadoopController.class);

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

	@RequestMapping(value = {"hbaseScan.sg"})
	public String hbaseScan(@Valid HbaseSearchPara hbaseSearchPara, 
			BindingResult bindingResult, ModelMap model, HttpSession session) throws Exception {
		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("hadoop");

		if (bindingResult.hasErrors()) {
			logger.warn("hbaseScan.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return "hbase/hbaseScan";
		}

		if(hbaseSearchPara.getRowKey() == null || hbaseSearchPara.getRowKey().length() < 1) {
			logger.info("1 >>> App search >>>>> id is " + hbaseSearchPara.getRowKey());
			// Only with table
			hbaseModel.setHbaseResultList(hbaseService.selectScan(hbaseSearchPara.getTableName()));	
		} else if((hbaseSearchPara.getRowKey() != null || hbaseSearchPara.getRowKey().length() > 1) 
				&& (hbaseSearchPara.getFamily() != null && hbaseSearchPara.getFamily().length() > 1)) {

			logger.info("2 >>> App search >>>>> id is " + hbaseSearchPara.getRowKey());
			// Table and Family
			hbaseModel.setHbaseResultList(hbaseService.selectScanColumnFamily(hbaseSearchPara.getTableName(), 
					hbaseSearchPara.getRowKey(), hbaseSearchPara.getFamily()));
		} else {
			logger.info("3 >>> App search >>>>> id is " + hbaseSearchPara.getRowKey());
			// Table and ROWKEY
			hbaseModel.setHbaseResultList(hbaseService.selectRow(hbaseSearchPara.getTableName(), hbaseSearchPara.getRowKey()));
		}

		// Set the parameters
		hbaseModel.setHbaseSearchPara(hbaseSearchPara);

		model.addAttribute("model", hbaseModel);

		return "hbase/hbaseScan";
	}

	@RequestMapping(value = {"hbaseDelete.sg"})
	public String hbaseDelete(@Valid HbaseDeletePara hbaseDeletePara, 
			BindingResult bindingResult, ModelMap model, HttpSession session) throws Exception {

		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("hadoop");

		if (bindingResult.hasErrors()) {
			logger.warn("hbaseDelete.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return "hbase/hbaseDelete";
		}

//		if(hbaseDeletePara.getRowKey() == null || hbaseDeletePara.getRowKey().length() < 1) {
//			hbaseModel.setBln(hbaseService.deleteTable(hbaseDeletePara.getTableName()));
//		} else {
//			hbaseModel.setBln(hbaseService.deleteRow(hbaseDeletePara.getTableName(), hbaseDeletePara.getRowKey()));
//		}

		model.addAttribute("model", hbaseModel);

		return "hbase/hbaseDelete";
	}

	@RequestMapping(value = {"hbaseCreate.sg"})
	public String hbaseCreate(@Valid HbaseCreatePara hbaseCreatePara, 
			BindingResult bindingResult, ModelMap model, HttpSession session) throws Exception {

		HbaseModel hbaseModel = new HbaseModel();
		hbaseModel.setNavi("hadoop");

		if (bindingResult.hasErrors()) {
			logger.warn("hbaseCreate.sg >>>> it is occured a parameter error.");
			String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
			model.addAttribute("errorMessage", message.getMessage("hadoop.parameter.error.message", new String[] {defaultMessage}, LOCALE));
			// Write the Log file a error
	        this.handleValidator(bindingResult.getAllErrors());
			model.addAttribute("model", hbaseModel);

			return "hbase/hbaseCreate";
		}

		String[] multiCf = hbaseCreatePara.getColumnFamily().split(" ");

		hbaseModel.setBln(hbaseService.createTableColumn(hbaseCreatePara.getTableName(), multiCf));	

		model.addAttribute("model", hbaseModel);

		return "hbase/hbaseCreate";
	}

}