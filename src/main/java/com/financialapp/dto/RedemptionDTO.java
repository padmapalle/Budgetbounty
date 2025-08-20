package com.financialapp.dto;

import lombok.*;
import java.time.LocalDateTime;

<<<<<<< HEAD
import com.financialapp.entity.RedemptionStatus;
=======
import com.financialapp.model.RedemptionStatus;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4

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
