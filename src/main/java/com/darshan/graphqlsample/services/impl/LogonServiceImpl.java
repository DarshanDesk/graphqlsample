package com.darshan.graphqlsample.services.impl;

import com.darshan.graphqlsample.model.csv.Logon;
import com.darshan.graphqlsample.repository.impl.InMemoryLogonMetricsRepoImpl;
import com.darshan.graphqlsample.services.LogonService;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;


@Slf4j
public class LogonServiceImpl implements LogonService {

    @Autowired
    private InMemoryLogonMetricsRepoImpl logonRepo;

    @Override
    public List<Logon> getLogonMetricsByChannelAndCriteria(String channel, String criteria, YearMonth fromDate, YearMonth toDate) {
        List<Logon> logonMetricsList = logonRepo.getLogonMetricsByChannelAndCriteria(channel, criteria, fromDate, toDate);

        return null;
    }

    @Override
    public Publisher<Logon> getLogonPublisher() {
        return null;
    }


}
