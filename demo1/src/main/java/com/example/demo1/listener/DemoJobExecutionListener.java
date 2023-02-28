package com.example.demo1.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class DemoJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution){
        System.out.println("Before starting the job - Job Name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Before starting the job" + jobExecution.getExecutionContext().toString());
        jobExecution.getExecutionContext().put("my name", "Dmitrii");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("before starting the Job - Job Execution Context" + jobExecution.getExecutionContext().toString());
    }
}
