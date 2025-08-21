package com.financialapp.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
<<<<<<< HEAD
<<<<<<< HEAD
import com.financialapp.entity.GoalType;
=======
import com.financialapp.model.GoalType;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
import com.financialapp.entity.GoalType;
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)

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
