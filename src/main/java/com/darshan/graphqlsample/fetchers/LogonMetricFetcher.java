package com.darshan.graphqlsample.fetchers;

import com.darshan.graphqlsample.model.csv.LogonEvent;
import com.darshan.graphqlsample.model.schema.LogonMetric;
import com.darshan.graphqlsample.repository.impl.InMemoryLogonMetricsRepoImpl;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class LogonMetricFetcher {

    @Autowired
    InMemoryLogonMetricsRepoImpl logonMetricsRepo;

    @DgsData(parentType = "Query", field = "logonMetricsByChannel")
            public List<LogonMetric> findAllWithFilter(@InputArgument String channel, DataFetchingEnvironment dfe){
        DataFetchingFieldSelectionSet s= dfe.getSelectionSet();
        List<LogonEvent> logonMetrics = logonMetricsRepo.getAllLogonEvents();

        List<LogonMetric> logonOutputList = logonMetrics.stream()
                .filter(eachLogonMetric -> channel.equalsIgnoreCase(eachLogonMetric.getChannel()))
                .map(eachLogonMetric ->{
                    LogonMetric logon = new LogonMetric();
                    logon.setFilterCriteria(eachLogonMetric.getMetricCriteria());
                    logon.setEventTimeStamp(eachLogonMetric.getEventTimeStamp());
                    logon.setMetricCount(eachLogonMetric.getCounter());

                    return logon;
                }).collect(Collectors.toCollection(ArrayList<LogonMetric>::new));

        return logonOutputList;
    }
}
