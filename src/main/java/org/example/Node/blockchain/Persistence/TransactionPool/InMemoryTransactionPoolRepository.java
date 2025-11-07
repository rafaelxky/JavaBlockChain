package org.example.Node.blockchain.Persistence.TransactionPool;

import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Services.ValidationServices.ITransactionValidator;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionPoolRepository implements ITransactionPoolRepository{

    private List<Transaction> transactions;
    private ITransactionValidator transactionValidator;

    public InMemoryTransactionPoolRepository(){}

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setTransactionValidator(ITransactionValidator transactionValidator) {
        this.transactionValidator = transactionValidator;
    }

    @Override
    public List<Transaction> getTransactions(int amount) {
        var curated_amount =  Math.min(amount, transactions.size());
        return new ArrayList<>(this.transactions.subList(0, curated_amount));
    }

    @Override
    public void removeTransactions(int amount){
        var curated_amount =  Math.min(amount, transactions.size());
        this.transactions.subList(0, curated_amount).clear();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (transactionValidator.isTransactionValid(transaction)) {
            this.transactions.add(transaction);
        }
    }
}
