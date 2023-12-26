package com.browngoo.springbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class CustomJobParameterValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {

        if (parameters == null) return;

        if (parameters.getString("name") == null) {
            throw new JobParametersInvalidException("name parameter is not found");
        }
    }
}
