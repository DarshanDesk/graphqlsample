package com.darshan.graphqlsample.repository;

import com.darshan.graphqlsample.model.csv.LogonEvent;

import java.util.List;

public interface CSVLoader {
    List<LogonEvent> loadLogonMetrics(String path);
}
