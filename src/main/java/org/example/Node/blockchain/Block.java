package org.example.Node.blockchain;

import org.example.Utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class Block {
    public List<Transaction> transactions;
    public String previousHash;
    public String hash;
    public Long nonce;

    public Block(List<Transaction> pool, String previousHash){
        IO.println("BLock constructor called, previous hash - " + previousHash);
        // hash is ok here
        transactions = pool;
        this.previousHash = previousHash;
    }

    public String getData(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction transaction : transactions) {
            stringBuilder.append(transaction.toString());
        }
        stringBuilder.append(previousHash);
        return stringBuilder.toString();
    }

    @Override
    public String toString(){
        var stringBuilder = new StringBuilder();
        stringBuilder.append("Block: \n");
        for (Transaction transaction : transactions){
            stringBuilder.append("Transaction: \n");
            stringBuilder.append(transaction);
            stringBuilder.append("\n");
            stringBuilder.append("Previous hash: \n");
            stringBuilder.append(previousHash);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
