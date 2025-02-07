package org.project.banking.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.project.banking.model.Transaction;
import org.project.banking.model.TransactionCategory;
import org.project.banking.model.TransactionType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankStatementParser {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<Transaction> parseFileContentStream(InputStream fileContentStream) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileContentStream));
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] data = line.split(",");
            LocalDate date = LocalDate.parse(data[0], DATE_FORMAT);
            String description = data[1];
            TransactionCategory category = categorizeTransaction(description);
            String amountStr = data[2];

            String currency = detectCurrency(amountStr); // Detect the currency
            double amount = Double.parseDouble(amountStr.replaceAll("[^\\d.]", ""));
            TransactionType type = TransactionType.valueOf(data[3].toUpperCase());

            transactions.add(Transaction.builder().date(date)
                    .amount(amount)
                    .currency(currency)
                    .category(category)
                    .description(description)
                    .type(type)
                    .build());
        }
        br.close();
        return transactions;
    }

    private static TransactionCategory categorizeTransaction(String description) {
        return TransactionCategorizer.categorizeTransaction(description);
    }

    private static String detectCurrency(String amountStr) {
        if (amountStr.contains("Rs") || amountStr.contains("₹")) {
            return "INR";
        } else if (amountStr.contains("$")) {
            return "USD";
        } else if (amountStr.contains("€")) {
            return "EUR";
        } else {
            return "INR";
        }
    }
}
