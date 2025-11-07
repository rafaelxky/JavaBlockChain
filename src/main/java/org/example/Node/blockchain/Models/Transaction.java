package org.example.Node.blockchain.Models;

import java.security.PublicKey;

public class Transaction {

    public PublicKey emitter;
    public PublicKey receiver;
    public int amount;
    public int nonce;
    public byte[] hash;

    public Transaction(PublicKey emitter, PublicKey receiver, int amount, int nonce){
        this.emitter = emitter;
        this.receiver = receiver;
        this.amount = amount;
        this.nonce = nonce;
    }

    public void sign(byte[] signature){
        this.hash = signature;
    }

    public String getData(){
        return String.valueOf(emitter) +
                String.valueOf(receiver) +
                String.valueOf(amount) +
                String.valueOf(nonce);
    }
}
