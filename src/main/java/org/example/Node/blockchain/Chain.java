package org.example.Node.blockchain;

import org.example.Utils.Validation;
import org.example.Utils.rsa.RsaGeneration;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Chain {
    public static int TRANSACTION_LIMIT = 20;
    public List<Block> blockChain = new ArrayList<>();
    public TransactionPool transactionPool = new TransactionPool();

    public Chain(){
        blockChain.add(Genesis.createBlock());
    }

    public Block newBlock(){
        return new Block(transactionPool.get(TRANSACTION_LIMIT), blockChain.getLast().hash);
    }

    public boolean addTransaction(Transaction transaction){
        if (!Validation.isTransactionValid(transaction)) {
            return false;
        }
        if (Validation.isTransactionInChain(transaction, blockChain)){
            return false;
        }
        if (Validation.isTransactionInPool(transaction, transactionPool)){
            return false;
        }
        transactionPool.addTransaction(transaction);
        return true;
    }

    public boolean addBlock(Block block){
        IO.println("Trying to add block");
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
