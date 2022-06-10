package com.darshan.graphqlsample.repository.impl;

import com.darshan.graphqlsample.model.csv.LogonEvent;
import com.darshan.graphqlsample.model.schema.LogonMetric;
import com.darshan.graphqlsample.repository.CSVLoader;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Repository("InMemoryLogonMetricsRepoImpl")
public class InMemoryLogonMetricsRepoImpl {

    private static List<LogonEvent> logonEvents = new ArrayList<>();
    private static final Map<String, LogonMetric> logonEntities = new ConcurrentHashMap<>();
    private FluxSink<LogonEvent> logonEventsStream;
    private ConnectableFlux<LogonEvent> logonEventsPublisher;

    public InMemoryLogonMetricsRepoImpl( @Value("${resources.csv.path}") String path) {

        //TODO: Implement folder iteration logic
        CSVLoader csvLoader = new CSVLoaderImpl();
        logonEvents = new CopyOnWriteArrayList<>(csvLoader.loadLogonMetrics(path));

        log.info("Total Events Data loaded: {}", logonEvents.size());
        log.debug("Events Data: \n{}", logonEvents);

        logonEvents.stream().forEach( eachEvent -> {
                    LogonMetric logonMetric =  new LogonMetric();
            logonMetric.setFilterCriteria(eachEvent.getMetricCriteria());
            logonMetric.setEventTimeStamp(eachEvent.getEventTimeStamp());
            logonMetric.setMetricCount(eachEvent.getCounter());
             logonEntities.put(eachEvent.getChannel(), logonMetric);
                }
        );

        log.info("Seed Data: \n{}", logonEntities);
        Flux<LogonEvent> publisher = Flux.create(emitter -> logonEventsStream = emitter);
        logonEventsPublisher = publisher.publish();
        logonEventsPublisher.connect();
    }

    public List<LogonEvent> getAllLogonEvents(){
        return logonEvents;
    }

    public Map<String, List<LogonMetric>> getChannelLogonMetricMappings(List<String> channels) {

        Map<String, List<LogonMetric>> channelLogonMetricRel = new ConcurrentHashMap<>();

        for (String channel:channels) {
            List<LogonMetric> logonMetrics = logonEntities.entrySet().stream()
                    .filter(eachLogonEntity -> eachLogonEntity.getKey().equalsIgnoreCase(channel))
                    .map(eachLogonEntity -> eachLogonEntity.getValue())
                    .collect(Collectors.toCollection(ArrayList<LogonMetric>::new));

            channelLogonMetricRel.put(channel, logonMetrics);
        }

        return channelLogonMetricRel;
    }

    public List<LogonMetric> getLogonMetricsByChannel(String channel){
        List<LogonEvent> allLogonEvents = this.getAllLogonEvents();

        return allLogonEvents.stream()
                .filter(eachLogonMetric -> channel.equalsIgnoreCase(eachLogonMetric.getChannel()))
                .map(eachEvent -> {
                    LogonMetric logonMetric = new LogonMetric();
                    logonMetric.setFilterCriteria(eachEvent.getMetricCriteria());
                    logonMetric.setEventTimeStamp(eachEvent.getEventTimeStamp());
                    logonMetric.setMetricCount(eachEvent.getCounter());
                    return logonMetric;
                })
                .collect(Collectors.toCollection(ArrayList<LogonMetric>:: new));
    }

    public List<LogonMetric> getLogonMetricsByChannelAndCriteria(String channel, String criteria){
        List<LogonEvent> allLogonEvents = this.getAllLogonEvents();

       return allLogonEvents.stream()
                .filter(eachLogonMetric -> channel.equalsIgnoreCase(eachLogonMetric.getChannel()))
                .filter(eachLogonMetric -> criteria.equalsIgnoreCase(eachLogonMetric.getMetricCriteria()))
                   .map(eachEvent -> {
                       LogonMetric logonMetric = new LogonMetric();
                       logonMetric.setFilterCriteria(eachEvent.getMetricCriteria());
                       logonMetric.setEventTimeStamp(eachEvent.getEventTimeStamp());
                       logonMetric.setMetricCount(eachEvent.getCounter());
                       return logonMetric;
                   })
                .collect(Collectors.toCollection(ArrayList<LogonMetric>:: new));
    }

    public Publisher<LogonEvent> getLogonEventsPublisher() {
        return logonEventsPublisher;
    }
}
