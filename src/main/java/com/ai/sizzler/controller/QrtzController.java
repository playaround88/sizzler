package com.ai.sizzler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.commons.pager.PagedList;
import com.ai.commons.pager.PagerUtils;
import com.ai.sizzler.commons.quartz.QuartzFacade;
import com.ai.sizzler.domain.CronJobForm;
import com.ai.sizzler.domain.SimpleJobForm;
import com.ai.sizzler.service.IQrtzService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/qrtz")
public class QrtzController {
	private static Logger LOG = LoggerFactory.getLogger(QrtzController.class);
	private IQrtzService qrtzService;
	
	@Autowired
	public void setQrtzService(IQrtzService qrtzService) {
		this.qrtzService = qrtzService;
	}

	@RequestMapping("/task/list")
	@ResponseBody
	public Object taskList(HttpServletRequest request){
		Map params=new HashMap();
		PagerUtils.buildPageParamEasyui(request, params);
		
		PagedList<Map> pList=qrtzService.queryTaskList(params);
		return PagerUtils.buildResultBs(pList);
	}
	
	@RequestMapping(value="/task/add/simple",method=RequestMethod.POST)
	@ResponseBody
	public Object taskAddSimple(SimpleJobForm sjf,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			QuartzFacade.getInstance().scheduleSimpleJob(sjf);
			result.put("success", true);
			result.put("message", "定时任务已提交运行！");
		}catch (Exception e) {
			LOG.error("运行定时任务报错",e);
			
			result.put("success", false);
			result.put("message", "运行定时任务报错:"+e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/task/add/cron",method=RequestMethod.POST)
	@ResponseBody
	public Object taskAddCron(CronJobForm cjf,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			QuartzFacade.getInstance().scheduleCronJob(cjf);
			result.put("success", true);
			result.put("message", "定时任务已提交运行！");
		}catch (Exception e) {
			LOG.error("运行定时任务报错",e);
			
			result.put("success", false);
			result.put("message", "运行定时任务报错:"+e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping("/task/unschedule")
	@ResponseBody
	public Object unschedule(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		String name=request.getParameter("jobName");
		String group=request.getParameter("group");
		TriggerKey triggerKey=new TriggerKey(name, group);
		try{
			QuartzFacade.getInstance().unscheduleJob(triggerKey);
			result.put("success", true);
			result.put("message", "操作成功");
		}catch (Exception e) {
			LOG.error("取消定时任务失败:",e);
			result.put("success", false);
			result.put("message", "取消定时任务失败:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/task/trigger")
	@ResponseBody
	public Object triggerJob(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		String name=request.getParameter("jobName");
		String group=request.getParameter("group");
		JobKey jobKey=new JobKey(name, group);
		try{
			QuartzFacade.getInstance().triggerJob(jobKey);
			result.put("success", true);
			result.put("message", "操作成功");
		}catch (Exception e) {
			LOG.error("触发任务失败:",e);
			result.put("success", false);
			result.put("message", "触发任务失败:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/task/pause")
	@ResponseBody
	public Object pauseJob(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		String name=request.getParameter("jobName");
		String group=request.getParameter("group");
		JobKey jobKey=new JobKey(name, group);
		try{
			QuartzFacade.getInstance().pauseJob(jobKey);
			result.put("success", true);
			result.put("message", "操作成功");
		}catch (Exception e) {
			LOG.error("暂停定时任务失败:",e);
			result.put("success", false);
			result.put("message", "暂停定时任务失败:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/task/resume")
	@ResponseBody
	public Object resumeJob(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		String name=request.getParameter("jobName");
		String group=request.getParameter("group");
		JobKey jobKey=new JobKey(name, group);
		try{
			QuartzFacade.getInstance().resumeJob(jobKey);
			result.put("success", true);
			result.put("message", "操作成功");
		}catch (Exception e) {
			LOG.error("恢复定时任务失败:",e);
			result.put("success", false);
			result.put("message", "恢复定时任务失败:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/task/log")
	public Object taskLog(){
		return null;
	}
}
