package com.qyxy.record.quartz;

import com.qyxy.record.service.IRemindDateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by luxiaoyong on 2018/3/3.
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean(name = "jobDetail")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(IRemindDateService remindDateService) {// ScheduleTask为需要执行的任务
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        /*
         *  是否并发执行
         *  例如每5s执行一次任务，但是当前任务还没有执行完，就已经过了5s了，
         *  如果此处为true，则下一个任务会执行，如果此处为false，则下一个任务会等待上一个任务执行完后，再开始执行
         */
        jobDetail.setConcurrent(false);

        jobDetail.setName("remind-date");// 设置任务的名字
        jobDetail.setGroup("remind-date");// 设置任务的分组，这些属性都可以存储在数据库中，在多任务的时候使用

        jobDetail.setTargetObject(remindDateService);

        jobDetail.setTargetMethod("remindReadyDatas");
        return jobDetail;
    }

    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean cronJobTrigger(MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetail.getObject());
        tigger.setCronExpression("0/60 * * * * ?");// 初始时的cron表达式
        tigger.setName("remind-date");// trigger的name
        return tigger;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(CronTriggerFactoryBean cronJobTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        bean.setAutoStartup(true);
        // 注册触发器
        bean.setTriggers(cronJobTrigger.getObject());
        return bean;
    }
}
