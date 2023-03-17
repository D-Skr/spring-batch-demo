package com.example.service;

import com.example.request.JobParamsRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
    public void startJob(String jobName, List<JobParamsRequest> jobParamsRequestList){
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        jobParamsRequestList.stream().forEach(jobParamReq -> {
            params.put(jobParamReq.getParamKey(), new JobParameter((jobParamReq.getParamValue())));
        });

        JobParameters jobParameters = new JobParameters(params);
        try {
            JobExecution jobExecution = null;
            if (jobName.equals("First job")) {
                jobExecution = jobLauncher.run(firstJob, jobParameters);
            } else {
                jobExecution = jobLauncher.run(secondJob, jobParameters);
            }
            System.out.println("jobExecution ID = " + jobExecution.getId());
        } catch (Exception e){
            System.out.println("Exception while running a job");
        }
    }
}
