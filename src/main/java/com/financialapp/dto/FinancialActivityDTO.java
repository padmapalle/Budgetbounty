package com.financialapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialActivityDTO {
    private Integer activityId;
    private Integer userId;
    private String activityType;

    private LocalDateTime activityDate;
}
