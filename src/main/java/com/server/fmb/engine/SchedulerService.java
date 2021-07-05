package com.server.fmb.engine;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    private TaskScheduler scheduler;

    public SchedulerService(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Async
    public ScheduledFuture<?> start(ScenarioInstance scenarioInstance, String cron) {
        ScheduledFuture<?> future = this.scheduler.schedule(() -> {
                    System.out.println("run at " + LocalDateTime.now() + " // " + scenarioInstance.getInstanceName());
                    scenarioInstance.run();
                },
                new CronTrigger(cron));
        return future;
    }
//
//    public void changeCron(String cron) {
//    if (future != null) future.cancel(true);
//    this.future = null;
//    this.cron = cron;
//    this.start();
//    }

}
