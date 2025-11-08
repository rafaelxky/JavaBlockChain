package org.example.Node.blockchain.Persistence.TransactionPool;

import org.example.Node.blockchain.Models.Transaction;

import java.util.List;

// impure
public interface ITransactionPoolRepository {
    public List<Transaction> getTransactions(int amount);
    public List<Transaction> pollTransactions(int amount);
    public void addTransaction(Transaction transaction);
    public void removeTransactions(int amount);
}
