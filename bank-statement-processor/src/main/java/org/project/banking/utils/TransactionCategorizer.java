package org.project.banking.utils;


import com.google.common.collect.Sets;
import org.project.banking.model.TransactionCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransactionCategorizer {

    private static final Map<TransactionCategory, Set<String>> CATEGORY_KEYWORDS = new HashMap<>();

    static {
        CATEGORY_KEYWORDS.put(TransactionCategory.TRAVEL, Sets.newHashSet("flight", "hotel", "railway", "uber", "ola", "airlines"));
        CATEGORY_KEYWORDS.put(TransactionCategory.SHOPPING, Sets.newHashSet("amazon", "flipkart", "myntra", "reliance digital", "shopping"));
        CATEGORY_KEYWORDS.put(TransactionCategory.DINING, Sets.newHashSet("zomato", "swiggy", "restaurant", "cafe", "food"));
        CATEGORY_KEYWORDS.put(TransactionCategory.FUEL, Sets.newHashSet("petrol", "diesel", "hpcl", "bpcl", "indianoil", "fuel"));
        CATEGORY_KEYWORDS.put(TransactionCategory.GROCERY, Sets.newHashSet("bigbasket", "grofers", "supermarket", "grocery"));
        CATEGORY_KEYWORDS.put(TransactionCategory.ENTERTAINMENT, Sets.newHashSet("movie", "netflix", "prime", "hotstar", "theater", "concert"));
        CATEGORY_KEYWORDS.put(TransactionCategory.LUXURY, Sets.newHashSet("golf", "premium lounge", "luxury", "concierge"));
        CATEGORY_KEYWORDS.put(TransactionCategory.ELECTRONICS, Sets.newHashSet("apple", "croma", "reliance digital", "gadget", "laptop", "mobile"));
        CATEGORY_KEYWORDS.put(TransactionCategory.FOREX, Sets.newHashSet("paypal", "forex", "international transaction", "currency exchange"));
        CATEGORY_KEYWORDS.put(TransactionCategory.UTILITY_BILLS, Sets.newHashSet("electricity", "gas bill", "water bill", "broadband", "internet"));
        CATEGORY_KEYWORDS.put(TransactionCategory.INSURANCE, Sets.newHashSet("lic", "insurance", "mutual fund", "sip", "investment"));
    }

    public static TransactionCategory categorizeTransaction(String description) {
        String lowerCaseDescription = description.toLowerCase();

        for (Map.Entry<TransactionCategory, Set<String>> entry : CATEGORY_KEYWORDS.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (lowerCaseDescription.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }

        return TransactionCategory.OTHERS; // Default category if no match found
    }
}

