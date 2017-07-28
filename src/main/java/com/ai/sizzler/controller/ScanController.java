package com.ai.sizzler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scan")
public class ScanController {
	
	@RequestMapping("/group")
	public Object group(){
		return null;
	}
	
	@RequestMapping("/importer")
	public Object importer(){
		return null;
	}
	
	@RequestMapping("/exporter")
	public Object exporter(){
		return null;
	}
	
	public Object datasource(){
		return null;
	}
	
	@RequestMapping("/task")
	public Object task(){
		return null;
	}
	
	@RequestMapping("/task/log")
	public Object taskLog(){
		return null;
	}
}
