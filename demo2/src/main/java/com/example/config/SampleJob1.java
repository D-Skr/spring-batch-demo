package com.example.config;

import com.example.service.SecondTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob1 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTasklet secondTasklet;

    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    private Step firstStep(){
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .build();

    }


    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is the 1st tasklet step.");
                return RepeatStatus.FINISHED;
            }
        };
    }

    private Step secondStep(){
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTasklet)
                .build();

    }

    //moved this part to the service
//    private Tasklet secondTask(){
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("This is the 2nd tasklet step.");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
}
