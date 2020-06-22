package com.todoapp.todoapp.config;

import com.todoapp.todoapp.jobs.MessageJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_MON_TEN_AM = "0 0 10 ? * MON *";

    @Bean(name = "message")
    public JobDetailFactoryBean jobMemberClassStats() {
        return QuartzConfig.createJobDetail(MessageJob.class, "Send message Job");
    }

    @Bean(name = "messageTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("message") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_MON_TEN_AM, "Send message Trigger");
    }

}
