package com.darshan.graphqlsample.model.schema;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LogonMetric {
    private String filterCriteria;
    private String eventTimeStamp;
    private Long metricCount;
}
