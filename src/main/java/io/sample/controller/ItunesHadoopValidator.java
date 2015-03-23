package io.sample.controller;

import io.sample.bean.para.SampleAppPara;
import io.sample.utility.DateUtility;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItunesHadoopValidator implements Validator {

	final Logger logger = LoggerFactory.getLogger(ItunesHadoopValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		if(SampleAppPara.class.equals(clazz)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void validate(Object object, Errors errors) {
		if (object instanceof SampleAppPara) {
			SampleAppPara sampleAppPara = (SampleAppPara) object;

	        if(sampleAppPara != null) {
	        	// Check a AmebaId.
	    		if(sampleAppPara.getStartdate() != null && sampleAppPara.getStartdate().length() > 7){
    				String startYear = sampleAppPara.getStartdate().substring(0, 4);
    				String startMonth = sampleAppPara.getStartdate().substring(4, 6);
    				String startDay = sampleAppPara.getStartdate().substring(6, 8);
    				Date startDate = DateUtility.getStartToday(Integer.parseInt(startYear), Integer.parseInt(startMonth), Integer.parseInt(startDay), 0);

    				Date endDate = null;
    				if(sampleAppPara.getEnddate() == null || sampleAppPara.getEnddate().length() < 7) {
    					endDate = DateUtility.getNowTime();
    				} else {
        				String endYear = sampleAppPara.getEnddate().substring(0, 4);
        				String endMonth = sampleAppPara.getEnddate().substring(4, 6);
        				String endDay = sampleAppPara.getEnddate().substring(6, 8);
        				endDate = DateUtility.getStartToday(Integer.parseInt(endYear), Integer.parseInt(endMonth), Integer.parseInt(endDay), 0);
        				if(DateUtility.getNowTime().before(endDate)) {
        					logger.warn("the end date is in future.");
        					endDate = DateUtility.getNowTime();
        				}
    				}

    				if(DateUtility.getNowTime().before(startDate)) {
    					errors.rejectValue("startdate", "hadoop.parameter.error.message");
    					logger.warn("the start date is error.");
    				}

    				if(endDate.before(startDate) || endDate.equals(startDate)) {
    					errors.rejectValue("startdate", "hadoop.parameter.error.message");
    					errors.rejectValue("enddate", "hadoop.parameter.error.message");
    					logger.warn("the start date and the end date is error. start=" + startDate + ", end=" + endDate);
    				}
    				sampleAppPara.setStartDate(startDate);
    				sampleAppPara.setEndDate(endDate);
	    		}
	        }
		}
	}

}
