package com.example.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;
    @Async
    public void srartJob(String jobName){
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(params);
        try {
            if (jobName.equals("First job")) {
                jobLauncher.run(firstJob, jobParameters);
            } else {
                jobLauncher.run(secondJob, jobParameters);
            }
        } catch (Exception e){
            System.out.println("Exception while running a job");
        }
    }
}
