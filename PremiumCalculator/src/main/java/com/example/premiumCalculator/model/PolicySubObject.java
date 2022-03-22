package com.example.premiumCalculator.model;

import com.example.premiumCalculator.enums.RiskType;


public class PolicySubObject  {
    private String subObjectName;
    private double sumInsured;
    private RiskType riskType;

    public PolicySubObject() {
    }

    public PolicySubObject(String subObjectName, double sumInsured, RiskType riskType) {

        this.subObjectName = subObjectName;
        this.sumInsured = sumInsured;
        this.riskType = riskType;
    }

    public String getSubObjectName() {
        return subObjectName;
    }

    public void setSubObjectName(String subObjectName) {
        this.subObjectName = subObjectName;
    }

    public double getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public RiskType getRiskType() {
        return riskType;
    }

    public void setRiskType(RiskType riskType) {
        this.riskType = riskType;
    }

}
