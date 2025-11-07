package org.example.Node.blockchain.Validation;

import org.example.Node.blockchain.Chain.Chain;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Persistence.BlockChain.IBlockChainRepository;
import org.example.Node.blockchain.Persistence.TransactionPool.ITransactionPoolRepository;
import org.example.Node.blockchain.Models.Transaction;
import org.example.Utils.Bytes.Utf8;
import org.example.Utils.rsa.RsaEncryption;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

public class TransactionValidator implements ITransactionValidator{

    private IBlockChainRepository blockChainRepository;
    private ITransactionPoolRepository transactionPoolRepository;

    public TransactionValidator(){}

    public void setBlockChainRepository(IBlockChainRepository blockChainRepository){
        this.blockChainRepository = blockChainRepository;
    }
    public void setTransactionPoolRepository(ITransactionPoolRepository transactionPoolRepository){
        this.transactionPoolRepository = transactionPoolRepository;
    }

    private boolean isEmitterEqualToReceiver(PublicKey emitter, PublicKey receiver){
       return emitter.equals(receiver);
    }

    private boolean isTransactionHashValid(Transaction transaction){
        var message = Utf8.stringToBytes(transaction.getData());
        var signature = transaction.hash;
        var key = transaction.emitter;
        return RsaEncryption.rsaVerify(message, signature, key);
    }

    private boolean userHasSufficientFunds(int amount, PublicKey emitter){
        return amount > blockChainRepository.getBalance(emitter);
    }
    private boolean isTransactionRepeatedInBlockChain(Transaction transaction, List<Block> blockChain){
        for (Block block : blockChain){
            for (Transaction transactionInChain : block.transactions){
                if (Arrays.equals(transaction.hash, transactionInChain.hash)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isTransactionRepeatedInTransactionPool(Transaction transaction, List<Transaction> transactionPool){
        for (Transaction transaction_from_pool : transactionPool){
            if (Arrays.equals(transaction.hash, transaction_from_pool.hash)){
                return true;
            }
        }
        return false;
    }

    public boolean areTransactionsValid(List<Transaction> transactions){
        for (Transaction transaction : transactions){
            if (!isTransactionValid(transaction)){
                return false;
            }
        }
        return true;
    }

    public boolean isTransactionValid(Transaction transaction) {
        // replace 20 later
        var transactionPool = transactionPoolRepository.getTransactions(Chain.TRANSACTION_LIMIT);
        var blockChain = blockChainRepository.getAllBLocks();

        IO.println("Checking transaction against blockchain");
        if (isEmitterEqualToReceiver(transaction.emitter, transaction.receiver)){
            IO.println("transaction invalid: emitter and receiver are equal");
            return false;
        }
        if (!isTransactionHashValid(transaction)){
            IO.println("transaction invalid: hash mismatch");
            return false;
        }
        if (!userHasSufficientFunds(transaction.amount, transaction.emitter)){
            IO.println("Transaction invalid: emitter doesn't have enough balance");
            return false;
        }
        if (isTransactionRepeatedInBlockChain(transaction, blockChain)){
            IO.println("Transaction invalid: repeated transaction in chain");
            return false;
        }
        if (isTransactionRepeatedInTransactionPool(transaction, transactionPool)){
            IO.println("Transaction invalid: transaction already in pool");
            return false;
        }

        return true;
    }
}
