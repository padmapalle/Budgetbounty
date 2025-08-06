package com.financialapp.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoalDTO {
    private Integer goalId;
    private Integer userId;
    private String domain;
    private String customAttrs;
    
    @JsonProperty("isAchieved")
    private boolean isAchieved;
}
