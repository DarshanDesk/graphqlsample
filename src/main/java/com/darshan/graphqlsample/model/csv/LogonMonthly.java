package com.darshan.graphqlsample.model.csv;

import lombok.*;
import java.time.YearMonth;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LogonMonthly extends Logon{
    private YearMonth yearMonthStamp;
}
