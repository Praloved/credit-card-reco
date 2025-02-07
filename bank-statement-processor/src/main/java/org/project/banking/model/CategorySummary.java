package org.project.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategorySummary {
    private String category;
    private double totalSpent;
    private int transactionCount;

    public void addTransaction(double amount) {
        this.totalSpent += amount;
        this.transactionCount++;
    }
}
