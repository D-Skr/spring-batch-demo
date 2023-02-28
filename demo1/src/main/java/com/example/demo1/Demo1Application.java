package com.example.demo1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class Demo1Application {


    /*
       @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("myJob", jobRepository)
                .start(step)
                .build();
     */
    @Autowired(required=false)
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @Bean
    public Job demoJob(){
        return jobs.get("demoJob")
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

    private Tasklet demoTasklet() {
        return (new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello Batch World");
                return RepeatStatus.FINISHED;
            }
        });
    }

}
