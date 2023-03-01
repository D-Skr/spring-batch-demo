package com.pluralsight.springbatch.patientbatchloader.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class BatchConfiguration implements BatchConfigurer {

    private JobRepositoryjobRepository;
    private JobExplorerjobExplorer;
    private JobLauncherjobLauncher;
}
