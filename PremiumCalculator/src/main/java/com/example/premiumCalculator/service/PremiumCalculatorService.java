package com.example.premiumCalculator.service;

import com.example.premiumCalculator.enums.RiskType;
import com.example.premiumCalculator.model.Policy;
import com.example.premiumCalculator.model.PolicySubObject;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PremiumCalculatorService {

    private  RiskType[] riskTypes = RiskType.values();
    public double calculatePremium(Policy policy) {
       AtomicReference<Double> premium = new AtomicReference<>(0.00);
       if(policy!= null && !policy.getPolicyObjects().isEmpty()) {
           policy.getPolicyObjects().stream().forEach(
                   policyObject -> {
                       List<PolicySubObject> policySubObjects = policyObject.getSubObjects();
                       for (RiskType riskType : riskTypes) {
                           premium.updateAndGet(v -> new Double( (v + premiumRisk(policySubObjects, riskType))));
                       }
                   }
           );
       }
        return premium.get();
    }

    public double premiumRisk(List<PolicySubObject> policySubObjects, RiskType riskType) {
        double sumInsuredRisk = sumInsuredByRisk(policySubObjects, riskType);
        double coefficientRisk = riskType.getCoefficientByRisk(sumInsuredRisk, riskType);

        return sumInsuredRisk * coefficientRisk;
    }

    public double sumInsuredByRisk(List<PolicySubObject> policySubObjects, RiskType riskType) {
         if(policySubObjects != null && policySubObjects.size()>0)
            return policySubObjects.stream()
                                   .filter(policySubObject -> policySubObject.getRiskType().equals(riskType))
                                   .mapToDouble(policySubObject -> policySubObject.getSumInsured())
                                   .sum();
        else
            return 0.0;
    }

}
