package com.darshan.graphqlsample.model.schema;

import lombok.*;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class LogonMonthlyMetric extends LogonMetric{
    YearMonth monthlyEvent;
}
