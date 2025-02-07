package org.project.utils;


import com.google.inject.Singleton;
import org.project.banking.model.Transaction;
import org.project.banking.model.TransactionCategory;
import org.project.banking.model.UserTransactions;
import org.project.model.ranking.context.Feature;
import org.project.model.ranking.context.FeatureType;
import org.project.model.ranking.context.UserContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class UserContextCreator {

    public UserContext buildUserContext(UserTransactions userTransactions) {
        Map<String, Feature> features = new HashMap<>();
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        List<Transaction> transactions = userTransactions.getTransactions().stream()
                .filter(txn -> txn.getDate().isAfter(sixMonthsAgo)).toList();

        double totalSpend = transactions.stream()
                .mapToDouble(Transaction::getAmount).sum();

        features.put("totalSixMonthSpend", new Feature("totalSixMonthSpend", FeatureType.NUMERIC, totalSpend));
        features.put("avgMonthlySpend", new Feature("avgMonthlySpend", FeatureType.NUMERIC, totalSpend / 6));
        features.put("yearProjection", new Feature("yearProjection", FeatureType.NUMERIC, totalSpend * 2));


        Map<TransactionCategory, Double> categoryWiseSpend = transactions.stream()
                .filter(txn -> txn.getDate().isAfter(sixMonthsAgo))
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)));

        features.put("categoryWiseSpend", new Feature("Category Wise Spend", FeatureType.MAP, categoryWiseSpend));


        List<String> topCategories = categoryWiseSpend.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Sort by spend descending
                .map(Map.Entry::getKey)
                .map(tc -> tc.name().toLowerCase())
                .limit(5)
                .collect(Collectors.toList());

        features.put("top5SpendingCategories", new Feature("top5SpendingCategories", FeatureType.LIST, topCategories));


        String topCategory = topCategories.stream().filter(ct -> !TransactionCategory.OTHERS.name().equalsIgnoreCase(ct))
                .findFirst().orElse(TransactionCategory.OTHERS.name().toLowerCase());
        features.put("topSpendingCategory", new Feature("topSpendingCategory", FeatureType.STRING, topCategory));

        double foreignSpend = transactions.stream()
                .filter(txn -> !txn.getCurrency().equals("INR"))
                .mapToDouble(Transaction::getAmount)
                .sum();
        features.put("foreignSpend", new Feature("foreignSpend", FeatureType.NUMERIC, foreignSpend));

        double maxSingleTransaction = transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0);
        features.put("maxSingleTransaction", new Feature("maxSingleTransaction", FeatureType.NUMERIC, maxSingleTransaction));

        return UserContext.builder()
                .userTransactions(userTransactions)
                .userFeatures(features)
                .build();
    }

}
