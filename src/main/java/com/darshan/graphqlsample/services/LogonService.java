package com.darshan.graphqlsample.services;

import com.darshan.graphqlsample.model.csv.LogonEvent;
import com.darshan.graphqlsample.model.schema.LogonMetric;
import org.reactivestreams.Publisher;
import java.util.List;
import java.util.Map;

public interface LogonService {

    List<LogonMetric> filterLogonMetricsByChannelAndCriteria(String channel, String criteria, String fromSelection, String toSelection);

    List<LogonMetric> filterLogonMetricsByChannel(String channel);

    Map<String, List<LogonMetric>> getAllLogonMetrics(List<String> channels);

    Publisher<LogonEvent> getLogonPublisher();

}
