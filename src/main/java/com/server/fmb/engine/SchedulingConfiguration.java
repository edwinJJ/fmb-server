package com.server.fmb.engine;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulingConfiguration {

	public final static int TASK_SCHEDULE_POOL_SIZE = 100;
	
	@Bean
	public ThreadPoolTaskScheduler schedulerExecutor() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(TASK_SCHEDULE_POOL_SIZE);
		taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		return taskScheduler;
	}
}
