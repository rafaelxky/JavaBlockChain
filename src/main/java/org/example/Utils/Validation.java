package org.example.Utils;

import org.example.Node.blockchain.*;
import org.example.Utils.Bytes.Base64;
import org.example.Utils.Bytes.Utf8;
import org.example.Utils.Sha.Sha;
import org.example.Utils.rsa.RsaEncryption;

import java.util.Arrays;
import java.util.List;

public class Validation {
    public static boolean isTransactionValid(Transaction transaction){
        if (transaction.receiver.equals(transaction.emitter)){
            return false;
        }
        var message = Utf8.stringToBytes(transaction.getData());
        var signature = transaction.hash;
        var key = transaction.emitter;
        return RsaEncryption.rsaVerify(message, signature, key);
    }
    public static boolean areTransactionsValid(List<Transaction> transactions){
        for (Transaction transaction : transactions){
            if (!isTransactionValid(transaction)){
                return false;
            }
        }
        return true;
    }

    public static boolean isBlockValid(Block block, Chain chain){

        if (!areTransactionsValid(block.transactions)){
            IO.println("block hash invalid");
            return false;
        }
        IO.println("transactions valid");

        if (!isBlockHashValid(block)){
            IO.println("block hash invalid");
            return false;
        }
        IO.println("block hash valid");

        for (Transaction transaction : block.transactions){
            if (isTransactionInChain(transaction, chain.blockChain)){
                return false;
            }
            if (isTransactionInPool(transaction, chain.transactionPool)){
                return false;
            }
        }
        return true;
    }

    public static boolean isBlockHashValid(Block block){
        return getBlockHash(block).startsWith("0".repeat(Miner.DIFFICULTY));
    }

    public static String getBlockHash(Block block){
        var blockHash = Base64.base64BytesToString(Sha.sha256Encrypt(Utf8.stringToBytes(block.getData() + block.nonce)));
        IO.println("blockHash - " + blockHash);
        return blockHash;
    }

    public static boolean isTransactionInChain(Transaction transaction, List<Block> blockChain) {
        for (Block block : blockChain){
            for (Transaction transaction_in_chain : block.transactions){
                if (Arrays.equals(transaction.hash, transaction_in_chain.hash)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isTransactionInPool(Transaction transaction, TransactionPool transactionPool) {
        for (Transaction transaction_from_pool : transactionPool.transactions){
            if (Arrays.equals(transaction.hash, transaction_from_pool.hash)){
                return true;
            }
        }
        return false;
    }
}
