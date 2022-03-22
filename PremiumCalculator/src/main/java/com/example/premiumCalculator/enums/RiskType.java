package com.example.premiumCalculator.enums;

public enum RiskType {
    FIRE, THEFT;

    public double getCoefficientByRisk(double sumInsuredByRisk, RiskType riskType) {
        double coefficient = 0.0;

        switch (riskType) {
            case FIRE:
                if (sumInsuredByRisk > 100.00) coefficient = 0.024;
                else coefficient = 0.014;
                break;
            case THEFT:
                if (sumInsuredByRisk >= 15.00) coefficient = 0.05;
                else coefficient = 0.11;
                break;
        }
        return coefficient;
    }
}
