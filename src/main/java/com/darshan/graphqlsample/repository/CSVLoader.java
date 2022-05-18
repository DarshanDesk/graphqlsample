package com.darshan.graphqlsample.repository;

import com.darshan.graphqlsample.model.csv.Logon;

import java.util.List;

public interface CSVLoader {
    List<Logon> loadLogonMetrics(String path);
}
