package com.ai.sizzler.commons.quartz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class HttpNotifyJob implements Job{
	private static final Logger LOG=LoggerFactory.getLogger(HttpNotifyJob.class);
	private static final Gson gson = new Gson();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String url=context.getJobDetail().getJobDataMap().getString("url");
		JobKey jobkey=context.getJobDetail().getKey();
		
		Map<String,String> log=new HashMap<String,String>();
		log.put("notify", url);
		log.put("jobName", jobkey.getName());
		log.put("jobGroup", jobkey.getGroup());
		
		HttpClient client=HttpClientBuilder.create().build();
		HttpGet get=new HttpGet(url);
		try {
			HttpResponse response=client.execute(get);
			
			int statusCode=response.getStatusLine().getStatusCode();
			get.releaseConnection();
			
			//TODO 持久化执行日志
			log.put("statusCode", statusCode+"");
			System.out.println(gson.toJson(log));
			LOG.info(gson.toJson(log));
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			get.releaseConnection();
		}
	}

}
