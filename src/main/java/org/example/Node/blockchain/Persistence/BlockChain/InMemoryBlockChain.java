package org.example.Node.blockchain.Persistence.BlockChain;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Validation.IBlockValidator;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class InMemoryBlockChain implements IBlockChainRepository{

    public List<Block> blockChain = new ArrayList<>();
    public IBlockValidator blockValidator;

    public InMemoryBlockChain(){}

    public void setBlockChain(List<Block> blockChain){
        this.blockChain = blockChain;
    }

    public void setBlockValidator(IBlockValidator blockValidator) {
        this.blockValidator = blockValidator;
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
    public boolean addBlockToChain(Block block) {
        IO.println("Trying to add block - " + block);

        if (!blockValidator.isBlockValid(block)){
            IO.println("Block not valid");
            return false;
        }

        IO.println("Block valid");
        blockChain.add(block);
        return true;

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
