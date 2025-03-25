package com.marcobikes.pricingservice.service;

import com.marcobikes.pricingservice.dto.PricingRuleDTO;
import com.marcobikes.pricingservice.dto.TotalPriceRequestDTO;
import com.marcobikes.pricingservice.dto.TotalPriceResponseDTO;

import java.util.UUID;

public interface PricingService {
    PricingRuleDTO getPricingByOption(UUID optionValueId);
    PricingRuleDTO setOrUpdatePricingRule(PricingRuleDTO pricingRuleDTO);
    TotalPriceResponseDTO calculateTotalPrice(TotalPriceRequestDTO requestDTO) throws Exception;

}
