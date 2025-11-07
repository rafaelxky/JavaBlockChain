package org.example.Node.blockchain.Models;

import java.util.List;

public class Block {
    public List<Transaction> transactions;
    public String previousHash;
    public String hash;
    public Long nonce;

    public Block(List<Transaction> pool, String previousHash){
        IO.println("BLock constructor called, previous hash - " + previousHash);
        this.transactions = List.copyOf(pool);
        this.previousHash = previousHash;
    }

    public List<Transaction> getTransactions(){
        return this.transactions;
    }
    public String getPreviousHash(){
        return this.previousHash;
    }
    public String getHash(){
        return this.previousHash;
    }
    public void setHash(String hash){
        this.hash = hash;
    }
    public void setPreviousHash(String previousHash){
        this.previousHash = previousHash;
    }
    public Long getNonce(){
        return this.nonce;
    }

    public String getData(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction transaction : transactions) {
            stringBuilder.append(transaction.toString());
        }
        stringBuilder.append(previousHash);
        return stringBuilder.toString();
    }
}
