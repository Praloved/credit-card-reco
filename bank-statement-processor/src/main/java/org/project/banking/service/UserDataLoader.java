package org.project.banking.service;

import com.google.inject.Singleton;
import org.project.banking.model.Transaction;
import org.project.banking.model.UserTransactions;
import org.project.banking.utils.BankStatementParser;

import java.io.InputStream;
import java.util.List;

@Singleton
public class UserDataLoader {

    public UserTransactions processUserData(String userId, InputStream fileContentStream) throws Exception {
        List<Transaction> transactions = BankStatementParser.parseFileContentStream(fileContentStream);
        return new UserTransactions(userId, transactions);
    }
}
