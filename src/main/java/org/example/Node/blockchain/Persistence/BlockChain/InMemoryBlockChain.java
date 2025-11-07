package org.example.Node.blockchain.Persistence.BlockChain;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class InMemoryBlockChain implements IBlockChainRepository{

    private List<Block> blockChain = new ArrayList<>();

    public InMemoryBlockChain(){}

    public void setBlockChain(List<Block> blockChain){
        this.blockChain = blockChain;
    }

    @Override
    public List<Block> getAllBLocks() {
        return blockChain;
    }

    @Override
    public Block getLastBlock() {
        return blockChain.getLast();
    }

    @Override
    public void addBlockToChain(Block block) {
        IO.println("Adding block!");
        blockChain.add(block);
    }

    @Override
    public int getBalance(PublicKey wallet) {
        var balance = 0;
        for (Block block : blockChain){
            for (Transaction transaction : block.transactions){
                if (transaction.receiver != null && transaction.receiver.equals(wallet)) {
                    balance += transaction.amount;
                    continue;
                }
                if (transaction.emitter != null && transaction.emitter.equals(wallet)) {
                    balance -= transaction.amount;
                }
            }
        }
        return balance;
    }
}
