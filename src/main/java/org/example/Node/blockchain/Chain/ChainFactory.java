package org.example.Node.blockchain.Chain;

import org.example.Node.blockchain.Genesis;
import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Persistence.BlockChain.InMemoryBlockChain;
import org.example.Node.blockchain.Persistence.TransactionPool.InMemoryTransactionPoolRepository;
import org.example.Node.blockchain.Services.ValidationServices.BlockValidator;
import org.example.Node.blockchain.Services.ValidationServices.TransactionValidator;

public class ChainFactory {

    public ChainFactory(){
        
    }

    public Chain newInMemoryChain() {
        // just get and set no validation and no dependencies, inpure
        var blockChainRepo = new InMemoryBlockChain();
        var transactionPoolRepo = new InMemoryTransactionPoolRepository();

        // pure validation
        var transactionValidator = new TransactionValidator();
        var blockValidator = new BlockValidator(transactionValidator);

        var miner = new Miner(blockValidator);

        var chain = new Chain(transactionPoolRepo, blockChainRepo, transactionValidator, blockValidator, miner);

        blockChainRepo.addBlockToChain(Genesis.createBlock());

        return chain;
    }
}
