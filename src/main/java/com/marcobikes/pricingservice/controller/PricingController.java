package com.marcobikes.pricingservice.controller;

import com.marcobikes.pricingservice.dto.PricingRuleDTO;
import com.marcobikes.pricingservice.dto.TotalPriceRequestDTO;
import com.marcobikes.pricingservice.dto.TotalPriceResponseDTO;
import com.marcobikes.pricingservice.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pricing")
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    // Get pricing for a specific option
    @GetMapping("/{optionValueId}")
    public ResponseEntity<PricingRuleDTO> getPricing(@PathVariable UUID optionValueId) {
        return ResponseEntity.ok(pricingService.getPricingByOption(optionValueId));
    }

    // Set or update pricing rule
    @PostMapping
    public ResponseEntity<PricingRuleDTO> setPricing(@RequestBody PricingRuleDTO pricingRuleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pricingService.setOrUpdatePricingRule(pricingRuleDTO));
    }

    // Calculate total price for selected options
    //one example to show Exception Handling - this can be further customized to throw Application Specific Exception
    @PostMapping("/calculate")
    public ResponseEntity<TotalPriceResponseDTO> calculateTotalPrice(@RequestBody TotalPriceRequestDTO requestDTO) {
        try{
            TotalPriceResponseDTO totalPriceResponseDTO = pricingService.calculateTotalPrice(requestDTO);
            return ResponseEntity.ok(totalPriceResponseDTO);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
