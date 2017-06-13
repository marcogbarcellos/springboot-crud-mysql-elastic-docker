package com.userCrud.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(){
        logger.info("Redirecting to Swagger Documentation page");
        return "redirect:swagger-ui.html";
	}
}