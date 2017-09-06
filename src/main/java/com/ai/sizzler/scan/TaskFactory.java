package com.ai.sizzler.scan;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.service.IScanTaskService;

public class TaskFactory {
	private static Logger LOG=LoggerFactory.getLogger(TaskFactory.class);
	private static ConcurrentHashMap<Long,Task> runingHolder=new ConcurrentHashMap<Long,Task>();
	private IScanTaskService service;
	
	public void setService(IScanTaskService service) {
		this.service = service;
	}
	
	/**
	 * 启动taskfactory，启动所有为启动状态的任务
	 * @throws ScanTaskException 
	 */
	public void init() throws ScanTaskException{
		LOG.debug("init scan task factory");
		HashMap params=new HashMap();
		params.put("state", "RUNNING");
		// 数据库查询所有运行中的task
		List<Task> tasks=service.selectList(params);
		// 启动所有状态为运行中的task
		for(Task task : tasks){
			startTask(task);
		}
	}
	
	/**
	 * 关闭taskfactory，关闭所有启动中的任务
	 */
	public void destroy(){
		LOG.debug("destroy scan task factory");
		Iterator<Task> it=runingHolder.values().iterator();
		while(it.hasNext()){
			Task t=it.next();
			t.shutdown();
		}
	}
	
	private void startTask(Task t) throws ScanTaskException{
		t.init();
		t.start();
		runingHolder.put(t.getId(), t);
	}
	
	/**
	 * 增加一个扫描任务、更新一个扫描任务
	 * @param task
	 * @throws ScanTaskException
	 */
	public void add(Task task) throws ScanTaskException{
		if(task.getId()!=0){
			// 更新数据库
			service.update(task);
			// 如已经在运行，先停掉，重新启动
			Task tmp=runingHolder.get(task.getId());
			if(tmp!=null){
				tmp.shutdown();
			}
		}else{
			// 新增数据库记录
			service.insert(task);
		}
		//重新加载task，会连带加载导入导出
		task=service.selectById(task.getId());
		
		startTask(task);
	}
	/**
	 * 删除一个扫描任务
	 * @param id
	 */
	public void del(long id){
		synchronized (runingHolder) {
			Task task=runingHolder.get(id);
			if(task!=null){
				task.shutdown();
			}
		}
		// 数据库删除任务记录
		service.del(id);
	}
	/**
	 * 暂停一个扫描任务
	 * @param id
	 */
	public void pause(long id){
		Task task=null;
		synchronized (runingHolder) {
			task=runingHolder.get(id);
			if(task!=null){
				task.shutdown();
				runingHolder.remove(id);
			}
		}
		if(task!=null){
			// 数据库修改状态
			task.setState("PAUSED");
			service.update(task);
		}
	}
	/**
	 * 恢复一个扫描任务
	 * @param id
	 * @throws ScanTaskException
	 */
	public void resume(long id) throws ScanTaskException{
		// 数据库查询任务
		Task task=service.selectById(id);
		if(task!=null){
			task.setState("RUNNING");
			//启动任务
			startTask(task);
			// 数据库修改任务状态
			service.update(task);
		}
	}
	
}
