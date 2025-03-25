package com.marcobikes.pricingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingRuleDTO {
    private UUID id;
    private UUID optionValueId;
    private UUID dependentOptionId;
    private double adjustedPrice;
}
