package org.example.Node.blockchain.Persistence.TransactionPool;

import org.example.Node.blockchain.Models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionPoolRepository implements ITransactionPoolRepository{

    public List<Transaction> transactions = new ArrayList<>();

    public InMemoryTransactionPoolRepository(){

    }

    @Override
    public List<Transaction> getTransactions(int amount) {
        var curated_amount =  Math.min(amount, transactions.size());
        List<Transaction> list = new ArrayList<>(this.transactions.subList(0, curated_amount));
        this.transactions.subList(0, curated_amount).clear();
        return list;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (Validation.isTransactionHashValid(transaction)) {
            this.transactions.add(transaction);
        }
    }
}
