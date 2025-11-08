package org.example.Node.blockchain.Services.ValidationServices;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;

import java.util.List;

// pure
public interface IBlockValidator {
    public boolean isBlockValid(
            Block block,
            String previousBlockHash,
            List<Transaction> transactionPool,
            List<Block> blockChain,
            List<Integer> emittersBalance
    );
    public String getBlockHash(Block block, Long nonce);
}
