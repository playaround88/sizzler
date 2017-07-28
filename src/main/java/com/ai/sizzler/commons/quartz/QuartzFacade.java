package com.ai.sizzler.commons.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.domain.CronJobForm;
import com.ai.sizzler.domain.SimpleJobForm;

public class QuartzFacade {
	private static final Logger LOG = LoggerFactory.getLogger(QuartzFacade.class);
	private static QuartzFacade instance = new QuartzFacade();
	private Scheduler scheduler;
	
	public void init(){
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (Exception e) {
			LOG.error("初始化quartz Scheduler异常:", e);
			System.exit(1);
		}
	}

	public static QuartzFacade getInstance() {
		return instance;
	}

	public void shutdown(){
		try {
			if(scheduler!=null){
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void scheduleSimpleJob(SimpleJobForm form) throws SchedulerException {
		JobDetail job = newJob(HttpNotifyJob.class)
				.withIdentity(form.getJobName(), form.getGroup())
				.withDescription(form.getDescription())
				.usingJobData("url", form.getNotify()) //notify url
				.build();

		SimpleScheduleBuilder schedulerBuilder = simpleSchedule()
				.withIntervalInSeconds(form.getIntervalInSeconds())
				.repeatForever();
		Trigger trigger = newTrigger()
				.withIdentity(form.getJobName(), form.getGroup())
				.withDescription(form.getDescription())
				.startNow()
				.withSchedule(schedulerBuilder)
				.build();

		scheduler.scheduleJob(job, trigger);
	}
	
	public void scheduleCronJob(CronJobForm form) throws SchedulerException{
		JobDetail job = newJob(HttpNotifyJob.class)
				.withIdentity(form.getJobName(), form.getGroup())
				.withDescription(form.getDescription())
				.usingJobData("url", form.getNotify()) //notify url
				.build();

		CronTrigger trigger = newTrigger()
			    .withIdentity(form.getJobName(), form.getGroup())
			    .withDescription(form.getDescription())
			    .withSchedule(cronSchedule(form.getCronExpression()))
			    .forJob(form.getJobName(), form.getGroup())
			    .build();
		
		scheduler.scheduleJob(job, trigger);
	}
	
	public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException{
		return scheduler.unscheduleJob(triggerKey);
	}
	
	public void triggerJob(JobKey jobKey) throws SchedulerException{
		scheduler.triggerJob(jobKey);
	}
	
	public void pauseJob(JobKey jobKey) throws SchedulerException{
		scheduler.pauseJob(jobKey);
	}
	
	public void resumeJob(JobKey jobKey) throws SchedulerException{
		scheduler.resumeJob(jobKey);
	}
	
	public TriggerState getTriggerState(TriggerKey triggerKey)
	        throws SchedulerException{
		return scheduler.getTriggerState(triggerKey);
	}
}
