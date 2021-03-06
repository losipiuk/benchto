/*
 * Copyright 2013-2016, Teradata, Inc. All rights reserved.
 */
package com.teradata.benchto.driver.listeners.measurements;

import com.google.common.collect.ImmutableList;
import com.teradata.benchto.driver.Measurable;
import com.teradata.benchto.driver.execution.BenchmarkExecutionResult;
import com.teradata.benchto.driver.execution.QueryExecutionResult;
import com.teradata.benchto.driver.service.Measurement;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.teradata.benchto.driver.service.Measurement.measurement;

@Component
public class DurationMeasurementProvider
        implements PostExecutionMeasurementProvider
{
    @Override
    public List<Measurement> loadMeasurements(Measurable measurable)
    {
        if (shouldMeasureDuration(measurable)) {
            return ImmutableList.of(measurement("duration", "MILLISECONDS", measurable.getQueryDuration().toMillis()));
        }
        else {
            return ImmutableList.of();
        }
    }

    private boolean shouldMeasureDuration(Measurable measurable)
    {
        if (measurable instanceof QueryExecutionResult) {
            return true;
        }
        else if (measurable instanceof BenchmarkExecutionResult && measurable.getBenchmark().isConcurrent()) {
            return true;
        }
        return false;
    }
}
