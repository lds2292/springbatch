package com.browngoo.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobParametersConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
                System.out.println("jobParameters.getString(\"name\") = " + jobParameters.getString("name"));;
                System.out.println("jobParameters.getLong(\"seq\") = " + jobParameters.getLong("seq"));;
                System.out.println("jobParameters.getDate(\"date\") = " + jobParameters.getDate("date"));;
                System.out.println("jobParameters.getDouble(\"age\") = " + jobParameters.getDouble("age"));;

                Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED).build();
    }
}
