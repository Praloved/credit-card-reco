package org.project.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Transaction {
    private LocalDate date;
    private String description;
    private TransactionCategory category;
    private double amount;
    private String currency;
    private TransactionType type;
}
