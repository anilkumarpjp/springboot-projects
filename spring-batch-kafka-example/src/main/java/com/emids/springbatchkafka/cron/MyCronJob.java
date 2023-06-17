package com.emids.springbatchkafka.cron;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyCronJob {

    @Autowired
    @Qualifier("processEmployeeData")
    private Job cronJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron="${cronExpression}")
    public void executeCronJob() throws Exception{
        JobParameters parameters = new JobParametersBuilder()
                .addString("Company", "Tech")
                .addString("Time", String.valueOf(System.currentTimeMillis())).toJobParameters();

        jobLauncher.run(cronJob,parameters);
    }

}
