# PremiumCalculator

Implementation description.

PremiumCalculator was imeplemented using the following technologies:
- java 8 
- spring boot 2.6.4
- junit 4
- mockito
- maven

PremiumCalculator provides a get method that receives a Policy and calculates the premium value of the policy.
This method can be accesed using the following endpoint http://localhost:8080/calculate and it receives in the body a json as shown in the example below:
{
    "policyNumber":"LV20-02-100000-5",
    "policyStatus":"REGISTERED",
    "policyObjects":[
        { "objectName":"House",
           "subObjects":[
              {
                  "subObjectName":"TV",
                  "sumInsured":"100.00",
                  "riskType":"FIRE"
              },
               {
                  "subObjectName":"PS4",
                  "sumInsured":"8.00",
                  "riskType":"THEFT"
              }
           ]
        }
    ]
}

The main object entities are:
2 enums : RiskType and PolicyStatus
3 clases : Policy, PolicyObject, PolicySubObjects containing the attributes described in the requirements.
1 controller (PremiumCalculatorController) that contains a GET method representing the main calculation method.
1 service class containing the calculation logic.

In order to easy expand the implementation for a potential new risk type, I choosed to create a enum class (RiskType) containing the risk types and a method which calculates the coefficient used for each risk type.
If another risk type will apear in the future, the new risk type and the calculation logic for the coeffiecient  should be added in the RiskType class.

The controller calles #calculatePremium(Policy policy) method in the service class that iterates through policy's objects and  sums the premium by risk type, returning the policy value.
Acording to the formula the value of the policy is the sum of premium values by risk ( PREMIUM = PREMIUM_FIRE + PREMIUM_THEFT ) .

The premium value by risk ( PREMIUM_FIRE/ PREMIUM_THEFT) is calculated using #premiumRisk method for policy's object.
This method receives 2 parameters : a list of sub-objects and a risk type and calculates the premium value for that particular risk send as a parameter.

In order to calculate the sum insured by risk type, #sumInsuredByRisk method  is used. This method receives a list of sub-objects and a risk type and filters the sub-object lists using the risk type provided and retruns the sum of all sub-objects that have the speciefied risk.


Unit tests for the implementation are also present containg 5
