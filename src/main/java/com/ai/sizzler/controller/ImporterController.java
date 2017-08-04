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
import com.ai.sizzler.domain.ImporterForm;
import com.ai.sizzler.service.IImporterService;

@Controller
@RequestMapping("/scan/importer")
public class ImporterController {
	private static Logger LOG=LoggerFactory.getLogger(ImporterController.class);
	private IImporterService service;
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(ImporterForm imp,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			if(imp.getId()!=0){
				// 更新
				service.update(imp);
			}else{
				// 新增
				service.insert(imp);
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
		String id=request.getParameter("id");
		try{
			// 删除数据源
			service.del(Long.parseLong(id));
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
		HashMap params=new HashMap();
		PagerUtils.buildPageParamEasyui(request, params);
		// 查询列表
		PagedList<Map> pList=service.selectPagedList(params);
		return PagerUtils.buildResultBs(pList);
	}
}
