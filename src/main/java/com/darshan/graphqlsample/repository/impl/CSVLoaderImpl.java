package com.darshan.graphqlsample.repository.impl;

import com.darshan.graphqlsample.exceptions.NonPaymentCSVLoaderException;
import com.darshan.graphqlsample.model.csv.Logon;
import com.darshan.graphqlsample.repository.CSVLoader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
public class CSVLoaderImpl implements CSVLoader {
    @Override
    public List<Logon> loadLogonMetrics(String path) {
        try{
            File resource = new ClassPathResource(path).getFile();

            return new CsvToBeanBuilder(new FileReader(resource.toPath().toString())).withType(Logon.class)
                    .build().parse();
        }catch(IOException ioException){
            log.error("Error while reading path {}", path);
            throw new NonPaymentCSVLoaderException("Error while reading path {}", ioException);
        }
    }
}
