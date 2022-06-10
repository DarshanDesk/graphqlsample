package com.darshan.graphqlsample.utils;

import com.darshan.graphqlsample.model.schema.LogonDailyMetric;
import com.darshan.graphqlsample.model.schema.LogonMetric;
import com.darshan.graphqlsample.model.schema.LogonMonthlyMetric;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DateTimeConversionUtils {

    public static List<LogonDailyMetric> getDateFromYearMonth(List<LogonMonthlyMetric> logonMonthlyMetrics){
        return logonMonthlyMetrics.stream().map(
                eachMonthlyMetric -> {
                    LogonDailyMetric dailyMetric = LogonDailyMetric.builder()
                            .dailyEvent(eachMonthlyMetric.getMonthlyEvent().atDay(1)).build();

                    dailyMetric.setMetricCount(eachMonthlyMetric.getMetricCount());
                    dailyMetric.setFilterCriteria(eachMonthlyMetric.getFilterCriteria());
                    return dailyMetric;
                }
        ).collect(Collectors.toCollection(ArrayList<LogonDailyMetric>:: new));
    }

    public static List<LogonMonthlyMetric> getYearMonthFromDate(List<LogonDailyMetric> logonDailyMetrics){
        return logonDailyMetrics.stream().map(
                dailyMetric -> {
                    LogonMonthlyMetric monthlyMetric = LogonMonthlyMetric.builder()
                            .monthlyEvent(YearMonth.from(dailyMetric.getDailyEvent())).build();

                    monthlyMetric.setMetricCount(dailyMetric.getMetricCount());
                    monthlyMetric.setFilterCriteria(dailyMetric.getFilterCriteria());
                    return monthlyMetric;
                }
        ).collect(Collectors.toCollection(ArrayList<LogonMonthlyMetric>:: new));
    }

    public static List<LogonMetric> getMetricFromMonth(List<LogonMonthlyMetric> logonMonthlyMetrics){
        return logonMonthlyMetrics.stream().map(
                monthlyMetric -> {
                    LogonMetric logonMetric = new LogonMetric();
                    logonMetric.setEventTimeStamp(monthlyMetric.getMonthlyEvent().toString());
                    logonMetric.setMetricCount(monthlyMetric.getMetricCount());
                    logonMetric.setFilterCriteria(monthlyMetric.getFilterCriteria());
                    return logonMetric;
                }
        ).collect(Collectors.toCollection(ArrayList<LogonMetric>:: new));
    }

    public static List<LogonMonthlyMetric> getYearMonthFromString(List<LogonMetric> logonMetrics){
        return logonMetrics.stream().map(
                eachMetric -> {
                    LogonMonthlyMetric monthlyMetric = LogonMonthlyMetric.builder()
                                    .monthlyEvent(YearMonth.parse(eachMetric.getEventTimeStamp(),
                                            DateTimeFormatter.ofPattern("yyyy-MM"))).build();

                    monthlyMetric.setMetricCount(eachMetric.getMetricCount());
                    monthlyMetric.setFilterCriteria(eachMetric.getFilterCriteria());
                    return monthlyMetric;
                }
        ).collect(Collectors.toCollection(ArrayList<LogonMonthlyMetric>:: new));
    }

}
