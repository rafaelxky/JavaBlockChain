package org.example.Node.blockchain.Persistence.TransactionPool;

import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Services.ValidationServices.ITransactionValidator;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionPoolRepository implements ITransactionPoolRepository{

    private final List<Transaction> transactions = new ArrayList<>();

    public InMemoryTransactionPoolRepository(){}

    @Override
    public List<Transaction> getTransactions(int amount) {
        var curated_amount =  Math.min(amount, transactions.size());
        return new ArrayList<>(this.transactions.subList(0, curated_amount));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    @Override
    public List<Transaction> pollTransactions(int amount) {
        var transactions = getTransactions(amount);
        removeTransactions(amount);
        return transactions;
    }

    @Override
    public void removeTransactions(int amount){
        var curated_amount =  Math.min(amount, transactions.size());
        this.transactions.subList(0, curated_amount).clear();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
