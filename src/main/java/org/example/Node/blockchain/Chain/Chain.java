package org.example.Node.blockchain.Chain;

import org.example.Node.blockchain.Miner;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Node.blockchain.Persistence.BlockChain.IBlockChainRepository;
import org.example.Node.blockchain.Persistence.TransactionPool.ITransactionPoolRepository;
import org.example.Node.blockchain.Services.ValidationServices.IBlockValidator;
import org.example.Node.blockchain.Services.ValidationServices.ITransactionValidator;

import java.security.PublicKey;
import java.util.List;

public class Chain implements IChain{
    // todo: mining reward
    // impure
    public static int TRANSACTION_LIMIT = 20;
    private final IBlockChainRepository blockChainRepository;
    private final ITransactionPoolRepository transactionPoolRepository;
    private final ITransactionValidator transactionValidator;
    private final IBlockValidator blockValidator;
    private final Miner miner;

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
        this.miner = miner;
    }

    public Miner getMiner() {
        return miner;
    }

    public List<Block> getAllBlocks(){
        return this.blockChainRepository.getAllBLocks();
    }

    // handles side-effects
    public Block getNewBlock(){
        IO.println("creating new Block");
        var lastBlockHash = blockChainRepository.getLastBlock().getHash();
        var transactions = transactionPoolRepository.pollTransactions(TRANSACTION_LIMIT);
        IO.println("Last block hash - " + lastBlockHash);
        return createNewBlock(transactions, lastBlockHash);
    }

    // handles just pure logic
    private Block createNewBlock(List<Transaction> transactions, String lastBlockHash){
        return new Block(transactions, lastBlockHash);
    }

    public boolean addTransactionToPool(Transaction transaction){
        IO.println("Adding transaction to pool!");
        if (transactionValidator.isTransactionValid(
                    transaction,
                    transactionPoolRepository.getTransactions(TRANSACTION_LIMIT),
                    blockChainRepository.getAllBLocks(),
                    blockChainRepository.getBalance(transaction.emitter)
                )
        ) {
            IO.println("Transaction added!");
            transactionPoolRepository.addTransaction(transaction);
            return true;
        }
        return false;
    }

    public String getLastBlockHash(){
        var lastHash = blockChainRepository.getLastBlock().getHash();
        IO.println("Getting last block hash - " + lastHash);
        return lastHash;
    }

    public boolean addBlockToChain(Block block){
        IO.println("Trying to add block - " + block);

        if (!blockValidator.isBlockValid(
                block,
                getLastBlockHash(),
                transactionPoolRepository.getTransactions(TRANSACTION_LIMIT),
                blockChainRepository.getAllBLocks(),
                blockChainRepository.getBalance(block.getTransactions())
                )){
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
