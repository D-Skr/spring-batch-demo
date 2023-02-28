package com.example.demo1.config;

import com.example.demo1.listener.DemoJobExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    @Autowired(required=false)
    private JobBuilderFactory jobs;

    @Autowired(required=false)
    private StepBuilderFactory steps;

    @Autowired
    private DemoJobExecutionListener demoListener;

    private Tasklet demoTasklet() {
        return (new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello Batch World");
                return RepeatStatus.FINISHED;
            }
        });
    }

    @Bean
    public Job demoJob(){
        return jobs.get("demoJob")
                .listener(demoListener)
                .start(step1())
                .build();
//            return new JobBuilder("demoJob")
//                    .start(step1())
//                    .build();
    }
    @Bean
    public Step step1(){
        return steps.get("step1")
                .tasklet(demoTasklet())
                .build();
//    public Step step1() {
//        return new StepBuilder("step1")
//                .tasklet(demoTasklet())
//                .build();
    }
}
