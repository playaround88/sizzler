package com.ai.sizzler.scan.url;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractImporter;

public class UrlImporter extends AbstractImporter{
	private static Logger LOG = LoggerFactory.getLogger(UrlImporter.class);
	private String loadUri;
	private String lockUri;
	private UrlDataSource ds;
	private HttpClient client;

	@Override
	public void load(Map map) {
		super.load(map);
		
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setLoadUri((String)prop.get("load_uri"));
		setLockUri((String)prop.get("lock_uri"));
		
		this.ds=new UrlDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}

	@Override
	public void init() {
		client=HttpClientBuilder.create().build();
	}

	@Override
	public List<Object> scan(int size) {
		// TODO Auto-generated method stub
		HttpGet get=new HttpGet(ds.getBaseUrl()+loadUri);
		try {
			HttpResponse response=client.execute(get);
			
			int statusCode=response.getStatusLine().getStatusCode();
			if(statusCode<200 || statusCode>=300){
				LOG.debug("URLImporter scan error,http status code:"+statusCode);
				return null;
			}
			HttpEntity entity=response.getEntity();
			String content=EntityUtils.toString(entity);
			get.releaseConnection();
			
			return JsonUtil.fromJson(content, List.class);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int updateState(Object record) {
		HttpPost post=new HttpPost(ds.getBaseUrl()+lockUri);
		try {
			//添加post请求内容
			HttpEntity reqEntity=new StringEntity(JsonUtil.toJson(record));
			post.setEntity(reqEntity);
			//发送请求
			HttpResponse response=client.execute(post);
			//判断请求状态
			int statusCode=response.getStatusLine().getStatusCode();
			if(statusCode<200 || statusCode>=300){
				LOG.debug("URLImporter update state error,http status code:"+statusCode);
				return 0;
			}
			//解析返回
			HttpEntity entity=response.getEntity();
			String content=EntityUtils.toString(entity);
			post.releaseConnection();
			Map result=JsonUtil.fromJson(content, Map.class);
			
			return (Integer)result.get("affect");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public String getLoadUri() {
		return loadUri;
	}

	public void setLoadUri(String loadUri) {
		this.loadUri = loadUri;
	}

	public String getLockUri() {
		return lockUri;
	}

	public void setLockUri(String lockUri) {
		this.lockUri = lockUri;
	}

}
