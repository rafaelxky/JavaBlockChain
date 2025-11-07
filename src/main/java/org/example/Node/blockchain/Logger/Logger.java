package org.example.Node.blockchain.Logger;

import org.example.Node.blockchain.Chain.Chain;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;

public class Logger implements ILogger{

    @Override
    public void logBlock(Block block) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("Block: \n");
        for (Transaction transaction : block.getTransactions()){
            stringBuilder.append("Transaction: \n");
            stringBuilder.append(transaction);
            stringBuilder.append("\n");
            stringBuilder.append("Previous hash: \n");
            stringBuilder.append(block.getPreviousHash());
            stringBuilder.append("\n");
        }
        IO.println(stringBuilder.toString());
    }

    @Override
    public void logTransaction(Transaction transaction) {
        String stringBuilder = "emitter: \n" +
                String.valueOf(transaction.emitter) +
                "\n receiver: \n" +
                String.valueOf(transaction.receiver) +
                "\n amount: \n" +
                String.valueOf(transaction.amount) +
                "\n";
        IO.println(stringBuilder);
    }

    @Override
    public void logChain(Chain chain){
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : chain.getAllBlocks()){
            stringBuilder.append(block);
            stringBuilder.append("\n");
        }
        IO.println(stringBuilder.toString());
    }

}
