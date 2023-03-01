package com.example.demo1.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class DemoStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("this is before step execution" + stepExecution.getJobExecution().getExecutionContext());
        //StepExecutionListener.super.beforeStep(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("this is from After step execution" + stepExecution.getJobExecution().getExecutionContext());
        //return StepExecutionListener.super.afterStep(stepExecution);
        return null;
    }
}
