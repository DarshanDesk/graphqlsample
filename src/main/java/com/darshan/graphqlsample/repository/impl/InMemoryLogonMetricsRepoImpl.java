package com.darshan.graphqlsample.repository.impl;

import com.darshan.graphqlsample.model.csv.Logon;
import com.darshan.graphqlsample.model.csv.LogonMonthly;
import com.darshan.graphqlsample.repository.CSVLoader;

import com.darshan.graphqlsample.utils.MetricsConstants;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jndi.JndiLookupFailureException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.darshan.graphqlsample.utils.Constants;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Repository("InMemoryLogonMetricsRepoImpl")
public class InMemoryLogonMetricsRepoImpl {

    private static List<Logon> logonMetrics = new ArrayList<>();
    private FluxSink<Logon> logonMetricsStream;
    private ConnectableFlux<Logon> logonMetricsPublisher;

    public InMemoryLogonMetricsRepoImpl(@Value("${resources.csv.path}") String path) {
        //TODO: Implement folder iteration logic
        CSVLoader csvLoader = new CSVLoaderImpl();
        logonMetrics = new CopyOnWriteArrayList<>(csvLoader.loadLogonMetrics(path));

        log.info("Total Metrics Data loaded: {}", logonMetrics.size());
        log.debug("Metrics Data: \n{}", logonMetrics);

        Flux<Logon> publisher = Flux.create(emitter -> {
            logonMetricsStream = emitter;
        });
        logonMetricsPublisher = publisher.publish();
        logonMetricsPublisher.connect();
    }

    private List<Logon> getAllLogonMetrics(){
        return logonMetrics;
    }

    public List<Logon> getLogonMetricsByChannel(String channel){
        return null;
    }

    public List<Logon> getLogonMetricsByChannelAndCriteria(String channel, String criteria, YearMonth fromDate, YearMonth toDate){

        List<Logon> allLogonMetrics = this.getAllLogonMetrics();

        if(Optional.ofNullable(allLogonMetrics).isPresent()) {
           List<LogonMonthly> logonMonthlyList =  allLogonMetrics.stream().map(eachLogon -> {
                LogonMonthly logonMonthly = LogonMonthly.builder()
                        .yearMonthStamp(YearMonth.parse(eachLogon.getEventTimeStamp(), DateTimeFormatter.ofPattern("yyyy-MM")))
                        .build();
                logonMonthly.setChannel(eachLogon.getChannel());
                logonMonthly.setMetricCriteria(eachLogon.getMetricCriteria());
                logonMonthly.setCounter(eachLogon.getCounter());
                return logonMonthly;
            }).collect(Collectors.toCollection(ArrayList<LogonMonthly>::new));
        }
        else{
            throw new IllegalArgumentException("Logon metrics is an empty list");
        }

        if(criteria.equalsIgnoreCase(MetricsConstants.valueOf(Constants.CRITERIA_MONTHLY).label)){

        }

        return null;
    }

    public Publisher<Logon> getLogonMetricsPublisher() {
        return logonMetricsPublisher;
    }
}
