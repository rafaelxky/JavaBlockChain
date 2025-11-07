package org.example.Node.blockchain.Services.ValidationServices;

import org.example.Node.blockchain.Models.Transaction;

import java.util.List;

public interface ITransactionValidator {
    boolean isTransactionValid(Transaction transaction);
    boolean areTransactionsValid(List<Transaction> transactions);
}
