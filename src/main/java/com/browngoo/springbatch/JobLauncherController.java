package com.browngoo.springbatch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final BasicBatchConfigurer basicBatchConfigurer;

    private final Job job;

    @PostMapping("batch")
    public String launch(
        @RequestBody MyParam myParam
    ) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", myParam.getName())
            .addDate("date", new Date())
            .toJobParameters();

        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        System.out.println(jobExecution.getStatus());
        System.out.println(jobExecution.getExitStatus());


        return "batch completed";
    }

    @Getter
    @NoArgsConstructor
    public static class MyParam {
        private String name = "";
    }
}
