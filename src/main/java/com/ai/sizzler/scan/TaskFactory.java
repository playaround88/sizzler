package com.ai.sizzler.scan;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class TaskFactory {
	private static Map<Long,Task> runingHolder=new HashMap<Long,Task>();
	
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	/**
	 * 启动taskfactory，启动所有为启动状态的任务
	 * @throws ScanTaskException 
	 */
	public void start() throws ScanTaskException{
		//TODO 数据库查询所有运行中的task
		List<Task> tasks=null;
		//TODO 启动所有状态为运行中的task
		for(Task task : tasks){
			startTask(task);
		}
	}
	/**
	 * 关闭taskfactory，关闭所有启动中的任务
	 */
	public void shutdown(){
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
	
	public void run(Task task) throws ScanTaskException{
		if(task.getId()!=0){
			//重复运行的情况 
			Task tmp=runingHolder.get(task.getId());
			if(tmp!=null){
				return ;
			}
		}else{
			//TODO 持久化任务
			
			//启动任务
			startTask(task);
		}
	}
	
	public void pause(long id){
		runingHolder.get(id).shutdown();
		runingHolder.remove(id);
		//TODO 数据库修改状态
		
	}
	
	public void resume(long id) throws ScanTaskException{
		//TODO 数据库查询任务
		Task task=null;
		//启动任务
		startTask(task);
		//TOTO 数据库修改任务状态
	}
	
	public void remove(long id){
		Task task=runingHolder.get(id);
		if(task!=null){
			task.shutdown();
		}
		//TODO 数据库删除任务记录
	}
	
}
