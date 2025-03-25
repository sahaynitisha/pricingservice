package com.marcobikes.pricingservice.service;

import com.marcobikes.pricingservice.dto.PricingRuleDTO;
import com.marcobikes.pricingservice.dto.TotalPriceRequestDTO;
import com.marcobikes.pricingservice.dto.TotalPriceResponseDTO;
import com.marcobikes.pricingservice.mapper.PricingMapper;
import com.marcobikes.pricingservice.model.PricingRule;
import com.marcobikes.pricingservice.repository.PricingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    private PricingRuleRepository pricingRuleRepository;
    @Autowired
    private PricingMapper pricingMapper;

    // Communicate with Product Service
    @Autowired
    private RestTemplate restTemplate;
    private final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8081/products";

    @Override
    public PricingRuleDTO getPricingByOption(UUID optionValueId) {
        PricingRule rule = pricingRuleRepository.findByOptionValueId(optionValueId)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found"));
        return pricingMapper.toDTO(rule);
    }

    @Override
    public PricingRuleDTO setOrUpdatePricingRule(PricingRuleDTO pricingRuleDTO) {
        PricingRule rule = pricingMapper.toEntity(pricingRuleDTO);
        return pricingMapper.toDTO(pricingRuleRepository.save(rule));
    }

    @Override
    public TotalPriceResponseDTO calculateTotalPrice(TotalPriceRequestDTO requestDTO) throws Exception {
        // Fetch base price of the product from Product Service
        String url = PRODUCT_SERVICE_BASE_URL + "/" + requestDTO.getProductId();
        double totalPrice;

        try{
            ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, null, Double.class);

            if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
                 totalPrice = response.getBody();
            else
                throw new RuntimeException("Failed to get base price: " + response.getStatusCode());
        }catch(Exception e){
            throw new RuntimeException("Error calling product service", e);
        }


        // Loop through each selected option value
        for (UUID optionValueId : requestDTO.getOptionValueIds()) {
            // Fetch adjusted price from Pricing Rules
            PricingRule pricingRule = pricingRuleRepository.findByOptionValueId(optionValueId)
                    .orElse(null);

            if (pricingRule != null) {
                totalPrice += pricingRule.getAdjustedPrice();
            }
        }

        return new TotalPriceResponseDTO(requestDTO.getProductId(), totalPrice);
    }
}
