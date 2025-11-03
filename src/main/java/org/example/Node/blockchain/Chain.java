package org.example.Node.blockchain;

import org.example.Utils.Validation;
import org.example.Utils.rsa.RsaGeneration;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Chain {
    // todo: mining reward
    public static int TRANSACTION_LIMIT = 20;
    public List<Block> blockChain = new ArrayList<>();
    public TransactionPool transactionPool = new TransactionPool();

    public Chain(){
        blockChain.add(Genesis.createBlock());
    }

    public Block getNewBlock(){
        IO.println("creating new Block");
        return new Block(transactionPool.get(TRANSACTION_LIMIT), blockChain.getLast().hash);
    }

    public boolean addTransactionToPool(Transaction transaction){
        IO.println("Adding transaction to pool!");
        if (checkTransactionAgainstBlockChain(transaction)) {
            IO.println("Transaction added!");
            transactionPool.addTransaction(transaction);
            return true;
        }
        return false;
    }

    public boolean checkTransactionAgainstBlockChain(Transaction transaction){
        IO.println("Checking transaction against blockchain");
        if (transaction.receiver.equals(transaction.emitter)){
            IO.println("transaction invalid: emitter and receiver are equal");
            return false;
        }
        if (!Validation.isTransactionHashValid(transaction)) {
            IO.println("transaction invalid: hash mismatch");
            return false;
        }
        if (transaction.amount > getBalance(transaction.emitter)){
            IO.println("Transaction invalid: emitter doesn't have enough balance");
            return false;
        }
        if (Validation.isTransactionInChain(transaction, blockChain)){
            IO.println("Transaction invalid: repeated transaction in chain");
            return false;
        }
        if (Validation.isTransactionInPool(transaction, transactionPool)){
            IO.println("Transaction invalid: transaction already in pool");
            return false;
        }

        return true;
    }

    public boolean addBlockToChain(Block block){
        IO.println("Trying to add block - " + block);

        if (!Validation.isBlockValid(block, this)){
            IO.println("Block not valid");
            return false;
        }

        IO.println("Block valid");
        blockChain.add(block);
        return true;
    }

    public int getBalance(PublicKey account){
        var balance = 0;
        for (Block block : blockChain){
            for (Transaction transaction : block.transactions){
                if (transaction.receiver != null && transaction.receiver.equals(account)) {
                    balance += transaction.amount;
                    continue;
                }
                if (transaction.emitter != null && transaction.emitter.equals(account)) {
                    balance -= transaction.amount;
                }
            }
        }
        return balance;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : blockChain){
            stringBuilder.append(block);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
