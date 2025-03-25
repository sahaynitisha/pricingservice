package com.marcobikes.pricingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPriceRequestDTO {
    private UUID productId;          // ID of the product
    private List<UUID> optionValueIds; // List of selected option values
}

