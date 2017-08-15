package com.ai.sizzler.controller;

import java.util.HashMap;
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
import com.ai.sizzler.scan.Task;
import com.ai.sizzler.scan.TaskFactory;
import com.ai.sizzler.service.IScanTaskService;

@Controller
@RequestMapping("/scan/task")
public class ScanTaskController {
	private static Logger LOG=LoggerFactory.getLogger(ScanTaskController.class);
	private IScanTaskService service;
	private TaskFactory taskFactory;
	
	@Autowired
	public void setService(IScanTaskService service) {
		this.service = service;
	}
	
	@Autowired
	public void setTaskFactory(TaskFactory taskFactory) {
		this.taskFactory = taskFactory;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(Task task,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			taskFactory.add(task);
			
			result.put("success", true);
			result.put("message", "保存成功");
		}catch (Exception e) {
			LOG.error("保存任务异常:",e);
			result.put("success", false);
			result.put("message", "保存任务异常:"+e.getMessage());
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
			taskFactory.del(Long.parseLong(id));
			
			result.put("success", true);
			result.put("message", "删除成功");
		}catch (Exception e) {
			LOG.error("删除任务异常:",e);
			result.put("success", false);
			result.put("message", "删除任务异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/toggle")
	@ResponseBody
	public Object updateState(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		String id=request.getParameter("id");
		String state=request.getParameter("state");
		try{
			if("RUNNING".equals(state)){
				taskFactory.resume(Long.parseLong(id));
			}else{
				taskFactory.pause(Long.parseLong(id));
			}
			
			result.put("success", true);
			result.put("message", "操作成功");
		}catch (Exception e) {
			LOG.error("更改任务状态异常:",e);
			result.put("success", false);
			result.put("message", "更改任务状态异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Object list(HttpServletRequest request){
		HashMap params=new HashMap();
		PagerUtils.buildPageParamEasyui(request, params);
		// 查询列表
		PagedList<Task> pList=service.selectPagedList(params);
		return PagerUtils.buildResultBs(pList);
	}
}
