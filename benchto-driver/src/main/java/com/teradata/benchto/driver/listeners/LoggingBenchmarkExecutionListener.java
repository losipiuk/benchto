/*
 * Copyright 2013-2016, Teradata, Inc. All rights reserved.
 */
package com.teradata.benchto.driver.listeners;

import com.teradata.benchto.driver.Benchmark;
import com.teradata.benchto.driver.execution.BenchmarkExecutionResult;
import com.teradata.benchto.driver.execution.QueryExecution;
import com.teradata.benchto.driver.execution.QueryExecutionResult;
import com.teradata.benchto.driver.listeners.benchmark.BenchmarkExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingBenchmarkExecutionListener
        implements BenchmarkExecutionListener
{

    private static final Logger LOG = LoggerFactory.getLogger(LoggingBenchmarkExecutionListener.class);

    @Override
    public void benchmarkStarted(Benchmark benchmark)
    {
        LOG.info("Executing benchmark: {}", benchmark.getName());
    }

    @Override
    public void benchmarkFinished(BenchmarkExecutionResult result)
    {
        LOG.info("Finished benchmark: {}", result.getBenchmark().getName());
    }

    @Override
    public void executionStarted(QueryExecution execution)
    {
        LOG.info("Query started: {} ({}/{})", execution.getQueryName(), execution.getRun(), execution.getBenchmark().getRuns());
    }

    @Override
    public void executionFinished(QueryExecutionResult result)
    {
        if (result.isSuccessful()) {
            LOG.info("Query finished: {} ({}/{}), rows count: {}, duration: {}", result.getQueryName(), result.getQueryExecution().getRun(),
                    result.getBenchmark().getRuns(), result.getRowsCount(), result.getQueryDuration());
        }
        else {
            LOG.error("Query failed: {} ({}/{}), execution error: {}", result.getQueryName(), result.getQueryExecution().getRun(),
                    result.getBenchmark().getRuns(), result.getFailureCause().getMessage());
        }
    }
}
