package com.browngoo.springbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomIncrementer implements JobParametersIncrementer {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    public JobParameters getNext(JobParameters parameters) {

        String runId = format.format(new Date());

        return new JobParametersBuilder().addString("run.id", runId).toJobParameters();
    }
}
