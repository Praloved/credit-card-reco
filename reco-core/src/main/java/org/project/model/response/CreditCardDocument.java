package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreditCardDocument {

    private String id;
    private String name;
    private String parentCompany;

    // travel, fuel, lifestyle
    private List<String> suitableFor;

    private boolean loungeBenefits;

    // Need to see how these can be used in feature engg.
    private List<String> extraBenefits;

    private double annualFee;

    private boolean premium;

    private double interestRate;

    private double foreignTransactionFee;

    private boolean fuelSurchargeWaiver;

    private String welcomeBonus;

    private String eligibilityCriteria;

    private String issuedBy;

    private double maxCashbackLimit;
}
