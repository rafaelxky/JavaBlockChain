package org.example.Node.blockchain.Chain;

import org.example.Node.blockchain.Genesis;
import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Persistence.BlockChain.IBlockChainRepository;
import org.example.Node.blockchain.Persistence.TransactionPool.ITransactionPoolRepository;
import org.example.Node.blockchain.Validation.IBlockValidator;
import org.example.Node.blockchain.Validation.ITransactionValidator;

import java.security.PublicKey;

public class Chain implements IChain{
    // todo: mining reward
    public static int TRANSACTION_LIMIT = 20;
    public IBlockChainRepository blockChainRepository;
    public ITransactionPoolRepository transactionPoolRepository;
    public ITransactionValidator transactionValidator;
    public IBlockValidator blockValidator;
    public Miner miner;

    public Chain(ITransactionPoolRepository transactionPoolRepository,
                 IBlockChainRepository blockChainRepository,
                 ITransactionValidator transactionValidator,
                 IBlockValidator blockValidator,
                 Miner miner
    ){
        this.blockChainRepository = blockChainRepository;
        this.transactionPoolRepository = transactionPoolRepository;
        this.transactionValidator = transactionValidator;
        this.blockValidator = blockValidator;
        blockChainRepository.addBlockToChain(Genesis.createBlock());
        this.miner = miner;
    }

    public Miner getMiner() {
        return miner;
    }

    public Block getNewBlock(){
        IO.println("creating new Block");
        var transactions = transactionPoolRepository.getTransactions(TRANSACTION_LIMIT);
        transactionPoolRepository.removeTransactions(TRANSACTION_LIMIT);
        var lastBlockHash = blockChainRepository.getLastBlock().getHash();
        return new Block(transactions, lastBlockHash);
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

        if (!blockValidator.isBlockValid(block)){
            IO.println("Block not valid");
            return false;
        }

        IO.println("Block valid");
        blockChainRepository.addBlockToChain(block);
        return true;
    }

    public void addGenesisBlock(Block block){
        blockChainRepository.addBlockToChain(block);
    }

    public int getBalance(PublicKey account){
        return blockChainRepository.getBalance(account);
    }
}
