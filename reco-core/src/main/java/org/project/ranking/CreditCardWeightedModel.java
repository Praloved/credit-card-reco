package org.project.ranking;

import com.google.inject.Singleton;
import org.project.banking.model.TransactionCategory;
import org.project.model.CreditCardModelContext;
import org.project.model.ranking.context.Feature;
import org.project.model.ranking.context.UserContext;
import org.project.model.response.CreditCardDocument;

import java.util.Map;

@Singleton
public class CreditCardWeightedModel implements IModel<CreditCardModelContext> {

    @Override
    public double score(CreditCardModelContext creditCardModelContext) {
        double score = 0;
        UserContext userContext = creditCardModelContext.getUserContext();
        CreditCardDocument card = creditCardModelContext.getCreditCardDocument();

        Map<String, Feature> userFeatures = userContext.getUserFeatures();

        String topSpendingCategory = userFeatures.get("topSpendingCategory").getFeatureValue().toString().toLowerCase();

        if (card.getSuitableFor().contains(topSpendingCategory)) {
            score += 10; // Strong match for user's spending preferences
        }

        double foreignSpend = userFeatures.get("foreignSpend").getFeatureValue();
        double totalSpend = userFeatures.get("totalSixMonthSpend").getFeatureValue();

        if (foreignSpend > 0) {
            // Boost if the card has low foreign transaction fees (under 2%)
            if (card.getForeignTransactionFee() < 2) {
                score += 3 + (foreignSpend * 10 / totalSpend); // Additional score based on foreign spend percentage
            }
        }

        if (card.getWelcomeBonus() != null && !card.getWelcomeBonus().isEmpty()) {
            score += 5; // Additional points for welcome bonus availability
        }

        if (card.isLoungeBenefits()) {
            score += 4; // Lounge access gives the card a boost
        }


        if (card.getExtraBenefits() != null && !card.getExtraBenefits().isEmpty()) {
            score += card.getExtraBenefits().size(); // Add +1 for every extra benefit
        }

        if (card.isPremium()) {
            score += 3; // Assign an extra score for premium cards
        }

        Object yearProjection = userContext.getUserFeatures().get("yearProjection").getFeatureValue();
        if (yearProjection != null) {
            double yp = (Double) yearProjection;
            if (yp >= card.getAnnualFee()) {
                score += 10;
            }
        }
        if (card.getAnnualFee() == 0) {
            score += 5;
        }

        boolean isFuelCategoryPresent = ((Map<TransactionCategory, Double>) userContext.getUserFeatures().get("categoryWiseSpend").getFeatureValue()).containsKey(TransactionCategory.FUEL);

        if (card.isFuelSurchargeWaiver() && isFuelCategoryPresent) {
            score += 3; // Fuel cards for fuel-heavy users
        }

        score += card.getMaxCashbackLimit() / 1000;


        return score;
    }

    @Override
    public String getModelName() {
        return "cc_weighted";
    }

    @Override
    public String getModelVersion() {
        return "1.0";
    }
}
