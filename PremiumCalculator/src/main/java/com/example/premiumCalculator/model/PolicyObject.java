package com.example.premiumCalculator.model;

import java.util.List;

public class PolicyObject {
    private String objectName;
    private List<PolicySubObject> subObjects;

    public PolicyObject() {
    }

    public PolicyObject(String objectName, List<PolicySubObject> subObjects) {
        this.objectName = objectName;
        this.subObjects = subObjects;
    }

    public String getObjectName() { return objectName; }

    public void setObjectName(String objectName) { this.objectName = objectName; }

    public List<PolicySubObject> getSubObjects() { return subObjects; }

    public void setSubObjects(List<PolicySubObject> subObjects) { this.subObjects = subObjects; }

}
