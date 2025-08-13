package com.financialapp.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialapp.model.GoalType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialGoalDTO {
    private Integer goalId;
    private Integer userId;
    private GoalType GoalType; 
    private String customAttrs;
    
    @JsonProperty("isAchieved")
    private boolean isAchieved;
}
