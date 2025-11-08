package org.example.Node.blockchain.Persistence.BlockChain;

import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;

import java.security.PublicKey;
import java.util.List;

// impure
public interface IBlockChainRepository {
    List<Block> getAllBLocks();
    Block getLastBlock();
    void addBlockToChain(Block block);
    int getBalance(PublicKey wallet);
    List<Integer> getBalance(List<Transaction> transactions);
}
