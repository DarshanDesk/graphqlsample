package com.darshan.graphqlsample.services.impl;

import com.darshan.graphqlsample.model.csv.LogonEvent;
import com.darshan.graphqlsample.model.schema.LogonDailyMetric;
import com.darshan.graphqlsample.model.schema.LogonMetric;
import com.darshan.graphqlsample.model.schema.LogonMonthlyMetric;
import com.darshan.graphqlsample.repository.impl.InMemoryLogonMetricsRepoImpl;
import com.darshan.graphqlsample.services.LogonService;
import com.darshan.graphqlsample.utils.Constants;
import com.darshan.graphqlsample.utils.DateTimeConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service("LogonService")
public class LogonServiceImpl implements LogonService {

    @Autowired
    private InMemoryLogonMetricsRepoImpl logonRepo;

    @Override
    public List<LogonMetric> filterLogonMetricsByChannelAndCriteria(String channel, String criteria, String fromSelection, String toSelection) {
        List<LogonMetric> filteredLogonMetricsList = logonRepo.getLogonMetricsByChannelAndCriteria(channel, criteria);

        if(criteria.equalsIgnoreCase(Constants.CRITERIA_MONTHLY)){
            List<LogonMonthlyMetric> logonMonthlyMetrics = DateTimeConversionUtils.getYearMonthFromString(filteredLogonMetricsList);
            List<LogonDailyMetric> logonDailyMetrics = DateTimeConversionUtils.getDateFromYearMonth(logonMonthlyMetrics);

            LocalDate fromSelectedDate = YearMonth.parse(fromSelection, DateTimeFormatter.ofPattern("yyyy-MM")).atDay(1);
            LocalDate toSelectedDate = YearMonth.parse(toSelection, DateTimeFormatter.ofPattern("yyyy-MM")).atDay(1);

            List<LogonDailyMetric> filteredDailyMetric = logonDailyMetrics.stream()
                    .filter(logonDaily -> (logonDaily.getDailyEvent()).isAfter(fromSelectedDate))
                    .filter(logonDaily -> (logonDaily.getDailyEvent()).isBefore(toSelectedDate))
                    .collect(Collectors.toCollection(ArrayList<LogonDailyMetric>:: new));

            List<LogonMonthlyMetric> filteredMonthlyMetrics = DateTimeConversionUtils.getYearMonthFromDate(filteredDailyMetric);
            return DateTimeConversionUtils.getMetricFromMonth(filteredMonthlyMetrics);

        }
        //TODO: Add Date filter logic
        return null;
    }

    @Override
    public List<LogonMetric> filterLogonMetricsByChannel(String channel) {
        return logonRepo.getLogonMetricsByChannel(channel);
    }

    @Override
    public Map<String, List<LogonMetric>> getAllLogonMetrics(List<String> channels) {
        return logonRepo.getChannelLogonMetricMappings(channels);
    }

    @Override
    public Publisher<LogonEvent> getLogonPublisher() {
        return logonRepo.getLogonEventsPublisher();
    }


}
