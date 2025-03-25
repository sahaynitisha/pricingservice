package com.marcobikes.pricingservice.mapper;

import com.marcobikes.pricingservice.dto.PricingRuleDTO;
import com.marcobikes.pricingservice.model.PricingRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricingMapper {
    PricingRuleDTO toDTO(PricingRule pricingRule);
    PricingRule toEntity(PricingRuleDTO pricingRuleDTO);
}
