package com.ai.sizzler.scan.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractImporter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class RedisImporter extends AbstractImporter{
	private static Logger LOG=LoggerFactory.getLogger(RedisImporter.class);
	private RedisDataSource ds;
	private String queue;
	private Jedis jedis;

	@Override
	public void load(Map map) {
		super.load(map);
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setQueue((String)prop.get("queue"));
		
		this.ds=new RedisDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}

	@Override
	public void init() {
		jedis=new Jedis(ds.getHost(),ds.getPort());
		if(ds.getPassword()!=null && !"".equals(ds.getPassword())){
			jedis.auth(ds.getPassword());
		}
	}

	@Override
	public void destroy() {
		if(jedis!=null){
			jedis.close();
			jedis=null;
		}
	}

	@Override
	public List<Object> scan(int size) {
		if(!jedis.isConnected()){
			jedis.connect();
		}
		List<Object> result = null;
		try {
			Pipeline pipe = jedis.pipelined();
			for (int i = 0; i < size; i++) {
				pipe.rpop(queue);
			}
			result = pipe.syncAndReturnAll();
		} catch (Exception e) {
			LOG.error("获取数据异常:",e);
		} finally {
			if(jedis!=null){
				jedis.disconnect();
			}
		}
		return result;
	}

	@Override
	public int updateState(Object record) {
		return 1;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

}
