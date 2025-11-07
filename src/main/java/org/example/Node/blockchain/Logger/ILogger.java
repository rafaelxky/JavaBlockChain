package org.example.Node.blockchain.Logger;

import org.example.Node.blockchain.Chain;
import org.example.Node.blockchain.Models.Block;
import org.example.Node.blockchain.Models.Transaction;

public interface ILogger {
    public void logBlock(Block block);
    public void logTransaction(Transaction transaction);
    public void logChain(Chain chain);
}
