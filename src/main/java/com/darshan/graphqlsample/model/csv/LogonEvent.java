package com.darshan.graphqlsample.model.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LogonEvent {

    @CsvBindByPosition(position = 0)
    private String metricCriteria;

    @CsvBindByPosition(position = 1)
    private  String channel;

    @CsvBindByPosition(position = 2)
    private String eventTimeStamp;

    @CsvBindByPosition(position = 3)
    private Long counter;
}
