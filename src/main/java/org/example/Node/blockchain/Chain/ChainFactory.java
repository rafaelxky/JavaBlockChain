package org.example.Node.blockchain.Chain;

import org.example.Node.blockchain.Genesis;
import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Persistence.BlockChain.InMemoryBlockChain;
import org.example.Node.blockchain.Persistence.TransactionPool.InMemoryTransactionPoolRepository;
import org.example.Node.blockchain.Services.ValidationServices.BlockValidator;
import org.example.Node.blockchain.Services.ValidationServices.TransactionValidator;

import java.util.ArrayList;
import java.util.List;

public class ChainFactory {

    public ChainFactory(){
        
    }


    public Chain newInMemoryChain() {
        // just get and set no validation and no dependencies, inpure
        var blockChainRepo = new InMemoryBlockChain();
        var transactionPoolRepo = new InMemoryTransactionPoolRepository();

        // pure validation
        var transactionValidator = new TransactionValidator(blockChainRepo, transactionPoolRepo);
        var blockValidator = new BlockValidator(transactionValidator, blockChainRepo);

        var miner = new Miner(blockValidator);

        var chain = new Chain(transactionPoolRepo, blockChainRepo, transactionValidator, blockValidator, miner);

        blockChainRepo.addBlockToChain(Genesis.createBlock());

        return chain;
    }
}
