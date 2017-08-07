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
import com.ai.sizzler.service.IScanTaskService;

@Controller
@RequestMapping("/scan/task")
public class ScanTaskController {
	private static Logger LOG=LoggerFactory.getLogger(ScanTaskController.class);
	private IScanTaskService service;
	
	@Autowired
	public void setService(IScanTaskService service) {
		this.service = service;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(Task task,HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			if(task.getId()!=0){
				// 更新
				service.update(task);
			}else{
				// 新增
				service.insert(task);
			}
			//重新加载task，会连带加载导入导出
//			task=service.selectById(task.getId());
//			
//			if("RUNNING".equals(task.getState())){
//				TaskFactory.getInstance().run(task);
//			}else if("PAUSED".equals(task.getState())){
//				TaskFactory.getInstance().pause(task.getId());
//			}
			
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
			service.del(Long.parseLong(id));
			
			//TaskFactory.getInstance().remove(Long.parseLong(id));
			
			result.put("success", true);
			result.put("message", "删除成功");
		}catch (Exception e) {
			LOG.error("保存数据源异常:",e);
			result.put("success", false);
			result.put("message", "删除任务异常:"+e.getMessage());
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
