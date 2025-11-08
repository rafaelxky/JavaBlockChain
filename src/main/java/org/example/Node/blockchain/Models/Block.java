package org.example.Node.blockchain.Models;

import java.util.List;

public class Block {
    private final List<Transaction> transactions;
    private final String previousHash;
    private String hash;
    private Long nonce;


    public Block(List<Transaction> pool, String previousHash){
        IO.println("Block constructor called, previous hash - " + previousHash);
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
        return this.hash;
    }
    public void setHash(String hash){
        this.hash = hash;
    }
    public Long getNonce(){
        return this.nonce;
    }
    public void setNonce(Long nonce) {
        this.nonce = nonce;
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
