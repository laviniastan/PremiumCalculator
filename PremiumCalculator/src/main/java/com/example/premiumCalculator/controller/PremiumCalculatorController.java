package com.example.premiumCalculator.controller;

import com.example.premiumCalculator.service.PremiumCalculatorService;
import com.example.premiumCalculator.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;

@RestController
public class PremiumCalculatorController {

    @Autowired
    PremiumCalculatorService premiumCalculatorService;

    @GetMapping("/calculate")
    public String calculate(@RequestBody Policy policy){

        DecimalFormat decimalFormat = new DecimalFormat("#####.##");
        return decimalFormat.format(premiumCalculatorService.calculatePremium(policy))+" EUR";
    }

}
