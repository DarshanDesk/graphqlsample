package com.darshan.graphqlsample.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class TracingInstrumentation extends SimpleInstrumentation {
    @Override
    public InstrumentationState createState() {
        return new TracingState();
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        TracingState tracingState = parameters.getInstrumentationState();
        tracingState.startTime = System.currentTimeMillis();
        return super.beginExecution(parameters);
    }

    @Override
    public DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher,
                                                InstrumentationFieldFetchParameters parameters) {
        if (parameters.isTrivialDataFetcher()) {
            return dataFetcher;
        }
        return environment -> {
            long initTime = System.currentTimeMillis();
            Object result = dataFetcher.get(environment);
            String msg = "Instrumentation of datafetcher {} took {} ms";
            if (result instanceof CompletableFuture) {
                ((CompletableFuture<?>) result).whenComplete((r, ex) -> {
                    long timeTaken = System.currentTimeMillis() - initTime;
                    log.info(msg, findDatafetcherTag(parameters), timeTaken);
                });
            } else {
                long timeTaken = System.currentTimeMillis() - initTime;
                log.info(msg, findDatafetcherTag(parameters), timeTaken);
            }
            return result;
        };
    }

    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(
            ExecutionResult executionResult, InstrumentationExecutionParameters parameters) {
        TracingState tracingState = parameters.getInstrumentationState();
        long timeTaken = System.currentTimeMillis() - tracingState.startTime;
        log.info("Request processing took: {} ms", timeTaken);
        return super.instrumentExecutionResult(executionResult, parameters);
    }

    private String findDatafetcherTag(InstrumentationFieldFetchParameters parameters) {
        GraphQLOutputType type = parameters.getExecutionStepInfo().getParent().getType();
        GraphQLObjectType parent;
        if (type instanceof GraphQLNonNull) {
            parent = (GraphQLObjectType) ((GraphQLNonNull) type).getWrappedType();
        } else {
            parent = (GraphQLObjectType) type;
        }
        return parent.getName() + "." + parameters.getExecutionStepInfo().getPath().getSegmentName();
    }


    static class TracingState implements InstrumentationState {
        long startTime;
    }
}
