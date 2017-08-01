package com.ai.sizzler.scan.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractExporter;

import redis.clients.jedis.Jedis;

public class RedisExporter extends AbstractExporter{
	private static Logger LOG=LoggerFactory.getLogger(RedisExporter.class);
	private String queue;
	private RedisDataSource ds;
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
		jedis=new Jedis(ds.getHost(), ds.getPort());
		if(ds.getPassword()!=null && !"".equals(ds.getPassword())){
			jedis.auth(ds.getPassword());
		}
	}

	@Override
	public void deal(Object record) {
		if(!jedis.isConnected()){
			jedis.connect();
		}
		jedis.lpush(queue, JsonUtil.toJson(record));
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

}
