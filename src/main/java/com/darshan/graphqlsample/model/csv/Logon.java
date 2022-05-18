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
    String metricCriteria;

    @CsvBindByPosition(position = 1)
    String channel;

    @CsvBindByPosition(position = 2)
    LocalDateTime eventTimeStamp;

    @CsvBindByPosition(position = 3)
    Long counter;
}
