package com.financialapp.dto;

import lombok.*;
import java.time.LocalDateTime;

<<<<<<< HEAD

import com.financialapp.entity.RedemptionStatus;
import com.financialapp.model.RedemptionStatus;

=======
import com.financialapp.entity.RedemptionStatus;
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedemptionDTO {
    private Integer redemptionId;
    private Integer userId;
    private Integer catalogItemId;
    private LocalDateTime redeemedAt;
    private RedemptionStatus status;
    private String fulfillmentDetails;
    private String failureReason;
    private LocalDateTime expiryDate;
    private String redemptionCode;
}
