package com.ai.sizzler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.commons.pager.PagedList;
import com.ai.commons.pager.PagerUtils;
import com.ai.sizzler.domain.DataSourceForm;
import com.ai.sizzler.service.IDataSourceService;

@Controller
@RequestMapping("/scan/ds")
public class DsController {
	private static Logger LOG=LoggerFactory.getLogger(DsController.class);
	private IDataSourceService service;
	
	@Autowired
	public void setService(IDataSourceService service) {
		this.service = service;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(DataSourceForm ds,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			if(ds.getId()!=0){
				service.update(ds);
			}else{
				service.insert(ds);
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
			//删除数据源
			service.del(Long.parseLong(dsId));
			result.put("success", true);
			result.put("message", "删除成功");
		}catch (Exception e) {
			LOG.error("保存数据源异常:",e);
			result.put("success", false);
			result.put("message", "删除数据源异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/listpage")
	@ResponseBody
	public Object listPage(HttpServletRequest request){
		HashMap params=new HashMap();
		PagerUtils.buildPageParamEasyui(request, params);
		// 查询列表
		PagedList<Map> pList=service.selectPagedList(params);
		return PagerUtils.buildResultBs(pList);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Object list(HttpServletRequest request){
		HashMap params=new HashMap();
		// 查询列表
		List<Map> list=service.selectList(params);
		return list;
	}
}
