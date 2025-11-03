package org.example.Node.blockchain;

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(emitter));
        stringBuilder.append(String.valueOf(receiver));
        stringBuilder.append(String.valueOf(amount));
        stringBuilder.append(String.valueOf(nonce));
        return stringBuilder.toString();
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("emitter: \n");
        stringBuilder.append(String.valueOf(emitter));
        stringBuilder.append("\n receiver: \n");
        stringBuilder.append(String.valueOf(receiver));
        stringBuilder.append("\n amount: \n");
        stringBuilder.append(String.valueOf(amount));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
