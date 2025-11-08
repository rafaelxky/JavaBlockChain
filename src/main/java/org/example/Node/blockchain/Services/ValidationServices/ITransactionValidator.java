package org.example.Node.blockchain.Services.ValidationServices;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;

import java.util.List;

// pure
public interface ITransactionValidator {
    boolean isTransactionValid(
            Transaction transaction,
            List<Transaction> transactionPool,
            List<Block> blockChain,
            int emitterBalance
    );

    boolean areTransactionsValid(
            List<Transaction> transactions,
            List<Block> blocksChain,
            List<Integer> emittersBalance
    );
}
