package com.ai.sizzler.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	private static final Logger LOG=LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping("/")
	public String index(HttpServletRequest request){
		LOG.info("access index");
		
		request.setAttribute("title", "hello world");
		
		return "index";
	}

}
