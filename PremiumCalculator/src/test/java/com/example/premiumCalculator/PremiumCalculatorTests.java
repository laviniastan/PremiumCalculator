package com.example.premiumCalculator;


import com.example.premiumCalculator.enums.PolicyStatus;
import com.example.premiumCalculator.enums.RiskType;
import com.example.premiumCalculator.model.Policy;
import com.example.premiumCalculator.model.PolicyObject;
import com.example.premiumCalculator.model.PolicySubObject;
import com.example.premiumCalculator.service.PremiumCalculatorService;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PremiumCalculatorTests {

    @Autowired
    PremiumCalculatorService premiumCalculatorService;


    @BeforeTestMethod
    public void  init()  throws Exception{
        initMocks(this);
    }

    /**
     * Scenario 1: One policy, one object, two sub-objects
     * riskType=FIRE , sum insured=100.00
     * riskType=THEFT , sum insured=8.00
     * Expected result 2.28 EUR
     */
    @Test
    public void calculatePremiumScenario1() {
        final double expectedValue = 2.28;
        Policy policy = setUpMockData();
        RiskType[] riskTypes = RiskType.values();
        ReflectionTestUtils.setField(premiumCalculatorService,"riskTypes",riskTypes);
        double actualValue = Math.round(premiumCalculatorService.calculatePremium(policy)*100.0)/100.0;

        assertEquals(expectedValue,actualValue);
    }

    /**
     * Scenario 2: Policy's total sum insured for risk type as described below
     * riskType=FIRE , sum insured=500.00
     * riskType=THEFT , sum insured=102.51
     * Expected result 17.13 EUR
     */
    @Test
    public void calculatePremiumScenario2 () {
        final double expectedValue = 17.13;
        PremiumCalculatorService service=mock(PremiumCalculatorService.class);
        Policy policy = setUpMockData();
        PolicyObject policyObject = policy.getPolicyObjects().get(0);
        RiskType[] riskTypes = RiskType.values();

        ReflectionTestUtils.setField(service,"riskTypes",riskTypes);
        when(service.sumInsuredByRisk(policyObject.getSubObjects(),RiskType.FIRE)).thenReturn(500.00);
        when(service.sumInsuredByRisk(policyObject.getSubObjects(),RiskType.THEFT)).thenReturn(102.51);
        when(service.premiumRisk(policyObject.getSubObjects(),RiskType.FIRE)).thenCallRealMethod();
        when(service.premiumRisk(policyObject.getSubObjects(),RiskType.THEFT)).thenCallRealMethod();
        when(service.calculatePremium(policy)).thenCallRealMethod();

        double actualValue = Math.round(service.calculatePremium(policy)*100.0)/100.0;
        assertEquals(expectedValue,actualValue);
    }

    /**
     * Scenario 3: One policy, one object, two sub-objects with same risk type
     * riskType=FIRE , sum insured=100.00
     * riskType=FIRE , sum insured=8.00
     * Expected result 2.59 EUR
     */
    @Test
    public void  calculatePremiumScenario3(){
        final double expectedValue=2.59;
        PolicySubObject subObject1 = new PolicySubObject("TV", 100.00, RiskType.FIRE);
        PolicySubObject subObject2 = new PolicySubObject("PS4", 8.00, RiskType.FIRE);
        PolicyObject policyObject  = new PolicyObject("House" , Arrays.asList(subObject1,subObject2));
        Policy policy              = new Policy("LV20-02-100000-5", PolicyStatus.REGISTERED, Arrays.asList(policyObject));


        RiskType[] riskTypes = RiskType.values();
        ReflectionTestUtils.setField(premiumCalculatorService,"riskTypes",riskTypes);
        double actualValue = Math.round(premiumCalculatorService.calculatePremium(policy)*100.0)/100.0;

        assertEquals(expectedValue,actualValue);
    }

    /**
     * Scenario 3: One policy, one object, two sub-objects with same risk type
     * riskType=THEFT , sum insured=5.00
     * riskType=THEFT , sum insured=1.00
     * Expected result 0.66 EUR
     */
    @Test
    public void  calculatePremiumScenario4(){
        final double expectedValue = 0.66;
        PolicySubObject subObject1 = new PolicySubObject("TV", 5.00, RiskType.THEFT);
        PolicySubObject subObject2 = new PolicySubObject("PS4", 1.00, RiskType.THEFT);
        PolicyObject policyObject  = new PolicyObject("House" , Arrays.asList(subObject1,subObject2));
        Policy policy              = new Policy("LV20-02-100000-5", PolicyStatus.REGISTERED, Arrays.asList(policyObject));


        RiskType[] riskTypes = RiskType.values();
        ReflectionTestUtils.setField(premiumCalculatorService,"riskTypes",riskTypes);
        double actualValue = Math.round(premiumCalculatorService.calculatePremium(policy)*100.0)/100.0;

        assertEquals(expectedValue,actualValue);
    }
    /**
     * Scenario 3: One policy with no policy objects
     * Expected result 0.00 EUR
     */
    @Test
    public void  calculatePremiumScenario5(){
        final double expectedValue = 0.00;
        Policy policy = new Policy("LV20-02-100000-5", PolicyStatus.REGISTERED, Collections.emptyList());
        RiskType[] riskTypes = RiskType.values();
        ReflectionTestUtils.setField(premiumCalculatorService,"riskTypes",riskTypes);

        double actualValue = Math.round(premiumCalculatorService.calculatePremium(policy)*100.0)/100.0;

        assertEquals(expectedValue,actualValue);
    }


    protected Policy setUpMockData(){
        PolicySubObject subObject1 = new PolicySubObject("TV", 100.00, RiskType.FIRE);
        PolicySubObject subObject2 = new PolicySubObject("PS4", 8.00, RiskType.THEFT);
        PolicyObject policyObject  = new PolicyObject("House" , Arrays.asList(subObject1,subObject2));
        Policy policy              = new Policy("LV20-02-100000-5", PolicyStatus.REGISTERED, Arrays.asList(policyObject));

        return policy;
    }
}
