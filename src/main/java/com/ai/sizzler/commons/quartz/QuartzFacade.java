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
	private QuartzFacade instance = new QuartzFacade();
	private Scheduler scheduler;

	private QuartzFacade() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (Exception e) {
			LOG.error("初始化quartz Scheduler异常:", e);
			System.exit(1);
		}
	}

	public QuartzFacade getInstance() {
		return this.instance;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// 关闭
		scheduler.shutdown();
	}

	public void scheduleSimpleJob(SimpleJobForm form) throws SchedulerException {
		JobDetail job = newJob(HttpNotifyJob.class)
				.withIdentity(form.getJobId(), form.getGroup())
				.withDescription("")
				.usingJobData("url", "") //notify url
				.build();

		SimpleScheduleBuilder schedulerBuilder = simpleSchedule()
				.withIntervalInSeconds(form.getIntervalInSeconds());
		if (form.isRepeat()) {
			schedulerBuilder.repeatForever();
		}
		Trigger trigger = newTrigger()
				.withIdentity(form.getJobId(), form.getGroup())
				.startNow()
				.withSchedule(schedulerBuilder)
				.build();

		scheduler.scheduleJob(job, trigger);
	}
	
	public void scheduleCronJob(CronJobForm form) throws SchedulerException{
		JobDetail job = newJob(HttpNotifyJob.class)
				.withIdentity(form.getJobId(), form.getGroup())
				.usingJobData("url", "") //notify url
				.build();

		CronTrigger trigger = newTrigger()
			    .withIdentity(form.getJobId(), form.getGroup())
			    .withSchedule(cronSchedule(form.getCronExpression()))
			    .forJob(form.getJobId(), form.getGroup())
			    .build();
		
		scheduler.scheduleJob(job, trigger);
	}
	
	public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException{
		return scheduler.unscheduleJob(triggerKey);
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
