package com.darshan.graphqlsample.model.schema;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class LogonDailyMetric extends LogonMetric{
    LocalDate dailyEvent;
}
