package org.example.Node.blockchain;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Persistence.BlockChain.IBlockChainRepository;
import org.example.Node.blockchain.Persistence.TransactionPool.ITransactionPoolRepository;
import org.example.Node.blockchain.Validation.IBlockValidator;
import org.example.Node.blockchain.Validation.ITransactionValidator;

import java.security.PublicKey;

public class Chain {
    // todo: mining reward
    public static int TRANSACTION_LIMIT = 20;
    public IBlockChainRepository blockChainRepository;
    public ITransactionPoolRepository transactionPoolRepository;
    public ITransactionValidator transactionValidator;
    public IBlockValidator blockValidator;

    public Chain(ITransactionPoolRepository transactionPoolRepository,
                 IBlockChainRepository blockChainRepository,
                 ITransactionValidator transactionValidator,
                 IBlockValidator blockValidator
    ){
        this.blockChainRepository = blockChainRepository;
        this.transactionPoolRepository = transactionPoolRepository;
        this.transactionValidator = transactionValidator;
        this.blockValidator = blockValidator;
        blockChainRepository.addBlockToChain(Genesis.createBlock());
    }

    public Block getNewBlock(){
        IO.println("creating new Block");
        return new Block(transactionPoolRepository.getTransactions(TRANSACTION_LIMIT), blockChainRepository.getLastBlock().getHash());
    }

    public boolean addTransactionToPool(Transaction transaction){
        IO.println("Adding transaction to pool!");
        if (transactionValidator.isTransactionValid(transaction)) {
            IO.println("Transaction added!");
            transactionPoolRepository.addTransaction(transaction);
            return true;
        }
        return false;
    }

    public boolean addBlockToChain(Block block){
        IO.println("Trying to add block - " + block);

        if (blockValidator.isBlockValid(block)){
            IO.println("Block not valid");
            return false;
        }

        IO.println("Block valid");
        blockChainRepository.addBlockToChain(block);
        return true;
    }

    public int getBalance(PublicKey account){
        return blockChainRepository.getBalance(account);
    }
}
