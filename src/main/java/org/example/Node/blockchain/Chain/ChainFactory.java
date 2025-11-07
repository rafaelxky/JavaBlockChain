package org.example.Node.blockchain.Chain;

import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Persistence.BlockChain.InMemoryBlockChain;
import org.example.Node.blockchain.Persistence.TransactionPool.InMemoryTransactionPoolRepository;
import org.example.Node.blockchain.Validation.BlockValidator;
import org.example.Node.blockchain.Validation.TransactionValidator;

import java.util.ArrayList;
import java.util.List;

public class ChainFactory {

    public ChainFactory(){
        
    }

    public Chain newInMemoryChain(){
        List<Block> blockChain = new ArrayList<>();
        List<Transaction> transactionPool = new ArrayList<>();
        var transactionPoolRepo = new InMemoryTransactionPoolRepository();
        var transactionValidator = new TransactionValidator();
        var blockValidator = new BlockValidator();
        var blockChainRepo = new InMemoryBlockChain();
        var miner = new Miner();

        transactionValidator.setTransactionPoolRepository(transactionPoolRepo);
        transactionValidator.setBlockChainRepository(blockChainRepo);

        transactionPoolRepo.setTransactionValidator(transactionValidator);
        transactionPoolRepo.setTransactions(transactionPool);

        blockValidator.setTransactionValidator(transactionValidator);
        blockValidator.setBlockChainRepository(blockChainRepo);

        blockChainRepo.setBlockChain(blockChain);

        miner.setBlockValidator(blockValidator);

        return new Chain(
                transactionPoolRepo,
                blockChainRepo,
                transactionValidator,
                blockValidator,
                miner
        );
    }
}
