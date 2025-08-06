package com.financialapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDTO {
    private Integer partnerId;
    private String name;
    private String apiKey;
}
