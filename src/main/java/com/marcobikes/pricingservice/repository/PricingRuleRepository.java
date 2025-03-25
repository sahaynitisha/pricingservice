package com.marcobikes.pricingservice.repository;

import com.marcobikes.pricingservice.model.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, UUID> {
    Optional<PricingRule> findByOptionValueId(UUID optionValueId);
}
