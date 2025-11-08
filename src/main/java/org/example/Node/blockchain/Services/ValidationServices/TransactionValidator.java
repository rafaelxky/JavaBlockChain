package org.example.Node.blockchain.Services.ValidationServices;

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

// pure
public class TransactionValidator implements ITransactionValidator{

    public TransactionValidator(){}

    private boolean isEmitterEqualToReceiver(PublicKey emitter, PublicKey receiver){
       return emitter.equals(receiver);
    }

    private boolean isTransactionHashValid(Transaction transaction){
        var message = Utf8.stringToBytes(transaction.getData());
        var signature = transaction.hash;
        var key = transaction.emitter;
        return RsaEncryption.rsaVerify(message, signature, key);
    }

    private boolean userHasSufficientFunds(int amount, int emitterBalance){
        return amount <= emitterBalance;
    }
    private boolean isTransactionRepeatedInBlockChain(Transaction transaction, List<Block> blockChain){
        for (Block block : blockChain){
            for (Transaction transactionInChain : block.getTransactions()){
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

    @Override
    public boolean areTransactionsValid(
            List<Transaction> transactions,
            List<Transaction> transactionsPool,
            List<Block> blocksChain,
            List<Integer> emittersBalance){
        for (int i = 0; i < transactions.size(); i++) {
            if (!isTransactionValid(transactions.get(i), transactionsPool, blocksChain, emittersBalance.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isTransactionValid(
            Transaction transaction,
            List<Transaction> transactionPool,
            List<Block> blockChain,
            int emitterBalance
            ) {

        IO.println("Checking transaction against blockchain");
        if (isEmitterEqualToReceiver(transaction.emitter, transaction.receiver)){
            IO.println("transaction invalid: emitter and receiver are equal");
            return false;
        }
        if (!isTransactionHashValid(transaction)){
            IO.println("transaction invalid: hash mismatch");
            return false;
        }
        if (!userHasSufficientFunds(transaction.amount, emitterBalance)){
            IO.println("Transaction invalid: emitter doesn't have enough balance!");
            IO.println("Expected " + transaction.amount + " has " + emitterBalance);
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
