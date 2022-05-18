package com.darshan.graphqlsample.repository.impl;

import com.darshan.graphqlsample.model.csv.Logon;
import com.darshan.graphqlsample.repository.CSVLoader;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class InMemoryLogonMetricsRepoImpl {

    private static List<Logon> logonMetrics = new ArrayList<>();
    private FluxSink<Logon> logonMetricsStream;
    private ConnectableFlux<Logon> logonMetricsPublisher;

    public InMemoryLogonMetricsRepoImpl(String path) {
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

    public List<Logon> getAllLogonMetrics(){
        return logonMetrics;
    }

    public List<Logon> getLogonMetricsByChannel(String channel){
        return null;
    }

    public List<Logon> getLogonMetricsByCriteria(String criteria){
        return null;
    }

    public List<Logon> getLogonMetricsByChannelAndCriteria(String channel, String criteria){
        return null;
    }

    public Publisher<Logon> getLogonMetricsPublisher() {
        return logonMetricsPublisher;
    }
}
