package com.ai.sizzler.scan.url;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractExporter;

public class UrlExporter extends AbstractExporter{
	private static Logger LOG = LoggerFactory.getLogger(UrlExporter.class);
	private String dealUri;
	private UrlDataSource ds;
	private HttpClient client;

	@Override
	public void load(Map map) {
		super.load(map);
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setDealUri((String)prop.get("dealUri"));
		
		this.ds=new UrlDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}
	
	@Override
	public void init() {
		LOG.debug("init UrlExporter");
		client=HttpClientBuilder.create().build();
	}

	@Override
	public void destroy() {
		this.client=null;
	}

	@Override
	public void deal(Object record) {
		String jsonStr=JsonUtil.toJson(record);
		LOG.debug("urlExport deal record:"+jsonStr);
		HttpPost post=new HttpPost(ds.getBaseUrl()+this.dealUri);
		try {
			//添加post请求内容
			HttpEntity reqEntity=new StringEntity(JsonUtil.toJson(record));
			post.setEntity(reqEntity);
			
			HttpResponse response=client.execute(post);
			
			int statusCode=response.getStatusLine().getStatusCode();
			if(statusCode<200 || statusCode>=300){
				LOG.debug("URLExporter deal error,http status code:"+statusCode);
			}else{
				LOG.debug("URLExporter deal success！"+statusCode);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			post.releaseConnection();
		}
		
		return;
	}

	public String getDealUri() {
		return dealUri;
	}

	public void setDealUri(String dealUri) {
		this.dealUri = dealUri;
	}
	
}
