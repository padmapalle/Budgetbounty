package com.financialapp.dto;

import lombok.*;
import java.time.LocalDateTime;

<<<<<<< HEAD
<<<<<<< HEAD
import com.financialapp.entity.ActivityType;
=======
import com.financialapp.model.ActivityType;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
import com.financialapp.entity.ActivityType;
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialActivityDTO {
    private Integer activityId;
    private Integer userId;
    private ActivityType activityType;  

    private LocalDateTime activityDate;
}
