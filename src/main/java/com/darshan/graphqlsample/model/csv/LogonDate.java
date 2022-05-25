package com.darshan.graphqlsample.model.csv;

import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LogonDate extends Logon{
    private LocalDate timestamp;
}
