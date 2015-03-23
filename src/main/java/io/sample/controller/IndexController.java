package io.sample.controller;

import javax.servlet.http.HttpSession;
import io.sample.bean.para.UserPara;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("session")
public class IndexController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value="/{sample}", method=RequestMethod.GET)
	public String index(@PathVariable String sample, ModelMap model) throws Exception {

    	String returnUrl = "index";

    	if(sample == null) {
    		logger.info("this is null");
    	}

    	if(sample.equals("sample")) {
    		returnUrl = "index";
    	} else {
    		returnUrl = "sample/" + sample;	
    	}

    	logger.info(">>>" + returnUrl);

    	model.addAttribute("name", "Hello World!");

		return returnUrl;
	}

    @RequestMapping(value="/comfirm")
	public String comfirm(@ModelAttribute UserPara userPara, HttpSession session, ModelMap model) throws Exception {

    	logger.info("Name >>>" + userPara.getName());
    	logger.info("State >>>" + userPara.getState());

    	model.addAttribute("name", userPara.getName());
    	session.setAttribute("cnt", Integer.valueOf(userPara.getCnt()) + 1);

		return "confirm";
	}


}