package com.example.config;

import com.example.listener.FirstJobListener;
import com.example.listener.FirstStepListener;
import com.example.model.StudentCsv;
import com.example.processor.FirstItemProcessor;
import com.example.reader.FirstItemReader;
import com.example.service.SecondTasklet;
import com.example.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class SampleJob1 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private FirstJobListener firstJobListener;


    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;


    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep(){
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .listener(firstStepListener)
                .build();

    }


    private Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is the 1st tasklet step.");
                System.out.println("second " + chunkContext.getStepContext().getJobExecutionContext());
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

    @Bean
    public Job secondJob(){
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }

    private Step firstChunkStep(){
        return stepBuilderFactory.get("First Chunk Step")
                .<Integer,Long>chunk(4)
                .reader(firstItemReader)
                //.processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();

    }

    public FlatFileItemReader<StudentCsv> flatFileItemReader() {
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();
        flatFileItemReader.setResource(new FileSystemResource(new File("C:\\Projects\\demo2\\InputFiles\\student.csv")));
        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
            {
            setLineTokenizer(new DelimitedLineTokenizer() {
                //super.setLineTokenizer(tokenizer);
                    {
                    setNames("ID","First Name","Last Name","Email");
                    }
                });
            setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
                {
                    setTargetType(StudentCsv.class);
                }
            });
            }
        });
        flatFileItemReader.setLinesToSkip(1); //skip 1st line with headers
        return flatFileItemReader;
    }
}
