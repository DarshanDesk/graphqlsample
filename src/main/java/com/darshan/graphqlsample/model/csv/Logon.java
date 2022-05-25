package com.darshan.graphqlsample.model.csv;

import java.time.LocalDateTime;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Logon {

    @CsvBindByPosition(position = 0)
    private String metricCriteria;

    @CsvBindByPosition(position = 1)
    private  String channel;

    @CsvBindByPosition(position = 2)
    private String eventTimeStamp;

    @CsvBindByPosition(position = 3)
    private Long counter;
}
