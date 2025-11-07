package org.example.Node.blockchain.Persistence.BlockChain;

import org.example.Node.blockchain.Models.Block;

import java.security.PublicKey;
import java.util.List;

public interface IBlockChainRepository {
    public List<Block> getAllBLocks();
    public Block getLastBlock();
    public boolean addBlockToChain(Block block);
    public int getBalance(PublicKey wallet);
}
