package com.ai.sizzler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.commons.pager.PagedList;
import com.ai.commons.pager.PagerUtils;
import com.ai.sizzler.domain.ExporterForm;
import com.ai.sizzler.service.IExporterService;

@Controller
@RequestMapping("/scan/exporter")
public class ExporterController {
	private static Logger LOG=LoggerFactory.getLogger(ExporterController.class);
	private IExporterService service;
	@RequestMapping("/save")
	@ResponseBody
	public Object save(ExporterForm exp,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			if(exp.getId()!=0){
				//TODO 更新
				service.update(exp);
			}else{
				//TODO 新增
				service.insert(exp);
			}
			result.put("success", true);
			result.put("message", "保存成功");
		}catch (Exception e) {
			LOG.error("保存数据源异常:",e);
			result.put("success", false);
			result.put("message", "保存数据源异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public Object del(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		String dsId=request.getParameter("id");
		try{
			//TODO 删除数据源
			
			result.put("success", true);
			result.put("message", "删除成功");
		}catch (Exception e) {
			LOG.error("保存数据源异常:",e);
			result.put("success", false);
			result.put("message", "删除数据源异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Object list(HttpServletRequest request){
		Map params=new HashMap();
		PagerUtils.buildPageParamEasyui(request, params);
		//TODO 查询列表
		PagedList<Map> pList=null; //qrtzService.queryTaskList(params);
		return PagerUtils.buildResultBs(pList);
	}
}
