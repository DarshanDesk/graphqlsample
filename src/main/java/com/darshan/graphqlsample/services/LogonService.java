package com.darshan.graphqlsample.services;

import com.darshan.graphqlsample.model.csv.Logon;
import org.reactivestreams.Publisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface LogonService {

    List<Logon> getLogonMetricsByChannelAndCriteria(String channel, String criteria, YearMonth fromDate, YearMonth toDate);

    Publisher<Logon> getLogonPublisher();

}
