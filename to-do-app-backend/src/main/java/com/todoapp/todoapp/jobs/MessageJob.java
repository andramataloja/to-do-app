package com.todoapp.todoapp.jobs;

import com.todoapp.todoapp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class MessageJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MessageService messageService;
    @Override
    public void execute(JobExecutionContext context) {
        logger.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        messageService.executeSendMessage();
        logger.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
