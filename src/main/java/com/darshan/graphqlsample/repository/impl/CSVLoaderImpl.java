package com.darshan.graphqlsample.repository.impl;

import com.darshan.graphqlsample.exceptions.NonPaymentCSVLoaderException;
import com.darshan.graphqlsample.model.csv.Logon;
import com.darshan.graphqlsample.repository.CSVLoader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Slf4j
public class CSVLoaderImpl implements CSVLoader {
    @Override
    public List<Logon> loadLogonMetrics(String path) {
        try{
            return new CsvToBeanBuilder(new FileReader(path)).withType(Logon.class)
                    .build().parse();
        }catch(FileNotFoundException fileNotFoundException){
            log.error("Error occurred while loading NonPayment CSV data with path {}", path);
            throw new NonPaymentCSVLoaderException("Error occurred while loading NonPayment CSV data", fileNotFoundException);
        }

    }
}
