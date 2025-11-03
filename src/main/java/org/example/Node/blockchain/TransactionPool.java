package org.example.Node.blockchain;

import org.example.Utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class TransactionPool {
    public List<Transaction> transactions = new ArrayList<>();

    public TransactionPool() {

    }

    public List<Transaction> get(int amount) {
        var curated_amount =  Math.min(amount, transactions.size());
        List<Transaction> list = new ArrayList<>(this.transactions.subList(0, curated_amount));
        this.transactions.subList(0, curated_amount).clear();
        return list;
    }

    public void addTransaction(Transaction transaction) {
        if (Validation.isTransactionValid(transaction)) {
            this.transactions.add(transaction);
        }
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("Block: \n");
        for (Transaction transaction : transactions) {
            stringBuilder.append("Transaction: \n");
            stringBuilder.append(transaction);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
